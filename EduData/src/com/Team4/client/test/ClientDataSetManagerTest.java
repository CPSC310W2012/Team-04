package com.Team4.client.test;

import java.util.Date;
import java.util.Random;

import org.junit.Test;

import com.Team4.client.ClientDataSet;
import com.Team4.client.ClientDataSetManager;
import com.Team4.client.DataSetNotPresentException;
import com.google.gwt.junit.client.GWTTestCase;

public class ClientDataSetManagerTest extends GWTTestCase {
	public ClientDataSetManager dSetM;
	public ClientDataSet dSet;
	
	public Date today = new Date();
	public int size = 10;
	public Random rand = new Random();

	@Override
	public String getModuleName() {
		return "com.Team4.client.edudata";
	}
	
	@Test
	public void testAddDataSet() {
		dSetM = new ClientDataSetManager();
		ClientDataSet entries[] = new ClientDataSet[size];
		// We add a bunch of DataSets to the DataSetManager...
		for (int a = 0; a < size; a++) { 
			dSet = new ClientDataSet((long) a, Integer.toString(a), today);
			dSetM.addDataSet( dSet );
			entries[a] = dSet;
		}
		
		// Then we make sure that all of them were successfully added
		// by checking if they exist in the DataSetManager.
		for (int a = 0; a < size; a++) { 
			assertTrue( dSetM.listAll().contains( entries[a] ) );
		}
	}
	
	@Test
	public void testRemoveRandomDataSet() {
		dSetM = new ClientDataSetManager();
		ClientDataSet entries[] = new ClientDataSet[size];
		// We add a bunch of DataSets to the DataSetManager...
		for (int a = 0; a < size; a++) { 
			dSet = new ClientDataSet((long) a, Integer.toString(a), today);
			dSetM.addDataSet( dSet );
			entries[a] = dSet;
		}
		
		// Then we randomly choose one, and remove it...
		int i = rand.nextInt(size);
		try {
			dSetM.removeDataSet( entries[i] );
		} catch (DataSetNotPresentException e) {
			System.out.println("Oh shit! Something seriously fucked up!");
		}
		
		// Finally, we assert that the removed DataSet is not present
		assert ( !dSetM.listAll().contains( entries[i] ) );
	}
	
	public void testDataSetNotPresent() {
		dSetM = new ClientDataSetManager();
		ClientDataSet entries[] = new ClientDataSet[size];
		// We add a bunch of DataSets to the DataSetManager...
		for (int a = 0; a < size; a++) { 
			dSet = new ClientDataSet((long) a, Integer.toString(a), today);
			dSetM.addDataSet( dSet );
			entries[a] = dSet;
		}
		
		boolean thrown = false;
		// We create a DataSet that is not present in the DataSetManager
		dSet = new ClientDataSet((long) size + 10, Integer.toString(size+10), today); 
		try {
			dSetM.removeDataSet(dSet);
		} catch (DataSetNotPresentException e) {
			// If an exception is thrown like it should be, we set thrown to true
			thrown = true;
		}
		// If all went well, thrown will be true
		assertTrue(thrown == true);
	}
	
	public void testEmtpyRemoveDataSet() {
		dSetM = new ClientDataSetManager();
		// We add no DataSets to the manager
		
		boolean thrown = false;
		// We create a DataSet that is not present in the DataSetManager
		dSet = new ClientDataSet((long) size + 10, Integer.toString(size+10), today); 
		try {
			dSetM.removeDataSet(dSet);
		} catch (DataSetNotPresentException e) {
			// If an exception is thrown like it should be, we set thrown to true
			thrown = true;
		}
		// If all went well, thrown will be true
		assertTrue(thrown == true);
	}
	
	public void testGetDataSet() {
		dSetM = new ClientDataSetManager();
		ClientDataSet entries[] = new ClientDataSet[size];
		// We add a bunch of DataSets to the DataSetManager...
		for (int a = 0; a < size; a++) { 
			dSet = new ClientDataSet((long) a, Integer.toString(a), today);
			dSetM.addDataSet( dSet );
			entries[a] = dSet;
		}
		
		//Then we randomly pick one and retrieve it from the DataSetManager...
		int i = rand.nextInt(size);
		ClientDataSet temp = null;
		//And assert that the one returned is the one we want
		try {
			temp = dSetM.getDataSet(entries[i].getDataSetID());
		} catch (DataSetNotPresentException e) {
			e.printStackTrace();
		  }
		assertTrue(temp == entries[i]);
		}
	
	public void testGetAbsentDataSet() {
		dSetM = new ClientDataSetManager();
		ClientDataSet entries[] = new ClientDataSet[size];
		// We add a bunch of DataSets to the DataSetManager...
		for (int a = 0; a < size; a++) { 
			dSet = new ClientDataSet((long) a, Integer.toString(a), today);
			dSetM.addDataSet( dSet );
			entries[a] = dSet;
		}
		
		// Then we create a data set NOT contained by the DataSetManager
		dSet = new ClientDataSet((long) size + 10, Integer.toString(size+10), today); 
		
		boolean thrown = false;
		
		try {
			// We try to get the set that is not contained by the DataSetManager...
		} catch (Exception e) {
			// And it should throw an exception
			thrown = true;
		}
		
		assertTrue(thrown == true);
	}
	
	public void testEmtpyGetDataSet() {
		dSetM = new ClientDataSetManager();
		// We add no DataSets to the manager
		
		boolean thrown = false;
		// We create a DataSet that is not present in the DataSetManager...
		dSet = new ClientDataSet((long) size + 10, Integer.toString(size+10), today); 
		try {
			dSetM.getDataSet(dSet.getDataSetID()); // And try to get it
		} catch (DataSetNotPresentException e) {
			// If an exception is thrown like it should be, we set thrown to true
			thrown = true;
		}
		// If all went well, thrown will be true
		assertTrue(thrown == true);
	}
	
	}
	
	//TODO: We should probably add a test for the loadDataSetMethod too
