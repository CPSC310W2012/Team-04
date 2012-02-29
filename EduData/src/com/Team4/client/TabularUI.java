package com.Team4.client;

import java.util.ArrayList;

import com.Team4.server.DataEntry;
import com.Team4.server.DataSet;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The TabularUI class
 * This class serves to create, format, and display pop-up windows. 
 * @author Tristan Sebens
 * */
public class TabularUI {
	
	/**
	 * The TabularUI constructor
	 * */
	public TabularUI() {}
		
	/**
	 * The publicly accessible method for displaying a data set in tabular form.
	 * The method produces a popup window that displays the data in a table
	 * @PRE: The DataSet dSet is initiated
	 * @dSet The DataSet to be displayed
	 * */
	public void renderTable( DataSet dSet ){
		PopUpWindow newPopUpWindow = new PopUpWindow( dSet ); 

		// Here we prepare the FlexTable for display
		newPopUpWindow.formatFlexTable(); 
		try { // The data set may be empty. If this is the case, we should add some kind of intelligent response
			newPopUpWindow.addElementsToDataTable();
		} catch (EmptyDataSetException e) {
			// If the data set is empty, fill the popup window with an error message instead
			emptyDataSetMessage( newPopUpWindow );
			return;
		} 
		
		// Finally, we format the PopUpWindow and make it visible
		newPopUpWindow.formatWindow();
		newPopUpWindow.display();
	}
	
	/**
	 * Helper method to the TabularUI
	 * Displays an error message if the renderTable method is handed an empty set
	 * @pop The PopupWindow to display the error message in
	 * */
	private void emptyDataSetMessage( PopUpWindow pop ) {
		pop.getPopUpPanel().setText("Sorry, the data set seems to be empty.");
		pop.display();
	}
	
	
	/**
	 * The PopUpWindow class. 
	 * A helper class to the TabularUI
	 * This class acts as an object which the TabularUI can modify and then display. 
	 * It contains a DataSet
	 * */
	private class PopUpWindow {
		private DialogBox popUpPanel = new DialogBox();
		private FlexTable dataEntryTable = new FlexTable();
		private DataSet dataSet;

		
		/**
		 * The PopUpWindow class constructor
		 * @dSet The DataSet that this PopUpWindow will display
		 *  */
		private PopUpWindow( DataSet dSet ) {
			dataSet = dSet;
		}
		
		/**
		 * Basic getters for the variables. All variables are static and specific to the instance of the
		 * PopUpWindow instance, and so cannot be set outside of the constructor. 
		 * */
		public DataSet getDataSet() {
			return dataSet;
		}
		
		public DialogBox getPopUpPanel() {
			return popUpPanel;
		}
		
		public FlexTable getDataEntryTable() {
			return dataEntryTable;
		}
		
		/**
		 * Helper method to the PopUpWindow class
		 * Prepares the popup Panel for display
		 * */
		private void formatWindow() {
			this.popUpPanel.setText( this.dataSet.getName() );
			this.popUpPanel.setTitle( this.dataSet.getName() );
			
			// Here, we create the scroll panel upon which we will build our PopUpPanel
			VerticalPanel vPanel = new VerticalPanel();
			ScrollPanel sPanel = new ScrollPanel( this.dataEntryTable );
			sPanel.setAlwaysShowScrollBars( false );
			
			// Here, we create the close button to the window
			Button closeButton = new Button( "Close" );
			closeButton.addClickHandler( new ClickHandler() {
				public void onClick( ClickEvent event ){
					popUpPanel.hide();
				}
			});
			
			this.popUpPanel.setWidget( vPanel );	
			vPanel.add( sPanel );
			vPanel.add( closeButton );
			sPanel.setHeight( "400px" );
		}
		
		/**
		 * Helper method to the PopUpWindow class
		 * Adds the necessary columns to the FlexTable
		 * @table The FlexTable to be formatted
		 * */
		private void formatFlexTable() {		
			// Here, we set the lables for the columns of data
			this.dataEntryTable.setTitle( dataSet.getName() );
			this.dataEntryTable.setText( 0, 0, "SCHOOL NAME" );
			this.dataEntryTable.setText( 0, 1, "COURSE" );
			this.dataEntryTable.setText( 0, 2, "PERCENT ABOVE C-" );
			
			// Here, we tune the finer points of the data's display 
			this.dataEntryTable.setBorderWidth( 1 );
			this.dataEntryTable.setCellPadding( 4 );
					
		}
		
		/**
		 * Helper method to the PopUpWindow class
		 * Adds all of the elements of the data set to the PopUpWindow
		 * @dset The DataSet containing the elements to be displayed
		 * @popUp The PopUpWindow object that will be displayed
		 * */
		private void addElementsToDataTable() throws EmptyDataSetException {
			int row = 1; // We start at row 1 because row 0 will contain the column labels
			
			for ( DataEntry d : dataSet.listAll() ) {
				// Here we add the actual data values to the FlexTable
				dataEntryTable.setText( row, 0, d.getSchool() );
				dataEntryTable.setText( row, 1, d.getCourse() );
				dataEntryTable.setText( row, 2, Integer.toString(d.getGrade()) );
				row++;
			}
			if ( row <= 1 ) // If the table is empty, we should make a note of this
				throw new EmptyDataSetException();
		}
		
		/**
		 * Helper method to the PopUpWindow class
		 * Displays the elements of the PopUpWindow
		 * */
		private void display() {
			this.popUpPanel.show();
			this.popUpPanel.center();
		}
	}
	
}
