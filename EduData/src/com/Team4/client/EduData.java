package com.Team4.client;

import com.Team4.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EduData implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = (GreetingServiceAsync) GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		/*
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();
		*/
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("nameFieldContainer");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);

		Grid grid = new Grid(10, 1);
		rootPanel.add(grid, 0, 0);
		grid.setSize("400px", "500px");

		Label lblEdudata = new Label("EduData\r\n");
		lblEdudata.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		grid.setWidget(0, 0, lblEdudata);
		grid.getCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lblStudentPerformanceAnalytics = new Label("Student Performance Analytics");
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
		grid.getCellFormatter().setHorizontalAlignment(5, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(7, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(9, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		Label yaDescrip = new Label("View and edit account information");
		grid.setWidget(8, 0, yaDescrip);

		Button yaButton = new Button("Your Account");
		grid.setWidget(9, 0, yaButton);
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
		grid.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		grid.getCellFormatter().setVerticalAlignment(5, 0, HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setVerticalAlignment(6, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		grid.getCellFormatter().setVerticalAlignment(7, 0, HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setVerticalAlignment(8, 0, HasVerticalAlignment.ALIGN_BOTTOM);

		/*
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {

			// Fired when the user clicks on the sendButton.
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			// Fired when the user types in the nameField.
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}


			// Send the name from the nameField to the server and wait for a response.

			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
		*/
	}
	
	
}
	
