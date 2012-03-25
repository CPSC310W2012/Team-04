package com.Team4.client;

import java.util.ArrayList;

import com.Team4.server.DataEntry;
import com.Team4.server.DataSet;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/*
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleMaps implements EntryPoint {

  protected DataSet dataSet;
// GWT module entry point method.
  public void onModuleLoad() {
   /*
    * Asynchronously loads the Maps API.
    *
    * The first parameter should be a valid Maps API Key to deploy this
    * application on a public server, but a blank key will work for an
    * application served from localhost.
   */
   Maps.loadMapsApi("AIzaSyAvCH2X_Wm1SiuTL4xoYanROAjIFwSijig", "2", false, new Runnable() {
      public void run() {
        buildUi(dataSet);
      }
    });
  }
   // feed in dataset to display
  private void buildUi(DataSet dataSet) {
    // Open a map plotting all the schools and their marks
	  
	  // center at vancouver long/lat - add to MapWidget params
	  final MapWidget map = new MapWidget();
	  map.setSize("100%", "100%");
	// Add some controls for the zoom level
	  map.addControl(new LargeMapControl());

	  //Get all entries of data set
	  ArrayList<DataEntry> entries = dataSet.listAll();
	  int i = 0; // change to iterator
	  while(i<entries.size()){
		  //iterate through each data entry, create markers, and plot
		  // TO-DO create school entry - get long/lat 
		  LatLng school = LatLng.newInstance(39.509, -98.434);

		    // Add a marker
		    map.addOverlay(new Marker(school));

		    // Add an info window to highlight a point of interest
		    // Read API to find color change wrt grade point average
		    map.getInfoWindow().open(map.getCenter(),
		        new InfoWindowContent("School entry grade stat"));

		i++;  
	  }
	  
    

    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
    dock.addNorth(map, 500);

    // Add the map to the HTML host page
    RootLayoutPanel.get().add(dock);
  }
}