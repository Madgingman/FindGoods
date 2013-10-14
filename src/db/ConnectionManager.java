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
    private static final String password = "123";

    public Connection getConnection() throws SQLException {
	Connection conn = DriverManager.getConnection(url /*+ ";create=true"*/, user, password);
	conn.setAutoCommit(true);
	return conn;
    }

    public void closeConnection(Connection connection) throws SQLException {
	if (connection != null) {
	    connection.close();
	}
    }
    
}
