package com.Team4.client;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientDataSetManager {

	private ArrayList<ClientDataSet> dataSets = new ArrayList<ClientDataSet>();
	private final DataSetServiceAsync dSService = GWT.create(DataSetService.class);
	
	public ClientDataSetManager() {
		this.loadDataSets();
	}
	
	public void addDataSet( ClientDataSet dSet ) {
		dataSets.add(dSet);
	}

	public ClientDataSet removeDataSet( ClientDataSet dSet ) throws DataSetNotPresentException {
		for( ClientDataSet iter : dataSets ) {
			if( iter.getDataSetID() == dSet.getDataSetID() ) {
				dataSets.remove(iter);
				dSService.removeDataSet( dSet.getDataSetID(), new AsyncCallback<Void>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(Void ignore) {
					}
				});
				return iter;
			}
		}
		throw new DataSetNotPresentException( "Data set not found.");
	}
	
	public void loadDataSets() {
		dSService.getDataSets( new AsyncCallback<ArrayList<ClientDataSet>>() {
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<ClientDataSet> result) {
				dataSets = result;				
			}
		});
	}

	public ArrayList<ClientDataSet> listAll() {
		return dataSets;
	}

	public ClientDataSet getDataSet( Long id ) throws DataSetNotPresentException{
	    for( ClientDataSet dSet : dataSets ) {
	    	if ( dSet.getDataSetID() == id )
	    		return dSet;
	    }

	    throw new DataSetNotPresentException("Data Set not found.");
	}
	
	private void handleError(Throwable error) {
	    Window.alert(error.getMessage());
	}
}