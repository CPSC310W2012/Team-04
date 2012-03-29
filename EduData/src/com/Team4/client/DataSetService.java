package com.Team4.client;

import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dataset")
public interface DataSetService extends RemoteService {
	  public void removeDataSet(Long dataSetID) throws DataSetNotPresentException;
	  public ArrayList<ClientDataSet> getDataSets();
	  public ArrayList<ClientDataEntry> getEntries();
	  public ArrayList<MapPoint> getMapPoints();
}
