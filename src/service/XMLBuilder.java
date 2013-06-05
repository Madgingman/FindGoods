/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.HistoryEntry;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author M
 */
public class XMLCreator {
    
    public String createTopQueries(List<HistoryEntry> helist) 
	    throws ParserConfigurationException, TransformerException {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.newDocument();
        
	Element root = dom.createElement("topqueries");
	root.setAttribute("amount", (new Integer(helist.size())).toString());
        dom.appendChild(root);
        
	Iterator it = helist.iterator();
	while (it.hasNext()) {
	    HistoryEntry he = (HistoryEntry) it.next();
	    Element heElem = createHistoryEntryElement(dom, he);
	    root.appendChild(heElem);
	}
	
	return DOMtoXML(dom);
    }

    private Element createHistoryEntryElement(Document dom, HistoryEntry he) {
	Element heElem = dom.createElement("query");

	Element query = dom.createElement("query");
	Text queryText = dom.createTextNode(he.getQuery());
	query.appendChild(queryText);
	heElem.appendChild(query);

	Element date = dom.createElement("date");
	Text dateText = dom.createTextNode(he.getDate().toString());
	date.appendChild(dateText);
	heElem.appendChild(date);

	Element rating = dom.createElement("rating");
	Text ratingText = dom.createTextNode((new Long(he.getRating())).toString());
	rating.appendChild(ratingText);
	heElem.appendChild(rating);

	return heElem;
    }

    private String DOMtoXML(Document dom) throws TransformerException {
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	DOMSource domSource = new DOMSource(dom);
	StringWriter stringWriter = new StringWriter();
	StreamResult streamResult = new StreamResult(stringWriter);
	transformer.transform(domSource, streamResult);
	return stringWriter.toString();
    }
}
