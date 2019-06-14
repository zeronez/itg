package com.connectgroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CsvRecord {
	
	private List<String> columns;
	
	private CsvRecord() {
		columns = new ArrayList<>();
	}
	
	public static CsvRecord build(String line){
		if(Objects.isNull(line)) throw new RuntimeException("Line must not be null");
		
		CsvRecord result = new CsvRecord();
		List<String> cols = new ArrayList<>(Arrays.asList(line.split(",")));
		result.setColumns(cols);
		return result;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	public List<String> getColumns(){
		return this.columns;
	}
}
