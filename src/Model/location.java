package Model;

import java.util.ArrayList;

public class location {
	
	//class variables
	//private String fileType;
	
	private String name; 
	private int id;
	private double latitude, longitude;
	private ArrayList<String> other;
	
	//double getters
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitudee(){
		return longitude;
	}
	//String getters
	public String getName(){
		return name;
	}
	
	//generic constructor
	public location(String Name, int ID, double Latitude, double Longitude, ArrayList<String> Other){
		
		name=Name;
		id=ID;
		latitude=Latitude;
		longitude=Longitude;
		other=Other;
		
	}
	
	/* Unused constructors and variables
	 String quadrangle, countySeat, city;
	 int elevation, estimatedProminence;
	  
	int getters
	public int getId(){
		return id;
	}
	
	public int getElevation(){
		return elevation;
	}
	
	public int getEstimatedProminence(){
		return estimatedProminence;
	}
	
		public String getQuadrangle(){
		return quadrangle;
	}
	
	public String getCountySeat(){
		return countySeat;
	}
	
	public String getCity(){
		return city;
	}
	
	//14ers location constructor
	public location(String Name, int Id, int Elevation, int EstimatedProminence, double Latitude, double Longitude, String Quadrangle){
		fileType = "14ers";
		name = Name;
		quadrangle=Quadrangle;
		id=Id;
		elevation=Elevation;
		estimatedProminence=EstimatedProminence;
		latitude=Latitude;
		longitude=Longitude;
	}
	
	//County Seats constructor
	public location(int Id, String Name, String CountySeat, double Latitude, double Longitude){
		fileType="County Seats";
		id=Id;
		name=Name;
		countySeat=CountySeat;
		latitude=latitude;
		longitude=Longitude;
	}
	
	//ski resorts constructor
	public location(int Id, String Name, double Latitude, double Longitude){
		fileType="Ski Resorts";
		id=Id;
		name=Name;
		latitude=latitude;
		longitude=Longitude;
	}
	
	//breweries constructor
	public location(String Name, String City, double Latitude, double Longitude){
		fileType="Breweries";
		name=Name;
		city=City;
		latitude=latitude;
		longitude=Longitude;
	}
	
	*/
}
