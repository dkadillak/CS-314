package main.java.edu.csu2017sp314.dtr18;

import java.io.File;
import java.io.FileNotFoundException;
import main.java.edu.csu2017sp314.dtr18.Model.*;
import main.java.edu.csu2017sp314.dtr18.View.*;
import main.java.edu.csu2017sp314.dtr18.Presenter.*;

public class TripCo {
	public static void main(String [ ] args) throws FileNotFoundException
	{	
		if(args.length != 0){ //check if there are any arguments at all
			if(!args[0].endsWith(".csv") || args[0].equals(".csv")){ //check if the file is type .csv or not
				System.out.println("Error - File: " + "\"" + args[0] + "\"" + " is not of type .csv!");
				System.exit(0);
			}
		}
		else{
			System.out.println("Error - No arguments given!");
			System.exit(0);
		}
		//these variables used for argument parsing
		int count_m = 0;
		int count_i = 0;
		int count_n = 0;
		//iterate through each argument after args[0] (the file name)
		for(int i = 1; i < args.length; i++){
			//make sure the each argument starts with "-"
			if(!(args[i].indexOf("-") == 0)){
				System.out.println("Error - " + "arguments must start with " + "\"" + "-" + "\"");
				System.exit(0);
			}
			//checks for any characters other than "i, n, m, or -"
			if(!args[i].matches("[inm-]+")){
				System.out.println("Error - " + "\"" + args[i] + "\"" + " is an invalid argument");
				System.exit(0);
			}
			if(args[i].contains("m"))
				count_m++;
			if(args[i].contains("i"))
				count_i++;
			if(args[i].contains("n"))
				count_n++;
			//checks for duplicates of "i, n, and m"
			if(count_m > 1 || count_i > 1 || count_n > 1){
				System.out.println("Error - " + "\"" + args[i] + "\"" + " is an invalid argument");
				System.exit(0);
			}
			//throws an error if "i" and "n" are both given
			if(count_n >= 1 && count_i >= 1){
				System.out.println("Error - Cannot have both " + "\"" + "n" + "\"" + " and " + "\"" + "i" + "\"" + " as arguments");
				System.exit(0);
			}
		}
		//this line used to test command line parsing
		//System.out.println("m:" + count_m + " i:" + count_i + " n:" + count_n);
		boolean opt_m = false; boolean opt_i = false; boolean opt_n = false;
		if(count_m == 1)
			opt_m = true;
		if(count_i == 1)
			opt_i = true;
		if(count_n == 1)
			opt_n = true;
		//System.out.println("m:" + m + " i:" + i + " n:" + n);
		String fileName = args[0].substring(0, args[0].length() - 4); //take ".csv" off the file name
		File input = new File(args[0]);
		File xml = new File(fileName + ".xml"); //make xml file with input file's name
		File svg = new File(fileName + ".svg"); //make svg file with input file's name
		Model model = new Model(input);
		int totalMiles = 0;
		for(int i = 0; i < model.legs.size(); i++){
			totalMiles += model.legs.get(i).getDistance();
		}
		View view = new View(xml, svg, totalMiles);
		Presenter presenter = new Presenter(view, model);
		presenter.makeTrip(opt_m, opt_i, opt_n);
	}	
}