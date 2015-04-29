package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import javax.swing.*;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.DatabaseManager;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.WebFieldInfo;
import org.fundaciobit.genapp.DatabaseManager.Fields;
import org.fundaciobit.genapp.common.db.DataBaseInfo;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */

public class Panel_1_SelectDB extends Paneable implements ItemListener {
  
  private final Logger log = Logger.getLogger(getClass());

  /**
   * 
   */
  private static final long serialVersionUID = 1074349763078514825L;

  JPanel fullPanel = new JPanel();

  JPanel panelNorte = new JPanel();
  JPanel panelCentro = new JPanel();

  JLabel etiProjectName = new JLabel();
  JTextField projectName = new JTextField();
/*
  JLabel etiDriverType = new JLabel();
  JComboBox driverType = new JComboBox();
*/
  JLabel etiDriver = new JLabel();
  JTextField driver = new JTextField();

  JLabel etiURL = new JLabel();
  JTextField url = new JTextField();

  JLabel etiUSR = new JLabel();
  JTextField usr = new JTextField();

  JLabel etiPWD = new JLabel();
  JTextField pwd = new JTextField();
  
  JLabel etiPackage = new JLabel();
  JTextField pack = new JTextField();
  
  
  JLabel etimodulSeguretat = new JLabel();
  JTextField modulSeguretat = new JTextField();
  
  
  JLabel etiprefix = new JLabel();
  JTextField prefix = new JTextField();
  
  
  JLabel etiIdiomes = new JLabel();
  JTextField idiomes = new JTextField();
  

  JLabel etiGenerateBack = new JLabel();
  JCheckBox generateBack = new JCheckBox();
  
  JLabel etiGenerateWs = new JLabel();
  JCheckBox generateWs = new JCheckBox();

