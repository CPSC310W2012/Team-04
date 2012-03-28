package com.Team4.client;
// March 22 2012
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
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
	private TabularUI tabUI;
	
	private RootPanel root;
	private HorizontalPanel basePanel;
	private HorizontalPanel buttonPanel;
	private VerticalPanel leftSidebarPanel;
	private VerticalPanel dataSetPanel;
	private VerticalPanel visualizePanel;
	
	private ArrayList<ClientDataSet> dataSets;
	private final DataSetServiceAsync dSService = GWT.create(DataSetService.class);
	private CellTable<ClientDataSet> table;

	public void onModuleLoad() {
		dataSets = new ArrayList<ClientDataSet>();
		loadDataSets();
		
		tabUI = new TabularUI();
		
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
							loadDataSets();
						}					    
					});
				}
			});		
		 
		// Here we define and implement the Remove button. When clicked, this button will remove all selected DataSets from the DataSetManager
		Button button0 = new Button("Remove Selected");
		button0.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				System.out.println( getSelectedDataSets( table ).size() + " Data Sets are selected. Bitch.");
				for ( ClientDataSet dSet : getSelectedDataSets( table ) ) {
					try {
						
						removeDataSet( dSet );
					} catch (DataSetNotPresentException e) {
						// If the code reaches this point, then we are trying to remove a data set that no longer exists
						// In other words, we don't care.
						// TODO: Add some intelligent response to trying to remove a DataSet that doesn't exist
					}
				}
				// At this point all selected DataSets have been removed, so we need to update the cell table
				table.setRowData( listAll() );
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
		
		table = tabUI.renderDataSetTable( listAll() );
		dataSetPanel.add(table);
		
	}

	public void updateVisualizePanel( CellTable<ClientDataEntry> table ) {
		visualizePanel.clear();
		visualizePanel.add( table );
		table.setVisible( true );
	}

//	datasetmanager methods
	
	public void addDataSet( ClientDataSet dSet ) {
		dataSets.add(dSet);
	}

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
		dSService.getDataSetIDs( new AsyncCallback<ArrayList<Long>>() {
			String returnName;
			Date returnDate;
			Long currentID;
			
			public void onFailure(Throwable error) {
		        handleError(error);
			}

			public void onSuccess(ArrayList<Long> dataSetIDs) {
				if( !dataSetIDs.isEmpty() ) {
					dataSets.clear();
					for( Long id : dataSetIDs ) {
						currentID = id;
						this.retrieveDataSetName( id );
					}
				}
			}

			private void retrieveDataSetName(Long id) {
				dSService.getDataSetName( id, new AsyncCallback<String>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(String name) {
						returnName = name;
						retrieveDataSetDateAdded( currentID );
					}
				});
			}

			private void retrieveDataSetDateAdded(Long id) {
				dSService.getDateAdded( id, new AsyncCallback<Date>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(Date date) {
						returnDate = date;
						retrieveDataSetEntries( currentID );
					}
				});
			}

			private void retrieveDataSetEntries(Long id) {
				
				dSService.getEntries( id, new AsyncCallback<String[][]>() {
					public void onFailure(Throwable error) {
				        handleError(error);
					}

					public void onSuccess(String[][] entries) {
						ClientDataSet addMe = new ClientDataSet(currentID, returnName, returnDate);
						
						for( int i = 0; i < entries.length; i++) {
							ClientDataEntry dataEntry = new ClientDataEntry( entries[i][0], entries[i][1], entries[i][2], entries[i][3]);
							addMe.addEntry(dataEntry);
						}

						dataSets.add(addMe);

						dataSetPanel.clear();
						dataSetPanel.add(tabUI.renderDataSetTable(listAll()));
					}
				
					
				});
			}
			});
	}

	public ArrayList<ClientDataSet> listAll() {
		return dataSets;
	}

	public ClientDataSet getDataSet( Long id ) throws DataSetNotPresentException{
	    for( ClientDataSet dSet : dataSets ) {
	    	if ( dSet.getDataSetID() == id )
	    		return dSet;
	    }

	    throw new DataSetNotPresentException("Data Set not found.");
	}
	
	public void handleError(Throwable error) {
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