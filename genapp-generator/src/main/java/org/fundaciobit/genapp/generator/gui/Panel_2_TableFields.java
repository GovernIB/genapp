package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.generator.gui.SharedData.ProjectType;
import org.fundaciobit.genapp.generator.gui.TableModelColumnList.ColumnConfig;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */
public class Panel_2_TableFields extends Paneable {

  private final Logger log = Logger.getLogger(getClass().getSimpleName());
  
  /**
   * 
   */
  private static final long serialVersionUID = 8826700913771104771L;
  // TABLE NAME - TITOL
  JLabel etiTableName = new JLabel();
  JPanel panelTableName = new JPanel();
  // Checkbox generar
  JPanel panelGenerateTable = new JPanel();
  JLabel etiGenerateTable = new JLabel("Generar");
  JCheckBox generateTable = new JCheckBox();
  // Nom Taula Java
  JLabel etiNomTaulaJava = new JLabel("Nom Java:");
  JTextField txtNomTaulaJava = new JTextField(15);
  // Nom Taula Web
  
  //JLabel etiNomTaulaWeb = new JLabel();
//= new JTextField(30);
  //Map<String,JTextField> labels  = new HashMap<String, JTextField>();
  
  
  // Boto de Edició de camps de la taula
  JButton botoEditaTableInfo2 = new JButton("Edita");
  
  // Boto de Edició de camps de la taula
  JButton botoTraduccions = new JButton("Traduccions");
  
  JButton gotoDarreraTaula;

  // OTHER
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel panelInfo = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel panelTablas = new JPanel();

  BorderLayout borderLayout2 = new BorderLayout();

  JLabel etiURL = new JLabel();
  JLabel etiURLtxt = new JLabel();

  JLabel etiDriver = new JLabel();
  JLabel etiDrivertxt = new JLabel();

  JLabel etiUser = new JLabel();
  JLabel etiUsertxt = new JLabel();

  final FieldsJTable taula = new FieldsJTable();
  final MouseAdapterUpDown mouseAdapter = new MouseAdapterUpDown(taula);
  
  final JFrame frame;

  int index = 0;
  
  boolean anarADarreraTaula = false;

  // protected Panel_2_TableList selectTable = null;

