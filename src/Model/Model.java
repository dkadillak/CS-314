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
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Model{
	
	//class variables
	public ArrayList<location> locations;
	public ArrayList<String> extra;
	public ArrayList<Leg> legs;
	public String Name;
	public int ID;
	private int[][] distances;
	private int lineCount=0, NamePosition, IDPosition, LatitudePosition, LongitudePosition;
	public double Latitude, Longitude;
	Scanner scan;

	//constructor without file parameter for testing purposes
	public Model(){
		locations = new ArrayList<location>();
		extra = new ArrayList<String>();
	}
	
	//regular constructor
	public Model(File file) throws FileNotFoundException{
	//initializing ArrayLists
		locations = new ArrayList<location>();
		extra = new ArrayList<String>();
		legs = new ArrayList<Leg>();
		
		
	//setting up scanner with inputed file
		scan= new Scanner(file);
		
	//setting up scanner to parse individual tokens by comma
		scan.useDelimiter(",");
		
	//lesgo bby
		parselocations();
		distances = new int[getFileSize()][getFileSize()];
		computeDistances();
		nearestNeighbor();
	}
	
	//getters
	public int getFileSize(){
		return locations.size();
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
		String s="Locations-\n";
		for(int i=0;i<locations.size();i++){
			s+=locations.get(i);
		}		
		s+="\nLegs-\n";
		for(int i=0;i<legs.size();i++){
			s+=legs.get(i);
		}	
		
		
		return s;
	}
	
private void parselocations(){

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
	locations.add(new location(Name,ID,Latitude,Longitude,extra));
	extra.clear();
	return;
}

public int circleDistance(double lat1, double lon1, double lat2, double lon2 ){
	//credit to http://www.movable-type.co.uk/scripts/latlong.html for formula
	if((lat1 ==lat2)&&(lon1==lon2)){
		return 0;
	}

	double lat1R = Math.toRadians(lat1);
	double lat2R = Math.toRadians(lat2);
	double latDiff = Math.toRadians(lat2-lat1);
	double longDiff= Math.toRadians(lon2-lon1);
	double R = 3958.756	;
	
	/*
	 R = 6371e3; // metres
	 φ1 = lat1.toRadians();
	 φ2 = lat2.toRadians();
	 Δφ = (lat2-lat1).toRadians();	
	 Δλ = (lon2-lon1).toRadians();

 	 a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
        Math.cos(φ1) * Math.cos(φ2) *
        Math.sin(Δλ/2) * Math.sin(Δλ/2);
     c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

     d = R * c;
*/
	
	double a = Math.sin(latDiff/2) * Math.sin(latDiff/2)+Math.cos(lat1R)*Math.cos(lat2R)*Math.sin(longDiff/2) * Math.sin(longDiff/2);
	double c = 2 * (Math.atan2(Math.sqrt(a), (Math.sqrt(1-a))));

	return (int) (R * c);

}
private void computeDistances(){
	
	for(int y=0;y<getFileSize();y++){
		
		for(int x=0;x<getFileSize();x++){
			distances[y][x]=(circleDistance(locations.get(y).getLatitude(), locations.get(y).getLongitude(), locations.get(x).getLatitude(), locations.get(x).getLongitude()));
		}
	}
}

private void printArray(){
	
	for(int y=0;y<getFileSize();y++){
		for(int x=0;x<getFileSize();x++){
		System.out.print(distances[y][x]+" ");
		}
		System.out.println();
	}
	System.out.println("\n");
}

private void nearestNeighbor(){
		
	int indexOfClosest, index=0,count=0;
	indexOfClosest=smallestOnLine(distances[0]);
	while(count!=(getFileSize())){
		
		
		legs.add(new Leg(locations.get(index).getName(),locations.get(indexOfClosest).getName(),distances[index][indexOfClosest]));
		zerOut(indexOfClosest);
		index = indexOfClosest;
		indexOfClosest=smallestOnLine(distances[index]);
		count++;
	}
}
public int smallestOnLine(int row[]){
	int smallest=0, position=0;
	
	for(int i=0;i<row.length;i++){ 
		if(row[i]!=0){
			smallest = row[i];
			position = i;
			break;
		}
	}
	for(int i=0;i<row.length;i++){
		if((row[i]!=0)&&row[i]<smallest){
			smallest = row[i];
			position = i;
		}
	}
	return position;
}
private void zerOut(int index){
	for(int i=0; i<getFileSize();i++){
		distances[i][index]=0;
	}
}
public double LatLongConverter(String LatLong){
	//for test cases that call method directly
	LatLong=LatLong.toLowerCase();
	
	boolean isNeg = false;
	int removePosition=0;
	double degrees,minutes,seconds;

	if((LatLong.contains((CharSequence)"s"))||(LatLong.contains((CharSequence)"w"))){
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