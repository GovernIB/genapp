package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.MultipleUnique;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.generator.CodeGenUtils;
import org.fundaciobit.genapp.generator.gui.SharedData.ProjectType;



/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */

public class Panel_5_StoreProject extends Paneable {
  
  private final Logger log = Logger.getLogger(getClass());
  
  /**
   * 
   */
  private static final long serialVersionUID = 3385481401735441167L;
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel etiFinal = new JLabel();

  public Panel_5_StoreProject()
  {
    try
    {
      jbInit();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception
  {
    etiFinal.setFont(new java.awt.Font("Dialog", 1, 18));
    etiFinal.setHorizontalAlignment(SwingConstants.CENTER);
    etiFinal.setText("Final. Push button <<Final>> to store project.");
    this.setBackground(UIManager.getColor("List.selectionBackground"));
    this.setLayout(borderLayout1);
    this.add(etiFinal, BorderLayout.CENTER);
  }

  public boolean next() {
       return(false);
  }

  /** Indica si desde el siguienete panel podemos volver otra vez a este */
  public boolean previus()
  {
     return(true);
  }

  public boolean isFinalizable()
  {
    return(true);
  }

  public boolean initPanel()
  { return(true); }
  
  
  public boolean finish() {

    
    if (SharedData.project == ProjectType.NEW) {
    
      JFileChooser fileChooser = new JFileChooser();
      
      fileChooser.setSelectedFile(new File(".\\" + SharedData.data.projectName +".genapp"));
      
      if( fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
        return true;
      }
  
      SharedData.projectFile = fileChooser.getSelectedFile();
      
      RebApp.saveLastFile(SharedData.projectFile);
      RebApp.saveLastConfig();
    }
    
    String[] idiomes = SharedData.data.getLanguages();
    
    log.info(" Checks ...");
    long now = System.currentTimeMillis();
    
    // TODO Comprovar javaName duplicats o shortNames duplicats
    
    
    // Eliminar descripcions buides i noms java amb espais
    // i Limitar noms de camps i taules de BBDD a 30 chars
    for (TableInfo table : SharedData.data.tables) {
      // Limitar noms taules de BBDD a 30 chars
      if (table.name.length() > 30) {
        System.err.println("El nom sql (" + table.getName() + ") de la taula " + table.nameJava
            + " supera els 30 caracters de longitud");
      }
      
      String shortStr =  table.getShortName();
      if (shortStr == null) {
        shortStr = table.getNameJava().toLowerCase();
        table.setShortName(shortStr);
      }
      
      if (shortStr.length() > 10) {
        System.err.println("El camp shortname de la taula " + table.getNameJava() 
            + " ha de tenir longitud de 10 o menys caracters (" + shortStr + ")");
      }
      
      int countPKs = 0;
      for (FieldInfo field : table.getFields()) {
        if (field.isPrimaryKey()) {
          countPKs ++;
        }
      }
      
      /*
      {
        Map<String, String> labels = table.getLabels();
        Map<String, String> labelsPlural = table.getLabelsPlural();
        
        for(String key : labels.keySet()) {
          labelsPlural.put(key, labels.get(key) + "_XX_" + key);
        }
      }
      */
      

      // Check constraints de multiple uniques
      MultipleUnique[] multipleUniques = table.getMultipleUniques();
      if (multipleUniques != null /*&& multipleUniques.length == 1*/) {
        
        for (int i = 0; i < multipleUniques.length; i++) {
          
          String ukName = multipleUniques[i].getName();
 
          {
          
            String ukNameStartsExpected = SharedData.data.getPrefix() + "_" + shortStr 
            + "_"; 
        
            if (!ukName.toLowerCase().startsWith(ukNameStartsExpected.toLowerCase())
              || !ukName.toLowerCase().endsWith("_uk")) {
              
              if (ukName.length() > 30) {
                
                System.err.println("La Constraint unique " + ukName + " de la taula " + table.nameJava
                     + " té un tamany de més de 30 caracters");
              } else {
                System.err.println("La Constraint unique " + ukName + " de la taula " + table.nameJava
                    + " té un nom incorrecte.");
              }
              

              // TODO Error
              
              String sql = "ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + ukName + ";\n";
              
              sql = sql + "ALTER TABLE " + table.getName() + " ADD CONSTRAINT " + ukNameStartsExpected + "MULTIPLE_uk"
                  + " UNIQUE (";
              
              String[] cols = multipleUniques[i].getSqlColumns();
              for (int j = 0; j < cols.length; j++) {
                if (j != 0) {
                  sql = sql + ",";
                }
                sql = sql + cols[j];
              }
              
              sql = sql + ");\n";
              
              System.err.println(sql);

            }
          }
        }       
      }
      
      
      
      

      checkTraduccions(idiomes, table.getNameJava(), table.getLabels());
      checkTraduccions(idiomes, table.getNameJava() + "_plural", table.getLabelsPlural());
      
      Map<String, String> constraints = new HashMap<String, String>();
      
      
      for (FieldInfo field : table.getFields()) {
  
        
        String desc = field.getDescripcio();
        if (desc != null && desc.trim().length() == 0) {
          field.setDescripcio(null);
        }
        // Espais
        String name = field.getJavaName();
        if(name.indexOf(' ') != -1) {
          name = name.trim();
          if(name.indexOf(' ') != -1) {
            name = name.replace(' ', '_');            
          }
          field.setJavaName(name);
        }
        // Limitar noms de camps de BBDD a 30 chars
        if (field.sqlName.length() > 30) {
          System.err.println("El nom sql (" + field.sqlName + ")del camp " + field.javaName 
            + " de la taula " + table.nameJava
            + " supera els 30 caracters de longitud.");
        }

        // Check PK constarint
        String pk = field.getPK();
        if (pk != null) {
          if (pk.length() > 30) {
            System.err.println("-- El nom sql de la contraint PK (" + pk 
              + ") pel camp " + field.javaName 
              + " de la taula " + table.nameJava
              + " supera els 30 caracters de longitud.");
          }

          String expected = table.name + "_pk";
          if (expected.length() > 30) {
            expected = SharedData.data.getPrefix() + "_" + shortStr + "_pk";
          }
          if (!pk.equals(expected)) {
            String sql = "ALTER INDEX " + pk + " RENAME to " + expected + ";";
            System.err.println(" --  Error de forma en contraint Primary KEY \n " + sql);
          }
        }
        
        
        

        // Check UNIQUE Constraint
        String unique = field.getUnique();
        if (unique != null) {
          if (unique.length() > 30) {
            System.err.println("-- El nom sql de la contraint unique (" + unique 
              + ") pel camp " + field.javaName 
              + " de la taula " + table.nameJava
              + " supera els 30 caracters de longitud.");
          }
          
          String expectedPre = SharedData.data.getPrefix() + "_" + shortStr + "_";
          String expectedPost = "_uk";
          
          if (unique.startsWith(expectedPre) && unique.endsWith(expectedPost)) {
            // OK
          } else {
            
            String sql = "ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + unique + ";\n";
            sql = sql + "ALTER TABLE " + table.getName()
                + " ADD CONSTRAINT " + expectedPre + field.sqlName + expectedPost
                + " unique (" + field.sqlName + ");\n";
            
            System.err.println(" --  Error de forma en contraint unique\n " + sql);
            
          }
        }

        
        // Check Foreign Keys Constraint
        ForeignKey[] fkImport = field.getForeignKeysByType(ForeignKey.IMPORTED);
        if (fkImport != null && fkImport.length > 1) {
          System.err.println("");
          System.err.println("ForeignKey.IMPORTED [" +table.nameJava+ "." +field.getJavaName()+ " ]");
          System.err.println("");
        }
        
        for (ForeignKey fk : fkImport) {
          
          String fkName = fk.getName();
          // TODO optimitzar
          TableInfo expTable = CodeGenUtils.findTableInfoByTableSQLName(SharedData.data, fk.getTable());
          FieldInfo expField = CodeGenUtils.findFieldInfoByColumnSQLName(expTable, fk.getField());

          String fkNameStartsExpected = SharedData.data.getPrefix() + "_" + shortStr 
              + "_" + expTable.getShortName() + "_"; //fk";

          if (!fkName.toLowerCase().startsWith(fkNameStartsExpected.toLowerCase())
              || !fkName.toLowerCase().endsWith("_fk") || fkName.length() > 30) {
            
            if (fkName.length() > 30) {
              
              System.err.println("La ForeignKey " + fkName + " de la taula " + table.nameJava
                   + " té un tamany de més de 30 caracters");
              
            }

            System.err.println("La ForeignKey " + fkName + " de la taula " + table.nameJava
               + " no té un nom correcte (esperat " + fkNameStartsExpected + "*_fk )");
            {
              String newC =  fkNameStartsExpected + "fk";
              String sql = "ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + fkName + ";\n";
              sql = sql + "ALTER TABLE " + table.getName() + " ADD CONSTRAINT " + newC + " "
                  + " FOREIGN KEY (" + field.sqlName + ") "
                  + " REFERENCES " + expTable.name + " (" + expField.sqlName + ");\n";
              
             constraints.put(String.valueOf(System.nanoTime()) , sql);
            }
            
          }
        } 
        
        
        
       // Check INDEXES
        String index = field.getIndex();
        if (index == null) {
          // Revisar si es necessaria
          
          // Check PK: PK haurien de tenir INDEX
          if (field.isPrimaryKey()) {
            
            checkExpectedIndexPK(table, shortStr, field, countPKs, index);
            
          } 
          
          if (fkImport != null && fkImport.length != 0) {
            // Check FK: FK haurien de tenir INDEX
            System.err.println(" -- Es recomanable tenir un index de la clau forania.");
            
            String expected = getExpectedIndexFK(table, shortStr, field);

            String avis = (expected.length() > 30)? "**" : "";
            
            System.err.println(avis + " create index " + expected + " on " + table.name + " (" + field.sqlName + ");\n");

          }
          
        } else {

          // Format PK requerit per INDEX
          if (field.isPrimaryKey()) {
            checkExpectedIndexPK(table, shortStr,field,countPKs, index );
            
            
            
          } else if (fkImport != null && fkImport.length != 0) {
            // Format FK requerit per INDEX
            if ((index.startsWith(table.name + "_") || index.startsWith(SharedData.data.getPrefix() + "_" + shortStr + "_")) 
                && index.endsWith("_fk_i") && index.length() <= 30) {
              // OK
            } else {
              String expected = getExpectedIndexFK(table, shortStr, field);              
              System.err.println(" --  Error de forma en l'index FK  " + index + ((expected.length() > 30)?": més gran de 30 caracters" : ""));
              System.err.println(" ALTER INDEX " + index + " RENAME to " + expected + ";");
            }
            
          } else {          
            // format camp especific
            
            if ((index.startsWith(table.name + "_") || index.startsWith(SharedData.data.getPrefix() + "_" + shortStr + "_")) 
                && index.endsWith("_i") && index.length() <= 30) {
              // OK
            } else {
              String expected = table.name + "_" + field.sqlName +  "_i";
              if (expected.length() > 30) {
                expected = SharedData.data.getPrefix() + "_" + shortStr + "_" + field.sqlName + "_i";
              }
              String avis = (expected.length() > 30)? "**" : "";
              System.err.println(" --  Error de forma en l'index especific  " + index);
              System.err.println(avis + " ALTER INDEX " + index + " RENAME to " + expected + ";");
            }
            
          }
          
        }
        

        // Traduccions
        Map<String, String> labels = field.getLabels();
        for (String lang : idiomes) {
          String value = labels.get(lang);
          boolean showerror = false;
          if (value == null) {
            labels.put(lang, field.javaName + "_XX_" + lang);
            showerror = true;
          } else {
            if (value.endsWith("_XX_" + lang)) {
              showerror = true;
            }
          }
          if (showerror) {
            System.err.println("El camp " + field.javaName + " de la taula "
             + table.getNameJava() + " no té definida l'etiqueta per l'idioma "
             + " [" + lang + "]");
          }

        }
        
        

      } // Final for fields
      
      if (constraints.size() != 0)  {
        System.err.println(" -- " + table.getName() + " (" + table.getNameJava() + ")");
        System.err.println();
        for (String val : constraints.values()) {
          System.err.println(val);
        }
      }
    }

    // Ordenar per nom java de les taules
    Arrays.sort(SharedData.data.tables, new Comparator<TableInfo>() {
      @Override
      public int compare(TableInfo o1, TableInfo o2) {
        // TODO Auto-generated method stub
        return o1.nameJava.compareToIgnoreCase(o2.nameJava);
      }
    });
    
    
    long finish = System.currentTimeMillis();
    
    log.info("Executing Checks in " + (finish - now) + "ms");

    now = System.currentTimeMillis();
    FileOutputStream os;
    try {
      os = new FileOutputStream(SharedData.projectFile);
      XMLEncoder encoder = new XMLEncoder(os);
      encoder.writeObject(SharedData.data);
      encoder.close();
      
      finish = System.currentTimeMillis();
      
      log.info("Saving File in " + (finish - now) + "ms");
      
      return true;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, e.getMessage());
      return false;
    }    
  }
  private String getExpectedIndexFK(TableInfo table, String shortStr, FieldInfo field) {
    String expected = table.name + "_" + field.sqlName +  "_fk_i";
    if (expected.length() > 30) {
      expected = SharedData.data.getPrefix() + "_" + shortStr + "_" + field.sqlName + "_fk_i";
    }
    return expected;
  }
  private String checkExpectedIndexPK(TableInfo table, String shortStr, FieldInfo field, int countPKs, String index) {
    
    String expected = null;
    final String[] tableNames = new String[] { table.name, SharedData.data.getPrefix() + "_" + shortStr  };
    String expectedtable = null;
    for (int i = 0 ; i< tableNames.length; i++) {
      expectedtable = tableNames[i];
      if (countPKs == 1) {
        expected = expectedtable + "_pk_i";
      } else {
        expected = expectedtable + "_" +  field.sqlName +  "_pk_i";
      }
      if (expected.length() > 30) {
        continue;
      }
      break;
    }
    

    if (index == null) {
      System.err.println(" -- Es recomanable tenir un index de la clau primaria.");
      
      System.err.println(" create index " + expected + ((expected.length() > 30)?"**" : "") + " on " + table.name + " (" + field.sqlName + ");\n");
      
    } else {
      if (expected.length()> 30 || !expected.startsWith(expectedtable) || !expected.endsWith("_pk_i") ) {
        System.err.println(" --  Error de forma en l'index PK  " + index);
        System.err.println("ALTER INDEX " + index + " RENAME to " + expected + ";");
      }
    }
    return expected;
  }

  private void checkTraduccions(String[] idiomes, String name,
      Map<String, String> labelsTaula) {
    for (String lang : idiomes) {
      String value = labelsTaula.get(lang);
      boolean showerror = false;
      if (value == null) {
        labelsTaula.put(lang, name + "_XX_" + lang);
        showerror = true;
      } else {
        if (value.endsWith("_XX_" + lang)) {
          showerror = true;
        }
      }
      if (showerror) {
        System.err.println("La taula "
         + name + " no té definida l'etiqueta per l'idioma "
         + " [" + lang + "]");
      }
    }
  }


}