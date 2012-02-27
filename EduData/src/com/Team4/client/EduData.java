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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EduData implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the EduData application.");
	private Anchor signInLink = new Anchor("Sign In");
	
	// Here we define the system classes
	DataSetManager dataSetMgr = new DataSetManager();
	

	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							loadEduData();
						} else {
							loadLogin();
						}
					}
				});
	}


	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);	
		
		/** TODO: I think the problem is here. The function is trying to access the 
		 * root panel associated with the element 'ui' but I don't see any initiation 
		 * of the 'ui' element in the HTML */
		RootPanel.get().add(loginPanel);
		//RootPanel.get("ui").add(loginPanel); 
	
	}

	private void loadEduData() {
		RootPanel rootPanel = RootPanel.get("nameFieldContainer");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		
		RootPanel.get().add(rootPanel);
		//RootPanel.get("ui").add(rootPanel);

		Grid grid = new Grid(10, 1);
		rootPanel.add(grid, 0, 0);
		grid.setSize("400px", "500px");

		Label lblEdudata = new Label("EduData\r\n");
		lblEdudata.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		grid.setWidget(0, 0, lblEdudata);
		grid.getCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		Label lblStudentPerformanceAnalytics = new Label(
				"Student Performance Analytics");
		grid.setWidget(1, 0, lblStudentPerformanceAnalytics);

		Label welcomeBackLabel = new Label("Welcome back, ##USERNAME##");
		grid.setWidget(2, 0, welcomeBackLabel);

		Label mdDescrip = new Label("Browse, upload, and manage data");
		grid.setWidget(4, 0, mdDescrip);

		Button mdButton = new Button("Manage Datasets");
		grid.setWidget(5, 0, mdButton);

		Label vdDescrip = new Label("Graph, map, and analyze datasets");
		grid.setWidget(6, 0, vdDescrip);

		Button vdButton = new Button("Visualize Data");
		grid.setWidget(7, 0, vdButton);


		Label yaDescrip = new Label("View and edit account information");
		grid.setWidget(8, 0, yaDescrip);

		Button yaButton = new Button("Your Account");
		grid.setWidget(9, 0, yaButton);
		
		// Here we set the alignment of the page
		setAlignment( grid );
		
	}
	
	/**
	 * Helper method to the EduData class. 
	 * Takes in a FlexTable as a parameter and adds all DataSets currently 
	 * held by the DataSetManager
	 * @param table The FlexTable to be updated 
	 * @return The newly updated FlexTable
	 * */
	private FlexTable updateDataSetDisplay( FlexTable table ) {
		formatFlexTable( table );
		ArrayList<DataSet> dataSets = this.dataSetMgr.listAll();
		
		int a = 1;
		for ( DataSet dSet : dataSets ) {
			table.setText( a , 0 , dSet.getName() );
			a++;
		}
		
		return table;
	}
	
	// Just a method to keep the updateDataSetDisplay method clean
	private void formatFlexTable( FlexTable table ) {		
		// Here, we set the lables for the columns of data
		table.setTitle( "Imported Data Sets" );
		table.setText( 0, 0, "Data Set Name" );
		
		//TODO: The table's appearance should be much nicer
		// Here, we tune the finer points of the data's display 
		table.setBorderWidth( 1 );
		table.setCellPadding( 4 );
	}
	
	/**
	 * Helper method to the EduData class. 
	 * */
	private void setAlignment(Grid grid) {		
		grid.getCellFormatter().setHorizontalAlignment(5, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(7, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(9, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(6, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setVerticalAlignment(9, 0,
				HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setHorizontalAlignment(8, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_BOTTOM);
		grid.getCellFormatter().setVerticalAlignment(3, 0,
				HasVerticalAlignment.ALIGN_BOTTOM);
		grid.getCellFormatter().setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setVerticalAlignment(1, 0,
				HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setVerticalAlignment(4, 0,
				HasVerticalAlignment.ALIGN_BOTTOM);
		grid.getCellFormatter().setVerticalAlignment(5, 0,
				HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setVerticalAlignment(6, 0,
				HasVerticalAlignment.ALIGN_BOTTOM);
		grid.getCellFormatter().setVerticalAlignment(7, 0,
				HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setVerticalAlignment(8, 0,
				HasVerticalAlignment.ALIGN_BOTTOM);
	}
	

}
