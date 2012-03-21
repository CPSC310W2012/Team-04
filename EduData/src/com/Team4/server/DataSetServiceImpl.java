package com.Team4.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.Team4.client.ClientDataEntry;
import com.Team4.client.ClientDataSet;
import com.Team4.client.DataSetNotPresentException;
import com.Team4.client.DataSetService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataSetServiceImpl extends RemoteServiceServlet implements DataSetService {

	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public static void addDataSet(DataSet dSet) {
		    PersistenceManager pm = PMF.getPersistenceManager();
		    try {
		      pm.makePersistent(dSet);
		    } finally {
		      pm.close();
		    }
	}
	
	@Override
	public void removeDataSet(Long dataSetID) throws DataSetNotPresentException {
		PersistenceManager pm = getPersistenceManager();
		try {
		      Query q = pm.newQuery(DataSet.class);
		      List<DataSet> DataSets = (List<DataSet>) q.execute();
		      for (DataSet dataSet : DataSets) {
		        if (dataSetID == dataSet.getDataSetID() ) {
		          pm.deletePersistent(dataSet);
		        }
		      }
		    } finally {
		      pm.close();
		    }
	}

	@Override
	public ArrayList<ClientDataSet> getDataSets() {
		PersistenceManager pm = getPersistenceManager();
	    ArrayList<ClientDataSet> dSets = new ArrayList<ClientDataSet>();
	    try {
	      Query q = pm.newQuery(DataSet.class);
	      q.setOrdering("dateAdded");
	      List<DataSet> DataSets = (List<DataSet>) q.execute();
	      for (DataSet dSet : DataSets) {
	    	System.out.println(dSet.getDataSetID().toString() + " " + dSet.getName() + " " + dSet.getDateAdded());
	        ClientDataSet cDSet = new ClientDataSet(dSet.getDataSetID(), dSet.getName(), dSet.getDateAdded());
	        ArrayList<DataEntry> dataEntries = dSet.listAll();
	        for (DataEntry dEntry : dataEntries ) {
		    	System.out.println(dEntry.getID().getName() + " " + dEntry.getSchool() + " " + dEntry.getCourse() + " " + dEntry.getGrade());
	        	cDSet.addEntry( new ClientDataEntry( dEntry.getID().getName(), dEntry.getSchool(), dEntry.getCourse(), dEntry.getGrade() ));
	        }
	        dSets.add(cDSet);
	      }
	    } finally {
	      pm.close();
	    }
	    return dSets;
	}
	
	private PersistenceManager getPersistenceManager() {
	    return PMF.getPersistenceManager();
	}

}
