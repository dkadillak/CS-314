package main.java.edu.csu2017sp314.dtr18.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import main.java.edu.csu2017sp314.dtr18.Model.Leg;
import main.java.edu.csu2017sp314.dtr18.Model.location;

public class View {
	private PrintWriter itinerary, map, selection;
	private int mapLegCount;
	private int mapLabelCount;
	private GUI gui;
	private String xmlFilename;
	private String svgFilename;
	private File select;
	private String coordinates;

	public View(File xml, File svg, boolean background){
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
	public View(){
		
	}

	//initialize XML and SVG
	public void initializeTrip(int totalMiles, boolean background, String units){
		itinerary.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		itinerary.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		itinerary.println("\t<Folder>");
		coordinates = "\t\t\t\t<coordinates>\n";

		if(!background){
			map.println("<?xml version=\"1.0\"?>");
			map.print("<svg width=\"1024\" height=\"512\" xmlns=\"http://www.w3.org/2000/svg\"");
			map.println(" xmlns:svg=\"http://www.w3.org/2000/svg\">");
		}

		addHeader("Titles");
		map.print("\t<text text-anchor=\"middle\" font-family=\"Sans-serif\"");
		map.println(" font-size=\"24\" id=\"state\" y=\"25\" x=\"512\">"
				+ svgFilename.substring(0,svgFilename.length()-4) + "</text>");
		map.print("\t<text text-anchor=\"middle\" font-family=\"Sans-serif\"");
		map.print(" font-size=\"24\" id=\"distance\" y=\"500\" x=\"512\">");
		map.println(totalMiles + " " + units + "</text>");
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
		map.print("y1=\"" + y1 + "\" ");
		map.print("x1=\"" + x1 + "\" ");
		
		//check if we need to wrap
		if(Math.abs(x2-x1) > 512.0){
			//wrap around
			int newX2;
			if(x1 < 512){
				newX2 = 0;
			}else{
				newX2 = 1024;
			}
			
			double xDistance = Math.abs((double)newX2 - (double)x1);
			double slope = (double)(y2-y1)/(double)(1024-(x2-x1));
			int roundY = Math.round((float)(slope * xDistance));
			int newY2 = y1 + roundY;
			
			map.print("y2=\"" + newY2 + "\" ");
			map.print("x2=\"" + newX2 + "\" ");
			map.print("stroke-width=\"3\" ");
			map.println("stroke=\"#999999\"/>");
			
			map.print("\t<line id=\"leg" + mapLegCount + " pt2\" ");
			map.print("y1=\"" + newY2 + "\" ");
			if(newX2 == 0){
				newX2 = 1024;
			}else{
				newX2 = 0;
			}
			map.print("x1=\"" + newX2 + "\" ");
			map.print("y2=\"" + y2 + "\" ");
			map.print("x2=\"" + x2 + "\" ");
			map.print("stroke-width=\"3\" ");
			map.println("stroke=\"#999999\"/>");
		}else{
			map.print("y2=\"" + y2 + "\" ");
			map.print("x2=\"" + x2 + "\" ");
			map.print("stroke-width=\"3\" ");
			map.println("stroke=\"#999999\"/>");
		}
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

	public void addLeg(location location){
		itinerary.println("\t\t<Placemark>");
		itinerary.print("\t\t\t<name>");
		itinerary.print(location.name + "-" + location.country);
		itinerary.println("</name>\n\t\t\t<description>");
		itinerary.println("\t\t\t\t" + location.airportUrl);
		itinerary.println("\t\t\t\t" + location.regionUrl);
		itinerary.println("\t\t\t\t" + location.countryUrl);
		itinerary.println("\t\t\t</description>\n\t\t\t<Point>");
		itinerary.print("\t\t\t\t<coordinates>" + location.longitude);
		itinerary.print("," + location.latitude  + ",");
		itinerary.println(location.elevation + "</coordinates>");
		itinerary.println("\t\t\t</Point>\n\t\t</Placemark>");
		coordinates += "\t\t\t\t\t" + location.longitude + ",";
		coordinates += location.latitude + "," + location.elevation + "\n";
	}

	public String makeString(location location){
		String line = "\t\t<id>" + location.id + "</id>\n";
		line += "\t\t<name>" + location.name + "</name>\n";
		line += "\t\t<latitude>" + location.latitude + "</latitude>\n";
		line += "\t\t<longitude>" + location.longitude + "</longitude>\n";
		line += "\t\t<elevation>" + location.elevation + "</elevation>\n";
		line += "\t\t<municipality>" + location.municipality + "</municipality>\n";
		line += "\t\t<region>" + location.region + "</region>\n";
		line += "\t\t<country>" + location.country + "</country>\n";
		line += "\t\t<continent>" + location.continent + "</continent>\n";
		line += "\t\t<airportURL>" + location.airportUrl + "</airportURL>\n";
		line += "\t\t<regionURL>" + location.regionUrl + "</regionURL>\n";
		line += "\t\t<countryURL>" + location.countryUrl + "</countryURL>";
		return line;
	}

	//finalize XML and SVG
	public void finalizeTrip(){
		coordinates += "\t\t\t\t</coordinates>";
		itinerary.println("\t\t<Placemark>\n\t\t\t<LineString>");
		itinerary.println("\t\t\t\t<extrude>1</extrude>");
		itinerary.println("\t\t\t\t<tessellate>1</tessellate>\n" + coordinates);
		itinerary.println("\t\t\t</LineString>\n\t\t\t<Style>");
		itinerary.println("\t\t\t\t<LineStyle>");
		itinerary.println("\t\t\t\t\t<color>ffff0000</color>");
		itinerary.println("\t\t\t\t\t<width>4</width>");
		itinerary.println("\t\t\t\t</LineStyle>\n\t\t\t</Style>");
		itinerary.println("\t\t</Placemark>");
		itinerary.println("\t</Folder>\n</kml>");
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

	public void displayXml(ArrayList<Leg> legs,String units){
		gui.displayXml(xmlFilename, legs, units);
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
