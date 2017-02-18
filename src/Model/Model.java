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
	public ArrayList<location> data;
	public ArrayList<String> extra;
	public String Name;
	public int ID;
	private int lineCount=0, NamePosition, IDPosition, LatitudePosition, LongitudePosition;
	public double Latitude, Longitude;
	Scanner scan;

	//constructor without file parameter for testing purposes
	public Model(){
		data = new ArrayList<location>();
		extra = new ArrayList<String>();
	}
	
	//regular constructor
	public Model(File file) throws FileNotFoundException{
	//initializing ArrayLists
		data = new ArrayList<location>();
		extra = new ArrayList<String>();
		
	//setting up scanner with inputed file
		scan= new Scanner(file);
		
	//setting up scanner to parse individual tokens by comma
		scan.useDelimiter(",");
		
	//lesgo bby
		parseData();
	}
	
	//getters
	public int getFileSize(){
		return data.size();
	}
	public int getNamePosition(){
		return NamePosition;
	}
	public int getLatitudePosition(){
		return LatitudePosition;
	}
	public int getLongitudePosition(){
		return LongitudePosition;
	}
	public int getIDPosition(){
		return IDPosition;
	}
	
	//toString for Model
	public String toString(){
		String s="";
		for(int i=0;i<data.size();i++){
			s+=data.get(i);
		}		
		return s;
	}
	
private void parseData(){

	//setting up string 
		String input="";
		
	//getting information from very first line of file
		if(scan.hasNextLine()){
			firstLineParser(scan.nextLine());
		}
	
	//parsing the rest of the file
	while(scan.hasNextLine()){
		
			input=scan.nextLine();
			input=input.replaceAll("\\s", "");
			lineParser(input);
	
		}
	}

public void firstLineParser(String firstLine){

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
	}
}

public void lineParser(String input){
	
	String s[] = input.split(",");

	
	for(int i=0; i<lineCount;i++){
		if(i==NamePosition){
			Name=s[i];
		}
		else if(i==IDPosition){
			ID=Integer.parseInt(s[i]);
		}
		else if(i==LatitudePosition){
			
			Latitude=LatLongConverter(s[i].toLowerCase());
		}
		else if(i==LongitudePosition){
			
			Longitude=LatLongConverter(s[i].toLowerCase());
		}
		else{
			extra.add(s[i]);
		}
	}
	data.add(new location(Name,ID,Latitude,Longitude,extra));
	extra.clear();
	return;
}

public double LatLongConverter(String LatLong){
	//for test cases that call method directly
	LatLong=LatLong.toLowerCase();
	
	boolean isNeg = false;
	int removePosition=0;
	double degrees,minutes,seconds;

	if((LatLong.contains((CharSequence)"n"))||(LatLong.contains((CharSequence)"w"))){
		isNeg = true;
	}
	
	//first check if degree symbol exists
	if(LatLong.contains(Character.toString((char) 176))){
	String doub[]=LatLong.split(Character.toString((char) 176));
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
		//if just degrees symbol exists, it's already in degrees
		if(isNeg){
		return (degrees*-1);
		}
		else{
			return degrees;
			}
		}
	//Oh sweet it was already in decimal degrees
	return Double.parseDouble(LatLong);
	
	}


private double degMinSecConvert(double degrees, double minutes, double seconds, boolean isNeg){
	
	degrees+=((minutes/60)+(seconds/3600));
	
	if(isNeg==true){
		degrees=(degrees*-1);
	}
	
	return degrees;
	
}

private double degMinConvert(double degrees, double minutes, boolean isNeg){
	
	degrees+=((minutes/60));
	
	if(isNeg==true){
		degrees=(degrees*-1);
	}
	
	return degrees;
	
}


}