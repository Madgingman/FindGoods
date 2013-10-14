/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.resultsTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author M
 */
public class ImageCellRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {
	// loading image
	this.setIcon((ImageIcon) value);
	this.setHorizontalAlignment(SwingConstants.CENTER);
	
	// showing JLabel background
	this.setOpaque(true);
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
