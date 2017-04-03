package main.java.edu.csu2017sp314.dtr18.Model;

import java.util.ArrayList;

public class location {
	
	//class variables
	//private String fileType;
	
	private String name; 
	private String id;
	private double latitude;
	private double longitude;
	private int elevation;
	private String municipality;
	private String region;
	private String country;
	private String continent;
	private String airportUrl;
	private String regionUrl;
	private String countryUrl;
	private int index;		//for use with model distance table
	
	//double getters
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	//String getters
	public String getName(){
		return name;
	}

	//int getters
	public String getID(){
		return id;
	}
	
	public int getIndex(){
		return index;
	}
	
	
	@Override
	public String toString(){
	//for some reason other is not getting passed anything
		return "Name: "+getName()+" ID: "+getID()+" Latitude: "+getLatitude()+" Longitude: "+getLongitude() +"\n";		
	}
	
	//generic constructor
	public location(String Name, String ID, double Latitude, double Longitude){
		
		name=Name;
		id=ID;
		latitude=Latitude;
		longitude=Longitude;
		index = -1;
	}
	
	public location(String Name, String ID, double Latitude, double Longitude, int index){
		
		name=Name;
		id=ID;
		latitude=Latitude;
		longitude=Longitude;
		this.index = index;
	}
	
	public location(String id, int index){
		this.id = id;
		this.index = index;
		sqlInfo();
	}
	
	private void sqlInfo(){
		DBquery q = new DBquery();
		q.addColumn("airports.name");
		q.addColumn("latitude");
		q.addColumn("longitude");
		q.addColumn("municipality");
		q.addColumn("regions.name");
		q.addColumn("countries.name");
		q.addColumn("continents.name");
		q.setFrom("airports");
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj instanceof location){
			location l = (location)obj;
			if(id == l.id && latitude == l.latitude && longitude == l.longitude && name.equals(l.name))
				return true;
		}
		return false;
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
