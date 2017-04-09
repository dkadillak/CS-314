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
	
	public location(){}
	
	public location(int index){
		this.index = index;
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
	
}
