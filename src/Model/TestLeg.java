package Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestLeg {
	
	@Test
	public void testLeg(){
	//Leg(String Start, int StartLocationID, String End, int EndLocationID,int Mileage)
		String Start = "Point A", End = "Point B";
		int StartID = 0, EndID = 4, mileage = 30;
		
		Leg l = new Leg(Start, StartID, End, EndID, mileage);
		
		
		assertEquals(Start, l.getStartLocation());
		assertEquals(End, l.getendLocation());
		assertEquals(StartID, l.getstartLocationID());
		assertEquals(End, l.getendLocation());
		assertEquals(mileage, l.getDistance());
	}
	
	
	

}
