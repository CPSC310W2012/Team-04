package com.Team4.client.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.junit.Test;

import com.Team4.client.ClientDataEntry;
import com.Team4.client.ClientDataSet;

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
	
	/**
	 * Here we ensure that the AddEntry function works
	 * */
	@Test
	public void testAddEntry() {
		dSet = new ClientDataSet(id, "Booyah", today);
		ClientDataEntry[] entries = new ClientDataEntry[size];

		// We add a bunch ClientDataEntries...
		for(int a = 0; a < size; a++) {
			dEntry = new ClientDataEntry(Integer.toString(a), "Test", "12", "Astrophysics" );
			dSet.addEntry(dEntry);	
			entries[a] = dEntry;
		}
		
		for(int a = 0; a < size; a++) { // For every element in the array...
			assertTrue( dSet.listAll().contains(entries[a])); // We assert that it exists somewhere in the dataSet
		}
	}
	
	/**
	 * This test adds a bunch of entries, and then removes all of them.
	 * */
	@Test
	public void testRemoveAllEntries() {
		dSet = new ClientDataSet(id, "Booyah", today);
		ClientDataEntry[] entries = new ClientDataEntry[size];

		// We add a bunch of ClientDataEntries...
		for(int a = 0; a < size; a++) {
			dEntry = new ClientDataEntry(Integer.toString(a), "Test", "12", "Astrophysics" );
			dSet.addEntry(dEntry);	
			entries[a] = dEntry;
		}
		// Then remove all of them
		for(int a = 0; a < size; a++) {
			dSet.removeEntry(entries[a]);	
			}
		// And ensure that all of them are gone
		int count = 0;
		for (ClientDataEntry de : dSet.listAll()) {
			count++; // We count the # of DataEntries in the DataSet
		}
		assertTrue(count == 0); // If all is well, there shouldn't be any
	}
	
	/**
	 * This test tests the remove function on the edges of the array
	 * */
	@Test
	public void testRemoveBoundaryCaseEntries() {
		dSet = new ClientDataSet(id, "Booyah", today);
		ClientDataEntry[] entries = new ClientDataEntry[size];

		// We add a bunch of ClientDataEntries...
		for(int a = 0; a < size; a++) {
			dEntry = new ClientDataEntry(Integer.toString(a), "Test", "12", "Astrophysics" );
			dSet.addEntry(dEntry);	
			entries[a] = dEntry;		
		}
		
		// We remove the boundary cases...
		dSet.removeEntry(entries[0]);
		dSet.removeEntry(entries[size - 1]);
		
		//And then we ensure they have been removed properly
		for (ClientDataEntry de : dSet.listAll()) {
			assertTrue(de != entries[0]);
			assertTrue(de != entries[size - 1]);
		}
	}
	
	/**
	 * This test tests the remove function on some random DataEntry in the DataSet
	 * */
	@Test
	public void testRemoveRandomEntry() {
		dSet = new ClientDataSet(id, "Booyah", today);
		ClientDataEntry[] entries = new ClientDataEntry[size];
		
		// We add a bunch ClientDataEntries, and then randomly remove one.
		for(int a = 0; a < size; a++) {
			dEntry = new ClientDataEntry(Integer.toString(a), "Test", "12", "Astrophysics" );
			dSet.addEntry(dEntry);
			entries[a] = dEntry;
			}
		
		int i = rand.nextInt(size);
		dSet.removeEntry( entries[i] );
		
		// Now we check to see that the proper entry has been removed
		for (ClientDataEntry de : dSet.listAll() ) {
			assertTrue(de != entries[i]);
		}
	}
}

