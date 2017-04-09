package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.Model;
import main.java.edu.csu2017sp314.dtr18.Model.location;

public class TestModel{
	Model m;

	@Test
	public void LatLongConverterTest() {
		m = new Model('m');
	//variable used to see if method successfully removes spaces and produce correct answer
		//String t1 = "37° 16' 20.47\" E";
		//Do I need a test for this?
		
	//variable used to see if method converts from degrees minutes seconds to decimal degrees
		String t2 = "179°59'59.99\" E";
	//Checking if adding W or N makes value negative
		String t2a = "179°59'59.99\" W";
		String t2b = "179°59'59.99\" S";
		
	//variable used to see if method converts from degrees minutes to decimal degrees
		String t3 = "179°59.99'N";
	//Checking if adding W or S makes value negative
		String t3a = "179°59.99'W";
		String t3b = "179°59.99'S";
		
	//variable used to see if method converts from degrees to decimal degrees
		String t4 = "179.99°E";
	//Checking if adding W or N makes value negative 
		String t4a = "179.99°W";
		String t4b = "179.99°S";
		
	//Answer variables
		double t2DD = 179.9999972;
		double t2abDD = -179.9999972;
		
		double t3DD = 179.9998333;
		double t3abDD = -179.9998333;
		
		double t4DD = 179.99;
		double t4abDD = -179.99;
		
	//assert for t2
		assertEquals(t2DD,m.LatLongConverter(t2),.01);
		assertEquals(t2abDD,m.LatLongConverter(t2a),.01);
		assertEquals(t2abDD,m.LatLongConverter(t2b),.01);
	//assert for t3
		assertEquals(t3DD,m.LatLongConverter(t3),1);
		assertEquals(t3abDD,m.LatLongConverter(t3a),.01);
		assertEquals(t3abDD,m.LatLongConverter(t3b),.01);
	//assert for t4
		assertEquals(t4DD,m.LatLongConverter(t4),1);
		assertEquals(t4abDD,m.LatLongConverter(t4a),.01);
		assertEquals(t4abDD,m.LatLongConverter(t4b),.01);
	}
	
	@Test
	public void smallestOnLineTest(){
		m = new Model('m');
		int t1[] = new int[] {12,3,0,12321,4};
		int t2[] = new int [] {0,0,0,0,8,89};
		
		assertEquals(1,m.smallestOnLine(t1));
		assertEquals(4,m.smallestOnLine(t2));
		
	}
	
	
	@Test
	public void circleDistanceTest(){
		m = new Model('m');
		
		double lat1=118.87, lon1=104.33, lat2=37.57, lon2=-105.79;
		
		assertEquals(2081,m.circleDistance(lat1, lon1, lat2, lon2 ));
		
		
	}
	
	@Test
	public void testBestTrip() throws FileNotFoundException{
		ArrayList<location> locs = new ArrayList<location>();
		locs.add(new location("1","Cheyenne County",38.84,-102.6));
		locs.add(new location("2","El Paso County", 38.83, -104.53));
		locs.add(new location("3","La Plata County",37.29,-107.84));
		locs.add(new location("4","Mineral County",37.65,-106.93));
		locs.add(new location("5","San Juan County",37.78,-107.67));
		
		m = new Model('m');
		m.locations = locs;
		m.computeDistances();
		m.bestNearestNeighbor();
		assertEquals(633,m.bestTripDistance);
	}
	
	@Test
	public void test2Opt() throws FileNotFoundException{
		ArrayList<location> locs = new ArrayList<location>();
		locs.add(new location("1","Cheyenne County",38.84,-102.6));
		locs.add(new location("2","El Paso County", 38.83, -104.53));
		locs.add(new location("3","La Plata County",37.29,-107.84));
		locs.add(new location("4","Mineral County",37.65,-106.93));
		locs.add(new location("5","San Juan County",37.78,-107.67));
		locs.add(new location("6","Adams County",39.87,-104.33));
		
		m = new Model('m');
		m.locations = locs;
		m.computeDistances();
		m.bestNearestNeighbor();
		int distance = m.bestTripDistance;
		m.twoOpt();
		assertTrue(m.bestTripDistance < distance);
	}
	 
	@Test
	public void test3opt() throws FileNotFoundException{
		ArrayList<location> locs = new ArrayList<location>();
		locs.add(new location("1","Cheyenne County",38.84,-102.6));
		locs.add(new location("2","El Paso County", 38.83, -104.53));
		locs.add(new location("3","La Plata County",37.29,-107.84));
		locs.add(new location("4","Mineral County",37.65,-106.93));
		locs.add(new location("5","San Juan County",37.78,-107.67));
		locs.add(new location("6","Adams County",39.87,-104.33));
		
		m = new Model('m');
		Model m2 = new Model('m');
		m.locations = locs;
		m2.locations = locs;
		m.computeDistances();
		m2.computeDistances();
		m2.twoOpt();
		m.threeOpt();
		assertTrue(m.bestTripDistance <= m2.bestTripDistance);
	}
	
	@Test
	public void testEscapeSingleQuotes(){
		String[] locs = {"St. John's Airport", "Denver Airport"};
		locs = Model.escapeSingleQuotes(locs);
		assertEquals("St. John''s Airport",locs[0]);
		assertEquals("Denver Airport",locs[1]);
	}

}
