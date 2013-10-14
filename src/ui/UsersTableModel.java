/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import logic.Administrator;
import logic.User;

/**
 *
 * @author M
 */
public class UsersTableModel extends AbstractTableModel {

    private String[] columnNames = {"ID", "Тип", "Логин", "Имя", "Фамилия", "Почта"};
    private ArrayList<List<Object>> data;
    
    public UsersTableModel() {
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
		return Integer.class;
	    case 2:
		return String.class;
	    case 3:
		return String.class;
	    case 4:
		return String.class;
	    case 5:
		return String.class;
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
    
    public void addRow(User user) {
	this.data.add(Arrays.asList(
		(Object) user.getId(),
		(Object) (user instanceof Administrator ? 1 : 0),
		(Object) user.getLogin(), (Object) user.getName(),
		(Object) user.getSurname(), (Object) user.getEmail()));
	fireTableDataChanged();
    }
    
    public void addRow(Long id, Integer userType, String login, String name,
	    String surname, String email) {
	ArrayList<Object> rowData = new ArrayList<>();
	rowData.add(id);
	rowData.add(userType);
	rowData.add(login);
	rowData.add(name);
	rowData.add(surname);
	rowData.add(email);
	this.data.add(rowData);
	fireTableDataChanged();
    }
    
    public void setData(List<User> users) {
	this.data.clear();
	
	for (User user : users) {
	    this.data.add(Arrays.asList(
		    (Object) user.getId(),
		    (Object) (user instanceof Administrator ? 1 : 0),
		    (Object) user.getLogin(), (Object) user.getName(),
		    (Object) user.getSurname(), (Object) user.getEmail()));
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
