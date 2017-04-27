package main.java.edu.csu2017sp314.dtr18.Model;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

//Innocuous change to force travis to rebuild

public class Model{

	//class variables
	public ArrayList<location> locations;
	//public ArrayList<String> extra;
	public ArrayList<Leg> legs;
	private int[][] distances;
	public boolean[] used;
	public int bestTripDistance;
	private boolean miles;

	//constructor without file parameter for testing purposes
	public Model(char units){
		locations = new ArrayList<location>();
		legs = new ArrayList<Leg>();
		distances = null;
		if(units == 'm'){
			miles = true;
		}else if(units == 'k'){
			miles = false;
		}else{
			System.err.println("Error: units must be either 'm' or 'k'");
			System.exit(-1);
		}
	}

	//regular constructor
	public Model(File file, char units) throws FileNotFoundException{
		locations = new ArrayList<location>();
		legs = new ArrayList<Leg>();
		setUnits(units);
		
	//lesgo bby

		subsetParser(file);
		distances = null;
		used = new boolean[locations.size()];
		for(int i = 0; i < used.length; i++){
			used[i] = false;
		}
	}

	//subset constructor
	public Model(String[] subset, char units){
		locations = new ArrayList<location>();
		legs = new ArrayList<Leg>();
		distances = null;
		if(units == 'm'){
			miles = true;
		}else if(units == 'k'){
			miles = false;
		}else{
			System.err.println("Error: units must be either 'm' or 'k'");
			System.exit(-1);
		}
		fillLocations(subset, true);
		used = new boolean[locations.size()];
		for(int i = 0; i < used.length; i++){
			used[i] = false;
		}
	}