  public Panel_1_SelectDB() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  void jbInit() throws Exception {
    /*
     * border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
     * Color.white, Color.white, new Color(142, 142, 142), new Color( 99, 99,
     * 99));
     */
    // ==================== Panel Norte
    BorderLayout borderLayout3 = new BorderLayout();
    panelNorte.setLayout(borderLayout3);

    // ==================== Panel Centro

    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.setRows(12);
    gridLayout1.setColumns(2);
    gridLayout1.setHgap(10);
    gridLayout1.setVgap(10);
    panelCentro.setLayout(gridLayout1);

    etiProjectName.setHorizontalAlignment(SwingConstants.RIGHT);
    etiProjectName.setText("Project Name:");
    panelCentro.add(etiProjectName, null);

    JPanel jPanel6 = new JPanel();
    VerticalFlowLayout verticalFlowLayout6 = new VerticalFlowLayout();
    jPanel6.setLayout(verticalFlowLayout6);
    jPanel6.add(projectName);

    panelCentro.add(jPanel6, null);

    /*
    etiDriverType.setHorizontalAlignment(SwingConstants.RIGHT);
    etiDriverType.setText("Driver Type:");
    PanelCentro.add(etiDriverType, null);

    
    VerticalFlowLayout verticalFlowLayout5 = new VerticalFlowLayout();
    verticalFlowLayout5.setAlignment(VerticalFlowLayout.MIDDLE);
    JPanel jPanel5 = new JPanel();
    jPanel5.setLayout(verticalFlowLayout5);
    driverType.addItemListener(this);
    for (int i = 0; i < DatabaseManager.sJDBCTypes.length; i++) {
      driverType.addItem(DatabaseManager.sJDBCTypes[i][0]);
    }
    jPanel5.add(driverType, null);
    PanelCentro.add(jPanel5, null);
    */

    etiDriver.setHorizontalAlignment(SwingConstants.RIGHT);
    etiDriver.setText("Driver:");
    etiDriver.setHorizontalAlignment(SwingConstants.RIGHT);

    
    panelCentro.add(etiDriver, null);

    VerticalFlowLayout verticalFlowLayout3 = new VerticalFlowLayout();
    verticalFlowLayout3.setAlignment(VerticalFlowLayout.MIDDLE);
    JPanel jPanel3 = new JPanel();
    jPanel3.setLayout(verticalFlowLayout3);
    panelCentro.add(jPanel3, null);
    jPanel3.add(driver, null);

    // ------------------------------

    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    verticalFlowLayout1.setAlignment(VerticalFlowLayout.MIDDLE);
    JPanel jPanel1 = new JPanel();
    jPanel1.setLayout(verticalFlowLayout1);

    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    verticalFlowLayout2.setAlignment(VerticalFlowLayout.MIDDLE);
    JPanel jPanel2 = new JPanel();
    jPanel2.setLayout(verticalFlowLayout2);

    VerticalFlowLayout verticalFlowLayout4 = new VerticalFlowLayout();
    verticalFlowLayout4.setAlignment(VerticalFlowLayout.MIDDLE);
    JPanel jPanel4 = new JPanel();
    jPanel4.setLayout(verticalFlowLayout4);

    etiURL.setHorizontalAlignment(SwingConstants.RIGHT);
    etiURL.setText("URL:");

    

    etiUSR.setHorizontalAlignment(SwingConstants.RIGHT);
    etiUSR.setText("User Name:");

    usr.setPreferredSize(new Dimension(44, 21));
    usr.setColumns(21);
    //usr.setHorizontalAlignment(SwingConstants.CENTER);

    etiPWD.setHorizontalAlignment(SwingConstants.RIGHT);
    etiPWD.setText("Password:");

    pwd.setText("");
    pwd.setHorizontalAlignment(SwingConstants.LEFT);

    // ---------------------------------

    panelCentro.add(etiURL, null);
    panelCentro.add(jPanel4, null);
    jPanel4.add(url, null);

    panelCentro.add(etiUSR, null);
    panelCentro.add(jPanel1, null);
    jPanel1.add(usr, null);

    panelCentro.add(etiPWD, null);
    panelCentro.add(jPanel2, null);
    jPanel2.add(pwd, null);
    
    
    // *********** Package
    // ETI
    etiPackage.setText("Package: ");
    etiPackage.setHorizontalAlignment(SwingConstants.RIGHT);
    panelCentro.add(etiPackage, null);
    
    // VAL
    {
      VerticalFlowLayout layout7 = new VerticalFlowLayout();
      layout7.setAlignment(VerticalFlowLayout.MIDDLE);
      JPanel jPanel7 = new JPanel();
      jPanel7.setLayout(layout7);
      panelCentro.add(jPanel7, null);
      jPanel7.add(pack,  BorderLayout.CENTER);
    }
    
    // Prefix
    {
    etiprefix.setText("Prefix");
    etiprefix.setHorizontalAlignment(SwingConstants.RIGHT);
    panelCentro.add(etiprefix, null);

    {
      VerticalFlowLayout layout7 = new VerticalFlowLayout();
      layout7.setAlignment(VerticalFlowLayout.MIDDLE);
      JPanel jPanel7 = new JPanel();
      jPanel7.setLayout(layout7);
      panelCentro.add(jPanel7, null);
      jPanel7.add(prefix,  BorderLayout.CENTER);
    }
    }
    
    
    
    // Idiomes
    etiIdiomes.setText("Idiomes");
    etiIdiomes.setHorizontalAlignment(SwingConstants.RIGHT);
    panelCentro.add(etiIdiomes, null);

    {
      VerticalFlowLayout layout7 = new VerticalFlowLayout();
      layout7.setAlignment(VerticalFlowLayout.MIDDLE);
      JPanel jPanel7 = new JPanel();
      jPanel7.setLayout(layout7);
      panelCentro.add(jPanel7, null);
      jPanel7.add(idiomes,  BorderLayout.CENTER);
    }
    
    
    // generate Back
    etiGenerateBack.setText("Generar Back");
    etiGenerateBack.setHorizontalAlignment(SwingConstants.RIGHT);
    panelCentro.add(etiGenerateBack, null);

    {
      VerticalFlowLayout layout7 = new VerticalFlowLayout();
      layout7.setAlignment(VerticalFlowLayout.MIDDLE);
      JPanel jPanel7 = new JPanel();
      jPanel7.setLayout(layout7);
      panelCentro.add(jPanel7, null);
      jPanel7.add(generateBack,  BorderLayout.CENTER);
    }
    
    
    // generate WS
    etiGenerateWs.setText("Generar WS");
    etiGenerateWs.setHorizontalAlignment(SwingConstants.RIGHT);
    panelCentro.add(etiGenerateWs, null);

    {
      VerticalFlowLayout layout7 = new VerticalFlowLayout();
      layout7.setAlignment(VerticalFlowLayout.MIDDLE);
      JPanel jPanel7 = new JPanel();
      jPanel7.setLayout(layout7);
      panelCentro.add(jPanel7, null);
      jPanel7.add(generateWs,  BorderLayout.CENTER);
    }
    
    
    
    // Modul Seguretat
    etimodulSeguretat.setText("Modul Seguretat");
    etimodulSeguretat.setHorizontalAlignment(SwingConstants.RIGHT);
    panelCentro.add(etimodulSeguretat, null);

    {
      VerticalFlowLayout layout7 = new VerticalFlowLayout();
      layout7.setAlignment(VerticalFlowLayout.MIDDLE);
      JPanel jPanel7 = new JPanel();
      jPanel7.setLayout(layout7);
      panelCentro.add(jPanel7, null);
      jPanel7.add(modulSeguretat,  BorderLayout.CENTER);
    }
    

    // Combine North and Center
    BorderLayout borderLayout2 = new BorderLayout();
    fullPanel.setLayout(borderLayout2);
    fullPanel.add(panelCentro, BorderLayout.NORTH);
    fullPanel.add(panelNorte, BorderLayout.CENTER);

    // JPanel
    BorderLayout borderLayout1 = new BorderLayout();
    this.setLayout(borderLayout1);
    this.add(fullPanel, BorderLayout.CENTER);

  }

