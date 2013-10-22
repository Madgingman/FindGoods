/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.net.URL;
import java.util.Objects;

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

    public void setName(String name) {
	this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Store) {
	    Store store = (Store) obj;
	    if (store.getName().equals(this.name) && store.getUrl().equals(this.url)
		    && store.getSearchUrl().equals(this.searchUrl) && store.getPropFile().equals(this.propFile)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 53 * hash + Objects.hashCode(this.name);
	hash = 53 * hash + Objects.hashCode(this.url);
	hash = 53 * hash + Objects.hashCode(this.propFile);
	hash = 53 * hash + Objects.hashCode(this.searchUrl);
	return hash;
    }

}
