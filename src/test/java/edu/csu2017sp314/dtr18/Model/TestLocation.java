package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.location;

public class TestLocation {

	@Test
	public void locationTests(){		
		String name = "Fort Collins";
		String id = "10";
		double latitude = -123.22, longitude = 56.98;
		
		location loc = new location(name, id, latitude, longitude);
		
		assertEquals(name, loc.getName());
		assertEquals(id,loc.getID());
		assertEquals(latitude,loc.getLatitude(),.01);
		assertEquals(longitude,loc.getLongitude(),.01);		
		
	}
	
	@Ignore
	@Test
	public void testSql(){
		location loc = new location("KDEN");
		
		assertEquals("Denver International Airport",loc.name);
		assertEquals("KDEN",loc.id);
		assertTrue(loc.latitude > 39.0 && loc.latitude < 40.0);
		assertTrue(loc.longitude < -104.0 && loc.longitude > -105.0);
		assertEquals(5431,loc.elevation);
		assertEquals("Denver",loc.municipality);
		assertEquals("Colorado",loc.region);
		assertEquals("United States",loc.country);
		assertEquals("North America",loc.continent);
		String url = "http://en.wikipedia.org/wiki/Denver_International_Airport";
		assertEquals(url,loc.airportUrl);
		url = "http://en.wikipedia.org/wiki/Colorado";
		assertEquals(url,loc.regionUrl);
		url = "http://en.wikipedia.org/wiki/United_States";
		assertEquals(url,loc.countryUrl);
	}
	
}
