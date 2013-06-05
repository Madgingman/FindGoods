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
    private static final String url = "jdbc:derby://localhost:1527/findgoods";
    private static final String user = "fguser";
    private static final String password = "iampassword";
    
    private static final ConnectionManager instance = new ConnectionManager();
    
    private ConnectionManager() {
    }
    
    public static ConnectionManager getInstance() {
	return instance;
    }

    public Connection getConnection() throws SQLException {
	return DriverManager.getConnection(url /*+ ";create=true"*/, user, password);
    }

    public void closeConnection(Connection connection) throws SQLException {
	if (connection != null) {
	    connection.close();
	}
    }
    
}
