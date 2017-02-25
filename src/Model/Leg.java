package Model;

public class Leg {
	
	private location start;
	private location end;
	private int distance;
	
	//constructor
	public Leg(location start, location end,int Mileage){
		this.start = start;
		this.end = end;
		distance=Mileage;
	}
	
	public location getStart() {
		return start;
	}

	public location getEnd() {
		return end;
	}

	public int getDistance() {
		return distance;
	}

	public String toString(){
		return start.getName() + end.getName() + distance;
	}

}
