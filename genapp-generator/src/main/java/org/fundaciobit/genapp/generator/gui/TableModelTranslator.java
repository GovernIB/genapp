package org.fundaciobit.genapp.generator.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.TraduibleInfo;

/**
 * 
 * @author anadal
 *
 */
public class TableModelTranslator extends AbstractTableModel {
  
  protected final Logger log = Logger.getLogger(getClass().getSimpleName());

  protected final String[] idiomes;

  final int countRow;
  
  //final Map<String, Integer> langToColumn = new HashMap<String, Integer>();
  
  final Map<Integer, String> columnToLang = new HashMap<Integer, String>();
  
  
  //final Map<Object, Integer> itemToRow = new HashMap<Object, Integer>();
  
  final Map<Integer, TraduibleItem> rowToTraduible = new HashMap<Integer, TraduibleItem>();
  
  public enum TraduibleType { TABLE_SINGULAR, TABLE_PLURAL , FIELD }
  
  
  public TableModelTranslator(TableInfo[] tables, String[] idiomes) {
    int langIndex = 0;
    for (String lang : idiomes) {
      langIndex++; // columnes idioma comencen per 1
      columnToLang.put(langIndex, lang);
    }
    
    
    // JAVA NAME

    int row = -1; //tables.length;
    for (TableInfo ti : tables) {
      row++;
      rowToTraduible.put(row, new TraduibleItem(ti.getNameJava() , ti, TraduibleType.TABLE_SINGULAR));
      
      row++;
      rowToTraduible.put(row, new TraduibleItemPlural(ti));
      
      for(FieldInfo fi : ti.getFields()) {
        row++;
        rowToTraduible.put(row, new TraduibleItem(ti.getNameJava() + "." + fi.getJavaName(), fi, TraduibleType.FIELD));
      }      
    }
    countRow = row;
    this.idiomes = idiomes;
  }
  
  
  
  public Color getBkgColour(int row, int column) {
    if (column != 0) {
      String lang = columnToLang.get(column);
      String value = (String) getValueAt(row, column);
      if (value.endsWith("_XX_" + lang)) {
        return Color.red;
      }
    }
    TraduibleItem info = rowToTraduible.get(row);
    switch (info.type) {
      case TABLE_SINGULAR:
        return Color.BLUE;
        
      case TABLE_PLURAL:
        return new Color(0 , 0, 120);
        
      default:
      case FIELD:
        return Color.WHITE;
        
    }
    
  }
  
  public Color getTextColour(int row, int column) {
    TraduibleItem info = rowToTraduible.get(row);
    if (info.type == TraduibleType.FIELD) {
      return Color.BLACK;
    } else {
      return Color.WHITE;
    }
  }
  
  
  public boolean isCellEditable(int row, int column) {
    
    //log.info("EDITABLE: " + row + " , " + column);
    return  (column != 0);
    
  }
  
  
  
  @Override
  public int getRowCount() {
    return countRow;
  }

  @Override
  public int getColumnCount() {
    // JAVA + #etiquetes langs
    return 1 +  idiomes.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    TraduibleItem info = rowToTraduible.get(rowIndex);
    if (columnIndex == 0) {
      return info.getName();
    } else {
      String lang =  this.columnToLang.get(columnIndex);
      return info.getTraduibleInfo().getLabels().get(lang);
    }
  }

  
  public void setValueAt(Object value, int row, int column) {
    log.info("Passa per set value (" +  value + ", " + row + ", " + column +")");
    TraduibleItem info = rowToTraduible.get(row);
    TraduibleInfo trad = info.getTraduibleInfo();
    
    String lang =  this.columnToLang.get(column);
    trad.getLabels().put(lang, (String) value);
  }
  
  
  
  static class MyTableCellRenderer extends DefaultTableCellRenderer {
  
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          TableModelTranslator model = (TableModelTranslator) table.getModel();
          Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
  
          Color col = model.getBkgColour(row, column);
          c.setBackground(col);

          col = model.getTextColour(row, column);
          c.setForeground(col);

          return c;
      }
  }
  
  
  public static JTable getJTable(TableInfo[] tables, String[] idiomes) {
    final JTableTranslator table = new JTableTranslator();
    table.setDefaultRenderer(Object.class, new MyTableCellRenderer());
    
    table.setModel(new TableModelTranslator(tables,  idiomes));
    
    TableColumnModel model = table.getColumnModel();
    {
      TableColumn col = model.getColumn(0);
      col.setHeaderValue("Nom");
    }
    
    
    for (int i = 0; i < idiomes.length; i++) {
      TableColumn col = model.getColumn(1 + i);
      col.setHeaderValue(idiomes[i].toUpperCase());
      //col.setCellRenderer(new TextAreaRenderer());
      col.setCellEditor(new TextAreaEditor());
    }
    
    return table;
    
  }
  
  
  static class JTableTranslator extends JTable {
    
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
      TableColumn tableColumn = getColumnModel().getColumn(column);
      TableCellRenderer renderer = tableColumn.getCellRenderer();
      if (renderer == null) {
          renderer = getDefaultRenderer(getColumnClass(column));
      }
      return renderer;
    }
  }
  
  
  public static class TraduibleItem {
    
    final TraduibleInfo tradInfo;
    
    final TraduibleType type;

    final String name;
    
    /**
     * @param tableInfo
     * @param fieldInfo
     */
    public TraduibleItem(String name, TraduibleInfo tradInfo, TraduibleType type) {
      super();
      this.tradInfo = tradInfo;
      this.type = type;
      this.name = name;
    }



    public TraduibleInfo getTraduibleInfo() {
      
      return tradInfo;
      
    }
    
    
    public String getName() {
      return name;
    }
    
  }
  
  
  public static class TraduibleItemPlural extends TraduibleItem {
    
    final TableInfo tableInfo;

    /**
     * @param tableInfo
     */
    public TraduibleItemPlural(TableInfo tableInfo) {
      super(tableInfo.getNameJava() + "(Plural)", tableInfo, TraduibleType.TABLE_PLURAL);
      this.tableInfo = tableInfo;
    }

    @Override
    public TraduibleInfo getTraduibleInfo() {
      return new TraduibleInfo() {
        @Override
        public Map<String, String> getLabels() {
          // TODO Auto-generated method stub
          return tableInfo.getLabelsPlural();
        }
      }; 
    }
    
  }
  
  
}
