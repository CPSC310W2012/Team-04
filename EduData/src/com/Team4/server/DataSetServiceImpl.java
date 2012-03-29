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

/**
 * @author ryanabooth
 * Main server class used for various RPC calls in EduData
 */
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
	
	public static void addDataEntry(DataEntry dEntry) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(dEntry);
		} finally {
			pm.close();
		}
	}

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
			
			Query q2 = pm.newQuery(DataEntry.class);
			List<DataEntry> DataEntries = (List<DataEntry>) q2.execute();
			for (DataEntry dataEntry : DataEntries) {
				if (dataSetID == dataEntry.getDataSetID()) {
					pm.deletePersistent(dataEntry);
				}
			}
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	public ArrayList<ClientDataSet> getDataSets() {
		PersistenceManager pm = getPersistenceManager();
		ArrayList<ClientDataSet> dSets;
		try {
			Query q = pm.newQuery(DataSet.class);
			List<DataSet> DataSets = (List<DataSet>) q.execute();
			dSets = new ArrayList<ClientDataSet>();

			for (DataSet dSet : DataSets) {
				ClientDataSet cDataSet = new ClientDataSet( dSet.getDataSetID(), dSet.getName(), dSet.getDateAdded() );
				dSets.add( cDataSet );
			}
		} finally {
			pm.close();
		}
		return dSets;
	}

	public ArrayList<ClientDataEntry> getEntries() {
		PersistenceManager pm = getPersistenceManager();
		ArrayList<ClientDataEntry> cDEntries;
		try {
			Query q = pm.newQuery(DataEntry.class);
			List<DataEntry> entries = (List<DataEntry>) q.execute();
			cDEntries = new ArrayList<ClientDataEntry>();
			for (DataEntry dEntry : entries) {
						ClientDataEntry addMe = new ClientDataEntry( dEntry.getID().toString(), dEntry.getSchool(), dEntry.getGrade(), dEntry.getCourse(), dEntry.getDataSetID() );
						cDEntries.add( addMe );
			}
		} finally {
			pm.close();
		}
		return cDEntries;
	}

	public static void addMapPoint(MapPoint mp) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(mp);
		} finally {
			pm.close();
		}
	}
	
	public ArrayList<com.Team4.client.MapPoint> getMapPoints() {
		PersistenceManager pm = PMF.getPersistenceManager();
		ArrayList<com.Team4.client.MapPoint> returnList = new ArrayList<com.Team4.client.MapPoint>();
		try {
			Query q = pm.newQuery(MapPoint.class);
			List<MapPoint> entries = (List<MapPoint>) q.execute();
			for (MapPoint mp : entries) {
				com.Team4.client.MapPoint addMe = new com.Team4.client.MapPoint( mp.getSchoolName(), mp.getLatitude(), mp.getLongitude() );
				returnList.add( addMe );
			}
		} finally {
			pm.close();
		}
		return returnList;
	}
}
