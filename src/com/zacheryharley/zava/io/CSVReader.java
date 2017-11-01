package com.zacheryharley.zava.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.zacheryharley.zava.structure.Row;
import com.zacheryharley.zava.structure.Table;

/**
 * The CSV Reader reader data from a CSV file, this file can be either
 * one created from a CSVWriter or Excel when exported as a CSV
 * @author Zachery
 *
 */
public class CSVReader {
	
	//Instace variables
	private String path = "";
	
	/**
	 * Create a new CSV reader file
	 */
	public CSVReader(){
		
	}
	
	/**
	 * Create a new CSV reader file
	 * @param path - The path of the CSV file to be loaded
	 */
	public CSVReader(String path){
		this.path = path;
	}
	
	
	/**
	 * Load the data from the path given
	 * @param path - The path the CSV file to be loaded
	 * @return - An array list of the lines inside the file
	 */
	private ArrayList<String> load(String path){
		//Use a try catch to prevent a program crash
		try {
			ArrayList<String> output = new ArrayList<>();
			//Cast the file to an array list
			output = (ArrayList<String>) Files.readAllLines(Paths.get(path));
			return output;
		}
		catch (IOException e){
			//An error occurred
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Take a row from a CSV file and convert it into a Row object
	 * @param line - The CSV line
	 * @return Row - A row of feilds taken from the CSV
	 */
	private Row parseRow(String line){
		//Loop though the row a letter at a time
		boolean encasing = false;
		boolean readingSpecial = false;
		String buffer = "";
		Row rowData = new Row();
		
		//Begin the loop
		for(char letter : line.toCharArray()){
			
			//Check for an encasing
			if(!encasing && letter == '"'){
				encasing = true;
				continue;
			}
			
			//Check for special
			if(encasing && !readingSpecial && letter == '"'){
				readingSpecial = true;
				continue;
			}
			
			//Read a special
			if(encasing && readingSpecial && letter != ','){
				buffer += letter;
				readingSpecial = false;
				continue;
			}
			
			//End of the field
			if(encasing && readingSpecial && letter == ',' || !encasing && letter == ','){
				rowData.add(buffer);
				buffer = "";
				encasing = false;
				readingSpecial = false;
				continue;
			}
			
			//Add to the buffer
			buffer += letter;
		}
		//check the buffer for a last space
		if(buffer.length() > 0){
			rowData.add(buffer);
		}
		//Return the row
		return rowData;
	}
	
	/**
	 * Convert an arrayList of CSV lines and convert them into a Table
	 * @param data - The arrayList of the CSV file
	 * @return Table - A table object containing all the data from the arrayList
	 */
	private Table parseData(ArrayList<String> data){
		//Parse the data
		Table output = new Table();
		for(String row : data){
			//Parse the row and get a complete row
			output.addRow(parseRow(row));
		}
		//return the output;
		return output;
	}
	
	/**
	 * Read the file and return the data in a table
	 * @param path - The path of the CSV file to be read
	 * @return Table - A table containing the data form the CSV file
	 */
	public Table read(String path){
		//Read from the path set
		if(path.length() > 0){
			//the path is there, try and load the data
			return parseData(load(path));
		}
		else {
			//An error occurred, print the error
			NullPointerException e = new NullPointerException();
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * Read the file and return the data in a table
	 * @return Table - A table containing the data form the CSV file
	 */
	public Table read(){
		return read(this.path);
	}
	
	
	
}
