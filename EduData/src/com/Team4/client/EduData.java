package com.Team4.client;
// March 22 2012
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.Team4.server.DataEntry;
import com.Team4.server.DataSet;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.maps.client.overlay.PolygonOptions;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EduData implements EntryPoint {
	private TabularUI tabUI;
	
	private RootPanel root;
	private HorizontalPanel basePanel;
	private HorizontalPanel buttonPanel;
	private VerticalPanel leftSidebarPanel;
	private VerticalPanel dataSetPanel;
	private VerticalPanel visualizePanel;
	private MapWidget map;
	private ArrayList<ClientDataSet> dataSets;
	private final DataSetServiceAsync dSService = GWT.create(DataSetService.class);
	private CellTable<ClientDataSet> table;


	
	public void onModuleLoad() {
		dataSets = new ArrayList<ClientDataSet>();
		loadDataSets();
		
		tabUI = new TabularUI();
		

		/*Kaya
		 * - adding default map directly centered on vancouver w/ marker
		 * probably should make this into a method of its own
		 * 
		 * */
		  Maps.loadMapsApi("AIzaSyC6ilLpJA3loHZ1kM7clv_0M-PauIBKBTA", "2", false, new Runnable() {
		      public void run() {
		        buildMap();
		      }

			private void buildMap() {
				
				LatLng vancouver = LatLng.newInstance(49.150, -123.100);
			    map = new MapWidget(vancouver, 8);
				map.addControl(new LargeMapControl());
			}
		    });

		root = RootPanel.get();
		basePanel = new HorizontalPanel();
		buttonPanel = new HorizontalPanel();
		leftSidebarPanel = new VerticalPanel();
		dataSetPanel = new VerticalPanel();
		visualizePanel = new VerticalPanel();
		
		root.add(basePanel);
		root.addStyleName("background");
		basePanel.addStyleName("background");
		leftSidebarPanel.addStyleName("background");
		visualizePanel.addStyleName("background");
		basePanel.add(leftSidebarPanel);
		basePanel.add(visualizePanel);
		leftSidebarPanel.add(buttonPanel);
		leftSidebarPanel.add(dataSetPanel);
		
		root.setSize( "100%" , "100%" );
		basePanel.setSize( "100%" , "100%" );
		leftSidebarPanel.setSize( "30%" , "100%" );
		visualizePanel.setSize( "70%" , "100%" );
		buttonPanel.setSize( "100%" , "20%" );
		dataSetPanel.setSize( "100%" , "80%" );
		
		basePanel.setBorderWidth( 1 );
		leftSidebarPanel.setBorderWidth( 1 );
		visualizePanel.setBorderWidth( 1 );
		buttonPanel.setBorderWidth( 1 );
		dataSetPanel.setBorderWidth( 1 );
		
		Button importButton = new Button("Import");
		buttonPanel.add(importButton);
		buttonPanel.setCellVerticalAlignment(importButton, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(importButton, HasHorizontalAlignment.ALIGN_CENTER);
		 importButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					final PopupPanel uploadPanel = new PopupPanel();
					SingleUploader defaultUploader = new SingleUploader();
				    uploadPanel.add(defaultUploader);
					uploadPanel.setPopupPosition(300, 300);
				    uploadPanel.show();
					defaultUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
						public void onFinish(IUploader uploader) {
							UploadedInfo info = uploader.getServerInfo();
							System.out.println(info.message);
							uploadPanel.hide();
							loadDataSets();
						}					    
					});
				}
			});		
		 
		//	Here we define and implement the Remove button. When clicked, this button will remove all selected DataSets from the DataSetManager
		Button button0 = new Button("Remove Selected");
		button0.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ArrayList<ClientDataSet> selected = getSelectedDataSets( table );
				System.out.println( selected.size() + " Data Sets are selected. Bitch.");
				for ( ClientDataSet dSet : selected ) {
					try {
						removeDataSet( dSet );
					} catch (DataSetNotPresentException e) {
						// If the code reaches this point, then we are trying to remove a data set that no longer exists
						// In other words, we don't care.
						// TODO: Add some intelligent response to trying to remove a DataSet that doesn't exist
					}
				}
				// At this point all selected DataSets have been removed, so we need to update the cell table
				table.setRowData( dataSets );
			}
		});
		buttonPanel.add(button0);
		buttonPanel.setCellVerticalAlignment(button0, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(button0, HasHorizontalAlignment.ALIGN_CENTER);
		
		//  Refreshes DataSet list
		Button button99 = new Button("Refresh");
		button99.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadDataSets();
				System.out.println( "-------Refreshed--------");
			}
		});
		buttonPanel.add(button99);
		buttonPanel.setCellVerticalAlignment(button99, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(button99, HasHorizontalAlignment.ALIGN_CENTER);
		

		// Here we define and implement the Visualize button. When clicked, this button will call upon the MapUI to display all selected DataSets on the Map.
		Button button = new Button("Visualize Selected");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Implement the MapUI visualize sequence. Call on the TabUI to see what DataSets are selected

	
				   visualizePanel.clear();
				   ArrayList<ClientDataEntry> entries = this.populateDummyData(); // create external or internal function?
				/*
				   for ( ClientDataEntry dEntry : entries ) {
				    	LatLng coordinate = LatLng.newInstance(dEntry.getLatitude(), dEntry.getLongitude());
				    	
				    	if(Integer.parseInt(dEntry.getGrade()) <= 100){

				    		String url = "http://www.google.com/mapfiles/markerA.png";
				    		Icon icon = Icon.newInstance("http://www.spikee.com/wp-content/uploads/r2d2-usb-hub.gif");
				    		icon.setIconSize(Size.newInstance(20, 34));
				    		MarkerOptions ops = MarkerOptions.newInstance(icon);
				    		Marker marker = new Marker(coordinate, ops);
				    		map.addOverlay(marker);

				    	}
				    	
				    }
				*/
				   //renderMap(selectedDataSets);
				   
				   visualizePanel.add(map);
				   map.setSize( "1000px", "600px");
			}
			
			public ArrayList<ClientDataEntry> populateDummyData() {
				
				ArrayList<ClientDataEntry> dataSet = new ArrayList<ClientDataEntry>();
				Long i = new Long(1);
				dataSet.add( new ClientDataEntry("1", "Lochdale Elementary", "98", "Particle Physics 12", i));
				dataSet.add( new ClientDataEntry("2", "West Woodland Elementary", "78", "Intermediate Chess", i));
				dataSet.add( new ClientDataEntry("3", "Haines High School", "96", "Math12", i));
				
				dataSet.get(1).setLongitude(-124.2177);				
				dataSet.get(1).setLatitude(48.2765);
				dataSet.get(2).setLongitude(-124.2177);				
				dataSet.get(2).setLatitude(50.2765);
				
				return dataSet;
			}
			
		});
		
		
		
		
		
		buttonPanel.add(button);
		buttonPanel.setCellVerticalAlignment(button, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);
		
		
		// Here we define and implement the Account Info button. When clicked, this button will open a menu that allows the user to view/edit their account information.
		Button button_1 = new Button("Account Info");
		buttonPanel.add(button_1);
		buttonPanel.setCellHorizontalAlignment(button_1, HasHorizontalAlignment.ALIGN_CENTER);
		buttonPanel.setCellVerticalAlignment(button_1, HasVerticalAlignment.ALIGN_MIDDLE);
		
		table = tabUI.renderDataSetTable( dataSets );
		dataSetPanel.add(table);
		
	}

	
	/*
	 * Plot data set entries on map
	 */
	public MapWidget plotEntries(ArrayList<ClientDataEntry> entries){

		   for ( ClientDataEntry dEntry : entries) {
		    	LatLng coordinate = LatLng.newInstance(dEntry.getLatitude(), dEntry.getLongitude());
		    	int grade = Integer.parseInt(dEntry.getGrade());
		    	String url = "";
		    	if(grade >= 86){ // A
		    		
		    	}
		    	else if(grade >= 73 && grade <= 85){//B
		    		url = "http://www.google.com/mapfiles/markerA.png";
		    	}
		    	else if(67 >= grade && grade <= 72){//C+
		    		url = "http://www.google.com/mapfiles/markerA.png";
		    	}
		    	else if (grade >= 60 && grade <= 66){//C
		    		url = "http://www.google.com/mapfiles/markerA.png";	
		    	}
		    	else if (grade >= 50 && grade <= 59){//C-
		    		url = "http://www.google.com/mapfiles/markerA.png";	
		    	}
		    	else{// F
		    		url = "http://www.google.com/mapfiles/markerA.png";	
		    	}
		    	
		    	Icon icon = Icon.newInstance(url);
	    		icon.setIconSize(Size.newInstance(40, 40));
	    		MarkerOptions ops = MarkerOptions.newInstance(icon);
	    		Marker marker = new Marker(coordinate, ops);
	    		map.addOverlay(marker);
		    	
		    }
		return map;
		
		
	}
	/*
	public ClientDataSet getCumulativeGrade(ArrayList<ClientDataSet> dataSets) throws DataSetNotPresentException{
		ClientDataSet sumSet = new ClientDataSet();
		for(ClientDataSet data : dataSets){
			Long id = data.getDataSetID();
				ArrayList<ClientDataEntry> ent = dSService.getEntries(id, asyncCallback);
				for(ClientDataEntry dEntry: ent){
					int grade = Integer.parseInt(dEntry.getGrade());
					String school = dEntry.getSchool();
					
					//TO DO
					
					
				}
		}

		return sumSet;
		
	}

*/	
	
	public void updateVisualizePanel( CellTable<ClientDataEntry> table ) {
		visualizePanel.clear();
		visualizePanel.add( table );
		table.setVisible( true );
	}
		
		public ArrayList<ClientDataEntry> populateDummyData() {
			
			ArrayList<ClientDataEntry> dataSet = new ArrayList<ClientDataEntry>();
			Long i = new Long(1);
			dataSet.add( new ClientDataEntry("1", "Lochdale Elementary", "98", "Particle Physics 12", i));
			dataSet.add(new ClientDataEntry("2", "West Woodland Elementary", "78", "Intermediate Chess", i));
			dataSet.add( new ClientDataEntry("3", "Haines High School", "96", "Math12", i));
			
			dataSet.get(1).setLongitude((float)-124.2177);				
			dataSet.get(1).setLatitude((float)48.2765);
			dataSet.get(2).setLongitude((float)-124.2177);				
			dataSet.get(2).setLatitude((float)50.2765);
			dataSet.get(3).setLongitude((float)-126.2177);				
			dataSet.get(3).setLatitude((float)48.2765);
			
			return dataSet;
		}
		
