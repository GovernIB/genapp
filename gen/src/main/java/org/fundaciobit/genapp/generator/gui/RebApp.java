package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.generator.CodeGenerator;
import org.fundaciobit.genapp.generator.gui.SharedData.ProjectType;



/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */

public class RebApp extends JFrame {
  
  private static final Logger log = Logger.getLogger(RebApp.class.getSimpleName());

  /**
   * 
   */
  private static final long serialVersionUID = -1902978827081105039L;

  /** IU */
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel vista = new JPanel();
  JPanel cursor = new JPanel();
  JButton next = new JButton();
  JButton previus = new JButton();
  CardLayout card = new CardLayout();
  JButton finish = new JButton();
  JButton cancel = new JButton();

  Panel_1_SelectDB selectDB = new Panel_1_SelectDB();
  // Panel_2_TableList selectTable = new Panel_2_TableList(selectDB);
  Panel_2_TableFields columnList = new Panel_2_TableFields(this);
  
  // Panel_5_CodeGenerator codeGenerator = new
  // Panel_5_CodeGenerator(columnList,connectionManager);
  Panel_5_StoreProject finalPan = new Panel_5_StoreProject();

  Paneable pannels[];
  String cardNames[];
  int pos = 0;
  
  public static final String LAST_FILE = "lastFile";
  
  public static final String LAST_GEN_DIR = "lastGenenationDir";
  
  
  public static final File lastConfigFile = new File("lastconfig.properties");
  public static final Properties lastConfig = new Properties();
  
