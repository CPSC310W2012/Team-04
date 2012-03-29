package com.Team4.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.Team4.client.FileHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author kgajos
 * @author ryanabooth
 * File reader for location data and Data Sets
 */
public class TxtHandler extends RemoteServiceServlet implements FileHandler{

	private DataSet dataSet;
	
	public TxtHandler(){};
	
	public void parseFile(String fileName, InputStream fstream) throws Exception{
	    try{

	    	DataInputStream in = new DataInputStream(fstream);
	    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    	String line = br.readLine();
	    	
	    	if( fileName.equals("locations.txt") ) {
	    		int i = 1;
		    	// Skip first line - just headers
		    	line = br.readLine();
		    	while (line != null) {
		    		// Split the Strings along tabs
	        		String[] toks = line.split("\\t+");
	        		
	        		// Make a map point from school name, latitude and longitude
		    		MapPoint mp = new MapPoint( toks[0], Double.parseDouble( toks[1] ), Double.parseDouble( toks[2] ) );
		    		DataSetServiceImpl.addMapPoint( mp );
		    		
		    		// Read the next line in
		    		line = br.readLine();
		    		i++;
			    }
	    	}
	    	
	    	else {
		    	// Save Data Set metadata
		    	dataSet = new DataSet(fileName);
		    	DataSetServiceImpl.addDataSet(dataSet);
		    	
		    	while (line != null) {
		        	String[] toks = line.split("\\t+");
		            
		        	// Check formatting
		        	if(toks[1].equals("SCHOOL LEVEL") && toks[2].equals("BC Public School")){
		        		// Make a new Data Entry based on parsed data
		        		if( !toks[11].equals("Msk") ) {
		        		DataEntry dataEntry = new DataEntry(toks[6], toks[11], toks[7], dataSet.getDataSetID());
		        		DataSetServiceImpl.addDataEntry(dataEntry);
		        		}
		        	}
		        	// Read next line
		        	line = br.readLine();
		        }
		    	
		    	in.close();
	    	}
	    
	    }catch (Exception e){
	      System.err.println(e.getMessage());
	    }
	}
	
}
