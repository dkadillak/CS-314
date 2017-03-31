package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Ignore;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.Model;

public class TestModel{
	Model m;

	@Test
	public void LatLongConverterTest() {
		m = new Model();
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
	public void firstLineParserTest(){
		m = new Model();
		String testFirstLine = "blah, ID, other-field, latitude, LONGITUDE, region, city, name ";
		int NamePosition=7, IDPosition=1, LatitudePosition=3, LongitudePosition=4;
		
		m.firstLineParser(testFirstLine);
		
		assertEquals(NamePosition,m.getNamePosition());
		assertEquals(IDPosition,m.getIDPosition());
		assertEquals(LatitudePosition,m.getLatitudePosition());
		assertEquals(LongitudePosition,m.getLongitudePosition());

	}
	
	@Test
	public void LineParserTest(){
		String firstLine = "extraField, Name, ID, extraField, Latitude, Longitude";
		
		//deliberately put no spaces in input string because that's how all input strings come
		//into this method from parselocations()
		String fileBody = "popcorn,Ireland,2,vin-deisal,118.87°N,104.33°E";
		m = new Model();
		
		//Setting up answer variables
		double Latitude= 118.87, Longitude= 104.33;
		
		//setting up private class variables
		m.firstLineParser(firstLine);
		m.lineParser(fileBody);
		
		assertEquals("Ireland",m.locations.get(0).getName());
		assertEquals("2",m.locations.get(0).getID());
		assertEquals(Latitude, m.locations.get(0).getLatitude(),.01);
		assertEquals(Longitude, m.locations.get(0).getLongitude(),.01);
	
		
	}
	
	
	@Test
	public void smallestOnLineTest(){
		m = new Model();
		int t1[] = new int[] {12,3,0,12321,4};
		int t2[] = new int [] {0,0,0,0,8,89};
		
		assertEquals(1,m.smallestOnLine(t1));
		assertEquals(4,m.smallestOnLine(t2));
		
	}
	
	
	@Test
	public void circleDistanceTest(){
		m = new Model();
		
		double lat1=118.87, lon1=104.33, lat2=37.57, lon2=-105.79;
		
		assertEquals(2081,m.circleDistance(lat1, lon1, lat2, lon2 ));
		
		
	}
	
	@Test
	public void testBestTrip() throws FileNotFoundException{
		File f = new File("testBestTrip.csv");
		PrintWriter out = new PrintWriter(f);
		out.println("Id,Name,County Seat,Latitude,Longitude");
		out.println("1,Cheyenne County,Cheyenne Wells,38.84°N,102.60°W");
		out.println("2,El Paso County,Colorado Springs,38.83°N,104.53°W");
		out.println("3,La Plata County,Durango,37.29°N,107.84°W");
		out.println("4,Mineral County,Creede,37.65°N,106.93°W");
		out.println("5,San Juan County,Silverton,37.78°N,107.67°W");
		out.close();
		
		m = new Model(f);
		f.delete();
		m.computeDistances();
		m.bestNearestNeighbor();
		assertEquals(633,m.bestTripDistance);
	}
	
	@Test
	public void test2Opt() throws FileNotFoundException{
		File f = new File("test2Opt.csv");
		PrintWriter out = new PrintWriter(f);
		out.print("Id,Name,County Seat,Latitude,Longitude\n");
		out.println("1,Cheyenne County,Cheyenne Wells,38.84°N,102.60°W");
		out.println("2,El Paso County,Colorado Springs,38.83°N,104.53°W");
		out.println("3,La Plata County,Durango,37.29°N,107.84°W");
		out.println("4,Mineral County,Creede,37.65°N,106.93°W");
		out.println("5,San Juan County,Silverton,37.78°N,107.67°W");
		out.print("6,Adams County,Brighton,39.87°N,104.33°W");
		out.close();
		
		m = new Model(f);
		f.delete();
		m.computeDistances();
		m.bestNearestNeighbor();
		int distance = m.bestTripDistance;
		//System.out.println(distance);
		m.twoOpt();
		//System.out.println(m.bestTripDistance);
		assertTrue(m.bestTripDistance < distance);
	}
	
	/*@Test
	public void test3OptSwap() throws FileNotFoundException{
		File f = new File("test3OptSwap.csv");
		PrintWriter out = new PrintWriter(f);
		out.println("Id,Name,County Seat,Latitude,Longitude");
		out.println("1,Cheyenne County,Cheyenne Wells,38.84°N,102.60°W");
		out.println("2,El Paso County,Colorado Springs,38.83°N,104.53°W");
		out.println("3,La Plata County,Durango,37.29°N,107.84°W");
		out.println("4,Mineral County,Creede,37.65°N,106.93°W");
		out.println("5,San Juan County,Silverton,37.78°N,107.67°W");
		out.println("6,Adams County,Brighton,39.87°N,104.33°W");
		out.close();
		
		String[] expected = new String[7];
		expected[0] = "5";
		expected[1] = "3";
		expected[2] = "4";
		expected[3] = "2";
		expected[4] = "1";
		expected[5] = "6";
		expected[6] = "5";
		
		m = new Model(f);
		f.delete();
		
		location[] input = new location[7];
		input[0] = m.getLocation("5");
		input[1] = m.getLocation("3");
		input[2] = m.getLocation("4");
		input[3] = m.getLocation("2");
		input[4] = m.getLocation("6");
		input[5] = m.getLocation("1");
		input[6] = m.getLocation("5");
		
		location[] result = m.threeOptSwap(input, 2, 3, 5);
		String[] optimized = new String[7];
		for(int i = 0; i < 7; i++){
			optimized[i] = result[i].getID();
		}
		assertArrayEquals(expected,optimized);
	}*/
	
	
	@Test
	public void test3opt() throws FileNotFoundException{
		File f = new File("test3Opt.csv");
		PrintWriter out = new PrintWriter(f);
		out.print("Id,Name,County Seat,Latitude,Longitude\n");
		out.println("1,Cheyenne County,Cheyenne Wells,38.84°N,102.60°W");
		out.println("2,El Paso County,Colorado Springs,38.83°N,104.53°W");
		out.println("3,La Plata County,Durango,37.29°N,107.84°W");
		out.println("4,Mineral County,Creede,37.65°N,106.93°W");
		out.println("5,San Juan County,Silverton,37.78°N,107.67°W");
		out.print("6,Adams County,Brighton,39.87°N,104.33°W");
		out.close();
		
		m = new Model(f);
		Model m2 = new Model(f);
		f.delete();
		m.computeDistances();
		m2.computeDistances();
		m.bestNearestNeighbor();
		m2.bestNearestNeighbor();
		m2.twoOpt();
		m.threeOpt();
		//System.out.println("2opt: " + m2.bestTripDistance);
		//System.out.println("3opt: " + m.bestTripDistance);
		assertTrue(m.bestTripDistance <= m2.bestTripDistance);
	}
	
	@Test
	public void testSubset() throws FileNotFoundException{
		File f = new File("testSubset.csv");
		PrintWriter out = new PrintWriter(f);
		out.println("Id,Name,County Seat,Latitude,Longitude");
		out.println("1,Cheyenne County,Cheyenne Wells,38.84°N,102.60°W");
		out.println("2,El Paso County,Colorado Springs,38.83°N,104.53°W");
		out.println("3,La Plata County,Durango,37.29°N,107.84°W");
		out.println("4,Mineral County,Creede,37.65°N,106.93°W");
		out.println("5,San Juan County,Silverton,37.78°N,107.67°W");
		out.close();
		
		m = new Model(f);
		f.delete();
		String[] s = {"2","4"};
		Model subset = new Model(m,s);
		//System.out.println(subset);
		assertEquals(2, subset.locations.size());
		assertEquals("El Paso County", subset.locations.get(0).getName());
		assertEquals("Mineral County", subset.locations.get(1).getName());
	}
	
	@Test
	public void testSubsetParser() throws FileNotFoundException{
		m = new Model();
		File f = new File("mstest.txt");
		PrintWriter write = new PrintWriter(f);
		
		write.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		write.println("<selection>");
		write.println("\t<title>name</title>");
		write.println("\t<filename>mstest.txt</filename>");
		write.println("\t<destinations>");
		write.println("\t\t<id>3</id>");
		write.println("\t\t<id>poop</id>");
		write.println("\t\t<id>$*&^#</id>");
		write.println("\t</destinations>");
		write.println("</selection>");
		write.close();
		
		String[] results = m.subsetParser(f);
		String[] expected = {"3", "poop", "$*&^#"};
		assertArrayEquals(expected, results);
		f.delete();
	}

}
