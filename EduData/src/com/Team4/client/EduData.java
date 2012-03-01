package com.Team4.client;

import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;

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
	private ClientDataSetManager dSMngr;


	public void onModuleLoad() {
		dSMngr = new ClientDataSetManager();
		dSMngr.loadDataSets();
		RootPanel root = RootPanel.get();

		VerticalPanel sidebar = new VerticalPanel();
		sidebar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(sidebar, 0, 100);
		sidebar.setSize("400px", "400px");
		sidebar.setBorderWidth( 10 );

		// The panel that will display the TabularUI, the MapUI, and the AccountSettingsUI alternately.
		displayset = new VerticalPanel();
		displayset.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(displayset, 400, 0);
		displayset.setSize("580px", "480px");
		displayset.setBorderWidth( 10 );
		
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

		Button button = new Button("Visualize");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TabularUI tab = new TabularUI();
			}
		});
		buttonPanel.add(button);
		buttonPanel.setCellVerticalAlignment(button, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonPanel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);

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

}