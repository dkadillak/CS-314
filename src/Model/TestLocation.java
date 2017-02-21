package Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestLocation {

	@Test
	public void locationTests(){
		
		//location = String Name, int ID, double Latitude, double Longitude
		//ArrayList<String> Other
		
		String Name = "Fort Collins", banana="banana",LarimerCounty="Larimer County",ok="ok";
		int ID = 10;
		double latitude = -123.22, longitude = 56.98;
		ArrayList<String> other = new ArrayList<String>();
		other.add("banana");
		other.add("Larimer County");
		other.add("ok");
		
		location l = new location(Name, ID, latitude, longitude, other);
		
		assertEquals(Name, l.getName());
		assertEquals(ID,l.getID());
		assertEquals(latitude,l.getLatitude(),.01);
		assertEquals(longitude,l.getLongitude(),.01);
		assertEquals(banana,l.getOtherAt(0));
		assertEquals(LarimerCounty,l.getOtherAt(1));
		assertEquals(ok,l.getOtherAt(2));
		
		
		
	}
	
}