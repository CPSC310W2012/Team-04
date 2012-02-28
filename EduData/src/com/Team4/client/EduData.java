package com.Team4.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EduData implements EntryPoint {

	//private LoginInfo loginInfo = null;
	//private VerticalPanel loginPanel = new VerticalPanel();
	//private Label loginLabel = new Label(
	//			"Please sign in to your Google Account to access the EduData application.");
	//private Anchor signInLink = new Anchor("Sign In");
	
	// Here we define the system classes
	// DataSetManager dataSetMgr = new DataSetManager();
	

	public void onModuleLoad() {
		RootPanel root = RootPanel.get();
		root.setSize("100%", "100%");
		
		HorizontalPanel basePanel = new HorizontalPanel();
		root.add(basePanel, 0, 0);
		basePanel.setSize("100%", "100%");
		
		VerticalPanel leftPanel = new VerticalPanel();
		basePanel.add(leftPanel);
		leftPanel.setSize("30%", "100%");
		
		Label lblWelcomeToEdudata_1 = new Label("Welcome to EduData");
		lblWelcomeToEdudata_1.setSize("100%", "15%");
		leftPanel.add(lblWelcomeToEdudata_1);
		leftPanel.setCellHorizontalAlignment(lblWelcomeToEdudata_1, HasHorizontalAlignment.ALIGN_CENTER);
		
		Grid buttonGrid = new Grid(1, 3);
		leftPanel.add(buttonGrid);
		buttonGrid.setSize("100%", "25%");
		
		Button visualizeButton = new Button("VisualizeData");
		visualizeButton.setText("Visualize Data");
		buttonGrid.setWidget(0, 0, visualizeButton);
		buttonGrid.getCellFormatter().setWidth(0, 0, "33%");
		
		Button importButton = new Button("Import");
		importButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Add import function
			}
		});
		buttonGrid.setWidget(0, 1, importButton);
		importButton.setHeight("100%");
		buttonGrid.getCellFormatter().setHeight(0, 1, "100%");
		buttonGrid.getCellFormatter().setWidth(0, 1, "33%");
		
		Button accountButton = new Button("Account Settings");
		buttonGrid.setWidget(0, 2, accountButton);
		buttonGrid.getCellFormatter().setWidth(0, 2, "33%");
		accountButton.setWidth("100%");
		buttonGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonGrid.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		
		FlexTable dataSetTable = new FlexTable();
		leftPanel.add(dataSetTable);
		dataSetTable.setSize("100%", "100%");
		
		VerticalPanel displayPanel = new VerticalPanel();
		basePanel.add(displayPanel);
		displayPanel.setSize("100%", "100%");
		// Check login status using login service.
//		LoginServiceAsync loginService = GWT.create(LoginService.class);
//		loginService.login(GWT.getHostPageBaseURL(),
//				new AsyncCallback<LoginInfo>() {
//					public void onFailure(Throwable error) {
//					}
//
//					public void onSuccess(LoginInfo result) {
//						loginInfo = result;
//						if (loginInfo.isLoggedIn()) {
//							loadEduData();
//						} else {
//							loadLogin();
//						}
//					}
//				});
	}
}
