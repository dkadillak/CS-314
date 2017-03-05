package main.java.edu.csu2017sp314.dtr18.Presenter;
import main.java.edu.csu2017sp314.dtr18.Model.*;
import main.java.edu.csu2017sp314.dtr18.View.*;

public class Presenter {
	private View view;
	private Model model;
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	public void makeTrip(boolean opt_m, boolean opt_i, boolean opt_n){
		for(int index = 0; index < model.legs.size(); index++){
			view.addLeg(model.legs.get(index).getStart().getID(), model.legs.get(index).getStart().getName(), model.legs.get(index).getEnd().getName(), model.legs.get(index).getDistance());
		}
		view.addHeader("Legs");
		for(int index = 0; index < model.legs.size(); index++){
			//double lat lon
			int [] first = view.convertCoords(model.legs.get(index).getStart().getLatitude(),model.legs.get(index).getStart().getLongitude());
			int [] second = view.convertCoords(model.legs.get(index).getEnd().getLatitude(),model.legs.get(index).getEnd().getLongitude());
			view.addLine(first[1], first[0], second[1], second[0]);
		}
		view.addFooter();
		if(opt_m){
			view.addHeader("Distances");
			for(int index = 0; index < model.legs.size(); index++){
				int [] first = view.convertCoords(model.legs.get(index).getStart().getLatitude(),model.legs.get(index).getStart().getLongitude());
				int [] second = view.convertCoords(model.legs.get(index).getEnd().getLatitude(),model.legs.get(index).getEnd().getLongitude());
				int midX = (first[1] + second[1]) / 2;
				int midY = (first[0] + second[0]) / 2;
				view.addLabel(midX, midY, Integer.toString(model.legs.get(index).getDistance()));
			}
			view.addFooter();
		}
		if(opt_i){
			view.addHeader("Locations");
			for(int index = 0; index < model.legs.size(); index++){
				int [] point = view.convertCoords(model.legs.get(index).getStart().getLatitude(),model.legs.get(index).getStart().getLongitude());
				//view.addLabel(int x, int y, String label);
				view.addLabel(point[1], point[0], model.legs.get(index).getStart().getID());
			}
			view.addFooter();
		}
		if(opt_n){
			view.addHeader("Locations");
			for(int index = 0; index < model.legs.size(); index++){
				int [] point = view.convertCoords(model.legs.get(index).getStart().getLatitude(),model.legs.get(index).getStart().getLongitude());
				view.addLabel(point[1], point[0], model.legs.get(index).getStart().getName());
			}
			view.addFooter();
		}
		view.finalizeTrip();
	}
}
