/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.swing.table.AbstractTableModel;
import logic.Product;
import utils.ProductUtils;

/**
 *
 * @author M
 */
public class ProductInfoTableModel extends AbstractTableModel {

    private String[] columnNames = {"Параметр", "Значение"};
    private ArrayList<List<String>> data;
    
    public ProductInfoTableModel() {
	data = new ArrayList<>();
    }
    public ProductInfoTableModel(Product product) {
	data = new ArrayList<>();
	setData(product);
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
    public String getValueAt(int row, int col) {
	return data.get(row).get(col);
    }

    @Override
    public Class getColumnClass(int col) {
	return String.class;
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
	data.get(row).set(col, value.toString());
	fireTableCellUpdated(row, col);
    }
    
    public void addRow(String propertyName, String value) {
	this.data.add(Arrays.asList(propertyName, value));
	fireTableDataChanged();
    }
    
    public void setData(Product product) {
	this.data.clear();

	SortedMap<String, String> properties = ProductUtils.getProductAsPropertyMap(product);
	for (Map.Entry<String, String> property : properties.entrySet()) {
	    this.data.add(Arrays.asList(property.getKey(), property.getValue()));
	}
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

}
