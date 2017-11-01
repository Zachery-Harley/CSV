package com.zacheryharley.zava.structure;

import java.util.ArrayList;

/**
 * 
 * @author Zachery
 * @category Structure
 * A table of data
 */
public class Table {
	
	//Instance Variables
	private ArrayList<Row> rowsData = new ArrayList<Row>();
	private int numberOfRows = -1;
	private int numberOfColumns = -1;
	
	/**
	 * Create a table object
	 */
	public Table(){
		
	}
	
	/**
	 * Create a table object with a fixed number of
	 * rows
	 * @param rows - A fixed number of rows in the table
	 */
	public Table(int rows){
		
	}
	
	/**
	 * Create a table object with a fixes number of
	 * rows and columns
	 * @param rows - A fixed number of rows in the table
	 * @param columns - A fixed number of columns in the row
	 */
	public Table(int columns, int rows){
		setTableSize(columns, rows);
	}
	
	///////Methods/////////
	
	/**
	 * Set the size of the table
	 * @param columns - The number of columns in the table [X] -1 disable fixed
	 * @param rows - The number of rows in the table [Y] -1 disable fixed
	 */
	public void setTableSize(int columns, int rows){
		setTableRows(rows);
		setTableColumns(columns);
	}
	
	/**
	 * Set a fixed column size for this table, there must be no
	 * row with columns greater than the desired value
	 * @param columns - The number of columns in each row -1 disable fixed
	 */
	public boolean setTableColumns(int columns){
		//Check that the table is currently in bounds and that the columns is a valid entry
		if(getMaxColumnLength() > columns && columns >= 0){
			//throw an exception as the table is too big
			new TableOverflowException();
			return false;
		}
		else if(columns < 0){
			this.numberOfColumns = -1;
			return true;
		}
		else {
			this.numberOfColumns = columns;
			//Set the new column sizes
			for(Row row : rowsData){
				row.setColumnSize(columns);
			}
			return true;
		}
	}
	
	/**
	 * Set a fixes number of rows for this table, there must be no
	 * more rows currently in the table than the fixed number being 
	 * set.
	 * @param rows - The number of fixed rows in this table. -1 disable fixed
	 */
	public boolean setTableRows(int rows){
		//Check the table is currently in bounds and that the rows is a valid entry
		if(this.rowsData.size() > rows && rows >= 0){
			//Throw an exception as the table is too big
			new TableOverflowException();
			return false;
		}
		else if(rows < 0){
			this.numberOfRows = -1;
			return true;
		}
		else {
			this.numberOfRows = rows;
			//Set the new row size
			for(int i = this.rowsData.size(); i < rows; i++){
				//Add a new row
				addRow();
			}
			return true;
		}
	}
	
	/**
	 * Get the size of the largest row in the table
	 * @return - Size of the largest row
	 */
	public int getMaxRowLength(){
		int max = 0;
		//Loop through all the rows
		for(Row row : rowsData){
			if(row.size() > max){
				max = row.size();
			}
		}
		//Return the max value
		return max;
	}
	
	/**
	 * Get the size of the largest column in the table
	 * @return
	 */
	public int getMaxColumnLength(){
		int max = 0;
		//Loop through all the rows
		for(Row row : rowsData){
			if(row.getMaxColumnLength() > max){
				max = row.getMaxColumnLength();
			}
		}
		//Return the max value
		return max;
	}
	
	/**
	 * Add a new row to the table
	 * @param newRow - The row to be added into the table
	 * @return True if the row was added, false if and error occurred
	 */
	public boolean addRow(Row newRow){
		//can the row be added
		if(this.rowsData.size() < this.numberOfRows || this.numberOfRows == -1){
			//There is space for the row
			//Check if a column limit is to be added
			if(this.numberOfColumns > -1){
				newRow = new Row(numberOfColumns);
			}
			//add the row and return true
			this.rowsData.add(newRow);
			return true;
		}
		else {
			//There is no space for the row in the table
			return false;
		}
	}
	
	/**
	 * Get and entire row of data from the table
	 * @param index - The index of the row wanted
	 * @return Row - the row requested, null if not found
	 */
	public Row getRow(int index){
		if(index >= rowsData.size() || index < 0){
			//Index out of bounds
			new NullPointerException().printStackTrace();
			return null;
		}
		else {
			//In bounds, return the row
			return rowsData.get(index);
		}
	}
	
	/**
	 * Convert the table into an array list of rows
	 * @return ArrayList<Row> - Return the ArrayList of rows
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Row> toArrayList(){
		return (ArrayList<Row>) rowsData.clone();
	}
	
	/**
	 * Add a row to the table
	 * @param newRow - The row you want to add
	 * @param index - The position you want to add the row
	 * @return
	 */
	public boolean addRow(Row newRow, int index){
		if(index >= rowsData.size() || index <= 0){
			new NullPointerException().printStackTrace();
			return false;
		}
		//can the row be added
		if(this.rowsData.size() < this.numberOfRows || this.numberOfRows == -1){
			//There is space for the row
			//Check if a column limit is to be added
			if(this.numberOfColumns > -1){
				newRow = new Row(numberOfColumns);
			}
			//add the row and return true
			this.rowsData.add(index, newRow);
			return true;
		}
		else {
			//There is no space for the row in the table
			return false;
		}
	}
	
	/**
	 * Make the table square by setting all columns to the the same as the current max
	 */
	public void square(){
		int columnCount = getMaxColumnLength();
		
		//Set the table size
		setTableColumns(columnCount);
	}
	
	/**
	 * Add a new row to the table
	 * @return - True if the row was added, False otherwise
	 */
	public boolean addRow(){
		Row newRow = new Row();
		return addRow(newRow);
	}
	
	/**
	 * Add a new row to the table
	 * @return - True if the row was added, False otherwise
	 */
	public boolean addRow(int columnSize){
		//Check the limit is in bounds
		if(columnSize < -1){
			columnSize = -1;
		}
		Row newRow = new Row(columnSize);
		return addRow(newRow);
	}
	
	
	/**
	 * delete a row from the table, if there is a fixed number of rows, a new
	 * row will be appended to the end of the table
	 * @param index - The row to be deleted
	 * @return Will return True if deleted, False if an error occurred
	 */
	public boolean deleteRow(int index){
		//Check the index is within bounds
		if(index >= rowsData.size() || index <= 0){
			//Out of bounds
			new NullPointerException().printStackTrace();
			return false;
		}
		else {
			//Valid index, delete the row
			rowsData.remove(index);
			
			//now check if there is a row limiter in place
			if(numberOfRows > -1){
				//add a new row to the end of the table
				addRow();
			}
			return true;
		}
	}
	
	/**
	 * Format the table, this will keep the table structure
	 */
	public void clear(){
		rowsData.clear();
		setTableRows(this.numberOfRows);
		setTableColumns(this.numberOfColumns);
	}
	
	/**
	 * Set a value in the table
	 * @param row - The row the field is in to set
	 * @param column - The column to set
	 * @param value - The new value
	 * @return
	 */
	public boolean set(int row, int column, String value){
		if(row >= rowsData.size() || row < 0){
			new NullPointerException().printStackTrace();
			return false;
		}
		else {
			//Valid row
			return rowsData.get(row).set(column, value);
		}
	}
	
	
	public String toString(){
		int fieldSize = getMaxColumnLength();
		String output = "";
		for(Row row : rowsData){
			//Add the row toString to the output
			output += row.toString(fieldSize);
			output += "\n";
			for(int i = 0; i < row.toString(fieldSize).length(); i++){
				output += "=";
			}
			output += "\n";
		}
		return output;
	}





}
