package com.zacheryharley.zava.structure;

import java.io.File;
import java.io.IOException;

import com.zacheryharley.zava.io.CSVReader;
import com.zacheryharley.zava.io.CSVWriter;

public class Testing {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Table testTable = new Table(10,5);
		testTable.set(2, 2, "Hello World");
		testTable.set(0, 0, "\"uhfiuhwiuhweifhiuwefh%$^$%^&&&£$%$£$£££");
		testTable.set(1, 2, "\" apples are \"awsome\",,");
		CSVWriter writer = new CSVWriter();
		String path = new File(".").getCanonicalPath();
		path += "/test.csv";
		writer.setPath(path);
		writer.write(testTable);
		
		CSVReader reader = new CSVReader(path);
		Table newTable = reader.read();
	}

}