//	datasetmanager methods

	public ClientDataSet removeDataSet( ClientDataSet dSet ) throws DataSetNotPresentException {
		for( ClientDataSet iter : dataSets ) {
			if( iter.getDataSetID() == dSet.getDataSetID() ) {
				dataSets.remove(iter);
				dSService.removeDataSet( dSet.getDataSetID(), new AsyncCallback<Void>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(Void ignore) {
					}
				});
				return iter;
			}
		}
		throw new DataSetNotPresentException( "Data set not found.");
	}
	
	public void loadDataSets() {
		dSService.getDataSets( new AsyncCallback<ArrayList<ClientDataSet>>() {
			
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<ClientDataSet> response) {
				dataSets.clear();
				if( !response.isEmpty() ) {
					for( ClientDataSet addMe : response ) {
						dataSets.add(addMe);
					}
				}
					dataSetPanel.clear();
					dataSetPanel.add(tabUI.renderDataSetTable(dataSets));
				}
			});
		
		if( !dataSets.isEmpty() ) {
			Long i = dataSets.get(0).getDataSetID();
			
		dSService.getEntries( i, new AsyncCallback<ArrayList<ClientDataEntry>>() {
			
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<ClientDataEntry> response) {
				if( !response.isEmpty() ) {
						visualizePanel.clear();
						visualizePanel.add(tabUI.renderTable(response));
						
				}
			}});
		}
	}

	public ClientDataSet getDataSet( Long id ) throws DataSetNotPresentException{
	    for( ClientDataSet dSet : dataSets ) {
	    	if ( dSet.getDataSetID() == id )
	    		return dSet;
	    }

	    throw new DataSetNotPresentException("Data Set not found.");
	}
	
	private void handleError(Throwable error) {
	    Window.alert(error.getMessage());
	}

	/**
	 * Returns an ArrayList of all selected ClientDataSets in the table
	 * @author Tristan
	 * @param table The table to iterate through for selected sets
	 * @return All selected sets in the table
	 * */	
	public ArrayList<ClientDataSet> getSelectedDataSets( CellTable<ClientDataSet> cTable ) {
		ArrayList<ClientDataSet> selectedSets = new ArrayList<ClientDataSet>();
		for ( ClientDataSet dSet : cTable.getVisibleItems() ) // For every item in the CellTable...
			if ( cTable.getSelectionModel().isSelected( dSet )) // ...if it is selected...
				selectedSets.add((ClientDataSet) dSet ); // ... add the set to the set of selected sets
		return selectedSets;
	}
}