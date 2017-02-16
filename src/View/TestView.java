package View;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestView {
	File xml,svg;
	View view;
	
	@Before
	public void setUp(){
		xml = new File("tempxml");
		svg = new File("tempsvg");
		view = new View(xml,svg);
	}

	@Test
	public void testStreams() {
		PrintWriter out = view.getMap();
		String s = "Test string for output stream";
		out.println(s);
		out.close();
		try {
			Scanner scan = new Scanner(svg);
			String scanned = scan.nextLine();
			assertEquals(s,scanned);
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanUp(){
		xml.delete();
		svg.delete();
	}

}
