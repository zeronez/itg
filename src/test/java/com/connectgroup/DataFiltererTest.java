package com.connectgroup;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
	
    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
        
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 100).isEmpty());
        
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
    }
    
    @Test
    public void shouldReturnOneRecord_WhenSingleLine() throws FileNotFoundException {
    	assertEquals(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "GB").size(), 1L);
    }
    
    @Test
    public void shouldReturnEmptyCollection_WhenNoMatchingRecords() throws FileNotFoundException {
    	
    	assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "US").isEmpty());
    	
    	assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "IR").isEmpty());
    }
    
    @Test
    public void shouldReturnOneRecord_WhenGBAndFilteredAboveZeroResponseTime() throws FileNotFoundException {
    	assertEquals(1L, DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "GB", 0).size());
    }
    
    @Test
    public void shouldReturnThreeRecords_WhenUSAndFilteredAboveZeroResponseTime() throws FileNotFoundException {
    	assertEquals(3L, DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 0).size());
    }
    
    @Test
    public void shouldReturnThreeRecords_WhenAboveAverageResponseTime() throws FileNotFoundException {
    	//average is 526
    	assertEquals(3L, DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines")).size());
    }
    
    

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
