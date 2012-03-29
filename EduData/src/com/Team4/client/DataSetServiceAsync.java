package com.Team4.client;

import java.util.ArrayList;
import java.util.Date;

import com.Team4.client.ClientDataSet;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataSetServiceAsync {
	  public void removeDataSet(Long dataSetID, AsyncCallback<Void> async);
	  public void getDataSets(AsyncCallback<ArrayList<ClientDataSet>> async);
//	  public void dummy( ClientDataEntry cde, AsyncCallback<ClientDataEntry> asyncCallback );
//	  public void getDataSetIDs(AsyncCallback<ArrayList<Long>> asyncCallback);
//	  public void getDataSetName( Long id, AsyncCallback<String> asyncCallback );
//	  public void getDateAdded( Long id, AsyncCallback<Date> asyncCallback );
	  public void getEntries( AsyncCallback<ArrayList<ClientDataEntry>> asyncCallback );
}
