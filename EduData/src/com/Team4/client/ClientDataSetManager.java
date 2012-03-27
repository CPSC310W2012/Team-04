package com.Team4.client;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientDataSetManager {

	private ArrayList<ClientDataSet> dataSets;
	private final DataSetServiceAsync dSService = GWT.create(DataSetService.class);
	
	public ClientDataSetManager() {
		dataSets = new ArrayList<ClientDataSet>();
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
		dSService.getDataSetIDs( new AsyncCallback<ArrayList<Long>>() {
			String returnName;
			Date returnDate;
			String[][] returnList;
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<Long> dataSetIDs) {
				dataSets.clear();
				for( Long id : dataSetIDs ) {
					this.retrieveDataSetName( id );
					String name = returnName;
					this.retrieveDataSetDateAdded( id );
					Date dateAdded = returnDate;
					this.retrieveDataSetEntries( id );
					String[][] entries = returnList;
					ClientDataSet addMe = new ClientDataSet(id, name, dateAdded);
					for( int i = 0; i < entries.length; i++ ) {
						ClientDataEntry dataEntry = new ClientDataEntry( entries[i][0], entries[i][1], entries[i][2], entries[i][3]);
						addMe.addEntry(dataEntry);
					}
					dataSets.add(addMe);
				}
			}

			private void retrieveDataSetName(Long id) {
				dSService.getDataSetName( id, new AsyncCallback<String>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(String name) {
						returnName = name;
					}
				});
			}

			private void retrieveDataSetDateAdded(Long id) {
				dSService.getDateAdded( id, new AsyncCallback<Date>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(Date date) {
						returnDate = date;
					}
				});
			}

			private void retrieveDataSetEntries(Long id) {
				
				dSService.getEntries( id, new AsyncCallback<String[][]>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(String[][] entries) {
						returnList = entries;
					}
				});
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