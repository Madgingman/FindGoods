/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author M
 */
public class HTTPClient {
    
    public static double getExchangeRate(Currencies currency) 
	    throws ParserConfigurationException, SAXException, IOException {
	URL url = new URL("http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml");
	URLConnection connection = url.openConnection();
	connection.connect();
	return XMLReader.getExchangeRate(connection.getInputStream(), currency);
    }
}
