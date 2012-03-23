package com.Team4.client;
import java.util.Date;

import com.Team4.client.EntryNotPresentException;

/**
 * Client side DataSet object
 * @author ryanabooth
 */
public class ClientDataSet {
	
	private Long dataSetID;
	private String name;
	private Date dateAdded;
	
	// 2D String Array
	// ROWS String entryID
	// COLUMN String schoolName
	// COLUMN String grade
	// COLUMN String course
	private String[][] dataEntries;
	
	public ClientDataSet( Long dataSetID, String title, Date dAdded, int size ) {
		this.dataSetID = dataSetID;
		this.name = title;
		this.dateAdded = dAdded;
		this.dataEntries = new String[size][4];
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
	
	/**
	 * @param entry - DataEntry to be added
	 * @pre The correct size was used to instantiate this DataSet
	 * 
	 * */
	public void addEntry( String ID, String schoolName, String grade, String course ) {
		for( int i=0; i < dataEntries.length; i++) {
			if( dataEntries[i] == null )
				dataEntries[i][0] = ID;
				dataEntries[i][1] = schoolName;
				dataEntries[i][2] = grade;
				dataEntries[i][3] = course;
		}
	}
	
	public String[][] listAll() {
		return dataEntries;
	}
	
	/**
	 * @param id
	 * @return Four element String array in the format ID, schoolName, grade, course
	 * @throws EntryNotPresentException
	 */
	public String[] getDataEntry( String id ) throws EntryNotPresentException {
		String[] dataEntry = new String[4];
		
	    for( int i = 0; i < dataEntries.length; i++ ) {
	    	if ( dataEntries[i][0].equals(id) )
	    	{
				dataEntry[0] = dataEntries[i][0];
				dataEntry[1] = dataEntries[i][1];
				dataEntry[2] = dataEntries[i][2];
				dataEntry[3] = dataEntries[i][3];
	    		return dataEntry;
	    	}
	    }
	    
	    throw new EntryNotPresentException("Entry not found.");
	}
	
}
