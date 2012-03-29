package com.Team4.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.Team4.client.FileHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TxtHandler extends RemoteServiceServlet implements FileHandler{

	private DataSet dataSet;
	private int count;
	
	public TxtHandler(){};
	
	public void parseFile(String fileName, InputStream fstream) throws Exception{
	    try{
	    	
	    	dataSet = new DataSet(fileName);
	    	DataSetServiceImpl.addDataSet(dataSet);

	    	DataInputStream in = new DataInputStream(fstream);
	       
	    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        
	    	String line = br.readLine();
	        
	    	while (line != null) {
	        
	        	String[] toks = line.split("\\t+");
	        	

		    	if( fileName.equals("locations.txt") ) {
		    		MapPoint mp = new MapPoint( toks[0], Long.parseLong( toks[1] ), Long.parseLong( toks[2] ) );
		    		DataSetServiceImpl.addMapPoint( mp );
		    		return;
		    	}
	            
	        	if(toks[1].equals("SCHOOL LEVEL") && toks[2].equals("BC Public School")){
	        		
	        		List<MapPoint> points = DataSetServiceImpl.getMapPoints();
	        		Long latitude;
	        		Long longitude;
	        		// TODO Check whether the school name exists in list of Map Points and if so, add entry and set long and lat
	        		if(  ) {
	        		DataEntry dataEntry = new DataEntry(toks[6], toks[11], toks[7], dataSet.getDataSetID());
	        		DataSetServiceImpl.addDataEntry(dataEntry);
	        		}
	        	}
	        	line = br.readLine();
	        }
	
	    	in.close();
	    
	    }catch (Exception e){
	      System.err.println("End of File");
	    }
	}
	
}
