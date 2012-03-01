package com.Team4.client;

import java.util.ArrayList;

import com.Team4.server.DataEntry;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
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
	 * The publicly accessible method for displaying a Data Set in tabular form.
	 * The method produces a popup window that displays the data in a table
	 * @dSet The DataSet to be displayed
	 * @return The newly formatted and filled table of DataEntries
	 * */
	public CellTable<ClientDataEntry> renderTable( ClientDataSet dSet ) {
		CellTable<ClientDataEntry> table = new CellTable<ClientDataEntry>();
		table.setRowData( 0 , dSet.listAll() );
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
		
		table.addColumn( schoolNameColumn , "School Name" );
		table.addColumn( courseNameColumn , "Course Name" );
		table.addColumn( courseGradeColumn , "Grade" );
		
		return table;
	}
	
	/**
	 * Helper method to the TabularUI class
	 * Adds the necessary columns to the CellTable to display a set of DataSets
	 * @param table The CellTable to add the columns to
	 * @return The newly formatted CellTable
	 * */
	private CellTable formatDataSetCellTable( CellTable<ClientDataSet> table ) {
		
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
				return d.getDateAdded().toString();
			}
		};
		
		// A button column for removing DataSets
		ButtonCell bCell = new ButtonCell();  
		// TODO: Implement the remove function
		// { // ignore this for now. I'm working on implementing the event handling to remove the data sets. probably won't be finished by the end of the 1st sprint
//			public void onBrowserEvent( Context c, DataSet d, String s, NativeEvent ne, ValueUpdater ve ) {
//				break;
//			}
//		};
		Column<ClientDataSet, String> buttonColumn = new Column<ClientDataSet, String>(bCell) {
			@Override
			public String getValue(ClientDataSet dSet) {
				return "X";
			}
		};
		
		// A button column for removing DataSets
				ButtonCell bCell2 = new ButtonCell();  
				Column<ClientDataSet, String> buttonColumn2 = new Column<ClientDataSet, String>(bCell2) {
					@Override
					public String getValue(ClientDataSet dSet) {
						return "Display";
					}
				};
		
		table.addColumn( dataSetNameColumn , 	"Data Set Name" );
		table.addColumn( dateAddedColumn , 		"Date Added" );
		table.addColumn( buttonColumn, 			"Remove" );
		table.addColumn( buttonColumn2 ,		"Tabular View" );
		
		return table;
	}
	
}
