package com.Team4.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.Team4.client.FileHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author kgajos
 * @author ryanabooth
 */
public class TxtHandler extends RemoteServiceServlet implements FileHandler{

	private DataSet dataSet;
	private int count;
	
	public TxtHandler(){};
	
	public void parseFile(String fileName, InputStream fstream) throws Exception{
	    try{
	    	
	    	if( !fileName.equals("locations.txt") ) {
		    	dataSet = new DataSet(fileName);
		    	DataSetServiceImpl.addDataSet(dataSet);
	    	}

	    	DataInputStream in = new DataInputStream(fstream);
	    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    	String line = br.readLine();
	    	
	    	if( fileName.equals("locations.txt") ) {
        		System.out.println("inside the if statement");
		    	// Skip first line - just headers
		    	line = br.readLine();
		    	while (line != null) {
		    		// Split the Strings along tabs
	        		String[] toks = line.split("\\t+");
	        		
	        		// Make a map point from school name, latitude and longitude
		    		MapPoint mp = new MapPoint( toks[0], Long.parseLong( toks[1] ), Long.parseLong( toks[2] ) );
	        		System.out.println("" + mp.getSchoolName() + " --- " + mp.getLatitude() + " --- " + mp.getLongitude() );
		    		DataSetServiceImpl.addMapPoint( mp );
		    		
		    		// Read the next line in
		    		line = br.readLine();
			    }
	    	}
	    	
	    	else {
        		
		    	while (line != null) {
		        
		        	String[] toks = line.split("\\t+");
		            
		        	if(toks[1].equals("SCHOOL LEVEL") && toks[2].equals("BC Public School")){
		        		
//		        		String entrySchoolName = toks[6];
//		        		List<MapPoint> points = DataSetServiceImpl.getMapPoints();
//		        		for( MapPoint mp : points ) {
//		        			if( entrySchoolName.equals(mp.getSchoolName()) ) {
				        		DataEntry dataEntry = new DataEntry(toks[6], toks[11], toks[7], dataSet.getDataSetID());
//				        		dataEntry.setLatitude( mp.getLatitude() );
//				        		dataEntry.setLongitude( mp.getLongitude() );
				        		DataSetServiceImpl.addDataEntry(dataEntry);
//		        			}
//		        		}
		        	}
		        	line = br.readLine();
		        }
	    	}
	
	    	in.close();
	    
	    }catch (Exception e){
	      System.err.println("End of File");
	    }
	}
	
}
