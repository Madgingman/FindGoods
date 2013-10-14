/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import logic.Store;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author M
 */
public class StoreMapper extends AbstractMapper<Store> {
    
    public enum StoreParams {
	NAME("Name"),
	URL("Url");
	
	private String value;
	
	private StoreParams(String value) {
	    this.value = value;
	}
	
	public String getValue() {
	    return value;
	}
    }
    
    public StoreMapper() {
    }

    @Override
    public int insert(Store store) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getInsertStatement(store, conn)) {
	    statement.executeUpdate();
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    return -1;
		}
		return keys.getInt(1);
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
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, store.getId());
	    statement.executeUpdate();
	}
    }

    @Override
    public Store find(long id) throws SQLException {
	String query = "SELECT * FROM Stores WHERE Id = ?";
	
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, id);
	    try (ResultSet rset = statement.executeQuery()) {
		List<Store> stores = getElementsFromResultSet(rset);
		if (stores != null && !stores.isEmpty()) {
		    return stores.get(0);
		}
		return null;
	    }
	}
    }
    
    /**
     * Finds store in database by specified parameter
     * @param param one of the StoreMapper.StoreParams enumeration values
     * @param value parameter's value
     * @return	    Store object if store with the specified parameter exists, null otherwise
     * @throws SQLException 
     */
    public Store findByParam(StoreParams param, String value) throws SQLException {
	String query = "SELECT * FROM Stores WHERE " + param.getValue() + " = '" + value + "'";
	
	try (Connection conn = getConnection();
		PreparedStatement statement = conn.prepareStatement(query.toString());
		ResultSet rset = statement.executeQuery()) {
//	    conn.setAutoCommit(false);
//	    conn.commit();
	    List<Store> stores = getElementsFromResultSet(rset);
	    if (stores != null && !stores.isEmpty()) {
		return stores.get(0);
	    }
	    return null;
	}
    }
    
    private PreparedStatement getInsertStatement(Store store, Connection conn) throws SQLException {
	String query = "INSERT INTO Stores(Name, Url, SearchUrl, PropertyFile) "
		+ "VALUES (?, ?, ?, ?)";
	
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
