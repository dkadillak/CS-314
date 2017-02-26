package Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestTrip {
	private Leg leg;
	private trip t;

	@Before
	public void setUp(){
		t = new trip();
		location l1 = new location("Fort Collins","1",100.0,100.0,null);
		location l2 = new location("Denver","2",200.0,200.0,null);
		leg = new Leg(l1,l2,50);
	}

	@Test
	public void testAddLeg() {
		t.addLeg(leg);
		assertEquals(leg.toString(),t.getLegAt(0).toString());
	}

}
