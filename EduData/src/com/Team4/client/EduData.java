package com.Team4.client;

import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
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
	private ClientDataSetManager dataSetManager;
	private TabularUI tabMe;

	public void onModuleLoad() {
		dataSetManager = new ClientDataSetManager();
		tabMe = new TabularUI();
		
		//ryanabooth - testing loading dataSets
		//dataSetManager.loadDataSets();
		
		this.generateDataSets();
		RootPanel root = RootPanel.get();

		VerticalPanel sidebar = new VerticalPanel();
		sidebar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(sidebar, 0, 100);
		sidebar.setSize("400px", "400px");
		sidebar.setBorderWidth( 10 );
		sidebar.add( tabMe.renderDataSetTable( dataSetManager.listAll() ) );

		// The panel that will display the TabularUI, the MapUI, and the AccountSettingsUI alternately.
		displayset = new VerticalPanel();
		displayset.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(displayset, 400, 0);
		displayset.setSize("580px", "480px");
		displayset.setBorderWidth( 10 );
		// TODO: add this back in once DataSets fixed
//		
//		try {
//			
//			//displayset.add( tabMe.renderTable( dataSetManager.getDataSet( (long) 1 ) ));
//		} catch (DataSetNotPresentException e) {
//			// TODO: Some kind of intelligent response to a missing DataSet
//			e.printStackTrace();
//		}
		
		
		// TODO: What does this hideous block of code actually do?
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(buttonPanel, 0, 50);
		buttonPanel.setSize("400px", "50px");
		buttonPanel.setBorderWidth( 10 );

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

		HorizontalPanel eduDataTitlePanel = new HorizontalPanel();
		eduDataTitlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(eduDataTitlePanel, 0, 0);
		eduDataTitlePanel.setSize("400px", "50px");

		Label lblEdudata = new Label("EduData");
		eduDataTitlePanel.add(lblEdudata);
		lblEdudata.setHeight("50px");

	}


	private void generateDataSets() {
		Long count = (long) 1.0;
		int count2 = 1;
		while(count<5){
		ClientDataSet ds = new ClientDataSet( count, "DataSet " + count, new Date() );
			while(count2<10){
				ClientDataEntry de = new ClientDataEntry( "" + count2, "Fake School " + count2, "grade " + count2, "course " + count2 );
				ds.addEntry(de);
				count2++;
			}
		dataSetManager.addDataSet(ds);
		count2 = 1;
		count++;
		}
	}

}