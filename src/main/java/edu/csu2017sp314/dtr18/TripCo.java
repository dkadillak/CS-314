package main.java.edu.csu2017sp314.dtr18;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.edu.csu2017sp314.dtr18.Model.*;
import main.java.edu.csu2017sp314.dtr18.View.*;
import main.java.edu.csu2017sp314.dtr18.Presenter.*;

public class TripCo{
	private int optCount;
	private int count_m; 
	private int count_i;
	private int count_d;
	private int count_k;
	private int count_g;
	private int count_2;
	private int count_3;
	public boolean opt_m;
	public boolean opt_i;
	public boolean opt_d;
	public boolean opt_k;
	public boolean opt_g;
	public boolean opt_2;
	public boolean opt_3;
	public boolean xml_exists;
	public boolean svg_exists = false;
	public File input, xml, svg;
	public TripCo(int count){
		optCount=count;
	}
	public void printArgs(String[] args){
		for(int i=0; i<args.length;i++){
			System.out.println(i+" "+args[i]);
		}
	}

	public void printOpt(){
		System.out.println("opt m: "+opt_m+" count: "+count_m);
		System.out.println("opt i: "+opt_i+" count: "+count_i);
		System.out.println("opt n: "+opt_d+" count: "+count_d);
		System.out.println("opt k: "+opt_k+" count: "+count_k);
		System.out.println("opt g: "+opt_g+" count: "+count_g);
		System.out.println("opt 2: "+opt_2+" count: "+count_2);
		System.out.println("opt 3: "+opt_3+" count: "+count_3);
	}
	public int getoptCount(){
		return optCount;
	}
	public boolean countCheck(){
		if(count_m>1){
			System.out.println("Error- duplicate -m options");
			return false;
		}
		if(count_i>1){
			System.out.println("Error- duplicate -i options");
			return false;
		}
		if(count_d>1){
			System.out.println("Error- duplicate -n options");
			return false;
		}
		if(count_k>1){
			System.out.println("Error- duplicate -k options");
			return false;
		}
		if(count_g>1){
			System.out.println("Error- duplicate -g options");
			return false;
		}
		if(count_2>1){
			System.out.println("Error- duplicate -2 options");
			return false;
		}
		if(count_3>1){
			System.out.println("Error- duplicate -3 options");
			return false;
		}
		//throws an error if "m" and "k" are both given
		if(count_m >= 1 && count_k >= 1){
			System.out.println("Error - Cannot have both " + "\"" + "-m" + "\"" + " and "
		+ "\"" + "-k" + "\"" + " as arguments");
			return false;
		}
		
		//throw error if we get a -g and any other options
		if((count_d>= 1||count_i>= 1||count_m>=1||count_k>=1||count_2>=1||count_3>=1)&&count_g>=1){
			System.out.println("Error - Cannot have multiple options with the -g option");
			return false;
		}
		
		//If we get both -2 and -3, just do -3
		if(count_2 >= 1 && count_3 >= 1){
			count_2 = 0;
		}
		
		//must use either -m or -k
		if(count_m == 0 && count_k ==0 && count_g != 1){
			System.err.println("Error: must provide either -m or -k if not using -g");
			return false;
		}
		
		//set bools based on options
		if(count_m == 1){
			opt_m = true;
		}
		if(count_k == 1){
			opt_k = true;
		}
		if(count_i == 1){
			opt_i = true;
		}
		if(count_d == 1){
			opt_d = true;
		}
		if(count_g == 1){
			opt_g = true;
		}
		if(count_2 == 1){
			opt_2 = true;
		}
		if(count_3 == 1){
			opt_3 = true;
		}
		return true;
	}
	public boolean optionCheck(String[] args){
		
		for(int i=0; i<optCount;i++){
			//check if options start with -
			if(!(args[i].indexOf("-") == 0)){
				System.out.println("Error - " + "argument \""+args[i]+"\" must start with " + "\"" + "-" + "\"");
				return false;
			}
			//check that options contain only legal characters
			if(!args[i].matches("[idkmg23-]+")){
				System.out.println("Error - " + "\"" + args[i] + "\"" + " is an invalid argument");
				return false;
			}
			//increment the appropriate count
			if(args[i].contains("m")){
				count_m++;
			}
			if(args[i].contains("k")){
				count_k++;
			}
			if(args[i].contains("i")){
				count_i++;
			}
			if(args[i].contains("d")){
				count_d++;
			}
			if(args[i].contains("g")){
				count_g++;
			}
			if(args[i].contains("2")){
				count_2++;
			}
			if(args[i].contains("3")){
				count_3++;
			}
		}
		//check if we have duplicate options
		if(countCheck()==false){
			return false;
		}
		
		return true;
	}
	
