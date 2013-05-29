/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M
 */
public class TopQueriesServer {
    
    private HttpServer server;
    
    public TopQueriesServer(Connection connection) {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/query/top", new TopQueriesHandler(connection));
            server.setExecutor(null);	// default executor
        } catch (IOException ex) {
            Logger.getLogger(TopQueriesServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start() {
        server.start();
    }
    
    public void stop() {
        server.stop(0);
    }
}
