package com.Team4.client;

import java.util.ArrayList;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class DataSet {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private int dataSetID;
	@Persistent
	private String name;
	@Persistent
	private ArrayList<DataEntry> dataEntries;
	@Persistent
	private Date dateAdded;
	
	public DataSet( String title ) {
		this.name = title;
		this.dateAdded = new Date();
		this.dataEntries = new ArrayList<DataEntry>();
	}
	
	public int getDataSetID() {
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
	
	public DataEntry getDataEntry( int id ) throws EntryNotPresentException{
	    for( DataEntry entry : dataEntries ) {
	    	if ( entry.getID() == id )
	    		return entry;
	    }
	    
	    throw new EntryNotPresentException("Entry not found.");
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	
}