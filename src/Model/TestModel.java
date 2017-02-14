package Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestModel{


	@Before
	public void initialize(){
	ArrayList<Integer> testArray= new ArrayList<Integer>(10);
	Model m = new Model(10);
	}


	@Test
	public void test(){

		assertEquals(testArray.size(),m.getSize());

	}

}