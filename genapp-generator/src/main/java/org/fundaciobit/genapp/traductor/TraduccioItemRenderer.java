package org.fundaciobit.genapp.traductor;

import java.awt.Color;
import java.awt.Component;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * 
 * @author anadal
 *
 */
public class TraduccioItemRenderer extends DefaultTableCellRenderer {
    
    protected final Set<String> clausDuplicades;
    
    public TraduccioItemRenderer(Set<String> clausDuplicades) {
        super();
        this.clausDuplicades = clausDuplicades;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        TraduccioItemTableModel model = (TraduccioItemTableModel) table.getModel();
        
        if ( clausDuplicades.contains(model.getValueAt(row, 1))) {
            c.setBackground(Color.yellow);
        } else {
            if (column >= 2 && model.getValueAt(row, column) == null) {
                c.setBackground(Color.red);
            } else {
                c.setBackground(Color.white);
            }
        }

        return c;
    }
}
