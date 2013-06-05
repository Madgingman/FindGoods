/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author M
 */
public class XMLReader {
    
    public static Map<String, Boolean> loadSettings(String filename) 
	    throws ParserConfigurationException, IOException, SAXException {
	HashMap<String, Boolean> stores = new HashMap<>();
	
	File xmlf = new File(filename);
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(xmlf);
	
	Element root = dom.getDocumentElement();
	root.normalize();
	NodeList nodeList = root.getElementsByTagName("store");
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    if (node instanceof Element) {
		Element storeNode = (Element) node;
		String name = storeNode.getAttribute("name");
		boolean isSelected = storeNode.getAttribute("selected").equalsIgnoreCase("true");
		stores.put(name, isSelected);
	    }
	}
	
	return stores;
    }
    
    public static double getExchangeRate(InputStream is, Currencies currency) 
	    throws ParserConfigurationException, IOException, SAXException {
	double RUBRate = 0;
	double rate = 0;
	double result = -1;
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(is);
	
	Element root = dom.getDocumentElement();
	root.normalize();
	NodeList nodeList = root.getElementsByTagName("Cube");
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    if (node instanceof Element) {
		Element storeNode = (Element) node;
		String curr = storeNode.getAttribute("currency");
		if (curr.equalsIgnoreCase("RUB")) {
		    RUBRate = Double.valueOf(storeNode.getAttribute("rate"));
		    continue;
		} else if (curr.equalsIgnoreCase(currency.toString())) {
		    rate = Double.valueOf(storeNode.getAttribute("rate"));
		    continue;
		}
	    }
	}
	if (RUBRate != 0 && rate != 0) {
	    result = (RUBRate / rate);
	}
	return result;
    }
}
