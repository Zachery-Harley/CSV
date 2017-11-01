package com.zacheryharley.zava.structure;

import java.util.ArrayList;

/**
 * 
 * @author Zachery
 * A row made up of fields
 */
public class Row {
	//Instance Variables
	private ArrayList<Field> data = new ArrayList<Field>();
	private int numberOfFields = -1;
	
	/**
	 * Create a new Row object
	 */
	public Row(){
		
	}
	
	/**
	 * Create a new row object with a value
	 * @param columns
	 */
	public Row(int columns){
		setColumnSize(columns);
	}
	
	
	/**
	 * Set a fixed number of fields for this row
	 * @param columns - The number of columns to fix this row at -1 disable fixed
	 */
	public void setColumnSize(int columns){
		if(this.data.size() > columns){
			//Out of bounds, therefore throw and exception
			new TableOverflowException();
		}
		else if (columns < 0){
			this.numberOfFields = -1;
		}
		else {
			this.numberOfFields = columns;
			//Fill in the empty spaces
			for(int i = data.size(); i < columns; i++){
				Field newFeild = new Field();
				data.add(newFeild);
			}
		}
	}
	
	/**
	 * Add a new blank field to the row
	 * @return - True if the field was added, False otherwise
	 */
	public boolean add(){
		return add("");
	}
	
	/**
	 * Add a new field to the row
	 * @param value - The value of the new field
	 * @return - True if the field was added, False otherwise
	 */
	public boolean add(String value){
		if(numberOfFields  == -1){
			Field newField = new Field(value);
			data.add(newField);
			return true;
		}
		else {
			if(size() < numberOfFields){
				Field newField = new Field(value);
				data.add(newField);
				return true;
			}
			else {
				new TableOverflowException();
				return false;
			}
		}
	}
	
	/**
	 * Return the number of fields in this row
	 * @return Int the number of fields
	 */
	public int size(){
		return this.data.size();
	}
	
	/**
	 * Set a value inside the row
	 * @param column - The column of the value to set
	 * @param value - The value to set it to
	 * @return True if the value was set, False if an error occurred
	 */
	public boolean set(int column,String value){
		if(column >= size() || column < 0){
			return false;
		}
		else {
			//Valid column
			return data.get(column).set(value);
		}
	}
	
	/**
	 * Get data from the row
	 * @param index - the position in the row the data is stored
	 * @return The data from the index given
	 */
	public String get(int index){
		if(index >= size() || index < 0){
			//An error occurred
			new TableOverflowException();
			return null;
		}
		else {
			//Return the data to the user
			return data.get(index).get();
		}
	}
	
	/**
	 * Return the values in the row using an ascii table
	 */
	public String toString(){
		String output = "||";
		//Build the output
		for(Field field : data){
			output += field.toString();
			output += "||";
		}
		return output;
	}
	
	/**
	 * Return the values in the row using an ascii table of fixed length
	 * @param length - The length each field must be
	 */
	public String toString(int length){
		String output = "||";
		//Build the output keeping each cell to the given length
		for(Field field : data){
			//Check if the field is to big or to small
			if(field.toString().length() > length){
				int newLength = length - 3;
				String holder = field.toString();
				output += holder.substring(0, newLength);
				output += "...||";
			}
			else {
				int fillerNeeded = length - field.toString().length();
				output += field.toString();
				for(int i = 0; i < fillerNeeded; i++){
					output += " ";
				}
				output += "||";
			}
		}
		return output;
	}
	
	/**
	 * Take all the fields and output them as an array list
	 * @return ArrayList<String> - ArrayList of the fields
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Field> toArrayList(){
		return (ArrayList<Field>) data.clone();
	}
	
	/**
	 * Get the size of the largest column in the row
	 * @return
	 */
	public int getMaxColumnLength(){
		int max = 0;
		//Loop through all the fields
		for(Field field : data){
			if(field.toString().length() > max){
				max = field.toString().length();
			}
		}
		return max;
	}
	
	
	
	
	
	
	
	
}
