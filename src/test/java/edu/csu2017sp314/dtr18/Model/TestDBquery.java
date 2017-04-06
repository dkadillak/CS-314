package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.DBquery;

public class TestDBquery {
	private DBquery query;
	
	@Before
	public void setup(){
		query = new DBquery();
	}

	@Test
	public void testAddColumn() {
		query.addColumn("airports.name");
		query.addColumn("countries.code");
		
		assertEquals("SELECT airports.name,countries.code", query.getColumns());
	}
	
	@Test
	public void testSetFrom(){
		query.setFrom("all");
		String expected = " from continents";
		expected += " inner join countries on countries.continent = continents.id";
		expected += " inner join regions on regions.iso_country = countries.code";
		expected += " inner join airports on airports.iso_region = regions.code";
		assertEquals(expected, query.getFrom());
		
		query.setFrom("airports continents");
		expected = " from continents";
		expected += " inner join airports on airports.continent = continents.id";
		assertEquals(expected, query.getFrom());
	}
	
	@After
	public void cleanup(){
		query.close();
	}

}
