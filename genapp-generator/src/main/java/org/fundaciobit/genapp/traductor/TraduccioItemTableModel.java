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
    
    private final Map<Integer, ITraduccioItem> traduccionsPerHashDeClau;

    public TraduccioItemTableModel(ITraduccioItem[] data, Map<Integer, ITraduccioItem> traduccionsPerHashDeClau) {
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
        
        this.traduccionsPerHashDeClau = traduccionsPerHashDeClau;

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

    @Override
    public Class<?> getColumnClass(int c) {

        Object obj = getValueAt(0, c);
        if (obj == null) {
            System.err.println("getValueAt(0, " + c + ") is null ");
            new Exception().printStackTrace();
            return null;
        } else {
            return obj.getClass();
        }
    }

    /*
     * Don't need to implement this method unless your table's editable.
     */
    @Override
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
    @Override
    public void setValueAt(Object value, int row, int col) {

        if (col >= 2) {
            ITraduccioItem item = data[row];
            String lang = mapColumnLanguage.get(col);
            String key = item.getKey();
            
            data[row].setStringValue(lang, (String) value);

            traduccionsPerHashDeClau.get(key.hashCode()).setStringValue(lang, (String) value);
            
            System.out.println("setValueAt(ITEM) = " + item.toString());
            System.out.println("setValueAt(data[row]) = " + data[row].toString());
            System.out.println("setValueAt(traduccionsPerHashDeClau.get(key.hashCode())) = " + traduccionsPerHashDeClau.get(key.hashCode()).toString());
            
            
            
            fireTableCellUpdated(row, col);
        }
    }

}