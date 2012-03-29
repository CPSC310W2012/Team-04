package com.Team4.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.Team4.client.EntryNotPresentException;
import com.google.appengine.api.datastore.Key;

/**
 * @author ryanabooth
 * Metadata class used to describe groups of Data Entries
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DataSet implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long dataSetID;
	@Persistent
	private String name;
	@Persistent
	private Date dateAdded;
	
	public DataSet() {
		this.name = "";
	}
	
	public DataSet( String title ) {
		this.name = title;
		this.dateAdded = new Date();
	}
	
	public Long getDataSetID() {
		return dataSetID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	
}
