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
        buildUi();
      }
    });
  }
   // feed in dataset to display
  private void buildUi(DataSet dataSet) {
    // Open a map centered on Cawker City, KS USA
	  // get data sets school names 
	  ArrayList<DataEntry> entries = dataSet.listAll();
	  int i = 0; // change to iterator
	  while(i<entries.size()){
		  
		  
		i++;  
	  }
	  
    LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);

    final MapWidget map = new MapWidget(cawkerCity, 2);
    map.setSize("100%", "100%");
    // Add some controls for the zoom level
    map.addControl(new LargeMapControl());

    // Add a marker
    map.addOverlay(new Marker(cawkerCity));

    // Add an info window to highlight a point of interest
    map.getInfoWindow().open(map.getCenter(),
        new InfoWindowContent("World's Largest Ball of Sisal Twine"));

    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
    dock.addNorth(map, 500);

    // Add the map to the HTML host page
    RootLayoutPanel.get().add(dock);
  }
}