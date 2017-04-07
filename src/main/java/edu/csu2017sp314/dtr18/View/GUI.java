package main.java.edu.csu2017sp314.dtr18.View;

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
	
	private String getData(location l){
		String s = "<HTML>" + l.id + "<br>" + l.name + "<br>" + l.latitude + "N, " + l.longitude + "W, " + l.elevation + " ft<br>";
    	String s2 = l.municipality + ", " + l.region + ",<br>" + l.country + ", " + l.continent + "<br>";
    	String s3 = l.airportUrl + "<br>" + l.regionUrl + "<br>" + l.countryUrl + "</HTML>";
    	return s + s2 + s3;
	}
	

	public void displayXML(String filename, ArrayList<location> locations, ArrayList<Integer> mileages, String units){
		String[] columns = new String[] {"Leg", "From", "To", "Distance"};
	    Object[][] data = new Object[locations.size() - 1][4];
	    JTable table = new JTable(data, columns);
	    table.setRowHeight(200);
	    TableColumn fromColumn = table.getColumnModel().getColumn(1);
	    fromColumn.setPreferredWidth(450);
	    TableColumn toColumn = table.getColumnModel().getColumn(2);
	    toColumn.setPreferredWidth(450);
	    for(int i = 0; i < data.length; i++){
	    	String from = getData(locations.get(i));
	    	int j = i;
	    	data[i][0] = ++j;
	    	data[i][1] = from;
	    	String to = getData(locations.get(j++));
	    	data[i][2] = to;
	    	data[i][3] = mileages.get(i) + " " + units;
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
		frame.setSize(1100, 850);

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
	
	public static void main(String [] args){
		GUI gui = new GUI();
		ArrayList<location> locations = new ArrayList<location>();
		ArrayList<Integer> mileages = new ArrayList<Integer>();
		int m1 = 2;
		int m2 = 12;
		int m3 = 22;
		int m4 = 32;
		int m5 = 42;
		int m6 = 52;
		mileages.add(m1);
		mileages.add(m2);
		mileages.add(m3);
		mileages.add(m4);
		mileages.add(m5);
		mileages.add(m6);
		
		location l = new location("KDEN");
		location l2 = new location("AYPY");
		location l3 = new location("BIKF");
		location l4 = new location("CYEG");
		location l5 = new location("CYHZ");
		location l6 = new location("CYOW");
		locations.add(l);
		locations.add(l2);
		locations.add(l3);
		locations.add(l4);
		locations.add(l5);
		locations.add(l6);
		gui.displayXML("test.xml", locations, mileages, "miles");
	}
	
}