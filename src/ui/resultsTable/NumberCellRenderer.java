/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.resultsTable;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author M
 */
public class NumberCellRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
	    boolean isSelected, boolean hasFocus, int row, int column) {
	setText(((Long) value).toString());
	setHorizontalAlignment(SwingConstants.CENTER);
	
	// showing background
	setOpaque(true);
	// setting L&F colors
        setForeground(isSelected ?
            UIManager.getColor("Table.selectionForeground") :
            UIManager.getColor("Table.foreground"));
        setBackground(isSelected ?
            UIManager.getColor("Table.selectionBackground") :
            new Color(226, 239, 255)/*UIManager.getColor("Table.background")*/);
	// ignoring focus border
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

	return this;
    }
    
}
