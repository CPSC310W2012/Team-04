package com.Team4.client.test;
import com.Team4.client.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ClientDataEntryTest {
	// Shared instances of the classes to test
	ClientDataEntry dEntry;
	
	@Test
	public void testGetters() {
		dEntry = new ClientDataEntry("0", "Babahooha", "12", "Astrophysics");
		String id = dEntry.getID();
		String school = dEntry.getSchool();
		String grade = dEntry.getGrade();
		String course = dEntry.getCourse();
		assert(id == "0");
		assert(school == "Babahooha");
		assert(grade == "12");
		assert(course == "Astrophysics");
	}
	
	/**
	 * Note that this test assumes that the getters work. Thus, if the preceding test is failing, this one
	 * will also fail.
	 * */
	@Test
	public void testSetters() {
		dEntry = new ClientDataEntry("0", "Ringaling", "12", "Astrophysics");
		
		dEntry.setSchoolName("BallooBalla");
		dEntry.setGrade("212");
		dEntry.setCourse("Microbiology");
		
		assert(dEntry.getSchool() == "BalloBalla");
		assert(dEntry.getGrade() == "212");
		assert(dEntry.getCourse() == "Microbiology");
	}
	
}
