package com.Team4.server;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class DataEntry implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long entryID;
	@Persistent
	private String schoolName;
	@Persistent
	private String grade;
	@Persistent
	private String course;
	@Persistent
	private Long dataSetID;
	@Persistent
	private double longitude;
	@Persistent
	private double latitude;
	
	public DataEntry(String schName, String stGrade, String courseName, Long dSetID){
		schoolName = schName;
		grade = stGrade;
		course = courseName;
		this.dataSetID = dSetID;
		longitude = 0.0;
		latitude = 0.0;
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
	
	public Long getID(){
		return entryID;
	}
	
	public String getCourse(){	
		return course;
	}
	
	public Long getDataSetID(){
		return this.dataSetID;
	}
	
	public void setCourse(String courseName){
		course = courseName;	
	}
	
	public boolean equals( DataEntry subject ) {
		if ( this.entryID == subject.entryID )
			return true;
		else
			return false;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLatitude() {
		return latitude;
	}
	
}
