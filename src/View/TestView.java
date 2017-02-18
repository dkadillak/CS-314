package View;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

public class TestView {
	File xml,svg;
	View view;
	
	
	@Test
	public void testEmptyXml(){
		xml = new File("testEmpty.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg,9999);
		
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
		view = new View(xml,svg,9999);
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
	
	@Test
	public void testEmptyMap(){
		xml = new File("temp.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg,9999);
		
		//expected file
		String s = "<?xml version=\"1.0\"?>\n";
		s += "<svg width=\"1280\" height=\"1024\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\">\n";
		s += "<g>\n";
		s += "\t<title>Titles</title>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"state\" y=\"40\" x=\"640\">Colorado</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"distance\" y=\"1014\" x=\"640\">9999 miles</text>\n";
		s += "</g>\n";
		s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"50\" x2=\"1230\" y1=\"50\" x1=\"50\" stroke-width=\"5\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"974\" x2=\"1230\" y1=\"50\" x1=\"1230\" stroke-width=\"5\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"974\" x2=\"50\" y1=\"974\" x1=\"1230\" stroke-width=\"5\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"50\" x2=\"50\" y1=\"974\" x1=\"50\" stroke-width=\"5\" stroke=\"#666666\"/>\n";
		s += "</g>\n";
		s += "</svg>\n";
		
		view.finalizeTrip();
		
		try {
			Scanner scan = new Scanner(svg);
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
		xml.delete();
		svg.delete();
	}

}
