package com.zacheryharley.zava.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.zacheryharley.zava.structure.Field;
import com.zacheryharley.zava.structure.Row;
import com.zacheryharley.zava.structure.Table;

/**
 * The CSV Writer stores data using the comma separated value
 * format using the MS-DOS Format used in Excel
 * @author Zachery
 *
 */
public class CSVWriter {
	
	//Instance variables
	String path = "";
	boolean append = true;
	
	/**
	 * Create a new empty CSV Writer,
	 * the default is to append to any file being written
	 */
	public CSVWriter(){
		
	}
	
	/**
	 * Create a new CSV Writer,
	 * @param path
	 */
	public CSVWriter(String path){
		this.path = path;
	}
	
	/**
	 * Prepare a table into a CSV file string
	 * @param data - The table to convert to CSV
	 * @return ArrayList<String> - A CSV file in string form
	 */
	private ArrayList<String> prepare(Table data){
		ArrayList<String> output = new ArrayList<String>();
		//Loop through each row
		for(Row row : data.toArrayList()){
			//Loop through all the fields
			String temp = "";
			for(Field field : row.toArrayList()){
				temp += prepareField(field.toString());
				temp += ",";
			}
			//End of the row, down a line
			output.add(temp);
		}
		return output;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	/**
	 * Take a single string and convert it into a CSV string
	 * @param value - The value to make CSV ready
	 * @return String - CSV safe value
	 */
	private String prepareField(String value){
		//State Variables
		String output = "";
		boolean encase = false;
		//Check for special quotation
		if(value.contains("\"")){
			output = value.replaceAll("\"", "\"\"");
			encase = true;
		}
		else {
			output = value;
		}
		//Check for special comma
		if(value.contains(",")){
			encase = true;
		}
		//Encase the string
		if(encase == true){
			String temp = "\"";
			temp += output;
			output = temp + "\"";
		}
		return output;
	}
	
	/**
	 * Create a new CSV Writer
	 * @param path - The path of the file to write to / Create
	 * @param append - Whether or not to append to the file
	 */
	public CSVWriter(String path, boolean append){
		this.path = path;
		this.append = append;
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public boolean write(Table data){
		 try {
			 //Convert the table to CSV
			 ArrayList<String> CSVString = prepare(data);
			 //Check the path is set before writing
			 if(path.length() > 0){
				 //Path is set
				 Path file = Paths.get(path);
				 Files.write(file, CSVString);
				 return true;
			 }
			 else {
				 //No path exists
				 return false;
			 }
		 }
		 catch(Exception e) {
			 //An error occurred, print the details
			 e.printStackTrace();
			 return false;
		 }
	}
	
	
}
