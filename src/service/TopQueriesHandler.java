/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.HistoryEntry;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import db.ConnectionManager;
import db.HistoryEntryMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author M
 */
public class TopQueriesHandler implements HttpHandler {
    private static final String ILLEGAL_PARAMETERS_RESPONSE = "Illegal parameter(s). Usage: amount=<pos_int>";
    private static final String ILLEGAL_VALUE_RESPONSE = "Illegal parameter value. Amount should have positive integer value.";
    private static final String UNEXPECTED_ERROR_RESPONSE = "Ups... Unexpected error.";

    private HistoryEntryMapper hemapper;
    
    TopQueriesHandler() {
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
	String response;
	OutputStream ostream = he.getResponseBody();
	try {
	    URI uri = he.getRequestURI();
	    String query = uri.getRawQuery();
	    if (!query.matches("amount=[0-9]+")) {	// example: amount=345
		response = ILLEGAL_PARAMETERS_RESPONSE;
		he.sendResponseHeaders(400, response.length()); // 400 is BAD_REQUEST response code
		ostream.write(response.getBytes());
	    }

	    String token[] = query.split("=");
	    int amount = Integer.parseInt(token[1]);
	    hemapper = new HistoryEntryMapper();
	    List<HistoryEntry> heTopList = hemapper.getTopQueries(amount);
	    response = XMLBuilder.createTopQueries(heTopList);
	    he.sendResponseHeaders(200, response.length());
	    ostream.write(response.getBytes());
	} catch (NumberFormatException ex) {
	    response = ILLEGAL_VALUE_RESPONSE;
	    he.sendResponseHeaders(400, response.length()); // 400 is BAD_REQUEST response code
	    ostream.write(response.getBytes());
	} catch (SQLException | IOException | ParserConfigurationException | TransformerException ex) {
	    response = UNEXPECTED_ERROR_RESPONSE;// + "\nError: " + ex.getMessage();
	    he.sendResponseHeaders(500, response.length()); // 500 is INTERNAL_SERVER_ERROR response code
	    ostream.write(response.getBytes());
	} finally {
	    ostream.close();
	}
    }
    
}
