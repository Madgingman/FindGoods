/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import service.XMLReader;

/**
 *
 * @author M
 */
public class HTTPClient {
    
    public static Map<String, Double> getExchangeRateMap() 
	    throws ParserConfigurationException, SAXException, IOException {
	URL url = new URL("http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml");
	URLConnection connection = url.openConnection();
	connection.connect();
	return XMLReader.getExchangeRateMap(connection.getInputStream());
    }
}
