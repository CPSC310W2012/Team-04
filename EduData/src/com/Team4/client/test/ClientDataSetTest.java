package com.Team4.client.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.junit.Test;

import com.Team4.client.ClientDataEntry;
import com.Team4.client.ClientDataSet;
import com.Team4.client.EntryNotPresentException;

public class ClientDataSetTest {
	ClientDataSet dSet;
	ClientDataEntry dEntry;
	
	Random rand = new Random();
	Date today = new Date();
	int size = 1000;
	long id = 1;
	
	@Test
	public void testSetName() {
		dSet = new ClientDataSet(id, "Booyah", today);
		dSet.setName("Lollipop");
		
		assertTrue(dSet.getName() == "Lollipop");
	}
	
	/**
	 * Here we ensure the basic variable retrieval functions work
	 * */
	@Test
	public void testBasicGetters() {
		dSet = new ClientDataSet(id, "Booyah", today);
		
		assertTrue(dSet.getDataSetID() == id);
		assertTrue(dSet.getName() == "Booyah");
		assertTrue(dSet.getDateAdded() == today);
	}
}