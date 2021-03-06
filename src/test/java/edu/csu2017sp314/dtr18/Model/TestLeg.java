package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.Leg;
import main.java.edu.csu2017sp314.dtr18.Model.location;

public class TestLeg {
	
	@Test
	public void testLeg(){
		String Name = "Fort Collins";
		String ID = "10";
		double latitude = -123.22, longitude = 56.98;
		
		location start = new location(Name, ID, latitude, longitude);
		location end = new location("Denver","56",-100.6,60.1);
		
		Leg l = new Leg(start,end,1000);
		
		
		assertEquals(start.getName(), l.getStart().getName());
		assertEquals(end.getName(), l.getEnd().getName());
		assertEquals(1000, l.getDistance());
	}
	
	
	

}
