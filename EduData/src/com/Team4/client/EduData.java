package com.Team4.client;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.cellview.client.DataGrid;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EduData implements EntryPoint {
	private VerticalPanel displayset;


	public void onModuleLoad() {
		RootPanel root = RootPanel.get();

		VerticalPanel sidebar = new VerticalPanel();
		sidebar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(sidebar, 0, 100);
		sidebar.setSize("400px", "400px");

		//DataGrid<DataSet> dataGrid = new DataGrid<DataSet>();
		//sidebar.add(dataGrid);
		//dataGrid.setSize("250px", "400px");

		displayset = new VerticalPanel();
		displayset.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(displayset, 400, 0);
		displayset.setSize("400px", "500px");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(horizontalPanel, 0, 50);
		horizontalPanel.setSize("400px", "50px");
		
		Button visualButton = new Button("Visualize Data");
		visualButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		horizontalPanel.add(visualButton);
		horizontalPanel.setCellVerticalAlignment(visualButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(visualButton, HasHorizontalAlignment.ALIGN_CENTER);

		Button importButton = new Button("Import");
		importButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopupPanel uploadPanel = new PopupPanel();
				SingleUploader defaultUploader = new SingleUploader();
			    uploadPanel.add(defaultUploader);
				uploadPanel.setPopupPosition(300, 300);
			    uploadPanel.show();
				defaultUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
					public void onFinish(IUploader uploader) {
						// TODO Add code to parse file and add to datasets
					}
				});
			}
		});
		horizontalPanel.add(importButton);
		horizontalPanel.setCellVerticalAlignment(importButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(importButton, HasHorizontalAlignment.ALIGN_CENTER);

		Button accountButton = new Button("Account Settings");
		accountButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		horizontalPanel.add(accountButton);
		horizontalPanel.setCellVerticalAlignment(accountButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(accountButton, HasHorizontalAlignment.ALIGN_CENTER);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		root.add(horizontalPanel_1, 0, 0);
		horizontalPanel_1.setSize("400px", "50px");

		Label lblEdudata = new Label("EduData");
		horizontalPanel_1.add(lblEdudata);
		lblEdudata.setHeight("50px");

	}
}