package com.Team4.client;

import java.util.ArrayList;
import javax.jdo.annotations.Persistent;

import com.Team4.server.DataSet;

public class DataSetManager {
	
	private ArrayList<DataSet> dataSets;
	
	public void addDataSet( DataSet dSet ) {
		dataSets.add(dSet);
	}
	
	public DataSet removeDataSet( DataSet dSet ) throws DataSetNotPresentException {
		for( DataSet iter : dataSets ) {
			if( iter.getDataSetID() == dSet.getDataSetID() ) {
				dataSets.remove(iter);
				return iter;
			}
		}
		throw new DataSetNotPresentException( "Data set not found.");
	}
	
	public ArrayList<DataSet> listAll() {
		return dataSets;
	}
	
	public DataSet getDataSet( int id ) throws DataSetNotPresentException{
	    for( DataSet dSet : dataSets ) {
	    	if ( dSet.getDataSetID() == id )
	    		return dSet;
	    }
	    
	    throw new DataSetNotPresentException("Data Set not found.");
	}
}
