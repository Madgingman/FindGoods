/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import business.Store;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author M
 */
public class StoreMapper extends AbstractMapper<Store> {
    
    public enum StoreParams {
	Name, Url;
    }
    
    public StoreMapper() {
    }

    @Override
    public void insert(Store store) throws SQLException {
	try (Connection conn = getConnection()) {
	    try (PreparedStatement statement = getInsertStatement(store, conn)) {
		statement.executeUpdate();
	    }
	}
    }

    @Override
    public void update(Store store) throws SQLException {
	try (Connection conn = getConnection()) {
	    try (PreparedStatement statement = getUpdateStatement(store, conn)) {
		statement.executeUpdate();
	    }
	}
    }

    @Override
    public void delete(Store store) throws SQLException {
	String query = "DELETE FROM Stores WHERE Id = ?";
	try (Connection conn = getConnection()) {
	    try (PreparedStatement statement = conn.prepareStatement(query)) {
		statement.setLong(1, store.getId());
		statement.executeUpdate();
	    }
	}
    }

    @Override
    public Store find(long id) throws SQLException {
	String query = "SELECT * FROM Stores WHERE Id = ?";
	try (Connection conn = getConnection()) {
	    try (PreparedStatement statement = conn.prepareStatement(query)) {
		statement.setLong(1, id);
		ResultSet rset = statement.executeQuery();
		List<Store> stores = getElementsFromResultSet(rset);
		if (stores != null) {
		    return stores.get(0);
		}
		return null;
	    }
	}
    }
    
    public Store findByParam(StoreParams param, String value) throws SQLException {
	String query = "SELECT * FROM Stores WHERE " + param.toString() + " = '" + value + "'";
	try (Connection conn = getConnection()) {
	    try (Statement statement = conn.createStatement()) {
		ResultSet rset = statement.executeQuery(query);
		List<Store> stores = getElementsFromResultSet(rset);
		if (stores != null && !stores.isEmpty()) {
		    return stores.get(0);
		}
		return null;
	    }
	}
    }
    
    private PreparedStatement getInsertStatement(Store store, Connection conn) throws SQLException {
	String query = "INSERT INTO Stores(Name, Url, SearchUrl, PropertyFile) "
		+ "VALUES (?, ?, ?, ?)";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query);
	    statement.setString(1, store.getName());
	    statement.setString(2, store.getUrl().toExternalForm());
	    statement.setString(3, store.getSearchUrl().toExternalForm());
	    statement.setString(4, store.getPropFile());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }

    private PreparedStatement getUpdateStatement(Store store, Connection conn) throws SQLException {
	String query = "UPDATE Stores SET Name = ?, Url = ?, SearchUrl = ?, "
		+ "PropertyFile = ? WHERE Id = ?";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query);
	    statement.setString(1, store.getName());
	    statement.setString(2, store.getUrl().toExternalForm());
	    statement.setString(3, store.getSearchUrl().toExternalForm());
	    statement.setString(4, store.getPropFile());
	    statement.setLong(5, store.getId());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }
    
    @Override
    protected List<Store> getElementsFromResultSet(ResultSet rset) throws SQLException {
	List<Store> stores = new ArrayList<>();
	while (rset.next()) {
	    long id = rset.getLong("Id");
	    String name = rset.getString("Name");
	    URL url, searchUrl;
	    try {
		url = new URL(rset.getString("Url"));
		searchUrl = new URL(rset.getString("SearchUrl"));
	    } catch (MalformedURLException ex) {
		continue;
	    }
	    String propFile = rset.getString("PropertyFile");
	    
	    stores.add(new Store(id, name, url, searchUrl, propFile));
	}
	return stores;
    }
    
}
