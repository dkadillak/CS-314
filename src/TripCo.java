import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Model.Model;
import View.View;
import Presenter.Presenter;

public class TripCo {
	public static void main(String [ ] args) throws FileNotFoundException
	{	
		if(args.length != 0){ //check if there are any arguments at all
			if(!args[0].substring(args[0].length() - 4).equals(".csv")){ //check if the file is type .csv or not
				System.out.println("Error - File: " + "\"" + args[0] + "\"" + " is not of type .csv!");
				System.exit(0);
			}
		}
		else{
			System.out.println("Error - No arguments given!");
			System.exit(0);
		}
		String fileName = args[0].substring(0, args[0].length() - 4); //take ".csv" off the file name
		File input = new File(args[0]);
		File xml = new File(fileName + ".xml"); //make xml file with input file's name
		File svg = new File(fileName + ".svg"); //make svg file with input file's name
		View view = new View(xml, svg, 1000);
		Model model = new Model(input);
		Presenter presenter = new Presenter(view, model);
	}	
}