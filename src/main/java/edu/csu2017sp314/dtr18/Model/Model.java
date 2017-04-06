package main.java.edu.csu2017sp314.dtr18.Model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Innocuous change to force travis to rebuild

public class Model{
	
	//class variables
	public ArrayList<location> locations;
	//public ArrayList<String> extra;
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
		//extra = new ArrayList<String>();
	}
	
	//regular constructor
	public Model(File file) throws FileNotFoundException{
	//initializing ArrayLists
		locations = new ArrayList<location>();
		//extra = new ArrayList<String>();
		legs = new ArrayList<Leg>();
		
		
	//setting up scanner with inputed file
		scan= new Scanner(file);
		
	//setting up scanner to parse individual tokens by comma
		scan.useDelimiter(",");
		
	//lesgo bby
		parselocations();
		distances = null;
		//computeDistances();
		//bestNearestNeighbor();
		scan.close();
	}
	
	public Model(Model m, String[] subset){
		locations = new ArrayList<location>();
		//extra = new ArrayList<String>();
		legs = new ArrayList<Leg>();
		distances = new int[subset.length][subset.length];
		
		//fill in locations
		for(int i = 0; i < subset.length; i++){
			locations.add(m.getLocation(subset[i]));
		}
		
		//fill in distance table with only the locations in the subset
		computeDistances();
	}
	
	public String[] subsetParser(File subset){
		ArrayList<String> temp_ids = new ArrayList<String>();
		try {
			Scanner s = new Scanner(subset);
			while(s.hasNext()){
				String line = s.next();
				if((line.length() >= 4 && line.startsWith("<id>")) && (line.length() >= 5 && line.substring(line.length() - 5, line.length()).equals("</id>"))){
					line = line.substring(0, line.length() - 5);
					line = line.substring(4, line.length());
					temp_ids.add(line);
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] ids = new String[temp_ids.size()];
		ids = temp_ids.toArray(ids);
		return ids;
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
	
	//takes a string that corresponds to a location's name or id, and return that location object
	public location getLocation(String in){
		for(int i = 0; i < locations.size(); i++){
			location l = locations.get(i);
			if(l.getName().contentEquals(in) || l.getID().contentEquals(in))
				return l;
		}
		return null;
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
	@Override
	public String toString(){
		String s="Locations-\n";
		for(int i=0;i<locations.size();i++){
			s+=locations.get(i);
			s+="  ";
		}		
		s+="\nLegs-\n";
		for(int i=0;i<legs.size();i++){
			s+=legs.get(i);
			s+="  ";
		}
		s+="\nDistance: ";
		s+=bestTripDistance;
		s+="\n";
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
		//input=input.replaceAll("\\s", "");
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
			Name=s[i].trim();
		}
		else if(i==IDPosition){
			ID=s[i].trim();
		}
		else if(i==LatitudePosition){
			
			Latitude=LatLongConverter(s[i].toLowerCase().replaceAll("\\s", ""));
		}
		else if(i==LongitudePosition){
			
			Longitude=LatLongConverter(s[i].toLowerCase().replaceAll("\\s", ""));
		}
		/*else{
			extra.add(s[i]);
		}*/
	}
	locations.add(new location(Name,ID,Latitude,Longitude,locations.size()));
	//extra.clear();
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
public void computeDistances(){
	distances = new int[getFileSize()][getFileSize()];
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

public void bestNearestNeighbor(){
	if(distances == null){
		System.err.println("Error: cannot execute bestNearestNeighbor without first computing distances!");
		System.exit(-1);
	}
	int n = getFileSize();
	trip bestTrip = nearestNeighbor(0);
	int count = 1;
	for(int i = 1; count < n; i = (i+1)%n){
		trip temp = nearestNeighbor(i);
		if(temp.getTotalDistance() < bestTrip.getTotalDistance())
			bestTrip = temp;
		count++;
	}
	
	legs.clear();
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

//return value is whether any swaps were made
public void twoOpt(){
	//based on pseudocode from https://en.wikipedia.org/wiki/2-opt
	int start = 0;
	trip best = null;
	while(start < locations.size()){
		trip t = nearestNeighbor(start);
		location[] route = new location[locations.size()+1];
		for(int i = 0; i < t.size(); i++){
			route[i] = t.getLegAt(i).getStart();
		}
		route[route.length-1] = route[0];
		
		for(int i = 1; i < route.length-2; i++){
			for(int k = i+1; k < route.length-1; k++){
				trip t2 = twoOptSwap(route, i, k);
				if(t2.getTotalDistance() < t.getTotalDistance()){
					t = t2;
					i = 1;
					k = 1;
					for(int j = 0; j < t2.size(); j++)
						route[j] = t2.getLegAt(j).getStart();
					route[route.length-1] = route[0];
				}
			}
		}
		
		if(best == null || t.getTotalDistance() < best.getTotalDistance())
			best = t;
		start++;
	}
	
	legs.clear();
	for(int i = 0; i < best.size(); i++){
		legs.add(best.getLegAt(i));
	}
	bestTripDistance = best.getTotalDistance();
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

//3opt cannot optimize a trip with less than 6 different locations! 
public void threeOpt(){	
	int start = 0;
	trip best = null;
	trip t = null;
	while (start < locations.size()) {
		int swapCount;
		t = nearestNeighbor(start);
		
		//generate location array
		location[] route = new location[locations.size() + 1];
		for (int i = 0; i < t.size(); i++) {
			route[i] = t.getLegAt(i).getStart();
		}
		route[route.length - 1] = route[0];
		
		do {
			swapCount = 0;

			//actual optimization
			for (int i = 0; i < route.length - 5; i++) {
				for (int j = i + 2; j < route.length - 3; j++) {
					for (int k = j + 2; k < route.length - 1; k++) {
						location[] newRoute = threeOptSwap(route, i, j, k);
						if (newRoute != null) {
							route = newRoute;
							swapCount++;
							i = j = route.length;
							break;
						}
					}
				}
			}

		} while (swapCount > 0);
		
		t = generateTrip(route);
		if(best == null || t.getTotalDistance() < best.getTotalDistance())
			best = t;
		start++;
	}
	
	legs.clear();
	for(int i = 0; i < best.size(); i++){
		legs.add(best.getLegAt(i));
	}
	bestTripDistance = best.getTotalDistance();
}


public location[] threeOptSwap(location[] route, int i, int j, int k){
	//total distance of the legs being removed
	int originalDist = distance(route[i],route[i+1]);
	originalDist += distance(route[j],route[j+1]);
	originalDist += distance(route[k],route[k+1]);
	//first 4 are 3opt, last 3 are 2opt
	int[] dists = new int[7];
	location[][] swaps = new location[7][route.length]; 
	
	//first possible swap
	int index = 0;
	for(int count = 0; count <= i; count++){
		for(int count2 = 0; count2 < 7; count2++)
			swaps[count2][index] = route[count];
		index++;
	}
	for(int count = j+1; count <= k; count++){
		swaps[0][index] = route[count];
		index++;
	}
	for(int count = i+1; count <= j; count++){
		swaps[0][index] = route[count];
		index++;
	}
	for(int count = k+1; count < route.length; count++){
		for(int count2 = 0; count2 < 7; count2++)
			swaps[count2][index] = route[count];
		index++;
	}
	//get total distance of added legs for later comparison to total distance of removed legs
	dists[0] = distance(route[i],route[j+1]);
	dists[0] += distance(route[k],route[i+1]);
	dists[0] += distance(route[j],route[k+1]);
	
	//second possible swap
	index = i+1;

	for(int count = k; count >= j+1; count--){
		swaps[1][index] = route[count];
		index++;
	}
	for(int count = i+1; count <= j; count++){
		swaps[1][index] = route[count];
		index++;
	}
	dists[1] = distance(route[i],route[k]);
	dists[1] += distance(route[j+1],route[i+1]);
	dists[1] += distance(route[j],route[k+1]);
	
	//third possible swap
	index = i+1;

	for(int count = j+1; count <= k; count++){
		swaps[2][index] = route[count];
		index++;
	}
	for(int count = j; count >= i+1; count--){
		swaps[2][index] = route[count];
		index++;
	}
	dists[2] = distance(route[i],route[j+1]);
	dists[2] += distance(route[k],route[j]);
	dists[2] += distance(route[i+1],route[k+1]);
	
	//fourth possible swap
	index = i+1;

	for(int count = j; count >= i+1; count--){
		swaps[3][index] = route[count];
		index++;
	}
	for(int count = k; count >= j+1; count--){
		swaps[3][index] = route[count];
		index++;
	}
	dists[3] = distance(route[i],route[j]);
	dists[3] += distance(route[i+1],route[k]);
	dists[3] += distance(route[j+1],route[k+1]);
	
	//fifth possible swap (first 2opt, i->i+1 stays the same)
	index = i+1;
	for(;index <= j; index++){
		swaps[4][index] = route[index];
	}
	for(int count = k; count >= j+1; count--){
		swaps[4][index] = route[count];
		index++;
	}
	dists[4] = distance(route[i],route[i+1]);
	dists[4] += distance(route[j],route[k]);
	dists[4] += distance(route[j+1],route[k+1]);
	
	//sixth possible swap (second 2opt, j->j+1 stays the same)
	index = i+1;
	for(int count = k; count >= j+1; count--){
		swaps[5][index] = route[count];
		index++;
	}
	for(int count = j; count >= i+1; count--){
		swaps[5][index] = route[count];
		index++;
	}
	dists[5] = distance(route[i],route[k]);
	dists[5] += distance(route[i+1],route[k+1]);
	dists[5] += distance(route[j],route[j+1]);
	
	//seventh possible swap (third 2opt, k->k+1 stays the same)
	index = i+1;
	for(int count = j; count >= i+1; count--){
		swaps[6][index] = route[count];
		index++;
	}
	for(int count = j+1; count <= k; count++){
		swaps[6][index] = route[count];
		index++;
	}
	dists[6] = distance(route[i],route[j]);
	dists[6] += distance(route[i+1],route[j+1]);
	dists[6] += distance(route[k],route[k+1]);
	
	//select and return the best swap, or null if none of the swaps are better
	int best = 0;
	for(int count = 1; count < 7; count++){
		if(dists[count] < dists[best]) best = count;
	}
	
	if(originalDist <= dists[best]) return null;
	return swaps[best];
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

	if((LatLong.contains("s"))||(LatLong.contains("w"))){
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