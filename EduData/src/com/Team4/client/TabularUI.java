package com.Team4.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * The TabularUI class
 * This class serves to create, format, and display pop-up windows. 
 * @author Tristan Sebens
 * */
public class TabularUI {
	
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
	public CellTable<ClientDataEntry> renderTable( ClientDataSet dSet ) {
		CellTable<ClientDataEntry> table = new CellTable<ClientDataEntry>();
		ArrayList<ClientDataEntry> entries = this.getEntries( dSet );
		
		table.setRowData( 0 , entries );
		return formatClientDataEntryCellTable( table );
	}
	
	/**
	 * This method converts the array of Strings in ClientDataSet to ClientDataEntry's,
	 * so the CellTable can handle them
	 * @author ryanabooth
	 * @param dSet
	 * @return list of DataEntries
	 */
	private ArrayList<ClientDataEntry> getEntries(ClientDataSet dSet) {
		ArrayList<ClientDataEntry> returnList = new ArrayList<ClientDataEntry>();
		String[][] entries = dSet.listAll();
		for (int i = 0; i < entries.length; i++){
			String ID = entries[i][0];
			String schoolName = entries[i][1];
			String grade = entries[i][2];
			String course = entries[i][3];
			
			ClientDataEntry entry = new ClientDataEntry(ID, schoolName, grade, course);
			returnList.add(entry);
		}
		return returnList;
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
		return (ArrayList<ClientDataSet>) selectionModel.getSelectedSet();
		// TODO: Add test cases to ensure that this cast works correctly. As far as I can tell, ArrayList and Set have the same methods, so there should be no trouble here 
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
		
		// The checkbox column that will be used to remove and visualize DataSets
		Cell<Boolean> cbCell = new CheckboxCell();
		Column<ClientDataSet, Boolean> selectColumn = new Column<ClientDataSet, Boolean>(cbCell) {
			@Override
			public Boolean getValue(ClientDataSet object) {
				return null;
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
		tabUI.setFieldUpdater( new DisplayListener() ); // TODO: Should be a variable?
		
		table.addColumn( selectColumn, 	"" ); // The check-box column
		table.addColumn( dataSetNameColumn, "Data Set Name" );
		table.addColumn( dateAddedColumn, "Date Added" );
		table.addColumn( tabUI, "Tabular View" );
		
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
		 * @index The current index of the DataSet
		 * @object The DataSet that has been clicked. Needs to be cast as a ClientDataSet
		 * */
		public void update(int index, Object object, Object value) {
			/**
			 * TODO:
			 * Implement the sequence of commands to display a DataSet in Tabular format
			 * at this point, object represents the DataSet to be displayed
			 * */
			
		}
		
	}
	
	
}





