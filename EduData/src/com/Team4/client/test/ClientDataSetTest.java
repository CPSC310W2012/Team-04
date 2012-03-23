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
		dSet = new ClientDataSet(id, "Booyah", today, 1);
		dSet.setName("Lollipop");
		
		assertTrue(dSet.getName() == "Lollipop");
	}
	
	/**
	 * Here we ensure the basic variable retrieval functions work
	 * */
	@Test
	public void testBasicGetters() {
		dSet = new ClientDataSet(id, "Booyah", today, 1);
		
		assertTrue(dSet.getDataSetID() == id);
		assertTrue(dSet.getName() == "Booyah");
		assertTrue(dSet.getDateAdded() == today);
	}
	
	/**
	 * Here we ensure that the AddEntry function works
	 * */
	@Test
	public void testAddEntry() {
		dSet = new ClientDataSet(id, "Booyah", today, size);
		ClientDataEntry[] entries = new ClientDataEntry[size];

		// We add a bunch ClientDataEntries...
		for(int a = 0; a < size; a++) {
			dEntry = new ClientDataEntry(Integer.toString(a), "Test", "12", "Astrophysics" );
			dSet.addEntry(dEntry);	
			entries[a] = dEntry;
		}
		
		for(int a = 0; a < size; a++) { // For every element in the array...
			try {
				dSet.getDataEntry(entries[a].getID()); // We assume that no exception should be thrown,
			} catch (EntryNotPresentException e) {	   // because every DataEntry should be present.
				e.printStackTrace();
			}
		}
	}
}