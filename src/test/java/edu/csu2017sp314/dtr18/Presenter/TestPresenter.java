package Presenter;
import Model.Model;
import View.View;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class TestPresenter {

	File testX, testS;
	View view;
	Model model;
	Presenter presenter;

	@Test
	public void testMakeTrip(){
		testX = new File("junit.xml");
		testS = new File("junit.svg");
		model = new Model();
		view = new View(testX, testS, 1000);
		presenter = new Presenter(view, model);

		assertNotNull(presenter);
		int midX = (222 + 620) / 2;
		assertEquals(midX, ((222 + 620) / 2));
		int midY = (525 + 482) / 2;
		assertEquals(midY, ((525 + 481) / 2));
		int expected [] = {485, 481};
		int expected2 [] = {469, 476};
		int expected3 [] = {505, 476};
		int results [] = view.convertCoords(39.1177, -106.4453);
		int results2 [] = view.convertCoords(39.1875, -106.4756);
		int results3 [] = view.convertCoords(39.0294, -106.4729);
		assertArrayEquals(expected, results);
		assertArrayEquals(expected2, results2);
		assertArrayEquals(expected3, results3);

		view.finalizeTrip();
	}

	@After
	public void delete(){
		testX.delete();
		testS.delete();
	}

}
