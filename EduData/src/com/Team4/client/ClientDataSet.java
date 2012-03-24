package com.Team4.client;
import java.util.ArrayList;
import java.util.Date;

/**
 * Client side DataSet object
 * @author ryanabooth
 */
public class ClientDataSet {
	
	private Long dataSetID;
	private String name;
	private Date dateAdded;

	private ArrayList<ClientDataEntry> dataEntries;
	
	public ClientDataSet( Long dataSetID, String title, Date dAdded ) {
		this.dataSetID = dataSetID;
		this.name = title;
		this.dateAdded = dAdded;
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
	
	public void addEntry( ClientDataEntry entry ) {
		dataEntries.add(entry);
	}
	
	public void removeEntry( ClientDataEntry entry ) {
		dataEntries.remove(entry);
	}
	
	public ArrayList<ClientDataEntry> listAll() {
		return dataEntries;
	}
	
	public ClientDataEntry getClientDataEntry( String id ) throws EntryNotPresentException{
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
