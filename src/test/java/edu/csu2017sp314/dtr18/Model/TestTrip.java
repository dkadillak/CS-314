package test.java.edu.csu2017sp314.dtr18.Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.Leg;
import main.java.edu.csu2017sp314.dtr18.Model.location;
import main.java.edu.csu2017sp314.dtr18.Model.trip;

public class TestTrip {
	private Leg leg;
	private trip t;

	@Before
	public void setUp(){
		t = new trip();
		location l1 = new location("Fort Collins","1",100.0,100.0);
		location l2 = new location("Denver","2",200.0,200.0);
		leg = new Leg(l1,l2,50);
	}

	@Test
	public void testAddLeg() {
		t.addLeg(leg);
		assertEquals(leg.toString(),t.getLegAt(0).toString());
	}

}
