package main.java.edu.csu2017sp314.dtr18.View;

import org.apache.batik.swing.*;
import org.apache.batik.swing.svg.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Event.*;

public class GUI {

	JSVGCanvas svgCanvas = new JSVGCanvas();

	public GUI() {
		//svgCanvas.setURI("file:/c:/files/cs314/rectangles.svg");
	}

	public void displaySVG(String filename){
		String filenameNoExtension = filename.substring(0, filename.length() - 4); //take ".svg" off filename
		JFrame frame = new JFrame(filenameNoExtension); //the name displayed at the top of the window
		frame.setSize(1100, 850);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", svgCanvas);
		frame.setVisible(true);
		svgCanvas.setURI(filename);
	}
}