package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.Model;
import main.java.edu.csu2017sp314.dtr18.Model.location;

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
		assertEquals("popcorn",m.locations.get(0).getOtherAt(0));
		assertEquals("vin-deisal",m.locations.get(0).getOtherAt(1));
	
		
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
		assertEquals(633,m.bestTripDistance);
	}
	
	@Test
	public void test2Opt() throws FileNotFoundException{
		File f = new File("test2Opt.csv");
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
		int distance = m.bestTripDistance;
		m.twoOpt();
		assertTrue(m.bestTripDistance < distance);
	}
	
	@Test
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
	}
	
	@Test
	public void test3opt() throws FileNotFoundException{
		File f = new File("test3Opt.csv");
		PrintWriter out = new PrintWriter(f);
		//ColoradoCountySeats.csv in its entirety O.o
		out.print("Id,Name,County Seat,Latitude,Longitude\n1,Adams County,Brighton,39.87°N,104.33°W\n2,Alamosa County,Alamosa,37.57°N,105.79°W\n3,Arapahoe County,Littleton,39.64°N,104.33°W\n4,Archuleta County,Pagosa Springs,37.20°N,107.05°W\n5,Baca County,Springfield,37.30°N,102.54°W\n6,Bent County,Las Animas,37.93°N,103.08°W\n7,Boulder County,Boulder,40.09°N,105.40°W\n8,City and County of Broomfield,Broomfield,39.95°N,105.05°W\n9,Chaffee County,Salida,38.74°N,106.32°W\n10,Cheyenne County,Cheyenne Wells,38.84°N,102.60°W\n11,Clear Creek County,Georgetown,39.69°N,105.67°W\n12,Conejos County,Conejos,37.21°N,106.18°W\n13,Costilla County,San Luis,37.28°N,105.43°W\n14,Crowley County,Ordway,38.32°N,103.79°W\n15,Custer County,Westcliffe,38.10°N,105.37°W\n16,Delta County,Delta,38.86°N,107.86°W\n17,City and County of Denver,Denver,39.76°N,104.88°W\n18,Dolores County,Dove Creek,37.75°N,108.53°W\n19,Douglas County,Castle Rock,39.33°N,104.93°W\n20,Eagle County,Eagle,39.63°N,106.69°W\n21,Elbert County,Kiowa,39.31°N,104.12°W\n22,El Paso County,Colorado Springs,38.83°N,104.53°W\n23,Fremont County,Ca°on City,38.46°N,105.42°W\n24,Garfield County,Glenwood Springs,39.60°N,107.91°W\n25,Gilpin County,Central City,39.86°N,105.53°W\n26,Grand County,Hot Sulphur Springs,40.12°N,106.10°W\n27,Gunnison County,Gunnison,38.67°N,107.08°W\n28,Hinsdale County,Lake City,37.81°N,107.38°W\n29,Huerfano County,Walsenburg,37.69°N,104.96°W\n30,Jackson County,Walden,40.66°N,106.33°W\n31,Jefferson County,Golden,39.59°N,105.25°W\n32,Kiowa County,Eads,38.39°N,102.76°W\n33,Kit Carson County,Burlington,39.31°N,102.60°W\n34,Lake County,Leadville,39.20°N,106.35°W\n35,La Plata County,Durango,37.29°N,107.84°W\n36,Larimer County,Fort Collins,40.66°N,105.48°W\n37,Las Animas County,Trinidad,37.32°N,104.04°W\n38,Lincoln County,Hugo,38.99°N,103.51°W\n39,Logan County,Sterling,40.73°N,103.09°W\n40,Mesa County,Grand Junction,39.02°N,108.46°W\n41,Mineral County,Creede,37.65°N,106.93°W\n42,Moffat County,Craig,40.57°N,108.20°W\n43,Montezuma County,Cortez,37.34°N,108.60°W\n44,Montrose County,Montrose,38.41°N,108.26°W\n45,Morgan County,Fort Morgan,40.26°N,103.81°W\n46,Otero County,La Junta,37.88°N,103.72°W\n47,Ouray County,Ouray,38.15°N,107.77°W\n48,Park County,Fairplay,39.12°N,105.72°W\n49,Phillips County,Holyoke,40.59°N,102.35°W\n50,Pitkin County,Aspen,39.22°N,106.92°W\n51,Prowers County,Lamar,37.96°N,102.39°W\n52,Pueblo County,Pueblo,38.17°N,104.49°W\n53,Rio Blanco County,Meeker,39.97°N,108.20°W\n54,Rio Grande County,Del Norte,37.49°N,106.45°W\n55,Routt County,Steamboat Springs,40.48°N,106.99°W\n56,Saguache County[7][8],Saguache,38.03°N,106.25°W\n57,San Juan County,Silverton,37.78°N,107.67°W\n58,San Miguel County,Telluride,38.01°N,108.43°W\n59,Sedgwick County,Julesburg,40.87°N,102.36°W\n60,Summit County,Breckenridge,39.62°N,106.14°W\n61,Teller County,Cripple Creek,38.87°N,105.18°W\n62,Washington County,Akron,39.97°N,103.21°W\n63,Weld County,Greeley,40.56°N,104.38°W\n64,Yuma County,Wray,40.00°N,102.42°W");
		out.close();
		
		m = new Model(f);
		Model m2 = new Model(f);
		f.delete();
		m2.twoOpt();
		m.threeOpt();
		assertTrue(m.bestTripDistance < m2.bestTripDistance);
	}

}
