package Model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Model{
	
	//class variables
	public ArrayList<location> locations;
	public ArrayList<String> extra;
	public ArrayList<Leg> legs;
	public String Name;
	public String ID;
	private int[][] distances;
	private int lineCount=0, NamePosition, IDPosition, LatitudePosition, LongitudePosition;
	public double Latitude, Longitude;
	Scanner scan;
	public int bestTripDistance;

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
		bestNearestNeighbor();
		scan.close();
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
	
	private int distance(location l1, location l2){
		return distances[locationIndex(l1)][locationIndex(l2)];
	}
	
	private int locationIndex(location l){
		for(int i = 0; i < locations.size(); i++){
			if(locations.get(i).equals(l))
				return i;
		}
		return -1;
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
		if(input.equals("")) continue;
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
	in.close();
}

public void lineParser(String input){
	
	String s[] = input.split(",");

	
	for(int i=0; i<lineCount;i++){
		if(i==NamePosition){
			Name=s[i];
		}
		else if(i==IDPosition){
			ID=s[i];
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
	double R = 3958.7558657441	;
	
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

	return Math.round((float)(R * c));

}
private void computeDistances(){
	
	for(int y=0;y<getFileSize();y++){
		
		for(int x=0;x<getFileSize();x++){
			distances[y][x]=(circleDistance(locations.get(y).getLatitude(), locations.get(y).getLongitude(), locations.get(x).getLatitude(), locations.get(x).getLongitude()));
		}
	}
}

@SuppressWarnings("unused")
private void printArray(){
	
	for(int y=0;y<getFileSize();y++){
		for(int x=0;x<getFileSize();x++){
		System.out.print(distances[y][x]+" ");
		}
		System.out.println();
	}
	System.out.println("\n");
}

private void bestNearestNeighbor(){
	int n = getFileSize();
	trip bestTrip = nearestNeighbor(0);
	int count = 1;
	for(int i = 1; count < n; i = (i+1)%n){
		trip temp = nearestNeighbor(i);
		if(temp.getTotalDistance() < bestTrip.getTotalDistance())
			bestTrip = temp;
		count++;
	}
	
	for(int i = 0; i < bestTrip.size(); i++){
		legs.add(bestTrip.getLegAt(i));
	}
	bestTripDistance = bestTrip.getTotalDistance();
}

//start is the index of the location you want to be the start point of the trip
private trip nearestNeighbor(int start){
	int distancesCopy[][] = new int[distances.length][distances[0].length];
	for(int i=0;i<distances.length;i++){
		for(int k=0; k<distances[0].length;k++){
			distancesCopy[i][k] = distances[i][k];
		}
	}
	int indexOfClosest, index=start,count=0;
	indexOfClosest=smallestOnLine(distances[start]);
	//printArray();
	zerOut(index);
	//printArray();
	trip t = new trip();
	while(count!=(getFileSize()) - 1){
		t.addLeg(new Leg(locations.get(index),locations.get(indexOfClosest),distancesCopy[index][indexOfClosest]));
		zerOut(indexOfClosest);
		index = indexOfClosest;
		indexOfClosest=smallestOnLine(distances[index]);	
		
		count++;
	}
	
	//special handling for last case to loop back to start location
	t.addLeg(new Leg(locations.get(index),locations.get(start),distancesCopy[index][start]));
	
	distances = distancesCopy.clone();
	return t;
}

public void twoOpt(){
	//based on pseudocode from https://en.wikipedia.org/wiki/2-opt
	//create list of locations in order of the current best trip
	location[] route = new location[legs.size()+1];
	for(int i = 0; i < legs.size(); i++){
		route[i] = legs.get(i).getStart();
	}
	route[route.length-1] = route[0];
	
	for(int i = 1; i < route.length-2; i++){
		for(int k = i+1; k < route.length-1; k++){
			trip t = twoOptSwap(route, i, k);
			if(t.getTotalDistance() < bestTripDistance){
				//update legs and bestTripDistance to new values
				//and the location array!
				bestTripDistance = t.getTotalDistance();
				legs.clear();
				for(int j = 0; j < t.size(); j++){
					legs.add(t.getLegAt(j));
				}
				for(int j = 0; j < legs.size(); j++){
					route[j] = legs.get(j).getStart();
				}
				route[route.length-1] = route[0];
				twoOpt();
				return;
			}
		}
	}
}

private trip twoOptSwap(location[] route,int l1, int l2){
	//based on pseudocode from https://en.wikipedia.org/wiki/2-opt
	//generate new location order
	location[] newRoute = new location[route.length];
	for(int i = 0; i < l1; i++){
		newRoute[i] = route[i];
	}
	
	int k = l1;
	for(int i = l2; i >= l1; i--){
		newRoute[k] = route[i];
		k++;
	}
	
	for(int i = l2+1; i < route.length; i++){
		newRoute[i] = route[i];
	}
	
	return generateTrip(newRoute);
}

//takes an array of locations and makes a trip out of them in the same order as the array
private trip generateTrip(location[] route){
	trip t = new trip();
	for(int i = 0; i < route.length-1; i++){
		t.addLeg(new Leg(route[i],route[i+1],distance(route[i],route[i+1])));
	}
	
	return t;
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
		if(((row[i]!=0)&&(row[i]<smallest))){
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