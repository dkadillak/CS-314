package main.java.edu.csu2017sp314.dtr18.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class View {
	private PrintWriter itinerary, map, selection;
	private int mapLegCount;
	private int mapLabelCount;
	private String itinContents;
	private GUI gui;
	private String xmlFilename;
	private String svgFilename;
	private File select;
	private String units;

	public View(File xml, File svg, boolean background){
		units = null;
		try {
			if(background){
				removeTag(svg);
				map = new PrintWriter(new FileWriter(svg, true));
			}
			else
				map = new PrintWriter(svg);
			itinerary = new PrintWriter(xml);
			mapLegCount = 0;
			mapLabelCount = 0;
			itinContents = "";
			gui = new GUI();
			xmlFilename = xml.getName();
			svgFilename = svg.getName();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void setUnits(String units){
		if(units.equals("Miles") || units.equals("Kilometers")){
			this.units = units;
		}else{
			System.err.println("Error: view units must be set to either 'Miles' or Kilometers'");
			System.exit(-1);
		}
	}
	
	//initialize XML and SVG
	public void initializeTrip(int totalMiles, boolean background){
		itinerary.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		itinerary.println("<trip>");

		if(!background){
			map.println("<?xml version=\"1.0\"?>");
			map.print("<svg width=\"1024\" height=\"512\" xmlns=\"http://www.w3.org/2000/svg\"");
			map.println(" xmlns:svg=\"http://www.w3.org/2000/svg\">");
		}

		addHeader("Titles");
		map.print("\t<text text-anchor=\"middle\" font-family=\"Sans-serif\"");
		map.println(" font-size=\"24\" id=\"state\" y=\"25\" x=\"512\">DTR-18</text>");
		map.print("\t<text text-anchor=\"middle\" font-family=\"Sans-serif\"");
		map.print(" font-size=\"24\" id=\"distance\" y=\"500\" x=\"512\">");
		map.println(totalMiles + " miles</text>");
		addFooter();

	}
	
	public void initializeSelection(String filename){
		select = new File(filename + ".xml");
		try {
			selection = new PrintWriter(select);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		selection.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		selection.println("<selection>");
		selection.println("\t<title>" + filename + "</title>");
		selection.println("\t<filename>" + svgFilename + "</filename>");
		selection.println("\t<destinations>");
	}
	
	public void addSelectionID(String id){
		selection.println("\t\t<id>" + id + "</id>");
	}
	
	public void finalizeSelection(){
		selection.println("\t</destinations>");
		selection.println("</selection>");
		selection.close();
	}

	//adds a section header to svg. for example, you need to call this with the string "Legs" before you add all the legs,
	//something like "Locations" before you add all the location labels
	//"Distances" before you add all the milages
	public void addHeader(String header){
		map.println("<g>");
		map.println("\t<title>" + header + "</title>");
	}

	//needs to be called after you finish a section. For example once you've added all the legs, call this
	public void addFooter(){
		map.println("</g>");
	}

	//adds a line to the map. Coordinates need to be svg coordinates, not lat/long
	public void addLine(int x1, int y1, int x2, int y2){
		mapLegCount++;
		map.print("\t<line id=\"leg" + mapLegCount + "\" ");
		map.print("y2=\"" + y2 + "\" ");
		map.print("x2=\"" + x2 + "\" ");
		map.print("y1=\"" + y1 + "\" ");
		map.print("x1=\"" + x1 + "\" ");
		map.print("stroke-width=\"3\" ");
		map.println("stroke=\"#999999\"/>");
	}

	//adds a label, such as an id or name of a location. takes coordinates and the label string
	//should also work for adding milages
	//When labeling a point, use the same coordinates you used for that point when making a line
	//When doing a milage, calculate the midpoint of the leg and use that
	public void addLabel(int x, int y, String label){
		mapLabelCount++;
		map.print("\t<text font-family=\"Sans-serif\" font-size=\"16\" ");
		map.print("id=\"id" + mapLabelCount + "\" ");
		map.print("y=\"" + y + "\" ");
		map.print("x=\"" + x + "\"");
		map.println(">" + label + "</text>");
	}

	//add a single leg to the XML itinerary
	public void addLeg(String sequence, String start, String finish, int milage){
		//sequence is which leg of the trip this is. e.g. 1 or 2 or 3
		//start is the name of the first location in the leg
		//finish the the name of the destination of the leg

		itinerary.println("<leg>");
		itinerary.println("\t<sequence>" + sequence + "</sequence>");
		itinerary.println("\t<start>" + start + "</start>");
		itinerary.println("\t<finish>" + finish + "</finish>");
		itinerary.println("\t<milage>" + milage + "</milage>");
		itinerary.println("</leg>");
		itinContents += "Sequence: " + sequence + "\nFrom: " + start + "\nTo: "
		+ finish + "\nMileage: " + Integer.toString(milage) + "\n\n";
	}

	//finalize XML and SVG
	public void finalizeTrip(){
		itinerary.println("</trip>");
		itinerary.close();

		map.println("</svg>");
		map.close();
	}

	//takes (latitude, longitude), returns (y,x) svg coordinates
	//result[0] = y		result[1] = x
	public int[] convertCoords(double lat, double lon){
		lat *= -1;
		lat += 90.0;
		lat *= 2.8287;
		int[] result = new int[2];
		result[0] = Math.round((float)lat);
		lon += 180;
		lon *= 2.8366;
		result[1] = Math.round((float)lon);
		return result;
	}

	public void removeTag(File svg) throws IOException{
		//Taken from Stackoverflow
		File tempFile = new File("newMap.svg");

		BufferedReader reader = new BufferedReader(new FileReader(svg));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = "</svg>";
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if(trimmedLine.equals(lineToRemove)) continue;
			if(trimmedLine.contains(lineToRemove))
				currentLine = trimmedLine.replace(lineToRemove, "");
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
		tempFile.renameTo(svg);
	}
	
	public void displayXML(){
		gui.displayXML(xmlFilename, itinContents);
		itinContents = ""; //clear out string for if they want to run another file
	}
	
	public void displaySVG(){
		gui.displaySVG(svgFilename);
	}

	//just for use by JUnit
	public PrintWriter getMap() {
		return map;
	}

	//just for use by JUnit
	public PrintWriter getItinerary() {
		return itinerary;
	}
	
	//just for use by JUnit
	public File getSelection() {
		return select;
	}
	
	//just for use by junit, with great power comes great responsibility; seriously though don't use this function except for junit
	public void deleteSelection(){
		select.delete();
	}

}
