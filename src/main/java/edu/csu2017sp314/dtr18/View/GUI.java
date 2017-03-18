package main.java.edu.csu2017sp314.dtr18.View;

//import org.apache.batik.swing.JSVGCanvas;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class GUI {

	//JSVGCanvas svgCanvas = new JSVGCanvas();

	public GUI() {
		//svgCanvas.setURI("file:/c:/files/cs314/rectangles.svg");
	}

	public void displayXML(String filename, String contents){
		JTextArea textArea = new JTextArea();
		textArea.setText(contents);
		JScrollPane scrollPane = new JScrollPane(textArea);
		String filenameNoExtension = filename.substring(0, filename.length() - 4); 
		JFrame frame = new JFrame(filenameNoExtension + " Itinerary");
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(750, 600));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

//	public void displaySVG(String filename){
//		String filenameNoExtension = filename.substring(0, filename.length() - 4); //take ".svg" off filename
//		JFrame frame = new JFrame(filenameNoExtension + " Map"); //the name displayed at the top of the window
//		frame.setSize(1100, 850);
//
//		frame.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent ev) {
//				System.exit(0);
//			}
//		});
//		frame.getContentPane().setLayout(new BorderLayout());
//		frame.getContentPane().add("Center", svgCanvas);
//		frame.setVisible(true);
//		svgCanvas.setURI(filename);
//	}
}