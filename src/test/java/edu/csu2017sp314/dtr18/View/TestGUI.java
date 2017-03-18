package test.java.edu.csu2017sp314.dtr18.View;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import org.junit.After;
import org.junit.Test;

import main.java.edu.csu2017sp314.dtr18.View.GUI;

public class TestGUI {
	GUI gui;
//	File svg;

//	@Test
//	public void testDisplaySVG() throws FileNotFoundException{
//		svg = new File("temp.svg");
//		gui = new GUI();
//		PrintWriter map = new PrintWriter(svg);
//		map.println("<?xml version=\"1.0\"?>\n");
//		map.println("<svg width=\"1280\" height=\"1024\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\">\n");
//		map.println("<g>\n");
//		map.println("\t<title>Borders</title>\n");
//		map.println("\t<line id=\"north\" y2=\"36\" x2=\"1030\" y1=\"36\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n");
//		map.println("\t<line id=\"east\" y2=\"747\" x2=\"1030\" y1=\"36\" x1=\"1028\" stroke-width=\"4\" stroke=\"#666666\"/>\n");
//		map.println("\t<line id=\"south\" y2=\"745\" x2=\"1031\" y1=\"746\" x1=\"35\" stroke-width=\"4\" stroke=\"#666666\"/>\n");
//		map.println("\t<line id=\"west\" y2=\"745\" x2=\"37\" y1=\"35\" x1=\"37\" stroke-width=\"4\" stroke=\"#666666\"/>\n");
//		map.println("</g>\n");
//		map.println("</svg>");
//		map.close();
//		gui.displaySVG("temp.svg");
//	}
	
	@Test
	public void testDisplayXML(){
		gui = new GUI();
		String testFilename = "trip.xml";
		String testContents = "Sequence: 1\nFrom: Denver\nTo: Boulder\nMileage: 45\n\n";
		gui.displayXML(testFilename, testContents);
	}

//	@After
//	public void cleanUp(){
//		svg.delete();
//	}

}
