package com.Team4.client;

import java.util.ArrayList;
import java.util.Date;

import com.Team4.client.ClientDataSet;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataSetServiceAsync {
	  public void removeDataSet(Long dataSetID, AsyncCallback<Void> async);
	  public void getDataSets(AsyncCallback<ArrayList<ClientDataSet>> async);
	  public void getEntries( AsyncCallback<ArrayList<ClientDataEntry>> asyncCallback );
	  public void getMapPoints( AsyncCallback<ArrayList<MapPoint>> asyncCallback );
}
