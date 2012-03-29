package com.Team4.client;

import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
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
	private ScrollPanel dataSetPanel;
	private ScrollPanel visualizePanel;
	private MapWidget map;
	private ArrayList<ClientDataSet> dataSets;
	private ArrayList<ClientDataEntry> entries;
	private ArrayList<MapPoint> mapPoints;
	private final DataSetServiceAsync dSService = GWT.create(DataSetService.class);
	private CellTable<ClientDataSet> table;
	
	
	public void onModuleLoad() {

		dataSets = new ArrayList<ClientDataSet>();
		entries = new ArrayList<ClientDataEntry>();
		mapPoints = new ArrayList<MapPoint>();
		loadMapPoints();
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
				 final LatLng vancouver = LatLng.newInstance(49.150+0.016, -123.110-0.011);
			    map = new MapWidget(vancouver, 11);
			    map.setSize("800px", "600px");
			   
				Icon icon = Icon.newInstance();
				
	    		icon.setIconSize(Size.newInstance(28, 40));
	    		
	    		MarkerOptions ops = MarkerOptions.newInstance(icon);
	    		ops.setClickable(true);

	    		map.addControl(new LargeMapControl());
	    		Marker marker = new Marker(vancouver, ops);
	    		
	    		map.addOverlay(marker);
				visualizePanel.add(map);
			}
		    });

		root = RootPanel.get();
		basePanel = new HorizontalPanel();
		buttonPanel = new HorizontalPanel();
		leftSidebarPanel = new VerticalPanel();
		dataSetPanel = new ScrollPanel();
		visualizePanel = new ScrollPanel();
			
		root.add(basePanel);
		basePanel.add(leftSidebarPanel);
		basePanel.add(visualizePanel);
		basePanel.setCellHorizontalAlignment(visualizePanel, HasHorizontalAlignment.ALIGN_LEFT);
		leftSidebarPanel.add(buttonPanel);
		leftSidebarPanel.add(dataSetPanel);
		
		root.setSize( "1100px" , "600px" );
		basePanel.setSize( "1000px" , "600px" );
		leftSidebarPanel.setSize( "300px" , "600px" );
		visualizePanel.setSize( "800px" , "600px" );
		buttonPanel.setSize( "300px" , "50px" );
		dataSetPanel.setSize( "450px" , "550px" );
		
		basePanel.setBorderWidth( 1 );
		leftSidebarPanel.setBorderWidth( 1 );
		buttonPanel.setBorderWidth( 1 );
		
		Button importButton = new Button("Import");
		buttonPanel.add(importButton);
		buttonPanel.setCellVerticalAlignment(importButton, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(importButton, HasHorizontalAlignment.ALIGN_CENTER);
		 importButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Window.alert("Import school location data with filename locations.txt");
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
							loadMapPoints();
						}					    
					});
				}
			});		
		 
		//	Here we define and implement the Remove button. When clicked, this button will remove all selected DataSets from the DataSetManager
		Button button0 = new Button("Remove Selected");
		button0.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {		
//				ArrayList<ClientDataSet> selected = tabUI.getSelectedDataSets();
//				for ( ClientDataSet dSet : selected ) {
//					try {
//						removeDataSet( dSet );
//						System.out.println( dSet.getName() + " removed.");
//					} catch (DataSetNotPresentException e) {
//						// If the code reaches this point, then we are trying to remove a data set that no longer exists
//						// In other words, we don't care.
//						// TODO: Add some intelligent response to trying to remove a DataSet that doesn't exist
//					}
//				}
//				// At this point all selected DataSets have been removed, so we need to update the cell table
//				table.setRowData( dataSets );
			}
		});
		buttonPanel.add(button0);
		buttonPanel.setCellVerticalAlignment(button0, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(button0, HasHorizontalAlignment.ALIGN_CENTER);

		// Here we define and implement the Visualize button. When clicked, this button will call upon the MapUI to display all selected DataSets on the Map.
		Button button = new Button("Visualize Selected");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {			
				ArrayList<ClientDataSet> selected = tabUI.getSelectedDataSets();
				if( selected.size() > 1 ) {
					Window.alert( "Only one DataSet can be mapped at a time." );
				}
				else {
					ArrayList<ClientDataEntry> renderMe = new ArrayList<ClientDataEntry>();
					for( ClientDataSet dataSet : selected ) {
						for( ClientDataEntry dataEntry : entries ) {
							if( dataEntry.getDataSetID().equals( dataSet.getDataSetID() ) ) {
								renderMe.add( dataEntry );
							}
						}
					}
					visualizePanel.clear();
					visualizePanel.add(renderMap( renderMe ));
				}
			}
		});
		buttonPanel.add(button);
		buttonPanel.setCellVerticalAlignment(button, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button button_1 = new Button("Statistics");
		buttonPanel.add(button_1);
		button_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {	
				ArrayList<ClientDataSet> selected = tabUI.getSelectedDataSets();
				if( selected.size() > 1 ) {
					Window.alert( "One DataSet at a time please." );
				}
				else {
					ArrayList<Integer> grades = new ArrayList<Integer>();
					double[] frequency = new double[101];
					for( ClientDataSet dataSet : selected ) {
						for( ClientDataEntry dataEntry : entries ) {
							if( dataEntry.getDataSetID().equals( dataSet.getDataSetID() ) ) {
								grades.add( Integer.parseInt( dataEntry.getGrade() ) );
								frequency[Integer.parseInt( dataEntry.getGrade() )]++;
							}
						}
					}
					double[] input = new double[grades.size()];
					int i = 0;
					int sum = 0;
					for( Integer grade : grades ) {
						input[i] = grade;
						sum = sum + grade;
						i++;
					}

					/**
					 * Get stats
					 */
					int mid;
					double median = 0;
					int mean = 0;
					//median
					Arrays.sort(input);
					mid = grades.size()/2;
					median = input[mid];
					
					//mean
					mean = sum/grades.size();
				
					
					
//					double maxFreq = new Max().evaluate(frequency);
//					ArrayList<Integer> mode = new ArrayList<Integer>();
//					for( i = 0; i < 101; i++ ) {
//						if( frequency[i] == maxFreq ) {
//							mode.add(i);
//						}
//					}
//					double stdDev = new StandardDeviation().evaluate(input);
					
					
					
					FlexTable statsFlexTable = new FlexTable();
					statsFlexTable.setText(0, 0, "Mean");
					statsFlexTable.setText(1, 0, "Median");
					statsFlexTable.setText(0, 1, "" + mean);
					statsFlexTable.setText(1, 1, "" + median);
					System.out.println( "Flex Table Made" );
					PopupPanel dBox2 = new PopupPanel();
					
					//add here 
					Window.alert( "Mean : " + mean + " Median : " + median );
				}
			}
		});
		buttonPanel.setCellHorizontalAlignment(button_1, HasHorizontalAlignment.ALIGN_CENTER);
		buttonPanel.setCellVerticalAlignment(button_1, HasVerticalAlignment.ALIGN_MIDDLE);
		
		button_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {			
				ArrayList<ClientDataSet> selected = tabUI.getSelectedDataSets();
				if( selected.size() > 1 ) {
					Window.alert( "You may only view statistics for one dataset at a time." );
				}
				else {
					ArrayList<ClientDataEntry> renderMe = new ArrayList<ClientDataEntry>();
					for( ClientDataSet dataSet : selected ) {
						for( ClientDataEntry dataEntry : entries ) {
							if( dataEntry.getDataSetID().equals( dataSet.getDataSetID() ) ) {
								renderMe.add( dataEntry );
							}
						}
					}

					
				}

			}
		});
		
		for ( int a = 0 ; a < 4 ; a++ ) {
			buttonPanel.getWidget( a ).setHeight( "50px" );
			buttonPanel.getWidget( a ).setWidth( "112.5px" );
		}
		
		table = tabUI.renderDataSetTable( dataSets );
		dataSetPanel.add(table);
		
	}
	
	
	/*
	 * Plot data set entries on map
	 */
	public MapWidget plotEntries(ArrayList<ClientDataEntry> entries){
		
			map.clearOverlays();

		   for ( ClientDataEntry dEntry : entries) {
			   final String school = dEntry.getSchool();
			   final String course = dEntry.getCourse();
			   final String displayGrade = dEntry.getGrade();
		    	final LatLng coordinate = LatLng.newInstance(dEntry.getLatitude(), dEntry.getLongitude());
		    	int grade = Integer.parseInt(dEntry.getGrade());
		    	String url = "images/";
		    	if(grade >= 86){ // A
		    		url = url+"a.png";
		    	}
		    	else if(grade >= 73 && grade <= 85){//B
		    		url = url+"b.png";
		    	}
		    	else if (grade >= 60 && grade <= 72){//C
		    		url = url+"c.png";	
		    	}
		    	else if (grade >= 50 && grade <= 59){//C-
		    		url = url+"d.png";	
		    	}
		    	else{// F
		    		url = url+"f.png";	
		    	}
		    	
		    	Icon icon = Icon.newInstance(url);
	    		icon.setIconSize(Size.newInstance(28, 40));
	    		icon.setIconAnchor( Point.newInstance(7, 14) );
	    		MarkerOptions ops = MarkerOptions.newInstance(icon);
	    		ops.setClickable(true);
	    		Marker marker = new Marker(coordinate, ops);
	    		marker.addMarkerClickHandler(new MarkerClickHandler() { 
	    			   public void onClick(MarkerClickEvent event) { 
	    				   map.getInfoWindow().open(coordinate, new InfoWindowContent("School:"+ school + " Course:"+ course + " Grade: " + displayGrade ));
	    			   } 
	    			}); 
	    		//LatLng adjusted = LatLng.newInstance(coordinate.getLatitude()+0.016,coordinate.getLongitude()-0.011);
	    		//marker.setLatLng(adjusted);
	    		map.addOverlay(marker);
		   }
		    
		return map;
		
		
	}
	/*
	 * Renders a map according to the dataSet passed
	 * */

	public MapWidget renderMap(ArrayList<ClientDataEntry> renderMe){
		return plotEntries(renderMe);
	}
	
	public void updateVisualizePanel( CellTable<ClientDataEntry> table ) {
		visualizePanel.clear();
		visualizePanel.add( table );
		table.setVisible( true );
	}

	public ClientDataSet removeDataSet( ClientDataSet dSet ) throws DataSetNotPresentException {
		for( ClientDataSet iter : dataSets ) {
			if( iter.getDataSetID() == dSet.getDataSetID() ) {
				dataSets.remove(iter);
				for( ClientDataEntry dEntry : entries) {
					if( dEntry.getDataSetID().equals( iter.getDataSetID() )) {
						entries.remove( dEntry );
					}
				}
				dSService.removeDataSet( dSet.getDataSetID(), new AsyncCallback<Void>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(Void ignore) {
						System.out.println( "DataSet successfully removed." );
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
		
		dSService.getEntries( new AsyncCallback<ArrayList<ClientDataEntry>>() {
			
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<ClientDataEntry> response) {
				if( !response.isEmpty() ) {
					entries = response;
					if( !mapPoints.isEmpty() ) {
						addLocationData();
					}
				}
			}});
	}
	
	private void addLocationData() {
		for( ClientDataEntry dEntry : entries ) {
			for( MapPoint mp : mapPoints ) {
				if( mp.getSchoolName().equals( dEntry.getSchool() ) ) {
					dEntry.setLatitude( mp.getLatitude() );
					dEntry.setLongitude( mp.getLongitude() );
				}
			}
		}
	}
	
	private void loadMapPoints() {
		dSService.getMapPoints( new AsyncCallback<ArrayList<MapPoint>>() {
			
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<MapPoint> response) {
					mapPoints.clear();
					if( !response.isEmpty() ) {
						for( MapPoint addMe : response ) {
							mapPoints.add(addMe);
						}
					}
				}
			});
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
	 * The TabularUI class
	 * This class serves to create, format, and display pop-up windows. 
	 * @author Tristan Sebens
	 * */
	private class TabularUI {

		private MultiSelectionModel<ClientDataSet> selectionModel = new MultiSelectionModel<ClientDataSet>();

		/**
		 * The TabularUI constructor
		 * */
		public TabularUI() {}

		/**
		 * The publicly accessible method for displaying a Data Set in tabular form.
		 * The method produces a popup window that displays the data in a table
		 * @dSet The DataSet to be displayed
		 * @return The newly formatted and filled table of DataEntries
		 * */
		public CellTable<ClientDataEntry> renderDataEntryTable( ArrayList<ClientDataEntry> entries ) {
			CellTable<ClientDataEntry> table = new CellTable<ClientDataEntry>();
			table.setRowData( 0 , entries );
			return formatClientDataEntryCellTable( table );
		}

		/**
		 * The publicly accessible method for displaying a collection of Data Sets in tabular form.
		 * The method produces a popup window that displays the data in a table
		 * @dSet The DataSet to be displayed
		 * @return The formatted and filled table of DataSets
		 * */
		public CellTable<ClientDataSet> renderDataSetTable( ArrayList<ClientDataSet> dSets ) {
			CellTable<ClientDataSet> table = new CellTable<ClientDataSet>();
			table.setRowData( 0 , dSets );
			return formatDataSetCellTable( table );
		}

		/**
		 * The publicly accessible method for retrieving the currently selected DataSets
		 * @return An ArrayList of all selected ClientDataSets
		 * */
		public ArrayList<ClientDataSet> getSelectedDataSets() {
			ArrayList<ClientDataSet> temp = new ArrayList<ClientDataSet>();
			for ( ClientDataSet dSet : selectionModel.getSelectedSet() ) {
				temp.add( dSet );
			}
			return temp;
		}

		/**
		 * Helper method to the TabularUI class
		 * Adds the necessary columns for displaying the entries of a DataSet to the CellTable
		 * @param table The CellTable to add the columns to
		 * @return The newly formatted CellTable
		 * */
		private CellTable<ClientDataEntry> formatClientDataEntryCellTable( CellTable<ClientDataEntry> table ) {

			// The column that will display the school name
			TextColumn<ClientDataEntry> schoolNameColumn = new TextColumn<ClientDataEntry>() {
				@Override
				public String getValue( ClientDataEntry d ) {
					return d.getSchool();
				}
			};

			// The column to display the course name
			TextColumn<ClientDataEntry> courseNameColumn = new TextColumn<ClientDataEntry>() {
				@Override
				public String getValue( ClientDataEntry d ) {
					return d.getCourse();
				}
			};


			// The column that will display the grade
			TextColumn<ClientDataEntry> courseGradeColumn = new TextColumn<ClientDataEntry>() {
				@Override
				public String getValue( ClientDataEntry d ) {
					return d.getGrade();
				}
			};
			
			// The column that will display the school name
			TextColumn<ClientDataEntry> longitudeLatitudeColumn = new TextColumn<ClientDataEntry>() {
				@Override
				public String getValue( ClientDataEntry d ) {
					return d.getLatitude() + ", " + d.getLongitude();
				}
			};

			table.addColumn( schoolNameColumn , "School Name" );
			table.addColumn( courseNameColumn , "Course Name" );
			table.addColumn( courseGradeColumn , "Grade" );
			table.addColumn( longitudeLatitudeColumn, "Latitude, Longitude");
			
//		    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
//		    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
//		    pager.setDisplay(table);
		    
		    table.setPageSize( 500 );
		    
			return table;
		}

		/**
		 * Helper method to the TabularUI class
		 * Adds the necessary columns to the CellTable to display a set of DataSets
		 * @param table The CellTable to add the columns to
		 * @return The newly formatted CellTable
		 * */
		private CellTable formatDataSetCellTable( CellTable<ClientDataSet> table ) {
			
			// The checkbox column that will be used to remove and visualize DataSets
			Cell<Boolean> cbCell = new CheckboxCell();
			
			Column<ClientDataSet, Boolean> selectColumn = new Column<ClientDataSet, Boolean>(cbCell) {
				@Override
				public Boolean getValue(ClientDataSet object) {
					return false;
				}
			};
			
			// The column that will display the DataSet name
			TextColumn<ClientDataSet> dataSetNameColumn = new TextColumn<ClientDataSet>() {
				@Override
				public String getValue( ClientDataSet d ) {
					return d.getName();
				}
			};

			// The column to display the date added
			TextColumn<ClientDataSet> dateAddedColumn = new TextColumn<ClientDataSet>() {
				@Override
				public String getValue( ClientDataSet d ) {
					return ( d.getDateAdded().toString() );
				}
			};

			// The column that will hold the "Display" buttons. Whenever one is clicked, the corresponding DataSet will be displayed in tabular form
			ButtonCell buttonCell = new ButtonCell();
			Column<ClientDataSet, String> tabUI = new Column<ClientDataSet, String>(buttonCell) {

				@Override
				public String getValue(ClientDataSet object) {
					return "Display";
				}
			};
			// Here we add our displayListener to listen for clicks on the Display buttons
			tabUI.setFieldUpdater( new DisplayListener() );

			table.addColumn( selectColumn, 	"" ); // The check-box column
			table.addColumn( dataSetNameColumn, "Data Set Name" );
			table.addColumn( dateAddedColumn, "Date Added" );
			table.addColumn( tabUI, "Tabular View" );

			table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<ClientDataSet> createCheckboxManager());
			table.getSelectionModel().addSelectionChangeHandler( new SelectionChangeEvent.Handler() {
				
				public void onSelectionChange(SelectionChangeEvent event) {
					// Method that fires anytime a change is made the to the selection in the DataSetPanel
				}
			});
			
			return table;
		}


		/**
		 * Helper class to the TabularUI
		 * This class listens for clicks made on the "Display" buttons next to each DataSet in the DataSet Table.
		 * When one is clicked, the update function is called
		 * */
		private class DisplayListener implements FieldUpdater {

			/**
			 * This function gets called whenever the "Display" buttons next to each DataSet in the DataSet Table is called
			 * @author tsebens
			 * @index The current index of the DataSet
			 * @object The DataSet that has been clicked. Needs to be cast as a ClientDataSet
			 * */
			public void update(int index, Object object, Object value) {
				visualizePanel.clear();
				ClientDataSet cDSet = (ClientDataSet) object;
				ArrayList<ClientDataEntry> renderMe = new ArrayList<ClientDataEntry>();
				for( ClientDataEntry cdEntry : entries ) {
					if( cdEntry.getDataSetID().equals( cDSet.getDataSetID() ) ){
						renderMe.add( cdEntry );
					}
				}
//				System.out.println(renderMe.size());
				CellTable<ClientDataEntry> w = renderDataEntryTable( renderMe );
				w.setWidth( "800px" );
//				w.setHeight( "600px" );

				ScrollPanel y = new ScrollPanel();
				y.setAlwaysShowScrollBars(true);
				y.add(w);
				visualizePanel.add( y );
			}
		}
	}
}