	private void subsetParser(File subset){
		ArrayList<String> temp_ids = new ArrayList<String>();
		try {
			Scanner s = new Scanner(subset);
			while(s.hasNext()){
				String line = s.next().trim();
				if(line.startsWith("<id>")){
					line = line.substring(4, line.length());
					if(line.contains("</id>")){
						String[] result = line.split("</id>");
						line = result[0];
						if(line == null || line.isEmpty()){
							continue;
						}
						line = line.trim();
						temp_ids.add(line);
					}
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}
		String[] ids = new String[temp_ids.size()];
		ids = temp_ids.toArray(ids);
		fillLocations(ids, false);
	}

	private void fillLocations(String[] locs, boolean names){
		DBquery query = new DBquery();
		query.addColumn("airports.name");
		query.addColumn("airports.id");
		query.addColumn("latitude");
		query.addColumn("longitude");
		query.addColumn("elevation_ft");
		query.addColumn("municipality");
		query.addColumn("regions.name");
		query.addColumn("countries.name");
		query.addColumn("continents.name");
		query.addColumn("airports.wikipedia_link");
		query.addColumn("regions.wikipedia_link");
		query.addColumn("countries.wikipedia_link");

		query.setFrom("all");

		String search;
		if(!names){
			search = "airports.id in (";
		}else{
			search = "airports.name in (";
			locs = escapeSingleQuotes(locs);
		}
		for(int i = 0; i < locs.length; i++){
			search += "'" + locs[i] + "',";
		}
		//remove last comma
		search = search.substring(0, search.length()-1);
		search += ")";

		query.setWhere(search);
		parseSqlResult(query.submit());
		query.close();
	}

	public static String[] escapeSingleQuotes(String[] locs){
		for(int i = 0; i < locs.length; i++){
			if(locs[i].contains("'")){
				String[] split = locs[i].split("'");
				locs[i] = split[0];
				for(int j = 1; j < split.length; j++){
					locs[i] = locs[i] + "''" + split[j];
				}
			}
		}

		return locs;
	}

	private void parseSqlResult(ResultSet rs){
		//output is the same order that columns were added to query

		try {
			int i = 0;
			while(rs.next()){
				location loc = new location(i++);
				//fill in location object with data from the database
				loc.name = rs.getString(1);
				loc.id = rs.getString(2);
				loc.latitude = rs.getDouble(3);
				loc.longitude = rs.getDouble(4);
				loc.elevation = rs.getInt(5);
				loc.municipality = rs.getString(6);
				loc.region = rs.getString(7);
				loc.country = rs.getString(8);
				loc.continent = rs.getString(9);
				loc.airportUrl = rs.getString(10);
				loc.regionUrl = rs.getString(11);
				loc.countryUrl = rs.getString(12);

				locations.add(loc);
			}
		} catch (SQLException e) {
			System.err.print("Error: ");
			System.err.println(e.getMessage());
		}
	}

	//getters
	public int getFileSize(){
		return locations.size();
	}
	
	public String getUnits(){
		if(miles){
			return "Miles";
		}else{
			return "Kilometers";
		}
	}
	
	public void setUnits(char units){
		if(units == 'm'){
			miles = true;
		}else if(units == 'k'){
			miles = false;
		}else if(units == 'z'){
			//don't initialize miles yet, because we won't know until we run the gui
		}else{
			System.err.println("Error: units must be either 'm' or 'k'");
			System.exit(-1);
		}
	}
	

	//takes a string that corresponds to a location's name or id, and return that location object
	//if no location is found, return null
	public location getLocation(String in){
		for(int i = 0; i < locations.size(); i++){
			location l = locations.get(i);
			if(l.getName().contentEquals(in) || l.getID().contentEquals(in))
				return l;
		}
		return null;
	}


	private int distance(location l1, location l2){
		return distances[l1.index][l2.index];
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

	public int circleDistance(double lat1, double lon1, double lat2, double lon2 ){
		//credit to http://www.movable-type.co.uk/scripts/latlong.html for formula
		if((lat1 ==lat2)&&(lon1==lon2)){
			return 0;
		}

		double lat1R = Math.toRadians(lat1);
		double lat2R = Math.toRadians(lat2);
		double latDiff = Math.toRadians(lat2-lat1);
		double longDiff= Math.toRadians(lon2-lon1);
		double R;
		if(miles){
			R = 3958.7558657441;
		}else{
			R = 6371;
		}

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
				if(x == y){
					distances[y][x] = -1;
				}else{
					distances[y][x]=(circleDistance(locations.get(y).getLatitude(), locations.get(y).getLongitude(), locations.get(x).getLatitude(), locations.get(x).getLongitude()));
				}
			}
		}
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
		for(int i = 0; i < used.length; i++){
			used[i] = false;
		}
		int indexOfClosest, index=start,count=0;
		
		used[index] = true;
		indexOfClosest = findClosest(index);
		
		trip t = new trip(locations.size());
		while(count!=locations.size() - 1){
			t.addLeg(new Leg(locations.get(index),locations.get(indexOfClosest),distances[index][indexOfClosest]));
			index = indexOfClosest;
			used[index] = true;
			indexOfClosest = findClosest(index);	

			count++;
		}

		//special handling for last case to loop back to start location
		t.addLeg(new Leg(locations.get(index),locations.get(start),distances[index][start]));

		return t;
	}

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

			boolean improved = false;
			do{
				improved = false;
				for(int i = 1; i < route.length-2; i++){
					for(int k = i+1; k < route.length-1; k++){
						//check if new route would be better before making it
						int oldDistance = distance(route[i-1],route[i]);
						oldDistance += distance(route[k],route[k+1]);
						int newDistance = distance(route[i-1],route[k]);
						newDistance += distance(route[k+1],route[i]);
						if(newDistance < oldDistance){
							twoOptSwap(route,i,k);												
							improved = true;
						}					
					}
				}
			}while(improved);
			
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

	private void twoOptSwap(location[] route,int i, int k){
		for(; i < k; i++, k--){
			location temp = route[i];
			route[i] = route[k];
			route[k] = temp;
		}		
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


	public void threeOptSwap(location[] route, int i, int j, int k){
		//total distance of the legs being removed
		int originalDist = distance(route[i],route[i+1]);
		originalDist += distance(route[j],route[j+1]);
		originalDist += distance(route[k],route[k+1]);
		//first 4 are 3opt, last 3 are 2opt		

		//first possible swap
		int newDist = distance(route[i],route[j+1]);
		newDist += distance(route[k],route[i+1]);
		newDist += distance(route[j],route[k+1]);
		if(newDist < originalDist){
			location[] temp = new location[j-i];
			for(int count = i+1, tempCount = 0; count <= j; count++,tempCount++){
				temp[tempCount] = route[count];
			}
			int count2 = j+1;
			int tempCount = 0;
			for(int count = i+1; count <= j; count++){
				route[count] = route[count2];
				route[count2] = temp[tempCount];				
				count2++;
				tempCount++;
			}
			return;
		}

		//second possible swap
		newDist = distance(route[i],route[k]);
		newDist += distance(route[j+1],route[i+1]);
		newDist += distance(route[j],route[k+1]);
		if(newDist < originalDist){
			location[] temp = new location[j-i];
			for(int count = i+1, tempCount = 0; count <= j; count++,tempCount++){
				temp[tempCount] = route[count];
			}
			int count2 = k;
			int tempCount = temp.length-1;
			for(int count = i+1; count <= j; count++){
				route[count] = route[count2];
				route[count2] = temp[tempCount];
				count2--;
				tempCount--;
			}
			return;
		}

		//third possible swap
		newDist = distance(route[i],route[j+1]);
		newDist += distance(route[k],route[j]);
		newDist += distance(route[i+1],route[k+1]);
		if(newDist < originalDist){
			location[] temp = new location[j-i];
			for(int count = i+1, tempCount = 0; count <= j; count++,tempCount++){
				temp[tempCount] = route[count];
			}
			int count2 = j+1;
			int tempCount = temp.length-1;
			for(int count = 0; count <= j; count++){
				route[count] = route[count2];
				route[count2] = temp[tempCount];
				count2++;
				tempCount--;
			}
			return;
		}

		//fourth possible swap
		newDist = distance(route[i],route[j]);
		newDist += distance(route[i+1],route[k]);
		newDist += distance(route[j+1],route[k+1]);
		if(newDist < originalDist){
			location temp;
			int count2 = j;
			for(int count = i+1; count < count2; count++){
				temp = route[count];
				route[count] = route[count2];
				route[count2] = temp;
				count2--;				
			}
			count2 = k;
			for(int count = j+1; count < count2; count++){
				temp = route[count];
				route[count] = route[count2];
				route[count2] = temp;
				count2--;
			}
			return;
		}

		//fifth possible swap (first 2opt, i->i+1 stays the same)
		newDist = distance(route[i],route[i+1]);
		newDist += distance(route[j],route[k]);
		newDist += distance(route[j+1],route[k+1]);
		if(newDist < originalDist){
			
		}

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

	public int findClosest(int index){
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		
		for(int i = 0; i < used.length; i++){
			if(i != index){
				if(distances[index][i] < min && !used[i]){
					min = distances[index][i];
					minIndex = i;
				}
			}
		}
		
		return minIndex;
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