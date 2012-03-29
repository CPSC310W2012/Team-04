package com.Team4.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Client side DataEntry object
 * @author RBooth
 */
public class ClientDataEntry implements IsSerializable {
	private String entryID;
	private String schoolName;
	private String grade;
	private String course;
	private Long dataSetID;
	
	public ClientDataEntry(){
	}
	private double longitude;
	private double latitude;
	private GeoCoord geo;
	
	public ClientDataEntry(String entryID, String schName, String stGrade, String courseName, Long dataSetID){
		
		this.entryID = entryID;
		schoolName = schName;
		grade = stGrade;
		course = courseName;
		this.dataSetID = dataSetID;
		geo = new GeoCoord();
		try {
			geo.geoFile();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		latitude = geo.getLatitude(schName);
		longitude = geo.getLongitude(schName);

	}
	

	public String getSchool(){
		return schoolName;
	}
	
	public void setSchoolName(String name){
		schoolName = name;	
	}
	
	public String getGrade(){
		return grade;
	}
	
	public void setGrade(String stGrade){
		grade = stGrade;
	}
	
	public String getID(){
		return entryID;
	}

	public Long getDataSetID(){
		return this.dataSetID;
	}
	
	public String getCourse(){	
		return course;
	}
	
	public void setCourse(String courseName){
		course = courseName;	
	}
	
	public boolean equals( ClientDataEntry subject ) {
		if ( this.entryID == subject.entryID )
			return true;
		else
			return false;
	}


	public double getLongitude() {
		return longitude;
	}


	public double getLatitude() {
		return latitude;
	}
	
}