	public boolean fileCheck(String[] args){
		//check for .svg file
		
		if(!args[optCount].endsWith(".svg") || args[optCount].equals(".svg")){
			//check if the file is type .svg or not
			System.err.println("Error - "
					+ "First argument after options must be background map");
			return false;
		}
		//include statement to get the user inputted background image args[optCount]
		svg_exists=true;
			
		if(optCount+1<args.length){
			if(!args[optCount+1].endsWith(".xml")){
				//check if the file is type .xml or not
				System.err.println("Error - Second argument "
						+ "after options must be subselection file");
				return false;
			}
			//include statement to get user inputed selection.xml args[optCount+2]
			xml_exists=true;
			
		} else if(!opt_g){
			System.err.println("Error: Must include selection file if not using -g");
			return false;
		}
		
		
	
		//generate names for output files
		
		input = new File(args[optCount+1]);
		String output = generateOutputName(args[optCount+1]);
		
		svg = new File(output + ".svg");
		xml = new File(output + ".xml"); //make xml file with input file's name
		if(svg_exists){
			File map = new File(args[optCount]);
		    try {
		    	if(!svg.exists())
		    		Files.copy(map.toPath(), svg.toPath());
		    	else{
		    		svg.delete();
		    		System.out.println("File: " + svg.getName() + " was overwritten.");
		    		Files.copy(map.toPath(), svg.toPath());
		    	}
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
		return true;
	}
	
	// generates base name of output without .xml or .svg
	private String generateOutputName(String input){
		//remove.xml
		String output = input.substring(0, input.length()-4);
		
		//add options
		if(opt_i){
			output += "-i";
		}
		if(opt_m){
			output += "-m";
		}
		if(opt_k){
			output += "-k";
		}
		if(opt_d){
			output += "-d";
		}
		if(opt_g){
			output += "-g";
		}
		if(opt_2){
			output += "-2";
		}
		if(opt_3){
			output += "-3";
		}
		
		// add team
		output += "t18";
		
		return output;
	}
	
	public void run() throws FileNotFoundException{
		char units = 'z';
		if(opt_m){
			units = 'm';
		}else if(opt_k){
			units = 'k';
		}else if(!opt_g){
			System.err.println("Error: must provide either -m or -k if not using -g");
			System.err.println("TripCo.run()");
			System.exit(-1);
		}
		
		Model model = new Model(input,units);
		View view = new View(xml, svg, svg_exists);	

		Presenter presenter = new Presenter(view, model, svg.getName(),input.getName());
		
		
		if(opt_g==true){
			presenter.runGui();
			view.initializeTrip(presenter.model.bestTripDistance, svg_exists,presenter.model.getUnits());
			presenter.makeTrip(AlertBox.opt_i,AlertBox.opt_d,opt_g);
  		}else{  			
  			model.computeDistances();
			if(opt_2==true && opt_3 == false){
				model.twoOpt();
			}else if(opt_3==true){
				model.threeOpt();
			}else{
				model.bestNearestNeighbor();
			}
		
			
			view.initializeTrip(model.bestTripDistance, svg_exists, model.getUnits());
			presenter.makeTrip(opt_i, opt_d, false);
  		}
	}


//END OF TRIPCO CLASS DEFINITION AND START OF MAIN
	public static void main(String [ ] args) throws FileNotFoundException{
		
		/*command line example:
		 
		java TripCo [options] [map.svg] [selection.xml]
						-i	  background	selection of
						-m	  	map		    locations in
						-n					  file.csv					  
						-g
						-2
						-3
		*/
		
		if(args.length==0){
			System.out.println("Error - No arguments given!");
			System.exit(0);
		}
		
		int opt=0, files=0;
		for(int i=0;i<args.length;i++){
			//check all options until the expected .csv file
			if(args[i].matches("(.*)svg")||args[i].matches("(.*)xml")){
				files++;
			}
			else if(args[i].matches("[idkmg23-]+")){
				opt++;
			}
		}

			if(files==0){
				System.out.println("Error- "
						+ "no subselection file was included in arguments");
				System.exit(0);
			}
			else if(files>2){
				System.out.println("Error- too many files given");
				System.exit(0);
			}
			
			TripCo tc = new TripCo(opt);
			
			if(tc.optionCheck(args)==false||tc.fileCheck(args)==false){
				System.exit(0);
			}
			tc.run();

	}	
}