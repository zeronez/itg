package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataFilterer {
	
    public static Collection<?> filterByCountry(Reader source, String country) {
    	List<CsvRecord> result = new ArrayList<>();
    	
    	try {
    		BufferedReader br = new BufferedReader(source);
    		
	    	readHeader(br);
	    	List<CsvRecord> rows = readRows(br);
	    	result = rows
		    		.stream()
		    		.filter( r -> r.getColumns().get(1).equals(country))
		    		.collect(Collectors.toList());
	    	
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    	
        return result;
    }

	public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
		List<CsvRecord> result = new ArrayList<>();
    	
    	try {
    		BufferedReader br = new BufferedReader(source);
    		
	    	readHeader(br);
	    	List<CsvRecord> rows = readRows(br);
	    	result = rows
		    		.stream()
		    		.filter( c -> c.getColumns().get(1).equals(country))
		    		.filter( r -> Long.parseLong(r.getColumns().get(2)) > limit)
		    		.collect(Collectors.toList());
	    	
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    	
        return result;
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
    	List<CsvRecord> result = new ArrayList<>();
    	
    	try {
    		BufferedReader br = new BufferedReader(source);
    		
	    	readHeader(br);
	    	List<CsvRecord> rows = readRows(br);
	    	Long average = getAverageResponseTime(rows);
	    	result = rows
		    		.stream()
		    		.filter( r -> Long.parseLong(r.getColumns().get(2)) > average)
		    		.collect(Collectors.toList());
	    	
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    	
        return result;
    }
    
    private static Long getAverageResponseTime(List<CsvRecord> rows) {
    	if(Objects.isNull(rows) || rows.isEmpty()) return 0L;
    	
		Long total =  rows
						.stream()
						.map( r -> Long.parseLong(r.getColumns().get(2)))
						.reduce(0L, Long::sum);
		
		return total/rows.size();
	}

	private static CsvRecord readHeader(BufferedReader br) throws IOException {
    	String line = br.readLine();
    	
    	if(Objects.isNull(line)) return null;
    	
    	return CsvRecord.build(line);
	}
    
    private static List<CsvRecord> readRows(BufferedReader br) throws IOException {
    	List<CsvRecord> rows = new ArrayList<>();
    	String line = null;
    	
    	while( Objects.nonNull( line = br.readLine() )) {
    		rows.add(CsvRecord.build(line));
    	}

    	return rows;
    }
}