  public boolean isFinalizable() {
    return (false);
  }

  public boolean previus() {
    log.info("Passa per previous de SELECTDB");
    return (false);
  }

  public boolean initPanel() {
    log.info(" ================ PASSA PER INITPANEL() de SELECTDB;");
    
    switch(SharedData.project) {

      case NEW:
        // ========================================
        // @todo delete default values 
        // ========================================
        
        // ============= COMU ==============
        modulSeguretat.setText("seycon");
        idiomes.setText("ca,es");
        generateBack.setSelected(true);
        generateWs.setSelected(true);
        usr.setText("username");
        pwd.setText("password");
        
        
        // ============= ORACLE ==============
        /*
        pack.setText("org.fundaciobit.jac");
        projectName.setText("JAC");
        url.setText("jdbc:oracle:thin:@oracle.ibit.org:1521:LIBRA");
        driver.setText("oracle.jdbc.driver.OracleDriver");
        prefix.setText("JAC");
        */

        /*
        pack.setText("org.fundaciobit.expedientweb");
        projectName.setText("DimedWeb");
        url.setText("jdbc:oracle:thin:@192.168.35.100:1521:XE");
        driver.setText("oracle.jdbc.driver.OracleDriver");

        */
        
        
        // ============= MY SQL ==============
        
        /*
        pack.setText("org.fundaciobit.portafib.db");
        projectName.setText("PortaFirmes");
        url.setText("jdbc:mysql://192.168.35.151:3306/portasib");
        driver.setText("com.mysql.jdbc.Driver");
        usr.setText("username");
        pwd.setText("password");
        prefix.setText("PFI");
        */
        
        
        /*
        pack.setText("org.fundaciobit.queesticfentotae.db");
        projectName.setText("QueEsticFentOTAE");
        url.setText("jdbc:mysql://localhost:3306/queesticfentotae");
        driver.setText("com.mysql.jdbc.Driver");
        usr.setText("username");
        pwd.setText("password");
        */
        
        
        /*
        pack.setText("org.fundaciobit.dimed.reb");
        projectName.setText("DimedWeb");
        url.setText("jdbc:mysql://192.168.35.100:3306/dimed");
        driver.setText("com.mysql.jdbc.Driver");
        usr.setText("username");
        pwd.setText("password");
        */

        // ============= POSTGRESQL ==============
        
        pack.setText("org.fundaciobit.rolsac.db");
        projectName.setText("Rolsac");
        url.setText("jdbc:postgresql://192.168.35.222:5432/rolsac");
        driver.setText("org.postgresql.Driver");
        usr.setText("username");
        pwd.setText("password");

        
        // ========================================
        // ========================================
      break;

      case UPDATE:      
        Project proj = SharedData.data;
        pack.setText(proj.getPackageName());
        projectName.setText(proj.projectName);
        url.setText(proj.dataBaseInfo.getUrl());
        driver.setText(proj.dataBaseInfo.getDriver());
        usr.setText(proj.dataBaseInfo.getUsr());
        pwd.setText(proj.dataBaseInfo.getPwd());
        prefix.setText(proj.getPrefix());
        if (proj.getModulSeguretat() == null) {
          modulSeguretat.setText("seycon2");
        } else {
          modulSeguretat.setText(proj.getModulSeguretat());
        }
        
        String langs = Arrays.toString(proj.getLanguages());
        idiomes.setText(langs.substring(1,langs.length() - 1));
        generateBack.setSelected(proj.isGenerateBack());
        generateWs.setSelected(proj.isGenerateWS());
        
      break;
      
      case OPEN: // Not possible case
        JOptionPane.showMessageDialog(this,
            "CASE IS NOT POSSIBLE: Showing SelectDB panel when projecttype is OPEN ");
        return false;
    }

    return (true);
  }

