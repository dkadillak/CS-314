package test.java.edu.csu2017sp314.dtr18.View;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.Model.location;
import main.java.edu.csu2017sp314.dtr18.View.View;

public class TestView {
	File xml,svg;
	View view;
	
	
	@Test
	public void testEmptyXml(){
		xml = new File("testEmpty.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
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
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		s += "<trip>\n<leg>\n";
		s += "\t<sequence>1</sequence>\n";
		s += "\t<start>\n";
		s += "\t\t<id>KDEN</id>\n";
		s += "\t\t<name>Denver International Airport</name>\n";
		s += "\t\t<latitude>39.861698150635</latitude>\n";
		s += "\t\t<longitude>-104.672996521</longitude>\n";
		s += "\t\t<elevation>5431</elevation>\n";
		s += "\t\t<municipality>Denver</municipality>\n";
		s += "\t\t<region>Colorado</region>\n";
		s += "\t\t<country>United States</country>\n";
		s += "\t\t<continent>North America</continent>\n";
		s += "\t\t<airportURL>http://en.wikipedia.org/wiki/Denver_International_Airport</airportURL>\n";
		s += "\t\t<regionURL>http://en.wikipedia.org/wiki/Colorado</regionURL>\n";
		s += "\t\t<countryURL>http://en.wikipedia.org/wiki/United_States</countryURL>\n";
		s += "\t</start>\n";
		s += "\t<finish>\n";
		s += "\t\t<id>AYPY</id>\n";
		s += "\t\t<name>Port Moresby Jacksons International Airport</name>\n";
		s += "\t\t<latitude>-9.443380355834961</latitude>\n";
		s += "\t\t<longitude>147.22000122070312</longitude>\n";
		s += "\t\t<elevation>146</elevation>\n";
		s += "\t\t<municipality>Port Moresby</municipality>\n";
		s += "\t\t<region>National Capital District (Port Moresby)</region>\n";
		s += "\t\t<country>Papua New Guinea</country>\n";
		s += "\t\t<continent>Oceania</continent>\n";
		s += "\t\t<airportURL>http://en.wikipedia.org/wiki/Jacksons_International_Airport</airportURL>\n";
		s += "\t\t<regionURL>http://en.wikipedia.org/wiki/National_Capital_District_(Port_Moresby)</regionURL>\n";
		s += "\t\t<countryURL>http://en.wikipedia.org/wiki/Papua_New_Guinea</countryURL>\n";
		s += "\t</finish>\n";
		s += "\t<distance>34</distance>\n";
		s += "\t<units>miles</units>\n";
		s += "</leg>\n";
		s += "</trip>\n";
		
		location location = new location("Denver International Airport", "KDEN", 
				39.861698150635, -104.672996521);
		location.elevation = 5431;
		location.municipality = "Denver";
		location.region = "Colorado";
		location.country = "United States";
		location.continent = "North America";
		location.airportUrl = "http://en.wikipedia.org/wiki/Denver_International_Airport";
		location.regionUrl = "http://en.wikipedia.org/wiki/Colorado";
		location.countryUrl = "http://en.wikipedia.org/wiki/United_States";
		location location2 = new location("Port Moresby Jacksons International Airport",
				"AYPY", -9.443380355834961, 147.22000122070312);
		location2.elevation = 146;
		location2.region = "National Capital District (Port Moresby)";
		location2.country = "Papua New Guinea";
		location2.continent = "Oceania";
		location2.airportUrl = "http://en.wikipedia.org/wiki/Jacksons_International_Airport";
		location2.regionUrl = "http://en.wikipedia.org/wiki/National_Capital_District_(Port_Moresby)";
		location2.countryUrl = "http://en.wikipedia.org/wiki/Papua_New_Guinea";
		location2.municipality = "Port Moresby";
		
		view.addLeg(1, location, location2, 34, "miles");
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
	public void testMakeString(){
		xml = new File("testAddLeg.xml");
		svg = new File("testAddLeg.svg");
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
		String s = "\t\t<id>KDEN</id>\n";
		s += "\t\t<name>Denver International Airport</name>\n";
		s += "\t\t<latitude>39.861698150635</latitude>\n";
		s += "\t\t<longitude>-104.672996521</longitude>\n";
		s += "\t\t<elevation>5431</elevation>\n";
		s += "\t\t<municipality>Denver</municipality>\n";
		s += "\t\t<region>Colorado</region>\n";
		s += "\t\t<country>United States</country>\n";
		s += "\t\t<continent>North America</continent>\n";
		s += "\t\t<airportURL>http://en.wikipedia.org/wiki/Denver_International_Airport</airportURL>\n";
		s += "\t\t<regionURL>http://en.wikipedia.org/wiki/Colorado</regionURL>\n";
		s += "\t\t<countryURL>http://en.wikipedia.org/wiki/United_States</countryURL>";
		
		location location = new location("Denver International Airport", "KDEN", 
				39.861698150635, -104.672996521);
		location.elevation = 5431;
		location.municipality = "Denver";
		location.region = "Colorado";
		location.country = "United States";
		location.continent = "North America";
		location.airportUrl = "http://en.wikipedia.org/wiki/Denver_International_Airport";
		location.regionUrl = "http://en.wikipedia.org/wiki/Colorado";
		location.countryUrl = "http://en.wikipedia.org/wiki/United_States";
		
		assertEquals(s, view.makeString(location));
		view.finalizeTrip();
	}
	
	@Test
	public void testEmptyMap(){
		xml = new File("temp.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
		//expected file
		String s = "<?xml version=\"1.0\"?>\n";
		s += "<svg width=\"1024\" height=\"512\" xmlns=\"http://www.w3.org/2000/svg\"";
		s += " xmlns:svg=\"http://www.w3.org/2000/svg\">\n";
		/*s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "</g>\n";*/
		s += "<g>\n";
		s += "\t<title>Titles</title>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"state\" y=\"25\" x=\"512\">DTR-18</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"distance\" y=\"500\" x=\"512\">9999 miles</text>\n";
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
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
		//expected file
		String s = "<?xml version=\"1.0\"?>\n";
		s += "<svg width=\"1024\" height=\"512\" xmlns=\"http://www.w3.org/2000/svg\"";
		s += " xmlns:svg=\"http://www.w3.org/2000/svg\">\n";
		/*s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "</g>\n";*/
		s += "<g>\n";
		s += "\t<title>Titles</title>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"state\" y=\"25\" x=\"512\">DTR-18</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"distance\" y=\"500\" x=\"512\">9999 miles</text>\n";
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
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
		//expected file
		String s = "<?xml version=\"1.0\"?>\n";
		s += "<svg width=\"1024\" height=\"512\" xmlns=\"http://www.w3.org/2000/svg\"";
		s += " xmlns:svg=\"http://www.w3.org/2000/svg\">\n";
		/*s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "</g>\n";*/
		s += "<g>\n";
		s += "\t<title>Titles</title>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"state\" y=\"25\" x=\"512\">DTR-18</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"distance\" y=\"500\" x=\"512\">9999 miles</text>\n";
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
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
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
	
	@Test
	public void testRemoveTag() throws IOException{
		xml = new File("temp.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg, false);
		view.initializeTrip(9999, false);
		
		//expected file
		String s = "<?xml version=\"1.0\"?>\n";
		s += "<svg width=\"1024\" height=\"512\" xmlns=\"http://www.w3.org/2000/svg\"";
		s += " xmlns:svg=\"http://www.w3.org/2000/svg\">\n";
		/*s += "<g>\n";
		s += "\t<title>Borders</title>\n";
		s += "\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n";
		s += "</g>\n";*/
		s += "<g>\n";
		s += "\t<title>Titles</title>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"state\" y=\"25\" x=\"512\">DTR-18</text>\n";
		s += "\t<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\"";
		s += " id=\"distance\" y=\"500\" x=\"512\">9999 miles</text>\n";
		s += "</g>\n";
		
		view.finalizeTrip();
		view.removeTag(svg);
		
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
	public void testInitializeSelection() throws FileNotFoundException{
		xml = new File("testEmpty.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg, false);
		view.initializeSelection("this_name_reserved");
		view.finalizeSelection();
		
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		s += "<selection>\n";
		s += "\t<title>this_name_reserved</title>\n";
		s += "\t<filename>testEmpty.svg</filename>\n";
		s += "\t<destinations>\n";
		s += "\t</destinations>\n";
		s += "</selection>\n";
		
		File sub = view.getSelection();
		try {
			Scanner scan = new Scanner(sub);
			String scanned = "";
			while(scan.hasNextLine()) scanned += scan.nextLine() + "\n";
			assertEquals(s,scanned);
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.deleteSelection();
	}
	
	@Test
	public void testAddSelectionID() throws FileNotFoundException{
		xml = new File("testEmpty.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg, false);
		view.initializeSelection("this_name_reserved");
		view.addSelectionID("2");
		view.addSelectionID("Denver");
		view.addSelectionID("Paris");
		view.addSelectionID("Jerusalem");
		view.addSelectionID(Integer.toString(56));
		view.finalizeSelection();
		
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		s += "<selection>\n";
		s += "\t<title>this_name_reserved</title>\n";
		s += "\t<filename>testEmpty.svg</filename>\n";
		s += "\t<destinations>\n";
		s += "\t\t<id>2</id>\n";
		s += "\t\t<id>Denver</id>\n";
		s += "\t\t<id>Paris</id>\n";
		s += "\t\t<id>Jerusalem</id>\n";
		s += "\t\t<id>56</id>\n";
		s += "\t</destinations>\n";
		s += "</selection>\n";
		
		File sub = view.getSelection();
		try {
			Scanner scan = new Scanner(sub);
			String scanned = "";
			while(scan.hasNextLine()) scanned += scan.nextLine() + "\n";
			assertEquals(s,scanned);
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.deleteSelection();
	}
	
	@Test
	public void testFinalizeSelection() throws FileNotFoundException{
		xml = new File("testEmpty.xml");
		svg = new File("testEmpty.svg");
		view = new View(xml,svg, false);
		view.initializeSelection("this_name_reserved");
		view.finalizeSelection();
		
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		s += "<selection>\n";
		s += "\t<title>this_name_reserved</title>\n";
		s += "\t<filename>testEmpty.svg</filename>\n";
		s += "\t<destinations>\n";
		s += "\t</destinations>\n";
		s += "</selection>\n";
		
		File sub = view.getSelection();
		try {
			Scanner scan = new Scanner(sub);
			String scanned = "";
			while(scan.hasNextLine()) scanned += scan.nextLine() + "\n";
			assertEquals(s,scanned);
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.deleteSelection();
	}
	
	@After
	public void cleanUp(){
		xml.delete();
		svg.delete();
	}

}
