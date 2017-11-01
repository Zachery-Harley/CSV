package com.zacheryharley.zava.structure;

/**
 * A field of data
 * @author Zachery
 *
 */
public class Field {
	
	//Instance Variables
	private String value = "";
	
	/**
	 * Create an empty field
	 */
	public Field(){
		
	}
	
	/**
	 * Create a field with a value
	 * @param value
	 */
	public Field(String value){
		set(value);
	}
	
	/**
	 * Set the value of the field
	 * @param value
	 * @return
	 */
	public boolean set(String value){
		this.value = value;
		return true;
	}
	
	/**
	 * Get the value of the field
	 * @return
	 */
	public String get(){
		return value;
	}
	
	
	public String toString(){
		return value;
	}
	
}
