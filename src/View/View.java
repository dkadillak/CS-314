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
	
	//initialize XML
	private void initializeTrip(){
		itinerary.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		itinerary.println("<trip>");
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