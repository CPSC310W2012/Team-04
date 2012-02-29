package com.Team4.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class DataEntry {

	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private int entryID;
	@Persistent
	private String schoolName;
	@Persistent
	private int grade;
	@Persistent
	private String course;
	
	public DataEntry(String schName, int stGrade, String courseName, int ID){
		schoolName = schName;
		grade = stGrade;
		course = courseName;
		entryID = ID;	
	}
	
	public String getSchool(){
		return schoolName;
	}
	
	public void setSchoolName(String name){
		schoolName = name;	
	}
	
	public int getGrade(){
		return grade;
	}
	
	public void setGrade(int stGrade){
		grade = stGrade;
	}
	
	public int getID(){
		return entryID;
	}
	
	public String getCourse(){	
		return course;
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
	
}
