package com.Team4.client;

public class DataEntry {

	private String schoolName;
	private int grade;
	private int entryID;
	private String course;
	
	public DataEntry(String schName, int stGrade, String courseName){
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
