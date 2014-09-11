package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
  
  private static final Logger log = Logger.getLogger(RebApp.class);

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
                "Generat el codi dins " + project.getAbsolutePath() , "Error",
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
      final Object[] options = { "Nou Projecte", "Actualitzar Projecte", "Obrir Projecte" , "Generar Projecte" };
      int n = JOptionPane.showOptionDialog(null, "Seleccioni un opció ", "GenApp 2014",
          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
          options, options[0]);

      log.info(" NUMERO -> " + n);

      switch (n) {
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
  
            FileInputStream fis = new FileInputStream(selectedFile);
            XMLDecoder dec = new XMLDecoder(fis);
            Project project = (Project) dec.readObject();
            
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
            SharedData.data = project;
            (new RebApp()).setVisible(true);
          }
          break;
        }

        default:
      }

    } catch (FileNotFoundException e) {
      // TODO Mostrar en un dialeg
      e.printStackTrace();
    }

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