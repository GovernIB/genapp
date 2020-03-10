package org.fundaciobit.genapp.traductor;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.fundaciobit.genapp.generator.gui.SharedData;

/**
 * 
 * @author anadal
 *
 */
class TraduccioItemTableModel extends AbstractTableModel {
  private final String[] columnNames;
  private final ITraduccioItem[] data;
  private final Map<Integer, String> mapColumnLanguage;

  public TraduccioItemTableModel( ITraduccioItem[] data) {
    super();

    this.data = data;
    
    String[] languages = SharedData.data.getLanguages();

    String[] columnNames = new String[2 + languages.length];

    columnNames[0] = "Type";
    columnNames[1] = "Key";
    Map<Integer, String> mapColumnLanguage = new HashMap<Integer, String>();
    for (int i = 0; i < languages.length; i++) {
      columnNames[2 + i] = languages[i];

      mapColumnLanguage.put(2 + i, languages[i]);

    }

    this.mapColumnLanguage = mapColumnLanguage;

    this.columnNames = columnNames;

  }

  public int getColumnCount() {
    return columnNames.length;
  }

  public int getRowCount() {
    return data.length;
  }

  public String getColumnName(int col) {
    return columnNames[col];
  }

  public Object getValueAt(int row, int col) {

    switch (col) {

      case 0: {
        return data[row].getType();
      }

      case 1: {
        return data[row].getKey();
      }

      default:

        return data[row].getStringValue(mapColumnLanguage.get(col));

    }

  }

  public Class<?> getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }

  /*
   * Don't need to implement this method unless your table's editable.
   */
  public boolean isCellEditable(int row, int col) {
    // Note that the data/cell address is constant,
    // no matter where the cell appears onscreen.
    if (col < 2) {
      return false;
    } else {
      return true;
    }
  }

  /*
   * Don't need to implement this method unless your table's data can change.
   */
  public void setValueAt(Object value, int row, int col) {

    if (col >= 2) {
      data[row].setStringValue(mapColumnLanguage.get(col), (String) value);
      fireTableCellUpdated(row, col);
    }
  }

}