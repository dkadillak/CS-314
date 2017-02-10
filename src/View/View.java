package View;

import java.util.ArrayList;

public class View {
	private ArrayList<Integer> id;
	private ArrayList<String> name;
	private ArrayList<Double> x, y;
	
	public View(ArrayList<Integer> ID, ArrayList<String> Name, ArrayList<Double> X, ArrayList<Double> Y){
		id = ID;
		name = Name;
		x = X;
		y = Y;
	}
}
