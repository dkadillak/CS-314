package Model;

public class location {
	
	//class variables
	private String fileType;
	
	private String name, quadrangle, countySeat, city;
	private int id, elevation, estimatedProminence;
	private double latitude, longitude;
	
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
	
	public String getQuadrangle(){
		return quadrangle;
	}
	
	public String getCountySeat(){
		return countySeat;
	}
	
	public String getCity(){
		return city;
	}
	
	//int getters
	public int getId(){
		return id;
	}
	
	public int getElevation(){
		return elevation;
	}
	
	public int getEstimatedProminence(){
		return estimatedProminence;
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
	
	
}