  static {
    if (lastConfigFile.exists()) {
      try {
        lastConfig.load(new FileInputStream(lastConfigFile));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  
  public static File getDirectoryOfLastFile() {
    if (lastConfig != null && lastConfig.getProperty(LAST_FILE) != null) {
      File f = new File(lastConfig.getProperty(LAST_FILE));
      if (f.exists()) {
        return f.getParentFile();
      } else {
        if (f.getParentFile().exists()) {
          return f.getParentFile();
        }
      }
    }
    return new File("."); //"D:\\dades\\dades\\CarpetesPersonals\\Programacio\\PortaFIB\\"
  }
  
  public static void saveLastFile(File selectedFile) {
    lastConfig.setProperty(LAST_FILE, selectedFile.getAbsolutePath());
  }
  
  
  @SuppressWarnings("deprecation")
  public static void saveLastConfig() {
    try {
      lastConfig.save(new FileOutputStream(lastConfigFile), "GenApp File");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public static void  saveDirectoryOfLastGeneration(File selectedDir) {
    lastConfig.setProperty(LAST_GEN_DIR, selectedDir.getAbsolutePath());
  }
  
  
  
  public static File getDirectoryOfLastGeneration() {
    if (lastConfig != null && lastConfig.getProperty(LAST_GEN_DIR) != null) {
      File f = new File(lastConfig.getProperty(LAST_GEN_DIR));
      if (f.exists()) {
        return f;
      }
    }
    File defaultDir = getDirectoryOfLastFile();
    if (defaultDir != null && defaultDir.exists()) {
      return defaultDir;
    } else {
      return new File("."); //"D:\\dades\\dades\\CarpetesPersonals\\Programacio\\PortaFIB\\"
    }
  }
  

  /** Construct the frame */
  public RebApp() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {

      

      if (SharedData.project == ProjectType.GENERATE) {
       
        JFileChooser dstDirDlg = new JFileChooser(getDirectoryOfLastGeneration());
        
        dstDirDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        dstDirDlg.setDialogTitle("Seleccioni el directori on generar el codi del projecte.");
       
    
        // opens dialog if button clicked
        if (dstDirDlg.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

          File dstDir = dstDirDlg.getSelectedFile();
          try {
            File project = CodeGenerator.generateCode(SharedData.data, dstDir);
            JOptionPane.showMessageDialog(null,
                "Generat el codi dins " + project.getAbsolutePath() , "Info",
                JOptionPane.INFORMATION_MESSAGE);
            
            saveDirectoryOfLastGeneration(dstDir);
            saveLastConfig();
 
            this.setVisible(false);
            System.exit(0);
          } catch(Throwable th) {
            th.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error generant el codi: " + th.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            System.exit(1);
          } 
        }

        
      } else {
      
      

        vista.setLayout(card);
        vista.setToolTipText("");
  
        switch(SharedData.project) {
  
          case OPEN:
            // if (SharedData.project == ProjectType.LOAD) {
            pannels = new Paneable[] { columnList, finalPan };
            cardNames = new String[] { "ColumnList", "Final" };
            columnList.initPanel();
            saveLastConfig();
          break;
          
          case UPDATE:  
            saveLastConfig();
          default:
          case NEW:
            pannels = new Paneable[] { selectDB, columnList, finalPan };
            cardNames = new String[] { "SelectDB", "ColumnList", "Final" };
            vista.add(selectDB, "SelectDB");
            selectDB.initPanel();
          break;
        }
  
        vista.add(columnList, "ColumnList");      
        vista.add(finalPan, "Final");
  
        cursor.add(cancel, null);
        cursor.add(previus, null);
        cursor.add(next, null);
        cursor.add(finish, null);
  
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setSize(new Dimension(1024, 800));
        this.setLocation(100, 100);
        this.setTitle("GenApp 2014");
        next.setText("Next >>");
        next.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            next_actionPerformed();
          }
        });
        previus.setText("<< Back");
        previus.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            previus_actionPerformed(e);
          }
        });
  
        finish.setEnabled(false);
        finish.setText("Finalizar");
        finish.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            finish_actionPerformed(e);
          }
        });
  
        cancel.setText("Cancelar");
        cancel.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            cancel_actionPerformed(e);
          }
        });
        contentPane.setMinimumSize(new Dimension(512, 354));
        contentPane.setPreferredSize(new Dimension(555, 400));
        contentPane.add(vista, BorderLayout.CENTER);
  
        contentPane.add(cursor, BorderLayout.SOUTH);
  
        log.info(" CARD NAME: " + cardNames[pos]);
  
        card.show(vista, cardNames[pos]);
  
        initButtonStatus(pos);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /** Overridden so we can exit when window is closed */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  public static void main(String[] args) {

    try {
      final Object[] options = { 
          "Nou Projecte", "Actualitzar Projecte", 
          "Obrir Projecte" , "Generar Projecte", "Generar taules base" };
      int n = JOptionPane.showOptionDialog(null, "Seleccioni una opció ", "GenApp 2015",
          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
          options, options[0]);

      log.info(" NUMERO -> " + n);

      switch (n) {
        case -1:
           System.exit(0);
          break;
      
        // ================= NEW PROJECT
        case 0: {
          SharedData.project = ProjectType.NEW;
          SharedData.data = new Project();
          SharedData.data.setLanguages(new String[] { "ca" , "es"});
          (new RebApp()).setVisible(true);
          break;
        }
        // ================= UPDATE & OPEN & GENERATE PROJECT
        case 1:
        case 2:
        case 3: {
          // new dialog
          JFileChooser loadEmp = new JFileChooser(getDirectoryOfLastFile());
          // "D:\\dades\\dades\\CarpetesPersonals\\Programacio\\PortaFIB");
          
          FileNameExtensionFilter filterm3u = new FileNameExtensionFilter(  
              "GenApp file (.genapp)", "genapp");  
          loadEmp.addChoosableFileFilter(filterm3u);  
          
          loadEmp.setDialogTitle("Seleccioni un arxiu .genapp");
          File selectedFile;
  
          // opens dialog if button clicked
          if (loadEmp.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            // gets file from dialog
            selectedFile = loadEmp.getSelectedFile();
  
            Project project = readProjectFromFile(selectedFile);
            
            saveLastFile(selectedFile);
            
            // XXX
            if (project.getLanguages() == null) {
              project.setLanguages(new String[] { "ca" , "es"});
            }

            SharedData.projectFile = selectedFile;
            if (n == 1) {
              SharedData.project = ProjectType.UPDATE;
            } else {
              SharedData.project = ((n == 2) ? ProjectType.OPEN : ProjectType.GENERATE);

              cleanProject(project);

            }
            SharedData.data = project;
            (new RebApp()).setVisible(true);
          }
          break;
        }

        // generate taules base
        case 4: 
        {
         
          final String nomCamp = "Prefix";
          final Integer expectedSize = 3;
          String prefix = readField(nomCamp, "pfx", expectedSize);
          prefix = prefix.toLowerCase();
          
          
          final String nomCamp2 = "Nom Projecte";
          final Integer expectedSize2 = null;
          String nomAppUp = readField(nomCamp2, "NomApp", expectedSize2);
          
          String nomApp = nomAppUp.toLowerCase();
          

          
          final String[][] sourceDest = {
                       {"genapp_required_tables.sql", prefix + "_" + nomApp + "_SCHEMA_BASE.sql" },
                       {"template.genapp", nomAppUp + ".genapp"}
          };
          
          File parent = null;;
          for (int j = 0; j < sourceDest.length; j++) {
            
            String src = sourceDest[j][0];
            String dest = sourceDest[j][1];
            
            ClassLoader classLoader = RebApp.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream(src);
            
            String content = IOUtils.toString(is);
            
            
            content = content.replace("PFX", prefix);
            
            content = content.replace("APPNAMEUP", nomApp);
            
            content = content.replace("APPNAME", nomApp);
            
            

            
            File f = new File(dest);
            File tmp  = new File(f.getCanonicalPath());
            System.out.println("tmp = " + tmp.getAbsolutePath());
            parent = tmp.getParentFile();
            System.out.println("PARENT = " + parent.getAbsolutePath());
            
            FileUtils.write(f, content);
          }
          
         
          

          JOptionPane.showMessageDialog(null,
              "Fitxer de SQL i plantilla GenApp creats a: " + parent.getAbsolutePath()
              + "\n Ara ha de fer el següent:\n"
              + "(1) Crear una BBDD a partir del fitxer SQL generat (" + sourceDest[0][1] + ")\n"
              + "(2) Executar genapp de nou i pitjar sobre el boto d'Actualitzar Projecte\n"
              + "(3) Elegir el fitxer " + sourceDest[1][1] + "\n"
              + "(4) Actualitzar els camps de connexio amb la BBBD i d'altres"
              ,
              "Info", JOptionPane.INFORMATION_MESSAGE);
          
          System.exit(0);
          
        }
        
        default:
      }

    } catch (Exception e) {
      // TODO Mostrar en un dialeg
      e.printStackTrace();
    }

  }

  public static Project readProjectFromFile(File selectedFile)
      throws FileNotFoundException {
    FileInputStream fis = new FileInputStream(selectedFile);
    XMLDecoder dec = new XMLDecoder(fis);
    Project project = (Project) dec.readObject();
    return project;
  }
  
  
  public static void saveProjectToFile(File file, Project project) throws FileNotFoundException {
    FileOutputStream os = new FileOutputStream(file);
    XMLEncoder encoder = new XMLEncoder(os);
    encoder.writeObject(project);
    encoder.flush();
    encoder.close();
  }
  
  

  public static void cleanProject(Project project) {
    for (int i = 0; i < project.tables.length; i++) {
      
      if (project.tables[i].getNameJava() == null) {
        project.tables[i].setNameJava(project.tables[i].getName());
      }
      String[] idiomes = project.getLanguages();
      Map<String,String> labelsTaula = project.tables[i].getLabels(); 
      if (labelsTaula == null || labelsTaula.size() == 0) {
        labelsTaula = new HashMap<String, String>();
        for (String lang : idiomes) {
          labelsTaula.put(lang,  project.tables[i].getName());
        }
        project.tables[i].setLabels(labelsTaula);
      }
      
      log.info(project.tables[i].getNameJava() 
          + "\t" + project.tables[i].getName());

      
      for(FieldInfo fi : project.tables[i].fields) {
        Map<String, String> labels = fi.getLabels(); 
        for (String lang : idiomes) {
          if (labels.get(lang) == null) {
            labels.put(lang, fi.getJavaName() + "_XX_" + lang);
          }
        }
      }
      /*
      for(int f=0;f< project.tables[i].fields.length; f++) {
        if (project.tables[i].fields[f].getLabel() == null) {
          project.tables[i].fields[f].setLabel(project.tables[i].fields[f].getJavaName());
        }
       
      }
      */
      
    }
  }

  public static String readField(String nomCamp, String sample, Integer expectedSize) {
    String prefix;
    do {
      prefix = (String)JOptionPane.showInputDialog(
          null, nomCamp + " de l'aplicació:\n", nomCamp + " de l'aplicació",
          JOptionPane.PLAIN_MESSAGE, null, null, sample);
      
      if (prefix == null) {
        System.exit(0);
      }
      if ( (expectedSize == null && prefix.trim().length() == 0) 
          || (expectedSize != null && prefix.trim().length() != expectedSize)) {
        
        String msg;
        if (expectedSize == null) {
          msg = "El " + nomCamp + " no ha d'estar buit";
        } else {
          msg = "El " + nomCamp + " ha d'estar format per una cadema de tamany " + expectedSize;
        }
        JOptionPane.showMessageDialog(null,
            msg,
            "Error",
            JOptionPane.ERROR_MESSAGE);
      } else {
        return prefix;
      }
        
    } while(true);
  }

  /**
   * 
   */
  public void next_actionPerformed() {

    if (this.pannels[pos].next() == false) {
      return;
    }

    if ((pos + 1) == cardNames.length) {
      return;
    }

    if (this.pannels[pos + 1].initPanel() == false) {
      return;
    }

    pos++;

    initButtonStatus(pos);

    this.card.show(vista, cardNames[pos]);
  }

  /**
   * 
   * @param e
   */
  void previus_actionPerformed(ActionEvent e) {

    if (pannels[pos].previus()) {
      pos--;

      initButtonStatus(pos);

      this.card.show(vista, cardNames[pos]);
    }
  }

  void cancel_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void finish_actionPerformed(ActionEvent e) {
    if (pannels[pos].finish()) {
      System.exit(0);
    }
  }

  void initButtonStatus(int pos) {
    
    log.info("INIT BUTTON STATUS: " + pannels[pos].isFinalizable());
    
    if (pannels[pos].isFinalizable()) {
      this.finish.setEnabled(true);
      this.finish.setVisible(true);
    } else {
      this.finish.setEnabled(false);
      this.finish.setVisible(false);
    }

    /* if(pannels[pos].next()) */
    if ((pos + 1) == cardNames.length)
      this.next.setEnabled(false);
    else
      this.next.setEnabled(true);
  }

}