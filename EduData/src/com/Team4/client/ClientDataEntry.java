package com.Team4.client;

public class ClientDataEntry {

	private String entryID;
	private String schoolName;
	private String grade;
	private String course;
	
	public ClientDataEntry(String entryID, String schName, String stGrade, String courseName){
		this.entryID = entryID;
		schoolName = schName;
		grade = stGrade;
		course = courseName;
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
	
}
