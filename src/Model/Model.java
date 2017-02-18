package Model;
//reader
//inputstream reader
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Model{
	
	//class variables
	private ArrayList<location> data;
	public ArrayList<String> extra;
	public String Name;
	public int ID;
	private int lineCount=0, NamePosition, IDPosition, LatitudePosition, LongitudePosition;
	public double Latitude, Longitude;
	Scanner scan;

	//model constructor without file parameter for testing purposes
	public Model(){
		
	}
	public Model(File file) throws FileNotFoundException{
	//initializing ArrayLists
		data = new ArrayList<location>();
		extra = new ArrayList<String>();
		
	//setting up scanner with inputted file
		scan= new Scanner(file);
		
	//setting up scanner to parse individual tokens by comma
		scan.useDelimiter(",");
		
	//lesgo bby
		parseData();
	}
	public int getFileSize(){
		return data.size();
	}
	private void parseData(){
		//setting up count
				int count=0;
		//setting up string 
		String input="";
		//getting information from very first line of file
		if(scan.hasNextLine()){
			firstLineParser(scan.nextLine());
		}
		
	while(scan.hasNext()){
		
		input =scan.next();
		
		//removing all white space from a token
		input=input.replaceAll("\\s", "");
	
		if(count==NamePosition){
			Name=input;
		}
		else if(count==IDPosition){
			ID=Integer.parseInt(input);
		}
		else if(count==LatitudePosition){
			Latitude=LatLongConverter(input.toLowerCase());
		}
		else if(count==LongitudePosition){
			Longitude=LatLongConverter(input.toLowerCase());
		}
		else{
			extra.add(input);
		}
		
		System.out.println(input);
		count++;
		}	
	data.add(new location(Name,ID,Latitude,Longitude,extra));
	extra.clear();
	count=0;
	}

protected void firstLineParser(String firstLine){

	String input="";
	//lower case everything
	firstLine=firstLine.toLowerCase();

	//remove extra spacing if there is any
	firstLine=firstLine.replaceAll("\\s", "");
	
	//setting up scanner for firstLine string and setting delimiter
	Scanner in = new Scanner(firstLine);
	in.useDelimiter(",");
	
	while(in.hasNext()){
		input=in.next();
		
		if(input.equalsIgnoreCase("name")){
			NamePosition=lineCount;
		}
		if(input.equalsIgnoreCase("id")){
			IDPosition=lineCount;
		}
		if(input.equalsIgnoreCase("latitude")){
			LatitudePosition=lineCount;
		}
		if(input.equalsIgnoreCase("longitude")){
			LongitudePosition=lineCount;
		}
		lineCount++;
		System.out.println("INPUT: "+input);
	}
	/*Testing to see if variables were set correctly
	System.out.println("char symbol test "+(char)176);
	System.out.println("lineCount="+lineCount);
	System.out.println("NamePosition="+NamePosition);
	System.out.println("IDPosition="+IDPosition);
	System.out.println("LatitudePosition="+LatitudePosition);
	System.out.println("LongitudePosition="+LongitudePosition);
	*/
}
//still need to implement
public double LatLongConverter(String LatLong){
	//for test cases that call method directly
	LatLong=LatLong.toLowerCase();
	
	//System.out.println("LatLongConverter recieved: "+LatLong);
	
	boolean isNeg = false;
	int removePosition=0;
	double degrees,minutes,seconds;
	if((LatLong.charAt(LatLong.length()-1)=='n')||(LatLong.charAt(LatLong.length()-1)=='w')){
		isNeg = true;
	}
	//System.out.println("result of isNeg: "+isNeg);
	
	//first check if degree symbol exists
	if(LatLong.contains(Character.toString((char) 176))){
	String doub[]=LatLong.split(Character.toString((char) 176));
	//System.out.println("degree parsed is: "+doub[0]);
	degrees = Double.parseDouble(doub[0]);
	
	//then check if minutes symbol exists
	if(LatLong.contains("'")){
	removePosition = LatLong.indexOf((char)176);
	LatLong = LatLong.substring(removePosition+1,LatLong.length()-1);
	String min[]=LatLong.split("'");
	//System.out.println("minute parsed is: "+min[0]);
	minutes = Double.parseDouble(min[0]);
	removePosition = LatLong.indexOf('\'');
	
	
	//finally, if minutes symbol exists then call appropriate converter
	if(LatLong.contains("\"")){
	LatLong = LatLong.substring(removePosition+1,LatLong.length()-1);
	String sec[]=LatLong.split("\"");
	//System.out.println("seconds parsed is: "+sec[0]);
	seconds = Double.parseDouble(sec[0]);
	
	return degMinSecConvert(degrees,minutes,seconds,isNeg);
	}
	//if just minutes and degree symbol exist, call appropriate converter
	return degMinConvert(degrees,minutes,isNeg);
		}
		//if just degrees symbol exists, it's already in decimal degrees
		if(isNeg){
		return (degrees*-1);
		}
		else{
			return degrees;
			}
		}
	//if all else fails return -1;
	return -1;
	
	}


public double degMinSecConvert(double degrees, double minutes, double seconds, boolean isNeg){
	
	degrees+=((minutes/60)+(seconds/3600));
	
	if(isNeg==true){
		degrees=(degrees*-1);
	}
	
	return degrees;
	
}

public double degMinConvert(double degrees, double minutes, boolean isNeg){
	
	degrees+=((minutes/60));
	
	if(isNeg==true){
		degrees=(degrees*-1);
	}
	
	return degrees;
	
}


}