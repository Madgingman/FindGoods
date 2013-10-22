/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import com.sun.org.apache.xpath.internal.operations.Equals;
import java.util.Objects;

/**
 *
 * @author M
 */
public class Category {
    protected long id;
    protected String name;
    
    /**
     * @throws IllegalArgumentException if name is null or empty
     */
    public Category(long id, String name) 
	    throws IllegalArgumentException {
	if (name == null || name.isEmpty()) {
	    throw new IllegalArgumentException();
	}
	
	this.id = id;
	this.name = name;
    }

    /**
     * @return the id
     */
    public long getId() {
	return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
	this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Category) {
	    Category category = (Category) obj;
	    if (category.getName().equals(this.name)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 71 * hash + Objects.hashCode(this.name);
	return hash;
    }

}
