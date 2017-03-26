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
	private int optCount, count_m, count_i ,count_n ,count_g ,count_2 ,count_3;
	public boolean opt_m, opt_i, opt_n, opt_g, opt_2, opt_3,xml_exists,svg_exists = false;
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
		System.out.println("opt n: "+opt_n+" count: "+count_n);
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
		if(count_n>1){
			System.out.println("Error- duplicate -n options");
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
		//throws an error if "i" and "n" are both given
		if(count_n >= 1 && count_i >= 1){
			System.out.println("Error - Cannot have both " + "\"" + "-n" + "\"" + " and " + "\"" + "-i" + "\"" + " as arguments");
			return false;
		}
		
		//throw error if we get a -g and any other options
		if((count_n >= 1 || count_i >= 1 ||count_n >=1||count_2>=1||count_3>=1)&&count_g>=1){
			System.out.println("Error - Cannot have multiple options with the -g option");
			return false;
		}
		
		//If we get both -2 and -3, just do -3
		if(count_2 >= 1 && count_3 >= 1){
			count_2 = 0;
		}
		
		//set bools based on options
		if(count_m == 1)
			opt_m = true;
		if(count_i == 1)
			opt_i = true;
		if(count_n == 1)
			opt_n = true;
		if(count_g == 1)
			opt_g = true;
		if(count_2 == 1)
			opt_2 = true;
		if(count_3 == 1)
			opt_3 = true;
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
			if(!args[i].matches("[inmg23-]+")){
				System.out.println("Error - " + "\"" + args[i] + "\"" + " is an invalid argument");
				return false;
			}
			//increment the appropriate count
			if(args[i].contains("m"))
				count_m++;
			if(args[i].contains("i"))
				count_i++;
			if(args[i].contains("n"))
				count_n++;
			if(args[i].contains("g"))
				count_g++;
			if(args[i].contains("2"))
				count_2++;
			if(args[i].contains("3"))
				count_3++;
		}
		//check if we have duplicate options
		if(countCheck()==false){
			return false;
		}
		
		return true;
	}
	
	public boolean fileCheck(String[] args){
		if(!args[optCount].endsWith(".csv") || args[optCount].equals(".csv")){
			//check if the file is type .csv or not
			System.out.println("Error - File: " + "\"" + args[optCount] + "\"" + " is not of type .csv!");
			return false;
		}
		
		//take ".csv" off the file name
		String fileName = args[optCount].substring(0, args[optCount].length() - 4); 
		//check for .svg file
		
		if(optCount+1<args.length){
			if(!args[optCount+1].endsWith(".svg") || args[optCount+1].equals(".svg")){
				//check if the file is type .svg or not
				System.out.println("Error - File: " + "\"" + args[optCount+1] + "\"" + " is not of type .svg!");
				return false;
			}
			//include statement to get the user inputted background image args[optCount+1]
			svg_exists=true;
		}
		if(optCount+2<args.length){
			if(!args[optCount+2].endsWith(".xml") || args[optCount+2].equals(".xml")){
				//check if the file is type .xml or not
				System.out.println("Error - File: " + "\"" + args[optCount+2] + "\"" + " is not of type .xml!");
				return false;
			}
			//include statement to get user inputed selection.xml args[optCount+2]
			xml_exists=true;
		}
		
		input = new File(args[optCount]);
		svg = new File(fileName + ".svg");
		xml = new File(fileName + ".xml"); //make xml file with input file's name
		if(svg_exists){
			File map = new File(args[optCount+1]);
		    try {
		    	if(!svg.exists())
		    		Files.copy(map.toPath(), svg.toPath());
		    	else{
		    		svg.delete();
		    		System.out.println("File: " + svg.getName() + " was overwritten.");
		    		Files.copy(map.toPath(), svg.toPath());
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	public void run() throws FileNotFoundException{
		Model model = new Model(input);
		model.computeDistances();
		model.bestNearestNeighbor();
    
  		if(opt_2==true){
			model.twoOpt();
		}
		else if(opt_3==true){
			model.threeOpt();
		}
  		
		
		View view = new View(xml, svg, svg_exists);
		Presenter presenter = new Presenter(view, model, svg.getName());
	
		if(opt_g==true){
  			AlertBox.fileName = svg.getName();
			AlertBox.launch();
			if(AlertBox.opt_2){
				model.twoOpt();
			}
			if(AlertBox.opt_3){
				
				model.threeOpt();
			}
			view.initializeTrip(model.bestTripDistance, svg_exists);
			presenter.makeTrip(AlertBox.opt_m, AlertBox.opt_i,AlertBox.opt_n,opt_g,false,false);
  		}
		else
		view.initializeTrip(model.bestTripDistance, svg_exists);
		presenter.makeTrip(opt_m, opt_i, opt_n, false, false, false);
	}


//END OF TRIPCO CLASS DEFINITION AND START OF MAIN
	public static void main(String [ ] args) throws FileNotFoundException{
		
		/*command line example:
		 
		java TripCo [options] file.csv [map.svg] [selection.xml]
						-i			  background	selection of
						-m				  map		locations in
						-n							  file.csv
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
			if(args[i].matches("(.*)csv")||args[i].matches("(.*)svg")||args[i].matches("(.*)xml")){
				files++;
			}
			else if(args[i].matches("[inmg23-]+")){
				opt++;
			}
		}

			if(files==0){
				System.out.println("Error- no .csv file was included in arguments");
				System.exit(0);
			}
			else if(files>3){
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