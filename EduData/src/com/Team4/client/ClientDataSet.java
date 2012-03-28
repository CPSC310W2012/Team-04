package com.Team4.client;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Client side DataSet object
 * @author ryanabooth
 */
public class ClientDataSet implements IsSerializable {
	
	private Long dataSetID;
	private String name;
	private Date dateAdded;
	
	public ClientDataSet() {
		
	}
	
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

	public Date getDateAdded() {
		return dateAdded;
	}
	
}
