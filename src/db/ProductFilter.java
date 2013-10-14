/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import logic.Store;

/**
 *
 * @author M
 */
public class ProductFilter {
    protected List<Store> stores;
    protected String name;
    protected Long categoryId;
    protected String producer;
    protected Date lowerDate;
    protected Date upperDate;
    protected Double lowerPrice;
    protected Double upperPrice;
    
    protected String condition;

    public ProductFilter(final List<Store> stores, String name, Long categoryId, String producer, Date lowerDate, 
	    Date upperDate, Double lowerPrice, Double upperPrice) {
	if (stores == null || stores.isEmpty()) {
	    throw new IllegalArgumentException();
	}
	if (upperDate != null && lowerDate != null && upperDate.before(lowerDate)) {
	    throw new IllegalArgumentException();
	}
	if (upperPrice != null && lowerPrice != null
		&& ((upperPrice < lowerPrice && upperPrice > -1) || upperPrice < -1 || lowerPrice < -1)) {
	    throw new IllegalArgumentException();
	}
	
	this.stores = stores;
	this.name = name;
	this.categoryId = categoryId;
	this.producer = producer;
	this.lowerDate = lowerDate;
	this.upperDate = upperDate;
	this.lowerPrice = lowerPrice;
	this.upperPrice = upperPrice;
	
	castToSQLConditionString();
    }

    public void setName(String name) {
	this.name = name;
	castToSQLConditionString();
    }

    public String getName() {
	return this.name;
    }
    
    public String getCondition() {
	return this.condition;
    }

    private void castToSQLConditionString() {
	StringBuilder cond = new StringBuilder();

	cond.append("Products.StoreId in (");
	cond.append(getStores().get(0).getId());
	for (int i = 1; i < getStores().size(); i++) {
	    cond.append(", ").append(getStores().get(i).getId());
	}
	cond.append(")");
	
	if (name != null && !name.isEmpty()) {
	    cond.append(" and Products.Name = '").append(name).append("'");
	}
	
	if (getCategoryId() != null) {
	    cond.append(" and Products.CategoryId = ").append(getCategoryId());
	}
	
	if (getProducer() != null && !producer.isEmpty()) {
	    cond.append(" and Products.Producer = '").append(getProducer()).append("'");
	}
	
	if (getLowerDate() != null || getUpperDate() != null) {
	    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    if (getLowerDate() == null) {
		try {
		    lowerDate = df.parse("01/01/1970");
		} catch (ParseException ex) {
		    // never happens
		    lowerDate = new Date(100);
		}
	    }
	    if (getUpperDate() == null) {
		upperDate = new Date();
	    }
	    cond.append(" and (Products.Date between '")
		    .append(df.format(getLowerDate()))
		    .append("' and '")
		    .append(df.format(getUpperDate()))
		    .append("')");
	}
	
	if ((getLowerPrice() != null || getUpperPrice() != null) && (getLowerPrice() >= 0 || getUpperPrice() >= 0)) {
	    if (getLowerPrice() == -1) {
		lowerPrice = 0.0;
	    }
	    if (getUpperPrice() == -1) {
		upperPrice = Double.MAX_VALUE / 2;
	    }
	    cond.append(" and (Products.Price between ")
		    .append(getLowerPrice())
		    .append(" and ")
		    .append(getUpperPrice())
		    .append(")");
	}
	
	this.condition = cond.toString();
    }

    /**
     * @return the stores
     */
    public List<Store> getStores() {
	return stores;
    }

    /**
     * @return the categoryId
     */
    public Long getCategoryId() {
	return categoryId;
    }

    /**
     * @return the producer
     */
    public String getProducer() {
	return producer;
    }

    /**
     * @return the lowerDate
     */
    public Date getLowerDate() {
	return lowerDate;
    }

    /**
     * @return the upperDate
     */
    public Date getUpperDate() {
	return upperDate;
    }

    /**
     * @return the lowerPrice
     */
    public Double getLowerPrice() {
	return lowerPrice;
    }

    /**
     * @return the upperPrice
     */
    public Double getUpperPrice() {
	return upperPrice;
    }
}
