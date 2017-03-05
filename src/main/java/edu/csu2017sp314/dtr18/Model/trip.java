package main.java.edu.csu2017sp314.dtr18.Model;

import java.util.ArrayList;

public class trip {
	private ArrayList<Leg> legs;
	private int totalDistance;
	
	public trip(){
		legs = new ArrayList<Leg>();
		totalDistance = 0;
	}
	
	public void addLeg(Leg leg){
		legs.add(leg);
		totalDistance += leg.getDistance();
	}

	public int getTotalDistance() {
		return totalDistance;
	}
	
	public Leg getLegAt(int index){
		return legs.get(index);
	}
	
	public int size(){
		return legs.size();
	}
}
