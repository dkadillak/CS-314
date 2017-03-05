package Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestLeg {
	
	@Test
	public void testLeg(){
		String Name = "Fort Collins";
		String ID = "10";
		double latitude = -123.22, longitude = 56.98;
		ArrayList<String> other = new ArrayList<String>();
		other.add("banana");
		other.add("Larimer County");
		other.add("ok");
		
		location start = new location(Name, ID, latitude, longitude, other);
		location end = new location("Denver","56",-100.6,60.1,other);
		
		Leg l = new Leg(start,end,1000);
		
		
		assertEquals(start.getName(), l.getStart().getName());
		assertEquals(end.getName(), l.getEnd().getName());
		assertEquals(1000, l.getDistance());
	}
	
	
	

}
