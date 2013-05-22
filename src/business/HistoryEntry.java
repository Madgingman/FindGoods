/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Date;

/**
 *
 * @author M
 */
public class HistoryEntry {
    protected long id;
    protected String query;
    protected Date lastDate;
    protected int rating;
    
    public HistoryEntry(long id, String query, Date lastDate, int rating) {
	this.id = id;
	this.query = query;
	this.lastDate = lastDate;
	this.rating = rating;
    }

    public void incrementRating() {
	rating++;
    }
    
    public void updateDate() {
	lastDate = new Date();
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
     * @return the query
     */
    public String getQuery() {
	return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
	this.query = query;
    }

    /**
     * @return the lastDate
     */
    public Date getLastDate() {
	return lastDate;
    }

    /**
     * @param lastDate the lastDate to set
     */
    public void setLastDate(Date lastDate) {
	this.lastDate = lastDate;
    }

    /**
     * @return the rating
     */
    public int getRating() {
	return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
	this.rating = rating;
    }
}