  public boolean next() {
    log.info("Passa per next SELECT DB");

    String nametxt = this.projectName.getText();
    if (nametxt.trim().equals("")) {
      JOptionPane.showMessageDialog(new Frame(), "'Project Name' esta buit",
          "error", JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }
    SharedData.data.projectName = nametxt.trim();
    
    String ms = modulSeguretat.getText();
    if (ms.trim().equals("")) {
      JOptionPane.showMessageDialog(new Frame(), "'Modul de Seguretat' esta buit",
          "Error", JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }
    SharedData.data.modulSeguretat = ms;
    
    
    String packtxt = this.pack.getText();
    if (packtxt.trim().equals("")) {
      JOptionPane.showMessageDialog(new Frame(), "'Package' esta buit",
          "error", JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }
    SharedData.data.setPackageName(packtxt.trim());
    
   
    String prefixtxt = this.prefix.getText();
    if (prefixtxt.trim().equals("")) {
      JOptionPane.showMessageDialog(new Frame(), "'Prefix' esta buit",
          "error", JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }
    SharedData.data.setPrefix(prefixtxt.trim());
    
    // idiomes
    String idiomestxt = this.idiomes.getText();
    if (idiomestxt.trim().equals("")) {
      JOptionPane.showMessageDialog(new Frame(), "'Idiomes' esta buit",
          "error", JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }
    String[] langs = idiomestxt.replace(" ", "").split(","); 
    if (langs.length == 0) {
      JOptionPane.showMessageDialog(new Frame(), "'Idiomes' esta mal escrit",
          "error", JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }
    SharedData.data.setLanguages(langs);
    
    
    SharedData.data.setGenerateBack(generateBack.isSelected());
    
    SharedData.data.setGenerateWS(generateWs.isSelected());
    

    String urltxt = this.url.getText();
    if (urltxt.equals("")) {
      JOptionPane.showMessageDialog(new Frame(), "'url' esta buit", "error",
          JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }

    String drivertxt = (String) this.driver.getText();
    if (drivertxt.equals("")) {
      JOptionPane.showMessageDialog(new Frame(), "'driver' esta buit", "error",
          JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }

    try {
      SharedData.data.dataBaseInfo = new DataBaseInfo(drivertxt, urltxt,
          usr.getText(), pwd.getText());
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null,
          "error connectatnt amb bd\n" + e.getMessage(), "error",
          JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }

    String[] tablesFromDB;
    DatabaseManager manager;
    try {
      DataBaseInfo.reuseConnection = true;
      manager = new DatabaseManager(SharedData.data.dataBaseInfo);
      
      
      
      switch(SharedData.project) {
      
        
        
        case NEW:
        {
          TableInfo tmp[] = manager.getTablesOfDataBase(null, true, SharedData.data.getLanguages());
          SelectionTablesDialog dlg = new SelectionTablesDialog(tmp);
          dlg.setVisible(true);
          tablesFromDB = dlg.getSelecteds();
          SharedData.data.setSchema(DatabaseManager.schemaName);
        }
        break;
        
        case UPDATE:
        { 
          TableInfo[] ti = SharedData.data.tables;
          String[] selected = new String[ti.length];
          
          for (int i = 0; i < ti.length; i++) {
            selected[i] = ti[i].name;
          }
          TableInfo tmp[] = manager.getTablesOfDataBase(SharedData.data.schema, false, SharedData.data.getLanguages());
          SelectionTablesDialog dlg = new SelectionTablesDialog(tmp, selected);
          dlg.setVisible(true);
          tablesFromDB = dlg.getSelecteds();
        }        
        break;

        default:
        case OPEN:
          TableInfo tmp[] = manager.getTablesOfDataBase(SharedData.data.schema, false, SharedData.data.getLanguages());
          tablesFromDB = TableInfo.tableInfoToTableName(tmp);
        break;
        
      } // Final Switch
      
      
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null,
          "Error Seleccionant taules de la BD\n" + e.getMessage(), "error",
          JOptionPane.INFORMATION_MESSAGE);
      return (false);
    }
    
    switch(SharedData.project) {

      case NEW:
        try {
          
          TableInfo[] selectedTables = new TableInfo[tablesFromDB.length];
          for (int i = 0; i < tablesFromDB.length; i++) {
            if (tablesFromDB[i].indexOf('$') != -1) {
              continue;
            }
            
            String[] idiomes = SharedData.data.getLanguages();
            
            log.info(" TABLA [ " + tablesFromDB[i] + "]");
            selectedTables[i] = new TableInfo(tablesFromDB[i], idiomes);
            Fields fields = manager.getFieldsInfoOfTable(SharedData.data.getSchema(), tablesFromDB[i], idiomes);
            selectedTables[i].setFields(fields.fields);
            selectedTables[i].setGenerate(true);
            selectedTables[i].setMultipleUniques(DatabaseManager.getMultipleUniques(fields));
          }
          SharedData.data.setTables(selectedTables);
        } catch (Exception e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(null,
              "Error Seleccionant els camps de taules de la BD\n"
              + e.getMessage(), "error",
              JOptionPane.INFORMATION_MESSAGE);
          return (false);
        }
      break;
      
      case UPDATE:
        // Compare tables
        TableInfo[] fromFile = SharedData.data.getTables();
        TableInfo[] mergedTables;
        try {
          mergedTables = mergeTables(manager, fromFile, tablesFromDB, SharedData.data.getLanguages());
        } catch (Exception e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(null,
              "Error Actualitzant els camps de les taules del fitxer" +
              " de projecte amb la informació de la BD\n" + e.getMessage(), "error",
              JOptionPane.INFORMATION_MESSAGE);
          return (false);
        }

        SharedData.data.tables = mergedTables;

      break;

    }
    return true;
  }
  
  public void itemStateChanged(ItemEvent e) {
    String s = (String) e.getItem();
    for (int i = 0; i < DatabaseManager.sJDBCTypes.length; i++) {
      if (s.equals(DatabaseManager.sJDBCTypes[i][0])) {
        driver.setText(DatabaseManager.sJDBCTypes[i][1]);
        url.setText(DatabaseManager.sJDBCTypes[i][2]);
      }
    }
  }

  
  
  public TableInfo[] mergeTables(DatabaseManager manager, TableInfo[] fromFile,
    String[] fromDB, String[] idiomes) throws Exception { 

    String[] fromFileNames = new String[fromFile.length];
    for (int i = 0; i < fromFile.length; i++) {
      fromFileNames[i] = fromFile[i].name;
    }
    ArrayList<String> fromDBNames = new ArrayList<String>();
    for (int i = 0; i < fromDB.length; i++) {
      fromDBNames.add(fromDB[i]);
    }
    
    Changes<String> changes = new Changes<String>(fromFileNames, fromDBNames);
    
    ArrayList<String> updateItems = changes.getUpdateItems();

    ArrayList<TableInfo> tableInfoUpdated = new ArrayList<TableInfo>();
    
    log.info("\n>>>>>>>>>>>>>>>>>  UPDATE TABLES <<<<<<<<<<<<<<<<<\n");

    // 1.- UPDATE EXISTENT TABLES
    for (int i = 0; i < fromFile.length; i++) {
      String nameBBDD = fromFile[i].getName();
      String nameJava = fromFile[i].getNameJava();      
      if (nameBBDD == null) {
        nameBBDD = nameJava;
      }
      String shortName = fromFile[i].getShortName();
      Map<String, String> labels = fromFile[i].getLabels();
      if (labels == null || labels.size() == 0) {
        for(String lang : idiomes) {
         labels.put(lang, nameBBDD);
        }
      } 
      
      Map<String, String> labelsPlural = fromFile[i].getLabelsPlural();
      if (labelsPlural == null || labelsPlural.size() == 0) {
        for(String lang : idiomes) {
         labelsPlural.put(lang, nameBBDD);
        }
      } 
      
      if (updateItems.contains(nameBBDD)) {
        
        Fields fields = manager.getFieldsInfoOfTable(SharedData.data.getSchema(), nameBBDD, idiomes);
        
        FieldInfo[] fieldsFromDB = fields.fields;
        
        
        log.info("\n  >>>>>>> " + i + ".- UPDATE TABLE " + nameBBDD );
        FieldInfo[] fieldsFromFile = fromFile[i].getFields();
        // 1.1.- UPDATE FIELDS OF TABLE
        
        Map<String, FieldInfo> fieldsFromDBMap = new Hashtable<String, FieldInfo>();
        for (FieldInfo fieldInfo : fieldsFromDB) {
          fieldsFromDBMap.put(fieldInfo.sqlName, fieldInfo);
        }
        
        Changes<FieldInfo> changesFields;
        changesFields = new Changes<FieldInfo>(fieldsFromFile, fieldsFromDB);
        
        ArrayList<FieldInfo> updatedFields = changesFields.getUpdateItems();
        ArrayList<FieldInfo> mergedFields = new ArrayList<FieldInfo>();
        
        for (int j = 0; j < fieldsFromFile.length; j++) {
          
          if (fieldsFromFile[j].isTransientField()) {
            mergedFields.add(fieldsFromFile[j]);
            continue;
          }
          
          if (updatedFields.contains(fieldsFromFile[j])) {
            log.info("    "
                + j + ".-Update Field[" + fieldsFromFile[j].sqlName);
            FieldInfo fieldInfo_File = fieldsFromFile[j];
            FieldInfo fieldInfo_DB = fieldsFromDBMap.get(fieldsFromFile[j].sqlName);

            fieldInfo_DB.isNotNullable = fieldInfo_File.isNotNullable;
            
            fieldInfo_DB.setLabels(fieldInfo_File.getLabels());
            
            fieldInfo_DB.javaName = fieldInfo_File.javaName;
            fieldInfo_DB.setJavaType(fieldInfo_File.getJavaType());
            
            fieldInfo_DB.setShowInReferenceList(fieldInfo_File.isShowInReferenceList());
            fieldInfo_DB.setShowInWebList(fieldInfo_File.isShowInWebList());            
            fieldInfo_DB.setTraduible(fieldInfo_File.isTraduible());
            fieldInfo_DB.setTransientField(fieldInfo_File.isTransientField());

            
            
            // Validació, Valor menor
            fieldInfo_DB.setMinAllowedValue(fieldInfo_File.getMinAllowedValue());
            
            //Validació, Valor major
            fieldInfo_DB.setMaxAllowedValue(fieldInfo_File.getMaxAllowedValue());
            
            // Anotacions adicionals
            fieldInfo_DB.setAdditonalAnnotations(fieldInfo_File.getAdditonalAnnotations());
            
            // Aplicar filter per aquest camp
            fieldInfo_DB.setFilter(fieldInfo_File.isFilter());
            
            // Unic per Grup de camps
            //fieldInfo_DB.setUniqueGroup(fieldInfo_File.isUniqueGroup());

            // 0 = No ordernat || >0 = Ordre ascendent || <0 Ordre descendent
            fieldInfo_DB.setOrderBy(fieldInfo_File.getOrderBy());
            
            // groupBy
            fieldInfo_DB.setGroupBy(fieldInfo_File.isGroupBy());
            
            //fieldInfo_DB.set(fieldInfo_File.is);

            if (fieldInfo_File.getDescripcio() != null) {
              fieldInfo_DB.setDescripcio(fieldInfo_File.getDescripcio());
            }
            if (fieldInfo_File.getDefaultValue() != null) {
              fieldInfo_DB.setDefaultValue(fieldInfo_File.getDefaultValue());
            }

            WebFieldInfo[] webTypes;
            webTypes = WebFieldInfo.getWebTypesFromJavaType(nameBBDD, fieldInfo_DB);
            boolean found = false;
            for (int k = 0; k < webTypes.length; k++) {
              if (webTypes[k].webtype == fieldInfo_File.webFieldInfo.webtype) {
                fieldInfo_DB.webFieldInfo = fieldInfo_File.webFieldInfo;
                found = true;
                break;
              }
            }
            if (!found) {
              fieldInfo_DB.webFieldInfo = webTypes[0];
            }

            mergedFields.add(fieldInfo_DB);
          }
        }

        // 1.2.- ADD NEW FIELDS
        ArrayList<FieldInfo> newFields = changesFields.getNewItems();
        for (FieldInfo fieldInfo : newFields) {
          log.info("    -  NEW  Field[" + fieldInfo.sqlName);
          mergedFields.add(fieldInfo);
        }
        
        // 1.3.- CREATE NEW TABLE INFO
        
        TableInfo tableInfo = new TableInfo(nameBBDD, nameJava, shortName, labels, labelsPlural,
            mergedFields.toArray(new FieldInfo[mergedFields.size()]),
            fromFile[i].generate);
        
        tableInfo.setMultipleUniques(DatabaseManager.getMultipleUniques(fields));
        
        tableInfo.setFileMappingEntity(fromFile[i].isFileMappingEntity());
        tableInfo.setLanguageMappingEntity(fromFile[i].isLanguageMappingEntity());
        tableInfo.setTranslationEntity(fromFile[i].isTranslationEntity());
        tableInfo.setTranslationMapEntity(fromFile[i].isTranslationMapEntity());

        tableInfoUpdated.add(tableInfo);
        
        

      }
    }
    
    // 2.- ADD NEW TABLES
    ArrayList<String> newItems = changes.getNewItems();
    log.info(">>>>>>>>>>>>>>>>>  NEW TABLES ("
        + newItems.size() + ") <<<<<<<<<<<<<<<<<");
    for (String name : newItems) {
      log.info("     +++ NEW TABLE " + name );
      TableInfo newT = new TableInfo(name, idiomes);
      Fields fields = manager.getFieldsInfoOfTable(SharedData.data.getSchema(), name, idiomes); 
      newT.setFields(fields.fields);
      newT.setGenerate(true);
      newT.setMultipleUniques(DatabaseManager.getMultipleUniques(fields));
      tableInfoUpdated.add(newT);
    }
    return tableInfoUpdated.toArray(new TableInfo[tableInfoUpdated.size()]);

  }
  
  
  public static class Changes <T>{
    
    /**
     * Array is necessary to maintain order.
     */
    final T[] fromFile;
    
    final ArrayList<T> fromDB;
    
    final ArrayList<T> deletedItems = new ArrayList<T>();
    
    final ArrayList<T> newItems = new ArrayList<T>();
    
    final ArrayList<T> updateItems = new ArrayList<T>();




    /**
     * 
     * @param fromFile Array is necessary to maintain order.
     * @param fromDB
     */
    public Changes(T[] fromFile, ArrayList<T> fromDB) {
      super();
      this.fromFile = fromFile;
      this.fromDB = fromDB;
      compute();
    }
    
    public Changes(T[] fromFile, T[] fromDBArray) {
      super();
      this.fromDB = new  ArrayList<T>();
      for (int i = 0; i < fromDBArray.length; i++) {
        fromDB.add(fromDBArray[i]);
      }
      this.fromFile = fromFile;
      
      compute();
    }


    public T[] getFromFile() {
      return fromFile;
    }


    public ArrayList<T> getFromDB() {
      return fromDB;
    }

    public ArrayList<T> getDeletedItems() {
      return deletedItems;
    }
    
    public ArrayList<T> getUpdateItems() {
      return updateItems;
    }
    
    public ArrayList<T> getNewItems() {
      return newItems;
    }

    public void addDeletedItem(T deletedFromFile) {
      this.deletedItems.add(deletedFromFile);
    }

    public void addUpdateItem(T toUpdate) {
      this.updateItems.add(toUpdate);
    }

    public void addNewItem(T newItem) {
      this.newItems.add(newItem);
    }

    private void compute() {
      for (int i = 0; i < fromFile.length; i++) {
        if (fromDB.contains(fromFile[i])) {
          addUpdateItem(fromFile[i]);
        } else {
          addDeletedItem(fromFile[i]);
        }
      }
      for (T dbItem:fromDB) {
        if (!this.updateItems.contains(dbItem)) {
          addNewItem(dbItem);
        }
      }
    }
    
  }
  
  
}