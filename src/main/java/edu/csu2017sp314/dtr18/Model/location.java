package main.java.edu.csu2017sp314.dtr18.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class location {
	
	//class variables
	//private String fileType;
	
	public String name; 
	public String id;
	public double latitude;
	public double longitude;
	public int elevation;
	public String municipality;
	public String region;
	public String country;
	public String continent;
	public String airportUrl;
	public String regionUrl;
	public String countryUrl;
	public int index;		//for use with model distance table
	
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
		String out = "Name: " + getName();
		out += " ID: " + getID();
		out += " latitude: " + getLatitude();
		out += " longitude: " + getLongitude();
		return out;		
	}
	
	//generic constructor
	public location(String name, String id, double latitude, double longitude){
		this.name = name;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		index = -1;
	}
	
	public location(String name, String id, double latitude, double longitude, int index){
		this.name = name;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.index = index;
	}
	
	public location(String id, int index){
		this.id = id;
		this.index = index;
		sqlInfo();
	}
	
	public location(String id){
		this.id = id;
		index = -1;
		sqlInfo();
	}
	
	private void sqlInfo(){
		DBquery query = new DBquery();
		query.addColumn("airports.name");
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
		
		String search = "airports.id = '" + id + "'";
		query.setWhere(search);
		parseSqlResult(query.submit());
		query.close();
	}
	
	private void parseSqlResult(ResultSet rs){
		//output is the same order that columns were added to query
		
		//output should only have a single row
		try {
			rs.next();
			//fill in location object with data from the database
			name = rs.getString(1);
			//System.out.println(name);
			latitude = rs.getDouble(2);
			//System.out.println(latitude);
			longitude = rs.getDouble(3);
			//System.out.println(longitude);
			elevation = rs.getInt(4);
			//System.out.println(elevation);
			municipality = rs.getString(5);
			//System.out.println(municipality);
			region = rs.getString(6);
			//System.out.println(region);
			country = rs.getString(7);
			//System.out.println(country);
			continent = rs.getString(8);
			//System.out.println(continent);
			airportUrl = rs.getString(9);
			//System.out.println(airportUrl);
			regionUrl = rs.getString(10);
			//System.out.println(regionUrl);
			countryUrl = rs.getString(11);
			//System.out.println(countryUrl);
		} catch (SQLException e) {
			System.err.print("Error: ");
			System.err.println(e.getMessage());
		}
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
