package Model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestModel{
	Model m;

	@Test
	public void LatLongConverterTest() {
		m = new Model();
	//variable used to see if method successfully removes spaces and produce correct answer
		String t1 = "37° 16' 20.47\" E";
		//Do I need a test for this?
		
	//variable used to see if method converts from degrees minutes seconds to decimel degrees
		String t2 = "179°59'59.99\" E";
	//Checking if adding W or N makes value negative
		String t2a = "179°59'59.99\" W";
		String t2b = "179°59'59.99\" N";
		
	//variable used to see if method converts from degrees minutes to decimel degrees
		String t3 = "179°59.99'S";
	//Checking if adding W or N makes value negative
		String t3a = "179°59.99'W";
		String t3b = "179°59.99'N";
		
	//variable used to see if method converts from degrees to decimal degrees
		String t4 = "179.99°S";
	//Checking if adding W or N makes value negative 
		String t4a = "179.99°W";
		String t4b = "179.99°N";
		
	//Answer variables
		double t2DD = 179.9999972;
		double t2abDD = 179.9999972;
		
		double t3DD = 179.9998333;
		double t3abDD = -179.9998333;
		
		double t4DD = 179.99;
		double t4abDD = -179.99;
		
	//assert for t2
		assertEquals(t2DD,m.LatLongConverter(t2),.01);
		assertEquals(t2abDD,m.LatLongConverter(t2a),.01);
		assertEquals(t2abDD,m.LatLongConverter(t2b),.01);
	//assert for t3
		assertEquals(t3DD,m.LatLongConverter(t3),.01);
		assertEquals(t3abDD,m.LatLongConverter(t3a),.01);
		assertEquals(t3abDD,m.LatLongConverter(t3b),.01);
	//assert for t4
		assertEquals(t4DD,m.LatLongConverter(t4),.01);
		assertEquals(t4abDD,m.LatLongConverter(t4a),.01);
		assertEquals(t4abDD,m.LatLongConverter(t4b),.01);
	}

}