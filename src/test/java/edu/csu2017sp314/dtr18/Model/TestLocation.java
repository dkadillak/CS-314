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
	
}
