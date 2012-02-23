package com.Team4.client;

public class DataEntry {

	private String schoolName;
	private String grade;
	private int entryID;
	private String course;
	
	public DataEntry(String schName, String stGrade, String courseName, int ID){
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
	
	public String getGrade(){
		return grade;
	}
	
	public void setGrade(String stGrade){
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
