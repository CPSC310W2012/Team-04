package com.Team4.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.Team4.client.FileHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TxtHandler extends RemoteServiceServlet implements FileHandler{

	private DataSet dataSet;
	private int count;
	
	public TxtHandler(){};
	
	public DataSet parseFile(File file) throws Exception{
	    try{
	    	dataSet = new DataSet(file.getName());
	    	
	    	FileInputStream fstream = new FileInputStream(file);

	    	DataInputStream in = new DataInputStream(fstream);
	       
	    	BufferedReader br = new BufferedReader(new InputStreamReader(in));

	    	//Map<String, String> m = new LinkedHashMap<String, String>();	    	
	        
	    	String line = br.readLine();
	    	
	    	count = 0;
	        
	    	while (line != null) {
	        
	        	String[] toks = line.split("\\t+");
	            
	        	if(toks[1].equals("SCHOOL LEVEL") && toks[2].equals("BC Public School")){
	        	
	        		
	        		// Test - correct data entry values
	        		System.out.println("School: "+toks[6]+" Grade: "+ toks[11]+" Course: "+toks[7]);
	        	
	        		DataEntry dataEntry = new DataEntry(toks[6], Integer.parseInt(toks[11]), toks[7], count);
	        	
	        		//Test - match toks[] and DataEntry
	        		System.out.println("School: "+dataEntry.getSchool()+ "Grade: "+dataEntry.getGrade() +" Course: "+dataEntry.getCourse()+" Entry ID: "+dataEntry.getID());
	        	
	        		dataSet.addEntry(dataEntry);
	        	
	        	}
	        
	        	line = br.readLine();
	        	
	        	count++;
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
