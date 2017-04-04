package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.location;

public class TestLocation {

	@Test
	public void locationTests(){
		
		//location = String Name, String ID, double Latitude, double Longitude
		//ArrayList<String> Other
		
		String Name = "Fort Collins";
		String ID = "10";
		double latitude = -123.22, longitude = 56.98;
		
		location l = new location(Name, ID, latitude, longitude);
		
		assertEquals(Name, l.getName());
		assertEquals(ID,l.getID());
		assertEquals(latitude,l.getLatitude(),.01);
		assertEquals(longitude,l.getLongitude(),.01);		
		
	}
	
	@Test
	public void testSql(){
		location l = new location("KDEN");
		
		assertEquals("Denver International Airport",l.name);
		assertEquals("KDEN",l.id);
		assertTrue(l.latitude > 39.0 && l.latitude < 40.0);
		assertTrue(l.longitude < -104.0 && l.longitude > -105.0);
		assertEquals(5431,l.elevation);
		assertEquals("Denver",l.municipality);
		assertEquals("Colorado",l.region);
		assertEquals("United States",l.country);
		assertEquals("North America",l.continent);
		String url = "http://en.wikipedia.org/wiki/Denver_International_Airport";
		assertEquals(url,l.airportUrl);
		url = "http://en.wikipedia.org/wiki/Colorado";
		assertEquals(url,l.regionUrl);
		url = "http://en.wikipedia.org/wiki/United_States";
		assertEquals(url,l.countryUrl);
	}
	
}
