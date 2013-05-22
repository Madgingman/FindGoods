/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author M
 */
public class ConnectionManager {
    private String url;
    private String username;
    private String password;
    
    public ConnectionManager(String url, String username, String password) {
	this.url = url;
	this.username = username;
	this.password = password;
    }

    public Connection getConnection() throws SQLException {
	return DriverManager.getConnection(url + ";create=true", username, password);
    }

    public void closeConnection(Connection connection) throws SQLException {
	if (connection != null) {
	    connection.close();
	}
    }
    
}
