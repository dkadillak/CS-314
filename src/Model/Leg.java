package Model;

public class Leg {
	
	private String startLocation;
	private String endLocation;
	private int distance, startLocationID, endLocationID;
	
	//getters
	public String getStartLocation(){
		return startLocation;
	}
	
	public String getendLocation(){
		return endLocation;
	}
	public int getDistance(){
		return distance;
	}
	public int getstartLocationID(){
		return startLocationID;
	}
	public int getendLocationID(){
		return endLocationID;
	}
	
	//constructor
	public Leg(String Start, int StartLocationID,  String End, int EndLocationID,int Mileage){
		startLocation=Start;
		endLocation=End;
		distance=Mileage;
		startLocationID=StartLocationID;
		endLocationID= EndLocationID;
	}
	
	public String toString(){
		return startLocation+" to "+endLocation+" Distance: "+distance+"\n";
	}

}
