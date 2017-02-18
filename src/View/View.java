package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class View {
	private PrintWriter itinerary, map;
	
	public View(File xml, File svg){
		try {
			itinerary = new PrintWriter(xml);
			map = new PrintWriter(svg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initializeTrip();
	}
	
	//initialize XML and SVG
	private void initializeTrip(){
		itinerary.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		itinerary.println("<trip>");
		
		map.println("<?xml version=\"1.0\"?>");
		map.println("<svg width=\"1280\" height=\"1024\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\">");
		addHeader("Borders");
	}
	
	//adds a section header to svg. for example, you need to call this with the string "Legs" before you add all the legs,
	//something like "Locations" before you add all the location labels
	//"Distances" before you add all the milages
	public void addHeader(String header){
		map.println("<g>");
		map.println("<title>" + header + "</title>");
	}
	
	//needs to be called after you finish a section. For example once you've added all the legs, call this
	public void addFooter(){
		map.println("</g>");
	}
	
	//adds a line to the map. Coordinates need to be svg coordinates, not lat/long
	public void addLine(int x1, int y1, int x2, int y2){
		
	}
	
	//adds a label, such as an id or name of a location. takes coordinates and the label string
	public void addLabel(int x, int y, String label){
		
	}
	
	//add a single leg to the XML itinerary
	public void addLeg(int sequence, String start, String finish, int milage){
		//sequence is which leg of the trip this is. e.g. 1 or 2 or 3
		//start is the name of the first location in the leg
		//finish the the name of the destination of the leg
		
		itinerary.println("<leg>");
		itinerary.println("\t<sequence>" + sequence + "</sequence>");
		itinerary.println("\t<start>" + start + "</start>");
		itinerary.println("\t<finish>" + finish + "</finish>");
		itinerary.println("\t<milage>" + milage + "</milage>");
		itinerary.println("</leg>");
	}
	
	//finalize XML
	public void finalizeTrip(){
		itinerary.println("</trip>");
		itinerary.close();
	}
	

	//just for use by JUnit
	public PrintWriter getMap() {
		return map;
	}

	//just for use by JUnit
	public PrintWriter getItinerary() {
		return itinerary;
	}
	
}