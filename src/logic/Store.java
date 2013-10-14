/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.net.URL;

/**
 *
 * @author M
 */
public class Store {
    protected long id;
    protected String name;
    protected URL url;
    protected String propFile;
    protected URL searchUrl;
    
    /**
     * 
     * @param name       store's name to display
     * @param url        link to the store's site
     * @param searchUrl  link to the store's search query
     * @param propFile   path to the store's property file
     */
    public Store(long id, String name, URL url, URL searchUrl, String propFile) {
	this.id = id;
	this.name = name;
	this.url = url;
	this.searchUrl = searchUrl;
	this.propFile = propFile;
    }
    
    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public URL getUrl() {
	return url;
    }

    public URL getSearchUrl() {
	return searchUrl;
    }

    public String getPropFile() {
	return propFile;
    }
    
    public void setId(long id) {
	this.id = id;
    }

}
