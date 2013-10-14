/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.resultsTable;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author M
 */
public class ResultsTableModel extends AbstractTableModel {

    private String[] columnNames = {"ID #", "Изображение", "Краткое описание", "Цена", "Рейтинг"};
    private ArrayList<List<Object>> data;
    
    public ResultsTableModel() {
	data = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
	return this.data.size();
    }

    @Override
    public int getColumnCount() {
	return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
	return data.get(row).get(col);
    }

    @Override
    public Class getColumnClass(int col) {
	switch (col) {
	    case 0:
		return Long.class;
	    case 1:
		return JLabel.class;
	    case 2:
		return JEditorPane.class;
	    case 3:
		return Double.class;
	    case 4:
		return Double.class;
	    default:
		throw new IndexOutOfBoundsException("Column number is too big. "
			+ "Trying to get " + col + " column's class type from table with "
			+ this.columnNames.length + " columns.");
	}
    }
    
    @Override
    public String getColumnName(int col) {
	return this.columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
	return false;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
	data.get(row).set(col, value);
	fireTableCellUpdated(row, col);
    }
    
    public void addRow(ArrayList<Object> value) {
	this.data.add(value);
	fireTableDataChanged();
    }
    
    public void addRow(Long id, ImageIcon img, JEditorPane pane, Double price, Double rating) {
	ArrayList<Object> rowData = new ArrayList<>();
	rowData.add(id);
	rowData.add(img);
	rowData.add(pane);
	rowData.add(price);
	rowData.add(rating);
	this.data.add(rowData);
	fireTableDataChanged();
    }
    
    public void removeRow(int row) {
	this.data.remove(row);
	fireTableDataChanged();
    }
    
    public void clear() {
	this.data.clear();
	fireTableDataChanged();
    }
    
    public void recalculatePrices(Double exchangeRate) {
	int priceColumn = getColumnCount() - 1;
	for (int i = 0; i < getRowCount(); i++) {
	    Double value = (Double) getValueAt(i, priceColumn) / exchangeRate;
	    setValueAt(value, i, priceColumn);
	}
    }
    
}
