package com.Team4.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.Team4.client.EntryNotPresentException;

/**
 * Client side DataSet object
 * @author ryanabooth
 */
public class ClientDataSet implements Serializable {

	private Long dataSetID;
	private String name;
	private Date dateAdded;
	private ClientDataEntry[] dataEntries;
	
	public ClientDataSet( Long dataSetID, String title, Date dAdded, int size ) {
		this.dataSetID = dataSetID;
		this.name = title;
		this.dateAdded = dAdded;
		this.dataEntries = new ClientDataEntry[size];
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
	
	/**
	 * @param entry - DataEntry to be added
	 * @pre The correct size was used to instantiate this DataSet
	 * 
	 * */
	public void addEntry( ClientDataEntry entry ) {
		for( int i=0; i < dataEntries.length; i++) {
			if( dataEntries[i] == null )
				dataEntries[i] = entry;
		}
	}
	
	public ClientDataEntry[] listAll() {
		return dataEntries;
	}
	
	public ClientDataEntry getDataEntry( String id ) throws EntryNotPresentException {
	    for( ClientDataEntry entry : dataEntries ) {
	    	if ( entry.getID().equals(id) )
	    		return entry;
	    }
	    
	    throw new EntryNotPresentException("Entry not found.");
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	
}
