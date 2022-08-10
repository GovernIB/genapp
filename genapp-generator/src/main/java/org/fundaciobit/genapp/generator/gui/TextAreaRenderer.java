package org.fundaciobit.genapp.generator.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class TextAreaRenderer extends JTextArea implements TableCellRenderer {

    private final DefaultTableCellRenderer adaptee = new DefaultTableCellRenderer();
    /** map from table to map of rows to map of column heights */
    @SuppressWarnings("all")
    private final Map cellSizes = new HashMap();

    public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(//
            JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        // set the colours, etc. using the standard for that platform
        adaptee.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        setForeground(adaptee.getForeground());
        setBackground(adaptee.getBackground());
        setBorder(adaptee.getBorder());
        setFont(adaptee.getFont());
        setText(adaptee.getText());

        // This line was very important to get it working with JDK1.4
        TableColumnModel columnModel = table.getColumnModel();
        setSize(columnModel.getColumn(column).getWidth(), 100000);
        int height_wanted = (int) getPreferredSize().getHeight();
        addSize(table, row, column, height_wanted);
        height_wanted = findTotalMaximumRowSize(table, row);
        if (height_wanted != table.getRowHeight(row)) {
            table.setRowHeight(row, height_wanted);
        }
        return this;
    }

    @SuppressWarnings("all")
    private void addSize(JTable table, int row, int column, int height) {
        Map rows = (Map) cellSizes.get(table);
        if (rows == null) {
            cellSizes.put(table, rows = new HashMap());
        }

        Map rowheights = (Map) rows.get(new Integer(row));
        if (rowheights == null) {
            rows.put(new Integer(row), rowheights = new HashMap());
        }
        rowheights.put(new Integer(column), new Integer(height));
    }

    /**
     * Look through all columns and get the renderer. If it is also a
     * TextAreaRenderer, we look at the maximum height in its hash table for this
     * row.
     */
    private int findTotalMaximumRowSize(JTable table, int row) {
        int maximum_height = 0;
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn tc = columns.nextElement();
            TableCellRenderer cellRenderer = tc.getCellRenderer();
            if (cellRenderer instanceof TextAreaRenderer) {
                TextAreaRenderer tar = (TextAreaRenderer) cellRenderer;
                maximum_height = Math.max(maximum_height, tar.findMaximumRowSize(table, row));
            }
        }
        return maximum_height;
    }

    @SuppressWarnings("all")
    private int findMaximumRowSize(JTable table, int row) {
        Map rows = (Map) cellSizes.get(table);
        if (rows == null)
            return 0;
        Map rowheights = (Map) rows.get(new Integer(row));
        if (rowheights == null)
            return 0;
        int maximum_height = 0;
        Iterator it = rowheights.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            int cellHeight = ((Integer) entry.getValue()).intValue();
            maximum_height = Math.max(maximum_height, cellHeight);
        }
        return maximum_height;
    }
}
