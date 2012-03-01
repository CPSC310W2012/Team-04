package com.Team4.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.Team4.client.FileHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TxtHandler extends RemoteServiceServlet implements FileHandler{

	private DataSet dataSet;
	private int count;
	
	public TxtHandler(){};
	
	public DataSet parseFile(String fileName, InputStream fstream) throws Exception{
	    try{
	    	dataSet = new DataSet(fileName);

	    	DataInputStream in = new DataInputStream(fstream);
	       
	    	BufferedReader br = new BufferedReader(new InputStreamReader(in));

	    	//Map<String, String> m = new LinkedHashMap<String, String>();	    	
	        
	    	String line = br.readLine();
	        
	    	while (line != null) {
	        
	        	String[] toks = line.split("\\t+");
	            
	        	if(toks[1].equals("SCHOOL LEVEL") && toks[2].equals("BC Public School")){
	        	
	        		
	        		// Test - correct data entry values
	        		System.out.println("School: "+toks[6]+" Grade: "+ toks[11]+" Course: "+toks[7]);
	        		
	        		String msk = new String("Msk");
	        		if( toks[11].equals(msk) )
	        			toks[11] = "0";
	        		DataEntry dataEntry = new DataEntry(toks[6], toks[11], toks[7]);
	        	
	        		//Test - match toks[] and DataEntry
	        		System.out.println("School: "+dataEntry.getSchool()+ "Grade: "+dataEntry.getGrade() +" Course: "+dataEntry.getCourse());
	        	
	        		dataSet.addEntry(dataEntry);
	        	
	        	}
	        	line = br.readLine();
	        }
	
	    	in.close();
	    
	    }catch (Exception e){
	      System.err.println("Error: " + e.getMessage());
	    }
		return dataSet;
	}
	
//	Old Test
//	public static void main(String [ ] args) throws Exception
//	{
//	      TxtHandler hdl = new TxtHandler();
//	      hdl.parseFile("Chemistry12Hist.txt");
//	}
//	
}
