package com.Team4.client;

import java.util.ArrayList;
import com.Team4.client.ClientDataSet;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataSetServiceAsync {
	  public void removeDataSet(Long dataSetID, AsyncCallback<Void> async);
	  public void getDataSets(AsyncCallback<ArrayList<ClientDataSet>> async);
}
