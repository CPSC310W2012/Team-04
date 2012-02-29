package com.Team4.client;

import java.util.ArrayList;

import com.Team4.server.DataSet;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dataset")
public interface DataSetService extends RemoteService {
	  public void addDataSet(DataSet dSet);
	  public void removeDataSets(String symbol) throws DataSetNotPresentException;
	  public ArrayList<DataSet> getDataSets();
}
