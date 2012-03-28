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
	private VerticalPanel displayset;
	private ClientDataSetManager dataSetManager;
	private TabularUI tabUI;
	
	private RootPanel root;
	private HorizontalPanel basePanel;
	private HorizontalPanel buttonPanel;
	private VerticalPanel leftSidebarPanel;
	private VerticalPanel dataSetPanel;
	private VerticalPanel visualizePanel;
	private MapWidget map;
	
	public void onModuleLoad() {
		// ryanabooth - should load datasets when constructed
		dataSetManager = new ClientDataSetManager();
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
//				map.setSize("500px", "500px");
				map.addControl(new LargeMapControl());
				map.addOverlay(new Marker(vancouver));
			}
		    });

		root = RootPanel.get();
		basePanel = new HorizontalPanel();
		buttonPanel = new HorizontalPanel();
		leftSidebarPanel = new VerticalPanel();
		dataSetPanel = new VerticalPanel();
		visualizePanel = new VerticalPanel();
		
		root.add(basePanel);
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
							dataSetManager.loadDataSets();
						}					    
					});
				}
			});		
		 
		// Here we define and implement the Remove button. When clicked, this button will remove all selected DataSets from the DataSetManager
		Button button0 = new Button("Remove Selected");
		button0.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Implement the remove DataSet sequence. Call on the TabUI to see what DataSets are selected
			}
		});
		buttonPanel.add(button0);
		buttonPanel.setCellVerticalAlignment(button0, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(button0, HasHorizontalAlignment.ALIGN_CENTER);
		

		// Here we define and implement the Visualize button. When clicked, this button will call upon the MapUI to display all selected DataSets on the Map.
		Button button = new Button("Visualize Selected");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Implement the MapUI visualize sequence. Call on the TabUI to see what DataSets are selected

	
				   visualizePanel.clear();
				   ArrayList<ClientDataEntry> entries = this.populateDummyData(); // create external or internal function?
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
				   visualizePanel.add(map);
				   map.setSize( "1000px", "600px");
			}
			
			public ArrayList<ClientDataEntry> populateDummyData() {
				
				ArrayList<ClientDataEntry> dataSet = new ArrayList<ClientDataEntry>();
				dataSet.add( new ClientDataEntry("1", "Lochdale Elementary", "98", "Particle Physics 12"));
				dataSet.add(new ClientDataEntry("2", "West Woodland Elementary", "78", "Intermediate Chess"));
				dataSet.add( new ClientDataEntry("3", "Haines High School", "96", "Math12"));
				
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
		
//		this.generateDataSets();
		
		dataSetPanel.add(tabUI.renderDataSetTable(dataSetManager.listAll()));
		
	}

	public void updateVisualizePanel( CellTable<ClientDataEntry> table ) {
		visualizePanel.clear();
		visualizePanel.add( table );
		table.setVisible( true );
	}

	
//	private void generateDataSets() {
//		long ID = 0L;
//		for ( int a = 0 ; a < 10 ; a++ ) { // The number of DataSets we will create
//			ClientDataSet dSet = new ClientDataSet( ID , "DataSet " + a , new Date() );
//			for ( int b = 0 ; b < 10 ; b++ ) { // The number of Entries in each DataSet
//				dSet.addEntry( "" + b , "Fake School " + b , "Fake Grade" , "Fake Course");
//			}
//			dataSetManager.addDataSet( dSet );
//			ID ++;
//		}
		
		public ArrayList<ClientDataEntry> populateDummyData() {
			
			ArrayList<ClientDataEntry> dataSet = new ArrayList<ClientDataEntry>();
			dataSet.add( new ClientDataEntry("1", "Lochdale Elementary", "98", "Particle Physics 12"));
			dataSet.add(new ClientDataEntry("2", "West Woodland Elementary", "78", "Intermediate Chess"));
			dataSet.add( new ClientDataEntry("3", "Haines High School", "96", "Math12"));
			
			dataSet.get(1).setLongitude((float)-124.2177);				
			dataSet.get(1).setLatitude((float)48.2765);
			dataSet.get(2).setLongitude((float)-124.2177);				
			dataSet.get(2).setLatitude((float)50.2765);
			dataSet.get(3).setLongitude((float)-126.2177);				
			dataSet.get(3).setLatitude((float)48.2765);
			
			return dataSet;
		}
		
//		Long count = (long) 1.0;
//		int count2 = 1;
//		while(count<15){
//		ClientDataSet ds = new ClientDataSet( count, "DataSet " + count, new Date(), 10 );
//			while(count2 < 10){
//				ds.addEntry("" + count2, "Fake School " + count2, "grade " + count2, "course " + count2);
//				count2++;
//			}
//		dataSetManager.addDataSet(ds);
//		count2 = 1;
//		count++;
//		}

}