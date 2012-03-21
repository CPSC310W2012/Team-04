package com.Team4.client;

import java.util.ArrayList;

import com.Team4.client.ClientDataSet;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dataset")
public interface DataSetService extends RemoteService {
	  public void removeDataSet(Long dataSetID) throws DataSetNotPresentException;
	  public ArrayList<ClientDataSet> getDataSets();
}