  public Panel_2_TableFields(JFrame frame) {
    this.frame = frame;
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean initPanel() {
    try {
      etiURL.setText("URL: " + SharedData.data.dataBaseInfo.getUrl());
      etiDriver.setText("Driver: " + SharedData.data.dataBaseInfo.getDriver());

      final int defIndex = 0;

      taula.addMouseListener(mouseAdapter);

      initTable(defIndex);
      taula.setShowGrid(true);
      taula.setShowHorizontalLines(true);
      taula.setShowVerticalLines(true);

      TableCellRenderer defaultRenderer;

      defaultRenderer = taula.getDefaultRenderer(JButton.class);
      taula.setDefaultRenderer(JButton.class, new JTableButtonRenderer(
          defaultRenderer));

   
      return (true);
    } catch (Exception ex) {
      ex.printStackTrace();
      return (false);
    }

  }

  private void initTable(int defIndex) throws Exception {

    TableInfo tableInfo = SharedData.data.tables[defIndex];

    updateTableInfo(tableInfo);

    String tableName = tableInfo.getName();

    TableModelColumnList tmcl = new TableModelColumnList(tableName,
        tableInfo.getFields());
    taula.setModel(tmcl);

    TableColumnModel tcm = taula.getColumnModel();
    for (int i = 0; i < tcm.getColumnCount(); i++) {
      TableColumn column = tcm.getColumn(i);
      column.setPreferredWidth(ColumnConfig.fromId(i).width);
    }

    TableColumnModel colModel = taula.getColumnModel();
    
    ColumnConfig[] areaColumns = new ColumnConfig[] {
        ColumnConfig.FOREIGNKEYS, ColumnConfig.WEBQUERY, ColumnConfig.WEBQUERYVALUE
      };
    for (int i = 0; i < areaColumns.length; i++) {
      TableColumn col = colModel.getColumn(areaColumns[i].index);
      col.setCellRenderer(new TextAreaRenderer());
      col.setCellEditor(new TextAreaEditor());
    }

    /*
    TableColumn col = taula.getColumnModel().getColumn(ColumnConfig.WEBTYPE.index);
    col.setCellEditor(new MyComboBoxEditor(values));
    // If the cell should appear like a combobox in its
    // non-editing state, also set the combobox renderer
    col.setCellRenderer(new MyComboBoxRenderer(values)); 
    */
    

    taula.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    taula.setAutoscrolls(true);

    taula.setRowHeight(50);
    taula.setPreferredScrollableViewportSize(taula.getPreferredSize());

    etiTableName.setText("TAULA " + tableName + " (" + (defIndex + 1) + "/"
        + SharedData.data.tables.length + ")");

    generateTable.setSelected(tableInfo.generate);
  }

  private void updateTableInfo(TableInfo tableInfo) {
    this.generateTable.setSelected(tableInfo.isGenerate());
    this.txtNomTaulaJava.setText(tableInfo.getNameJava());
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    etiURL.setHorizontalAlignment(SwingConstants.CENTER);
    
    etiDriver.setHorizontalAlignment(SwingConstants.CENTER);
    
    panelInfo.setLayout(gridLayout1);
    panelTablas.setLayout(borderLayout2);
    panelTablas.setBorder(BorderFactory.createEtchedBorder());

    panelGenerateTable.add(new JLabel("   |   "));
    panelGenerateTable.add(generateTable, null);    
    panelGenerateTable.add(etiGenerateTable, null);
    panelGenerateTable.add(new JLabel("   |   "));
    panelGenerateTable.add(etiNomTaulaJava, null);
    panelGenerateTable.add(txtNomTaulaJava, null);
    //panelGenerateTable.add(new JLabel("   |   Label: " + this.taula.getL ));
    //panelGenerateTable.add(etiNomTaulaWeb, null);
    //panelGenerateTable.add(txtNomTaulaWeb, null);

    panelGenerateTable.add(botoEditaTableInfo2, null);
    
    botoEditaTableInfo2.addActionListener(new ActionListener() {
      
      public void actionPerformed(ActionEvent e) {
        TableInfo tableInfo = SharedData.data.tables[index];
        EditTableInfo editor = new EditTableInfo(frame, tableInfo, SharedData.data.getLanguages());
        editor.setVisible(true);
        
        if (editor.clickOK) {
          editor.updateFieldInfoWithNewValues();
        }

        updateTableInfo(tableInfo);
        log.info(" ---------- EDIT TAULA ------");        
      }
    });
    
    
    panelGenerateTable.add(botoTraduccions, null);
    
    botoTraduccions.addActionListener(new ActionListener() {
      
      public void actionPerformed(ActionEvent e) {
       
        
        JDialog dialog = new JDialog(frame);
        JTable table = TableModelTranslator.getJTable(SharedData.data.tables, SharedData.data.getLanguages());
        dialog.add( new JScrollPane(table));
        
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
        dialog.pack();
        
        dialog.setVisible(true);
        
       
        log.info(" ---------- EDIT TAULA ------");        
      }
    });
    

    gotoDarreraTaula = new JButton("Anar a Guardar");

    panelGenerateTable.add(gotoDarreraTaula, null);

    gotoDarreraTaula.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        anarADarreraTaula = true;
        gotoDarreraTaula.setEnabled(false);
        gotoDarreraTaula.setText("Pitja el boto de next.");
      }
    });
    
    
    
    
    
    
    

    panelTableName.add(etiTableName, BorderLayout.NORTH);
    panelTableName.add(panelGenerateTable, BorderLayout.SOUTH);
    this.add(panelTableName, BorderLayout.NORTH);

    this.add(panelInfo, BorderLayout.SOUTH);
    panelInfo.add(etiDriver, null);
    panelInfo.add(etiURL, null);
    

    
    panelTablas.add(taula, BorderLayout.CENTER);
    panelTablas.add(taula.getTableHeader(), BorderLayout.NORTH);
    
    
    JScrollPane scroll = new JScrollPane(panelTablas);
    //scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    //this.add(panelTablas, BorderLayout.CENTER);
    this.add(scroll, BorderLayout.CENTER);
    
    
    txtNomTaulaJava.addActionListener(new ActionListener() {
      
      public void actionPerformed(ActionEvent e) {
        /*
        String val = txtNomTaulaJava.getText().trim();
        /*
        if (val.length() > 0) {
          txtNomTaulaWeb.setText(TableModelColumnList.convertToLabel(val));
        }
        */
        
        System.err.println("Modificat les etiquetes de la taula: " + txtNomTaulaJava.getText().trim());
        
      }
    });
    
    
    
    this.generateTable.addChangeListener(new ChangeListener() {

      //@Override
      public void stateChanged(ChangeEvent e) {

        log.info(" generateTable.isSelected(): "
            + generateTable.isSelected());

        log.info(" Valor actual: "
            + SharedData.data.tables[index].generate);

        SharedData.data.tables[index].generate = generateTable.isSelected();
      }
    });
  }

  /** Indica si desde el siguiente panel podemos volver otra vez a este */
  public boolean previus() {

     log.info("PREVIOUS: PASSA PER TABLE FIELDS");

    if (this.index - 1 == -1) {
      if (SharedData.project == ProjectType.NEW) {
        return true;
      }
    } else {
      index--;
      try {
        initTable(index);
      } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showInternalMessageDialog(this, "Error: " + e.getMessage());
      }
    }
    return (false);
  }

  public boolean next() {

    // Fer-ho per totes les taules
     {
      // Verificar NomWeb i NomJava
      this.txtNomTaulaJava.setBackground(Color.white);
      String nomJava = this.txtNomTaulaJava.getText().trim();
      if ("".equals(nomJava) || nomJava.indexOf(' ') != -1 ) {
        this.txtNomTaulaJava.setBackground(Color.red);
        return false;
      } else {
        SharedData.data.tables[index].setNameJava(nomJava);
      }
      /*
      this.txtNomTaulaWeb.setBackground(Color.white);
      String nomWeb = this.txtNomTaulaWeb.getText().trim();
      if ("".equals(nomWeb)) {
        this.txtNomTaulaWeb.setBackground(Color.red);
        return false;
      } else {
        SharedData.data.tables[index].setLabel(nomWeb);
      }
      */
    }

    if (this.index + 1 == SharedData.data.tables.length || anarADarreraTaula) {
      return true;
    } else {
      // Passar seg�ent taula
      index++;
      try {
        initTable(index);
      } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showInternalMessageDialog(this, "Error: " + e.getMessage());
      }
    }

    /*
     * boolean isPK; for (int r = 0; r < model.getRowCount(); r++) { isPK =
     * ((Boolean) model.getValueAt(r,
     * TableModelColumnList.PRIMARY_KEY)).booleanValue();
     * 
     * if (isPK) return (true); }
     * 
     * JOptionPane.showMessageDialog(new Frame(),
     * "No ha seleccionado ninguna Clave Primaria", "error",
     * JOptionPane.INFORMATION_MESSAGE);
     */
    return (false);
  }

  public boolean isFinalizable() {
    return false; //!(this.index + 1 == SharedData.data.tables.length);
  }

  class JTableButtonRenderer implements TableCellRenderer {
    private TableCellRenderer __defaultRenderer;

    public JTableButtonRenderer(TableCellRenderer renderer) {
      __defaultRenderer = renderer;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      if (value instanceof Component)
        return (Component) value;
      return __defaultRenderer.getTableCellRendererComponent(table, value,
          isSelected, hasFocus, row, column);
    }
  }

  public class MouseAdapterUpDown extends MouseAdapter {

    final JTable taula;

    public MouseAdapterUpDown(JTable taula) {
      super();
      this.taula = taula;
    }

    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() != 1) {
        return;
      }
      JTable target = (JTable) e.getSource();
      int row = target.getSelectedRow();
      int column = target.getSelectedColumn();
      //log.info(" TTTTTTTTTTT " + row + " AAAAAAAAAAAAA  " + column);
      TableInfo tableInfo = SharedData.data.getTables()[index];

      FieldInfo[] fields = tableInfo.fields;
      
      if (column == ColumnConfig.EDIT.index) {
        
        EditFieldInfo editor = new EditFieldInfo(frame, fields[row],
            SharedData.data.getLanguages());
        editor.setVisible(true);
        
        if (editor.clickOK) {
          editor.updateFieldInfoWithNewValues();
        }
        
        
        taula.repaint();
        log.info(" ---------- EDIT CAMP ------");
        return;
      }


      FieldInfo tmpField;
      
      
      
      boolean isUp = (ColumnConfig.UP.index == column);
      boolean isDown = (ColumnConfig.DOWN.index == column);

      if (isUp) {
        if (row == 0) {
          log.info("NO FER RES: UP i Primera Fila.");
          return;
        }

        tmpField = fields[row - 1];
        fields[row - 1] = fields[row];
        fields[row] = tmpField;

        taula.repaint();
        log.info(" ---------- UP ------");
        return;
      }

      if (isDown) {

        if (row == (fields.length - 1)) {
          log.info("NO FER RES: DOWN i Darrera Fila.");
          return;
        }

        tmpField = fields[row + 1];
        fields[row + 1] = fields[row];
        fields[row] = tmpField;
  
        taula.repaint();
        log.info(" ---------- DOWN ------");
      }
    }
  }
  
  

  public class MyComboBoxRenderer implements TableCellRenderer {

    //private static final long serialVersionUID = -2782386544682856105L;

    public MyComboBoxRenderer() {
      super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      /*
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        super.setBackground(table.getSelectionBackground());
      } else {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
      } 
      // Select the current value
      setSelectedItem(value);
      */
      //Object value = table.getValueAt(row, column);
      return (JComboBox<?>)value;
    }
    
  }
  /*
  public class MyComboBoxEditor extends DefaultCellEditor {
    public MyComboBoxEditor(String[] items) {
      super(new JComboBox(items)); 
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected,
        int row, int column) {
      delegate.setValue(value);
      return editorComponent;
    }
  }
  */
  
  
  class FieldsJTable extends JTable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4553177104014699622L;

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
       Object value = super.getValueAt(row, column);
       if(value != null) {
          if(value instanceof JComboBox) {
               return new DefaultCellEditor((JComboBox<?>)value);
          }
          return getDefaultEditor(value.getClass());
       }
       return super.getCellEditor(row, column);
    }
    
    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
      Object value = super.getValueAt(row, column);
      if(value != null) {
         if(value instanceof JComboBox) {
           return new MyComboBoxRenderer();
         }
      }
      TableColumn tableColumn = getColumnModel().getColumn(column);
      TableCellRenderer renderer = tableColumn.getCellRenderer();
      if (renderer == null) {
          renderer = getDefaultRenderer(getColumnClass(column));
      }
      return renderer;
  }

    
  }
  
  
}