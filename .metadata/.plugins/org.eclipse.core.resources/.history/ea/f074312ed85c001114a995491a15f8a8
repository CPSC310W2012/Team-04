package com.Team4.client;

import java.util.ArrayList;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
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
		dateAdded = new Date();
		
	}
	
	public String getName() {
		return name;
	}

}
