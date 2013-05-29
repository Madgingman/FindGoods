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
    protected Date date;
    protected long rating;
    
    public HistoryEntry(long id, String query, Date lastDate, int rating) {
	this.id = id;
	this.query = query;
	this.date = lastDate;
	this.rating = rating;
    }

    public void incrementRating() {
	this.rating++;
    }
    
    public void updateDate() {
	this.date = new Date();
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
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
	this.date = date;
    }

    /**
     * @return the rating
     */
    public long getRating() {
	return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(long rating) {
	this.rating = rating;
    }
}
