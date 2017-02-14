package View;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestView {
	ArrayList<Integer> ids = new ArrayList<Integer>();
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<Double> xs = new ArrayList<Double>();
	ArrayList<Double> ys = new ArrayList<Double>();
	View view;
	
	@Before
	public void intialize(){
		ids.add(10); ids.add(20);
		names.add("Fort Collins"); names.add("Boulder");
		xs.add(1.0); xs.add(2.0);
		ys.add(3.0); ys.add(4.0);
		view = new View(ids,names,xs,ys);
	}

	@Test
	public void test() {
		assertEquals(ids,view.getId());
		assertEquals(names,view.getName());
		assertEquals(xs,view.getX());
		assertEquals(ys,view.getY());
	}

}
