package com.Team4.client;

import java.util.ArrayList;
import com.Team4.server.DataSet;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataSetServiceAsync {
	  public void addDataSet(DataSet dSet, AsyncCallback<Void> async);
	  public void removeDataSets(String symbol, AsyncCallback<Void> async);
	  public void getDataSets(AsyncCallback<ArrayList<DataSet>> async);
}
