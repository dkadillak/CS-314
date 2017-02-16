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