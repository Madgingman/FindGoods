/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.resultsTable;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author M
 */
public class ButtonMouseListener implements MouseListener {
    private JTable table;
    
    public ButtonMouseListener(JTable table) {
	this.table = table;
    }

    private void forwardEventToButton(MouseEvent e) {
	TableColumnModel columnModel = table.getColumnModel();
	int column = columnModel.getColumnIndexAtX(e.getX());
	int row = e.getY() / table.getRowHeight();
	
	Object value;
	JButton button;
	MouseEvent buttonEvent;

	if (row >= table.getRowCount() || row < 0 || column >= table.getColumnCount() || column < 0) {
	    return;
	}

	value = table.getValueAt(row, column);

	if (!(value instanceof JButton)) {
	    return;
	}

	button = (JButton) value;
	buttonEvent = (MouseEvent) SwingUtilities.convertMouseEvent(table, e, button);
	button.dispatchEvent(buttonEvent);

	table.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	forwardEventToButton(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//	forwardEventToButton(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
//	forwardEventToButton(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
//	forwardEventToButton(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//	forwardEventToButton(e);
    }
}
