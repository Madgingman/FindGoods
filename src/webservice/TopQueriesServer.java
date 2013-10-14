/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M
 */
public class TopQueriesServer implements java.lang.AutoCloseable {
    
    private HttpServer server;
    
    public TopQueriesServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/query/top", new TopQueriesHandler());
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

    @Override
    public void close() {
	stop();
    }
}
