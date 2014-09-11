package org.fundaciobit.genapp.generator.gui;

/*

 */

/**
 * TODO An adaptor, transforming the JDBC interface to the TableModel interface.
 **
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.WebFieldInfo;
import org.fundaciobit.genapp.WebType;
import org.fundaciobit.genapp.common.GenAppUtils;
import org.fundaciobit.genapp.generator.SQL2Java;


public class TableModelColumnList extends AbstractTableModel {

  /*
   * public static final int PRIMARY_KEY=; public static final int NOT_NULL=;
   * public static final int AUTOINCREMENT=; public static final int JAVA_NAME=;
   * public static final int JAVA_TYPE=; public static final int SQL_NAME=;
   * public static final int SQL_TYPE=; public static final int SIZE=; public
   * static final int FOREIGNKEYS=; public static final int WEBTYPE=; public
   * static final int WEBADDTYPE=;
   */

  /**
   * 
   */
  private static final long serialVersionUID = -8790999167540053409L;

  public enum ColumnConfig {
    /*
     * public static String columnNames[] = { "PK", , , "Java Type", "SQL Name",
     * "SQL Type", "SIZE", "DIGITS", }; public static Class<?> columnClass[] = {
     * java.lang.Boolean.class, java.lang.Boolean.class, java.lang.String.class,
     * java.lang.String.class, java.lang.String.class, java.lang.String.class,
     * java.lang.String.class, java.lang.String.class, }; boolean
     * columnIsEditable[] = {false, false, true, true, false, false, false,
     * false, };
     */

    PRIMARY_KEY(0, "PK", Boolean.class, false, 29),
    NOT_NULL(1, "NN", Boolean.class, true, 29),
    AUTOINCREMENT(2, "AU", Boolean.class, true, 29),
    LABEL(3, "Label", String.class, false, 86),
    JAVA_NAME(4, "Java Name", String.class, true, 86),
    JAVA_TYPE(5, "Java Type", String.class, true,100),
    SQL_NAME(6, "SQL Name", String.class, false, 95),
    SQL_TYPE(7, "SQL Type", String.class, false, 95),
    SIZE(8, "Size", String.class, false, 35),
    FOREIGNKEYS(9, "ForeignKeys", String.class, false, 150),
    WEBTYPE(10, "WebType", JComboBox.class, true, 90),
    WEBQUERYVALUE(11, "QueryValue", String.class, true, 90),
    WEBQUERY(12, "WebQuery", String.class, false, 150),
    UP(13, "", JButton.class, false, 35),
    DOWN(14, "", JButton.class, false, 35),
    EDIT(15, "", JButton.class, false, 35);

    public final int index;

    public final String columnName;

    public final Class<?> javaType;

    public final boolean isEditable;
    
    public final int width;

    private ColumnConfig(int index, String columnName, Class<?> javaType,
        boolean isEditable, int width) {
      this.index = index;
      this.columnName = columnName;
      this.javaType = javaType;
      this.isEditable = isEditable;
      this.width = width;
    }

    public int getIndex() {
      return index;
    }

    public String toString() {
      return columnName;
    }

    /*
     * public static ColumnConfig fromString(final String str) { for
     * (ColumnConfig e : ColumnConfig.values()) { if
     * (e.toString().equalsIgnoreCase(str)) { return e; } } return null; }
     */
    public static ColumnConfig fromId(final int id) {
      for (ColumnConfig e : ColumnConfig.values()) {
        if (e.index == id) {
          return e;
        }
      }
      return null;
    }
  }

  /*
   * Each column description has the following columns: 1 TABLE_CAT String =>
   * table catalog (may be null) 2 TABLE_SCHEM String => table schema (may be
   * null) 3 TABLE_NAME String => table name 4 COLUMN_NAME String => column name
   * 5 DATA_TYPE short => SQL type from java.sql.Types 6 TYPE_NAME String =>
   * Data source dependent type name, for a UDT the type name is fully qualified
   * 7 COLUMN_SIZE int => column size. For char or date types this is the
   * maximum number of characters, for numeric or decimal types this is
   * precision. 8 BUFFER_LENGTH is not used. 9 DECIMAL_DIGITS int => the number
   * of fractional digits 10 NUM_PREC_RADIX int => Radix (typically either 10 or
   * 2) 11 NULLABLE int => is NULL allowed? columnNoNulls - might not allow NULL
   * values columnNullable - definitely allows NULL values columnNullableUnknown
   * - nullability unknown 12 REMARKS String => comment describing column (may
   * be null) 13 COLUMN_DEF String => default value (may be null) 14
   * SQL_DATA_TYPE int => unused 15 SQL_DATETIME_SUB int => unused 16
   * CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the
   * column 17 ORDINAL_POSITION int => index of column in table (starting at 1)
   * 18 IS_NULLABLE String => "NO" means column definitely does not allow NULL
   * values; "YES" means the column might allow NULL values. An empty string
   * means nobody knows. Parameters:catalog
   */

  // ResultSet resultSet;
  // ResultSetMetaData metaData;

  /*
   * ResultSet col=dMeta.getColumns(null,null,name,null); while(col.next()) {
   * String c=col.getString(4); String k1=key+"col-"+c+"-";
   * tTree.addRow(k1,c,"+",color_column); String type=col.getString(6);
   * tTree.addRow(k1+"t","Type: "+type); boolean
   * nullable=col.getInt(11)!=DatabaseMetaData.columnNoNulls;
   * tTree.addRow(k1+"n","Nullable: "+nullable); }
   */

  String tableName;

  FieldInfo[] data;
  
  // SQLNAme -> JCombobox
  Hashtable<String, JComboBox> mapComboBox = new Hashtable<String, JComboBox>();
  
  


  /**
   * 
   * @param taula
   * @param tableName
   * @throws Exception
   */
  public TableModelColumnList(String tableName, FieldInfo[] fields) throws Exception {
    this.tableName = tableName;
    this.data = fields;
    
    for (int j = 0; j < fields.length; j++) {
      FieldInfo row = fields[j];
      WebFieldInfo[] availables;
      int selected = 0;
      try {
        availables = WebFieldInfo.getWebTypesFromJavaType(tableName, row);
        for (int i = 0; i < availables.length; i++) {
          if (availables[i].webtype == row.webFieldInfo.webtype) {
            selected = i;
            availables[i] = row.webFieldInfo;
          }
        }
      } catch (Exception e) {
        availables = new WebFieldInfo[] { row.webFieldInfo };
      }
      JComboBox box = new JComboBox(availables);
      box.setSelectedIndex(selected);
      box.addActionListener(new JComboBoxChangeListener(row, box));
      mapComboBox.put(row.sqlName, box);
    }
    
    
  }

  // ////////////////////////////////////////////////////////////////////////
  //
  // Implementation of the TableModel Interface
  //
  // ////////////////////////////////////////////////////////////////////////

  // MetaData

  public String getColumnName(int column) {

    ColumnConfig config = ColumnConfig.fromId(column);

    return config.columnName;
  }

  public Class<?> getColumnClass(int column) {
    ColumnConfig config = ColumnConfig.fromId(column);

    return config.javaType;
  }

  public boolean isCellEditable(int row, int column) {
    ColumnConfig config = ColumnConfig.fromId(column);
    return config.isEditable;
  }

  public int getColumnCount() {
    return ColumnConfig.values().length;
  }

  // Data methods
  public int getRowCount() {
    return data.length;
  }

  public Object getValueAt(int aRow, int aColumn) {
    FieldInfo row = data[aRow];
    ColumnConfig config = ColumnConfig.fromId(aColumn);

    switch (config) {
      case PRIMARY_KEY:
        return row.isPrimaryKey();
      case NOT_NULL:
        return row.isNotNullable;
      case AUTOINCREMENT:
        return row.isAutoIncrement;
      case LABEL:
        return row.getLabels().get(SharedData.data.getLanguages()[0]);
      case JAVA_NAME:
        return row.javaName;
      case JAVA_TYPE: {
          Class<?> cl = row.getJavaType();
          return cl.getName();
        }
      case SQL_NAME:
        return row.sqlName;
      case SQL_TYPE:
        return SQL2Java.getSqlTypeName(row.sqlType) + " (" + row.sqlType + ")";
      case SIZE:
        return row.size;
      case FOREIGNKEYS: {
        StringBuffer txt = new StringBuffer();
        ForeignKey[] fk = row.getForeignKeys();
        for (int f = 0; f < fk.length; f++) {
          if (f != 0) {
            txt.append("\n");
          }
          txt.append('+').append(fk[f].getTable()).append('[');
          txt.append(fk[f].getField()).append("] (");
          txt.append(fk[f].getType()==ForeignKey.IMPORTED?"IMP":"EXP").append(")");
        }
        return txt.toString();
      }

      case WEBTYPE: {
        
        //log.info(" XXXXXXXXXXXXXX   GET VALUE WEBTYPE !!!!!!!!!!!! "
        //    + aRow + ".-" + row.javaName);
        JComboBox box = this.mapComboBox.get(row.sqlName);
        return box;
        
      }
/*     
      case WEBQUERYKEY: {
        WebFieldInfo webFI = row.webFieldInfo;
        if (webFI.webtype.equals(WebType.Query)) {
          return webFI.foreignKey.getField();
        } else {
          return "-";
        }
      }
      */
      case WEBQUERYVALUE: {
        WebFieldInfo webFI = row.webFieldInfo;
        String[] label = webFI.getLabel();
        if (label == null) {
          return null;
        } else {
          return GenAppUtils.stringArrayToString(label);
        }
        
      }

      case WEBQUERY: {
        WebFieldInfo webFI = row.webFieldInfo;
        if (webFI.webtype == WebType.Query) {
          return "Reference to " + webFI.foreignKey.getTable() + "["
            + webFI.foreignKey.getField() + "]";
        } else {
          return SharedData.data.createSelect(this.tableName, row, "");
        }
        /*
        WebFieldInfo webFI = row.webFieldInfo;
        if (webFI.webtype.equals(WebType.Query)) {
          return webFI.createSelect();
        } else {
          return row.javaName;
        }
        */
      }
      case UP:
        return createImageIcon("up.gif", aRow, true);

      case DOWN:
        return createImageIcon("down.gif", aRow, false);
        
      case EDIT:
        return createImageIcon("edit.gif", aRow, false);

      default:
        return "ERROR: Columna desconeguda " + aColumn;
    }
  }




  public void setValueAt(Object value, int row, int column) {

    FieldInfo field = data[row];
    ColumnConfig config = ColumnConfig.fromId(column);

    switch (config) {
    
      case AUTOINCREMENT:
        Boolean autoInc = (Boolean)value;
        field.setAutoIncrement(autoInc.booleanValue());
        break;
    
      case NOT_NULL:
        Boolean notNull = (Boolean)value;
        //log.info(" SETVALUE -----> " + value.getClass());
        field.setNotNullable(notNull.booleanValue());
        break;
        //field.isNotNullable = row.isNotNullable;
      
        
      case JAVA_NAME:
        String val = ((String)value).trim();
        field.setJavaName(val);
      
        
        Map<String, String> labels =  field.getLabels();
      
        for(String lang : new HashSet<String>(labels.keySet())) {
          // Això és per indicar que s'ha modificat
          String suffix = "_XX_" + lang;
          String val2 = labels.get(lang);
          if (!val2.endsWith(suffix)) {
            labels.put(lang, val2 + suffix);
          }
          
        }
        // field.setLabel(convertToLabel(val));
        this.fireTableCellUpdated(row, ColumnConfig.LABEL.index);
        
        break;
        
      case LABEL:
        //field.setLabel((String)value);
        break;
        


      case JAVA_TYPE: {
        Class<?> classe = checkJavaClass((String)value);
        if (classe != null) {
          field.setJavaType(classe);
        }
        /*
        try {
          // @todo ComboBox compatible types
          field.setJavaType(Class.forName((String)value));
        } catch(ClassNotFoundException e) {
          // Is Primitive
          Class<?> primitiveType = SQL2Java.primitiveType2javaClassName((String)value);
          if (primitiveType == null) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());  
          } else {
            field.setJavaType(primitiveType);
          }
          }*/
        }
        break;
      
  
      case WEBTYPE: 
        //log.info("]]]]]]]]]] Passa per WEBTYPE");
        //log.info("]]]]]]]]]] Class: " + value.getClass());
        //log.info("]]]]]]]]]] Selected: " + ((WebFieldInfo)value).webtype);
        field.webFieldInfo = (WebFieldInfo)value;
      break;  
      //  return row.webFieldInfo.webtype;

      case WEBQUERYVALUE: {
        if (field.webFieldInfo.getLabel() != null) {
          // String separated by commas to String array
          String[] newLabel = GenAppUtils.strignToStringArray((String)value);
          field.webFieldInfo.setLabel(newLabel);
          // Update select
          this.fireTableCellUpdated(row, ColumnConfig.WEBQUERY.index);
        }
      }
    }

  }
  
  
  public static Class<?> checkJavaClass(String value2) {
    try {
      // @todo ComboBox compatible types
      return Class.forName(value2);
    } catch(ClassNotFoundException e) {
      // Is Primitive
      Class<?> primitiveType = SQL2Java.primitiveType2javaClassName(value2);
      if (primitiveType == null) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        return null;
      } else {
        return primitiveType;
      }
    }
  }

  
  

  public static String convertToLabel(String val) {
    StringBuffer buf = new StringBuffer();
    buf.append(Character.toUpperCase(val.charAt(0)));
    for (int i = 1; i < val.length(); i++) {
      char c = val.charAt(i);
      if (Character.isUpperCase(c)) {
        buf.append(' ');
      }
      buf.append(c);
    }
    return buf.toString();
  }
  
  protected UpDownButton createImageIcon(String path, int index, boolean isUp) {
    java.net.URL imgURL = TableModelColumnList.class.getResource(path);
    UpDownButton udb = new UpDownButton(new ImageIcon(imgURL),index, isUp);
    return udb;
}


  /*
  @Override
  public void actionPerformed(ActionEvent e) {
    log.info(" XXXXXXXXXXXXXXXX");
    
    //UpDownButton udb = (UpDownButton)e.getSource();
    //JOptionPane.showMessageDialog(null, "ROW: " + udb.index + " | ISUP: " + udb.isUp);
    
  }
  */
  


  public class UpDownButton extends JButton {
    /**
     * 
     */
    private static final long serialVersionUID = -248912565027188484L;
    
    final boolean isUp;
    final int index;

    private UpDownButton(Icon icon, int index, boolean isUp) {
      super(icon);
      this.index = index;
      this.isUp = isUp;
    }

    public boolean isUp() {
      return isUp;
    }

    public int getIndex() {
      return index;
    }

  }
  
  
  class JComboBoxChangeListener implements ActionListener {
    
    final FieldInfo fi;
    final JComboBox box;

    private JComboBoxChangeListener(FieldInfo fi, JComboBox box ) {
      super();
      this.fi = fi;
      this.box = box;
    }

    public void actionPerformed(ActionEvent e) {
      //log.info("ZZZZZZZZZZZZZZ  ActionPerformet -> " + fi.getJavaName());
      WebFieldInfo wfi = (WebFieldInfo)box.getSelectedItem();
      fi.webFieldInfo = wfi;
      fireTableDataChanged();
    }
  }


}; // Final de Classe Table

