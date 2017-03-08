package test.java.edu.csu2017sp314.dtr18.View;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.View.View;

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
		s += "\t<sequence>" + "1" + "</sequence>\n";
		s += "\t<start>" + start + "</start>\n";
		s += "\t<finish>" + finish + "</finish>\n";
		s += "\t<milage>" + milage + "</milage>\n";
		s += "</leg>\n";
		s += "</trip>\n";
		
		view.addLeg("1", start, finish, milage);
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
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"state\" y=\"25\" x=\"532.5\">Colorado</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"distance\" y=\"770\" x=\"532.5\">9999 miles</text>\n";
		s += "</g>\n";
		s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
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
	
	@Test
	public void testAddLine(){
		xml = new File("temp.xml");
		svg = new File("testAddLine.svg");
		view = new View(xml,svg,9999);
		
		//expected file
		String s = "<?xml version=\"1.0\"?>\n";
		s += "<svg width=\"1280\" height=\"1024\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\">\n";
		s += "<g>\n";
		s += "\t<title>Titles</title>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"state\" y=\"25\" x=\"532.5\">Colorado</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"distance\" y=\"770\" x=\"532.5\">9999 miles</text>\n";
		s += "</g>\n";
		s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "</g>\n";
		s += "<g>\n";
		s += "\t<title>Legs</title>\n";
		s += "\t<line id=\"leg1\" y2=\"110\" x2=\"500\" y1=\"100\" x1=\"100\" stroke-width=\"3\" stroke=\"#999999\"/>\n";
		s += "</g>\n";
		s += "</svg>\n";
		
		view.addHeader("Legs");
		view.addLine(100, 100, 500, 110);
		view.addFooter();
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
	
	@Test
	public void testAddLabel(){
		xml = new File("temp.xml");
		svg = new File("testAddLabel.svg");	
		view = new View(xml,svg,9999);
		
		//expected file
		String s = "<?xml version=\"1.0\"?>\n";
		s += "<svg width=\"1280\" height=\"1024\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\">\n";
		s += "<g>\n";
		s += "\t<title>Titles</title>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"state\" y=\"25\" x=\"532.5\">Colorado</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"distance\" y=\"770\" x=\"532.5\">9999 miles</text>\n";
		s += "</g>\n";
		s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "</g>\n";
		s += "<g>\n";
		s += "\t<title>Labels</title>\n";
		s += "\t<text font-family=\"Sans-serif\" font-size=\"16\" id=\"id1\" y=\"100\" x=\"100\">CityA</text>\n";
		s += "</g>\n";
		s += "</svg>\n";
		
		view.addHeader("Labels");
		view.addLabel(100, 100, "CityA");
		view.addFooter();
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
	
	@Test
	public void testConvert(){
		xml = new File("temp.xml");
		svg = new File("testConvert.svg");
		view = new View(xml,svg,9999);
		
		view.addHeader("Labels");
		int point1[] = view.convertCoords(39.1177, -106.4453);
		int point2[] = view.convertCoords(37.5774, -105.4857);
		view.addLabel(point1[1], point1[0], "Point1");
		view.addLabel(point2[1], point2[0], "Point2");
		view.addFooter();
		view.addHeader("Legs");
		view.addLine(point1[1], point1[0], point2[1], point2[0]);
		view.addFooter();
		view.finalizeTrip();
	}
	
	@After
	public void cleanUp(){
		xml.delete();
		svg.delete();
	}

}
