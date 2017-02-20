package Model;

public class Leg {
	
	private String startLocation;
	private String endLocation;
	private int distance;
	
	//getters
	public String getStartLocation(){
		return startLocation;
	}
	
	public String getendLocation(){
		return endLocation;
	}
	public int getDistane(){
		return distance;
	}
	
	//constructor
	public Leg(String Start, String End, int Mileage){
		startLocation=Start;
		endLocation=End;
		distance=Mileage;
	}
	
	public String toString(){
		return startLocation+"to "+endLocation+" Distance: "+distance+"\n";
	}

}
