package com.Team4.server;

import java.util.ArrayList;
import java.util.Date;
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

public class DataSetServiceImpl extends RemoteServiceServlet implements
		DataSetService {

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

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
				if (dataSetID == dataSet.getDataSetID()) {
					pm.deletePersistent(dataSet);
				}
			}
		} finally {
			pm.close();
		}
	}

	// public ArrayList<ClientDataSet> getDataSets() {
	// PersistenceManager pm = getPersistenceManager();
	// ArrayList<ClientDataSet> dSets;
	// try {
	// Query q = pm.newQuery(DataSet.class);
	// List<DataSet> DataSets = (List<DataSet>) q.execute();
	// dSets = new ArrayList<ClientDataSet>();
	// int i = 0;
	//
	// for (DataSet dSet : DataSets) {
	//
	// ArrayList<DataEntry> dataEntries = dSet.listAll();
	// ClientDataSet cDSet = new ClientDataSet(dSet.getDataSetID(),
	// dSet.getName(), dSet.getDateAdded());
	//
	// for (DataEntry dEntry : dataEntries ) {
	// cDSet.addEntry( dEntry );
	// }
	// dSets.add(cDSet);
	// i++;
	// }
	// } finally {
	// pm.close();
	// }
	// return dSets;
	// }

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	@Override
	public ArrayList<Long> getDataSetIDs() {
		PersistenceManager pm = getPersistenceManager();
		ArrayList<Long> dSetIDs;
		try {
			Query q = pm.newQuery(DataSet.class);
			List<DataSet> DataSets = (List<DataSet>) q.execute();
			dSetIDs = new ArrayList<Long>();

			for (DataSet dSet : DataSets) {
				dSetIDs.add( dSet.getDataSetID() );
			}
		} finally {
			pm.close();
		}
		return dSetIDs;
	}

	@Override
	public String getDataSetName(Long id) {
		PersistenceManager pm = getPersistenceManager();
		String name = null;
		try {
			Query q = pm.newQuery(DataSet.class);
			List<DataSet> DataSets = (List<DataSet>) q.execute();

			for (DataSet dSet : DataSets) {
				if (dSet.getDataSetID().equals(id))
					name = dSet.getName();
			}
		} finally {
			pm.close();
		}
		return name;
	}

	@Override
	public Date getDateAdded(Long id) {
		PersistenceManager pm = getPersistenceManager();
		Date dateAdded = null;
		try {
			Query q = pm.newQuery(DataSet.class);
			List<DataSet> DataSets = (List<DataSet>) q.execute();

			for (DataSet dSet : DataSets) {
				if (dSet.getDataSetID().equals(id))
					dateAdded = dSet.getDateAdded();
			}
		} finally {
			pm.close();
		}
		return dateAdded;
	}

	@Override
	public ArrayList<ClientDataEntry> getEntries(Long id) {
		PersistenceManager pm = getPersistenceManager();
		ArrayList<ClientDataEntry> entries = new ArrayList<ClientDataEntry>();
		entries.add( new ClientDataEntry("test", "test", "test", "test") );
		try {
			Query q = pm.newQuery(DataSet.class);
			List<DataSet> DataSets = (List<DataSet>) q.execute();

			for (DataSet dSet : DataSets) {
				if (dSet.getDataSetID().equals(id)){
					ArrayList<DataEntry> serverEntries = dSet.listAll();
					for (DataEntry dataEntry : serverEntries ){
						ClientDataEntry addMe = new ClientDataEntry( dataEntry.getID().toString(), dataEntry.getSchool(), dataEntry.getGrade(), dataEntry.getCourse());
						entries.add(addMe);
					}
				}
			}
		} finally {
			pm.close();
		}
		return entries;
	}
	
	public ClientDataEntry dummy (ClientDataEntry cde) {
		return null;
	}

}
