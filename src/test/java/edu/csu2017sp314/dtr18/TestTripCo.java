package test.java.edu.csu2017sp314.dtr18;

import static org.junit.Assert.*;
import main.java.edu.csu2017sp314.dtr18.TripCo;

import org.junit.Test;


public class TestTripCo {

	@Test
	
	public void testConstructor(){
		int count = 4;
		TripCo test = new TripCo(count);
		assertEquals(count,test.getoptCount());
	}
 

}
