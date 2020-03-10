package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.MultipleUnique;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.generator.CodeGenUtils;
import org.fundaciobit.genapp.generator.gui.SharedData.ProjectType;



/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */

public class Panel_5_StoreProject extends Paneable {
  
  private final Logger log = Logger.getLogger(getClass().getSimpleName());
  
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
    
    log.info("\n =============== CHECKS ============== ");
    long now = System.currentTimeMillis();
    
    // TODO Comprovar javaName duplicats o shortNames duplicats
    StringBuffer allErrors = new StringBuffer();
    
    // Eliminar descripcions buides i noms java amb espais
    // i Limitar noms de camps i taules de BBDD a 30 chars
    for (TableInfo table : SharedData.data.tables) {
      
      if (table.isTranslationMapEntity() || table.isTranslationEntity()) {
        continue;
      }
      
      StringBuffer errors = new StringBuffer();
      
      boolean errorgreu = false;
      
      // Limitar noms taules de BBDD a 30 chars
      if (table.name.length() > 30) {
        errors.append("-- El nom sql (" + table.getName() + ") de la taula " + table.nameJava
            + " supera els 30 caracters de longitud.\n");
        errorgreu = true;
      }
      

      // Noms curts menors o iguals a 10
      String shortStr =  table.getShortName();
      if (shortStr == null) {
        shortStr = table.getNameJava().toLowerCase();
        table.setShortName(shortStr);
      }
      
      if (shortStr.length() > 10) {
        errors.append("-- El camp shortname de la taula " + table.getNameJava() 
            + " ha de tenir longitud de 10 o menys caracters (" + shortStr + ")");
        errorgreu = true;
      }
      


      

      
      // Check Traduccions
      
      /*
      {
        Map<String, String> labels = table.getLabels();
        Map<String, String> labelsPlural = table.getLabelsPlural();
        
        for(String key : labels.keySet()) {
          labelsPlural.put(key, labels.get(key) + "_XX_" + key);
        }
      }
      */
      // Traduccions nom de la taula
      checkTraduccions(idiomes, table.getNameJava(), table.getLabels(), errors);
      checkTraduccions(idiomes, table.getNameJava() + "_plural", table.getLabelsPlural(), errors);

      // Traduccions dels camps
      for (FieldInfo field : table.getFields()) {
        
        // Traduccions de Camps
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
            errors.append("-- El camp " + field.javaName + " de la taula "
             + table.getNameJava() + " no té definida l'etiqueta per l'idioma "
             + " [" + lang + "]\n");
          }

        }
      }
      
      if (errorgreu) {
        errors.append("\n-- No es faran comprobacions de Longitud de camps ni de constraints\n"
                     + "--      (PrimaryKeys, ForeignKeys, Indexs i UNIQUEs) mentre no es\n"
                     + "--      solventin els problemes anteriors.\n\n");
      } else {

        // Check constraints de multiple uniques
        MultipleUnique[] multipleUniques = table.getMultipleUniques();
        if (multipleUniques != null /*&& multipleUniques.length == 1*/) {
          
          for (int i = 0; i < multipleUniques.length; i++) {
            
            String ukName = multipleUniques[i].getName();
   
            {
              boolean error = false;
              if (ukName.length() > 30) {
                errors.append("-- La Constraint unique " + ukName + " de la taula " + table.nameJava
                     + " té un tamany de més de 30 caracters\n");
                error = true;
              }
            
              String ukNameStartsExpected = SharedData.data.getPrefix() + "_" + shortStr 
              + "_"; 
          
              if (!ukName.toLowerCase().startsWith(ukNameStartsExpected.toLowerCase())
                || !ukName.toLowerCase().endsWith("_uk")) {
                errors.append("-- La Constraint unique " + ukName + " de la taula " + table.nameJava
                      + " té un nom incorrecte.\n");
                error = true;
              }
                
  
              if (error) {
                
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
                
                errors.append(sql);
  
              }
            }
          }       
        }
        
        
        
        
        // Longitud de camps i constraints (PrimaryKeys, ForeignKeys, Indexs i UNIQUEs)
        Map<String, String> constraints = new HashMap<String, String>();
        
        int countPKs = 0;
        for (FieldInfo field : table.getFields()) {
          if (field.isPrimaryKey()) {
            countPKs ++;
          }
        }
        
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
            errors.append("-- El nom sql (" + field.sqlName + ") del camp " + field.javaName 
              + " de la taula " + table.nameJava
              + " supera els 30 caracters de longitud.\n");
          }
  
          // Check PK constarint
          String pk = field.getPK();
          if (pk != null) {
            if (pk.length() > 30) {
              errors.append("-- El nom sql de la contraint PK (" + pk 
                + ") pel camp " + field.javaName 
                + " de la taula " + table.nameJava
                + " supera els 30 caracters de longitud.\n");
            }
  
            String expected = table.name + "_pk";
            if (expected.length() > 30) {
              expected = SharedData.data.getPrefix() + "_" + shortStr + "_pk";
            }
            if (!pk.equals(expected)) {
              errors.append(" --  Error de forma en contraint Primary KEY \n");
              errors.append("ALTER INDEX " + pk + " RENAME to " + expected + ";\n");
            }
          }
          
          
          
  
          // Check UNIQUE Constraint
          String unique = field.getUnique();
          if (unique != null) {
            if (unique.length() > 30) {
              errors.append("-- El nom sql de la contraint unique (" + unique 
                + ") pel camp " + field.javaName 
                + " de la taula " + table.nameJava
                + " supera els 30 caracters de longitud.\n");
            }
            
            String expectedPre = SharedData.data.getPrefix() + "_" + shortStr + "_";
            String expectedPost = "_uk";
            
            if (unique.startsWith(expectedPre) && unique.endsWith(expectedPost)) {
              // OK
            } else {

              errors.append(" --  Error de forma en contraint unique\n");
              errors.append("ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + unique + ";\n");
              errors.append("ALTER TABLE " + table.getName()
                  + " ADD CONSTRAINT " + expectedPre + field.sqlName + expectedPost
                  + " unique (" + field.sqlName + ");\n");
              
            }
          }
  
          
          // Check Foreign Keys Constraint
          ForeignKey[] fkImport = field.getForeignKeysByType(ForeignKey.IMPORTED);
          if (fkImport != null && fkImport.length > 1) {
            errors.append("\n-- ForeignKey.IMPORTED duplicades [" +table.nameJava+ "." +field.getJavaName()+ " ]\n\n");
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
                errors.append("-- La ForeignKey " + fkName + " de la taula " + table.nameJava
                     + " té un tamany de més de 30 caracters\n");
              }
  
              errors.append("-- La ForeignKey " + fkName + " de la taula " + table.nameJava
                 + " no té un nom correcte (esperat " + fkNameStartsExpected + "*_fk )\n");
              {
            	int tamanyField = 30 - (fkNameStartsExpected + "_fk").length();
            	String fieldfk;
            	if (tamanyField < 5) {
            		fieldfk = field.sqlName;
            	} else {
            		if (field.sqlName.length() <= tamanyField) {
            			fieldfk = field.sqlName;
            		} else {
            			fieldfk = field.sqlName.substring(0, tamanyField);
            		}
            	}
            	
                String newC =  fkNameStartsExpected + fieldfk +  "_fk";
                String sql = "ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + fkName + ";\n";
                sql = sql + "ALTER TABLE " + table.getName() + " ADD CONSTRAINT " + newC + " "
                    + " FOREIGN KEY (" + field.sqlName + ") "
                    + " REFERENCES " + expTable.name + " (" + expField.sqlName + ");\n";
                errors.append(sql);
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

  
        } // Final for fields
        
        if (constraints.size() != 0)  {
          System.err.println(" -- " + table.getName() + " (" + table.getNameJava() + ")");
          System.err.println();
          for (String val : constraints.values()) {
            System.err.println(val);
          }
        }
      }
      
      if (errors.length() != 0) {
        allErrors.append("\n-- ==== ERRORS EN TAULA ]" + table.getNameJava() + "[:\n\n");
        allErrors.append(errors.toString());
        allErrors.append("\n");
        
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
    
    
    if (allErrors.length() != 0) {
      log.error("\n" + allErrors.toString());
      JTextArea ta = new JTextArea(allErrors.toString());
      ta.setMaximumSize(new Dimension(800, 400));
      ta.setSize(new Dimension(800, 400));
      JScrollPane sp =new JScrollPane(ta);
      sp.setMaximumSize(new Dimension(800, 400));
      sp.setSize(new Dimension(800, 400));
      
      dialog = new JDialog();
      dialog.setTitle("Errors en el Model i dades del projecte");
      dialog.setLayout(new BorderLayout());
      dialog.add(sp, BorderLayout.CENTER);
      JButton botook = new JButton("OK");
      botook.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
           Panel_5_StoreProject.this.dialog.setVisible(false);
        }
      });
      dialog.add(botook, BorderLayout.SOUTH);
      dialog.setMaximumSize(new Dimension(800, 400));
      dialog.pack();
      dialog.setSize(new Dimension(800, 400));
      
      final Toolkit toolkit = Toolkit.getDefaultToolkit();
      final Dimension screenSize = toolkit.getScreenSize();
      final int x = (screenSize.width - dialog.getWidth()) / 2;
      final int y = (screenSize.height - dialog.getHeight()) / 2;
      dialog.setLocation(x, y);
      
      dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
      dialog.setVisible(true);

    }
    
    
    
    
    

    now = System.currentTimeMillis();
    
    try {
      Project project = SharedData.data;
      File file = SharedData.projectFile;
      
      RebApp.saveProjectToFile(file, project);
      
      finish = System.currentTimeMillis();
      
      log.info("Saving File in " + (finish - now) + "ms");
      return true;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, e.getMessage());
      return false;
    }    
  }
 
  protected static JDialog dialog;
  
  
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
      Map<String, String> labelsTaula, StringBuffer errors) {
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
        errors.append("-- La taula "
         + name + " no té definida l'etiqueta per l'idioma "
         + " [" + lang + "]\n");
      }
    }
  }


}