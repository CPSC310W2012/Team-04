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
//		this.loadDataSets();
		dataSets = new ArrayList<ClientDataSet>();
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
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<Long> dataSetIDs) {
				dataSets.clear();
				for( Long id : dataSetIDs ) {
					String name = this.retrieveDataSetName( id );
					Date dateAdded = this.retrieveDataSetDateAdded( id );
					ArrayList<ClientDataEntry> entries = this.retrieveDataSetEntries( id );
					ClientDataSet addMe = new ClientDataSet(id, name, dateAdded);
					for( ClientDataEntry entry : entries ) {
						addMe.addEntry(entry);
					}
					dataSets.add(addMe);
				}
			}

			private String retrieveDataSetName(Long id) {
				final String returnName;
				dSService.getDataSetName( id, new AsyncCallback<String>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(String name) {
						returnName = name;
					}
				});
				return returnName;
			}

			private Date retrieveDataSetDateAdded(Long id) {
				final Date returnDate;
				dSService.getDateAdded( id, new AsyncCallback<Date>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(Date date) {
						returnDate = date;
					}
				});
				return returnDate;
			}

			private ArrayList<ClientDataEntry> retrieveDataSetEntries(Long id) {
				ArrayList<ClientDataEntry> returnList;
				dSService.getEntries( id, new AsyncCallback<ArrayList<ClientDataEntry>>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(ArrayList<ClientDataEntry> entries) {
						returnList = entries;
					}
				});
				return returnList;
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