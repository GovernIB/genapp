package org.fundaciobit.genapp.generator.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.fundaciobit.genapp.TableInfo;


/**
 * GenApp
 */
public class EditTableInfo extends javax.swing.JDialog {
  
  /**
   * 
   */
  private static final long serialVersionUID = -3792059171506172910L;

  private final TableInfo tableInfo;

  private JPanel totPanell;
  private JPanel campsPanell;
  private JPanel botonsPanell;
  
  private JTextField sqlName;
  private JTextField javaName;
  
  private JTextField shortName;
  //private JTextField label;
  private Map<String, JTextField> labels;
  
  private Map<String, JTextField> labelsPlural;
  
  private JTextField descripcio;
  
     
  public JCheckBox generate;
  
  public JCheckBox fileMappingEntity;
  
  public JCheckBox languageMappingEntity;
  
  public JCheckBox translationEntity;
  
  public JCheckBox translationMapEntity;
  
  public boolean clickOK = false;
  
  public String[] idiomes;

 
  

  public EditTableInfo(JFrame frame, TableInfo tableInfo, String[] idiomes) {
    super(frame);
    this.setModal(true);
    this.setTitle("Editar taula " + tableInfo.getNameJava());
    this.tableInfo = tableInfo;
    this.idiomes = idiomes;
    
    sqlName= new JTextField(this.tableInfo.name);
    sqlName.setEditable(false);
    javaName= new JTextField(this.tableInfo.nameJava);
    
    if (this.tableInfo.shortName == null || this.tableInfo.shortName.length() == 0) {
      shortName = new JTextField(this.tableInfo.nameJava.toLowerCase());
    } else {
      shortName = new JTextField(this.tableInfo.shortName);
    }
    //label =  new JTextField(this.tableInfo.label);
    
    labels = new HashMap<String, JTextField>();
    for (String lang : idiomes) {
      labels.put(lang, new JTextField(tableInfo.getLabels().get(lang)));
    }
    
    
    labelsPlural = new HashMap<String, JTextField>();
    for (String lang : idiomes) {
      labelsPlural.put(lang, new JTextField(tableInfo.getLabelsPlural().get(lang)));
    }

    
    descripcio =  new JTextField(this.tableInfo.descripcio);
    
    generate = new  JCheckBox("",this.tableInfo.generate);
    fileMappingEntity = new  JCheckBox("",this.tableInfo.isFileMappingEntity());
    languageMappingEntity = new  JCheckBox("",this.tableInfo.isLanguageMappingEntity());

    translationEntity = new  JCheckBox("",this.tableInfo.isTranslationEntity());
    
    translationMapEntity = new  JCheckBox("",this.tableInfo.isTranslationMapEntity());

    initGUI();
  }
  
 
  
  private void initGUI() {
    try {
      {
        totPanell = new JPanel();
        BorderLayout totPanellLayout = new BorderLayout();
        totPanellLayout.setVgap(10);
        getContentPane().add(totPanell, BorderLayout.CENTER);
        totPanell.setLayout(totPanellLayout);
        {
          {
            JButton botoOK = new JButton("OK");
            botoOK.addActionListener(new ActionListener() {
              
              public void actionPerformed(ActionEvent e) {
                clickOK = true;
                if (checks()) {
                  setVisible(false);
                }
              }
            });
            
            JButton botoCancel = new JButton("Cancel");
            botoCancel.addActionListener(new ActionListener() {
              
              public void actionPerformed(ActionEvent e) {
                clickOK = false;
                setVisible(false);
              }
            });
            botonsPanell = new JPanel();
            botonsPanell.add(botoOK);
            botonsPanell.add(botoCancel);
            totPanell.add(botonsPanell, BorderLayout.SOUTH);
            
            
          }
          
          {
            campsPanell = new JPanel();
            GridBagLayout campsPanellLayout = new GridBagLayout();
            campsPanellLayout.columnWidths = new int[] {7, 7};
            campsPanellLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
            campsPanellLayout.columnWeights = new double[] {0.25, 0.75};
            campsPanellLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};            
            campsPanell.setLayout(campsPanellLayout);

            // ----  1
            int i = 0;
            campsPanell.add(new JLabel("sqlName"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(sqlName, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("javaName"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(javaName, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("shortName"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(shortName, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("descripcio"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(descripcio, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("generate"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(generate, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("fileMappingEntity"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(fileMappingEntity, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("languageMappingEntity"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(languageMappingEntity, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("translationEntity"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(translationEntity, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            campsPanell.add(new JLabel("translationMapEntity"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(translationMapEntity, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            

            for (String  lang : idiomes) {
              campsPanell.add(new JLabel("Label[" + lang.toUpperCase()+ "]"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
              campsPanell.add(labels.get(lang), new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
              i++;
            }
            
            for (String  lang : idiomes) {
              campsPanell.add(new JLabel("Label[" + lang.toUpperCase()+ "] - PLURAL"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
              campsPanell.add(labelsPlural.get(lang), new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
              i++;
            }
            
            
            totPanell.add(campsPanell, BorderLayout.CENTER);
          }
        }
      }
      
      this.setSize(450, 600);
      
      this.pack();
      
      if (this.getWidth() < 450) {
        this.setSize(450, this.getHeight());
      }
      
      final Toolkit toolkit = Toolkit.getDefaultToolkit();
      final Dimension screenSize = toolkit.getScreenSize();
      final int x = (screenSize.width - this.getWidth()) / 2;
      final int y = (screenSize.height - this.getHeight()) / 2;
      this.setLocation(x, y);
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  public boolean checks() {
    
   // TODO Mirar tots els checks
    String shortStr =  shortName.getText();
    if (shortStr.length() > 10) {
      JOptionPane.showMessageDialog(null,
          "El camp shortname ha de tenir longitud de 10 o menys caracters");
      return false;
    }
    
    return true;
  }
  
  public void updateFieldInfoWithNewValues() {
    // TODO ReadOnly  public String sqlName;
    this.tableInfo.setNameJava(javaName.getText());
    //this.tableInfo.setLabel(label.getText());
    
    
    
    
    this.tableInfo.setShortName(shortName.getText());
    
    Map<String, String> labelsStr = this.tableInfo.getLabels();
    for (String  lang : idiomes) {
      labelsStr.put(lang, this.labels.get(lang).getText());
    }
    
    Map<String, String> labelsPluralStr = this.tableInfo.getLabelsPlural();
    for (String  lang : idiomes) {
      labelsPluralStr.put(lang, this.labelsPlural.get(lang).getText());
    }
    
    this.tableInfo.setDescripcio(checkNull(descripcio.getText()));
    
    this.tableInfo.setGenerate(generate.isSelected());
    this.tableInfo.setFileMappingEntity(fileMappingEntity.isSelected());
    this.tableInfo.setLanguageMappingEntity(languageMappingEntity.isSelected());
    this.tableInfo.setTranslationEntity(translationEntity.isSelected());
    this.tableInfo.setTranslationMapEntity(translationMapEntity.isSelected());
 }
  
  public String checkNull(String value) {
    if (value == null || value.length() == 0) {
      return null;
    }
    return value;
  }

}
