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

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DataSet implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long dataSetID;
	@Persistent
	private String name;
	@Persistent
	private Date dateAdded;
	@Persistent
	private ArrayList<DataEntry> dataEntries;
	
	public DataSet( String title ) {
		this.name = title;
		this.dateAdded = new Date();
		this.dataEntries = new ArrayList<DataEntry>();
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
	
	public void addEntry( DataEntry entry ) {
		dataEntries.add(entry);
	}
	
	public void removeEntry( DataEntry entry ) {
		dataEntries.remove(entry);
	}
	
	public ArrayList<DataEntry> listAll() {
		return dataEntries;
	}
	
	public DataEntry getDataEntry( Key id ) throws EntryNotPresentException{
	    for( DataEntry entry : dataEntries ) {
	    	if ( entry.getID().equals(id) )
	    		return entry;
	    }
	    
	    throw new EntryNotPresentException("Entry not found.");
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	
}
