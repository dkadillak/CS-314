package main.java.edu.csu2017sp314.dtr18.View;

import main.java.edu.csu2017sp314.dtr18.Model.Leg;
import main.java.edu.csu2017sp314.dtr18.Model.location;
import org.apache.batik.swing.JSVGCanvas;
import javax.swing.*;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI{
	
	JSVGCanvas svgCanvas = new JSVGCanvas();
	
	public GUI() {
	}
	
	private String getData(location location){
		String str = "<HTML>" + location.id + "<br>" + location.name + "<br>";
		String s2 = location.latitude + ", " + location.longitude + ", ";
		String s3 = location.elevation + " ft<br>";
    	String s4 = location.municipality + ", " + location.region + ",<br>";
		String s5 = location.country + ", " + location.continent + "<br>";
    	String s6 = location.airportUrl + "<br>" + location.regionUrl;
    	String s7 = "<br>" + location.countryUrl + "</HTML>";
    	return str + s2 + s3 + s4 + s5 + s6 + s7;
	}
	

	public void displayXml(String filename, ArrayList<Leg> legs, String units){
		String[] columns = new String[] {"Leg", "From", "To", "Distance"};
	    Object[][] data = new Object[legs.size()][4];
	    JTable table = new JTable(data, columns);
	    table.setRowHeight(200);
	    TableColumn fromColumn = table.getColumnModel().getColumn(1);
	    fromColumn.setPreferredWidth(450);
	    TableColumn toColumn = table.getColumnModel().getColumn(2);
	    toColumn.setPreferredWidth(450);
	    for(int i = 0; i < data.length; i++){
	    	String from = getData(legs.get(i).getStart());
	    	data[i][0] = i+1;
	    	data[i][1] = from;
	    	String to = getData(legs.get(i).getEnd());
	    	data[i][2] = to;
	    	data[i][3] = legs.get(i).getDistance() + " " + units;
	    }
		JScrollPane scrollPane = new JScrollPane(table);
		String filenameNoExtension = filename.substring(0, filename.length() - 4); 
		JFrame frame = new JFrame(filenameNoExtension + " Itinerary");
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1060, 750));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void displaySVG(String filename){
		String filenameNoExtension = filename.substring(0, filename.length() - 4); //take ".svg" off filename
		JFrame frame = new JFrame(filenameNoExtension + " Map"); //the name displayed at the top of the window
		frame.setSize(1024, 542);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", svgCanvas);
		frame.setVisible(true);
		svgCanvas.setURI(filename);
	}
	
//	public static void main(String [] args){
//		GUI gui = new GUI();
//		ArrayList<location> locations = new ArrayList<location>();
//		ArrayList<Integer> mileages = new ArrayList<Integer>();
//		int m1 = 2;
//		int m2 = 12;
//		int m3 = 22;
//		int m4 = 32;
//		int m5 = 42;
//		int m6 = 52;
//		mileages.add(m1);
//		mileages.add(m2);
//		mileages.add(m3);
//		mileages.add(m4);
//		mileages.add(m5);
//		mileages.add(m6);
//		
//		location l = new location("KDEN");
//		location l2 = new location("AYPY");
//		location l3 = new location("BIKF");
//		location l4 = new location("CYEG");
//		location l5 = new location("CYHZ");
//		location l6 = new location("CYOW");
//		locations.add(l);
//		locations.add(l2);
//		locations.add(l3);
//		locations.add(l4);
//		locations.add(l5);
//		locations.add(l6);
//		gui.displayXml("test.xml", locations, mileages, "miles");
//	}
	
}