package View;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

public class TestView {
	File xml,svg;
	View view;
	

	@Test
	public void testStreams() {
		xml = new File("testStreams.xml");
		svg = new File("testStreams.svg");
		view = new View(xml,svg);
		
		PrintWriter out = view.getMap();
		String s = "Test string for output stream";
		out.println(s);
		out.flush();
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
	
	@Test
	public void testEmptyXml(){
		xml = new File("testEmpty.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg);
		
		view.finalizeTrip();
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		s += "<trip>\n</trip>\n";
		
		try {
			Scanner scan = new Scanner(xml);
			String scanned = "";
			while(scan.hasNextLine()){ 
				scanned += scan.nextLine() + "\n";
			}
			assertEquals(s,scanned);
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddLeg(){
		xml = new File("testAddLeg.xml");
		svg = new File("testAddLeg.svg");
		view = new View(xml,svg);
		String start = "Fort Collins";
		String finish = "Denver";
		int milage = 12;
		
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		s += "<trip>\n<leg>\n";
		s += "\t<sequence>" + 1 + "</sequence>\n";
		s += "\t<start>" + start + "</start>\n";
		s += "\t<finish>" + finish + "</finish>\n";
		s += "\t<milage>" + milage + "</milage>\n";
		s += "</leg>\n";
		s += "</trip>\n";
		
		view.addLeg(1, start, finish, milage);
		view.finalizeTrip();
		
		try {
			Scanner scan = new Scanner(xml);
			String scanned = "";
			while(scan.hasNextLine()) scanned += scan.nextLine() + "\n";
			assertEquals(s,scanned);
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanUp(){
		view.getMap().close();
		xml.delete();
		svg.delete();
	}

}
