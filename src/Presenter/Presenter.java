package Presenter;
import Model.Model;
import View.View;

public class Presenter {
	private View view;
	private Model model;
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	public void makeTrip(boolean opt_m, boolean opt_i, boolean opt_n){
		for(int index = 0; index < model.legs.size(); index++){
			view.addLeg(model.legs.get(index).getstartLocationID(), model.legs.get(index).getStartLocation(), model.legs.get(index).getendLocation(), model.legs.get(index).getDistance());
		}
		view.addHeader("Legs");
		for(int index = 0; index < model.legs.size(); index++){
			int [] first = view.convertCoords(model.locations.get(model.legs.get(index).getstartLocationID()-1).getLatitude(), model.locations.get(model.legs.get(index).getstartLocationID()-1).getLongitude());
			int [] second = view.convertCoords(model.locations.get(model.legs.get(index).getendLocationID()-1).getLatitude(), model.locations.get(model.legs.get(index).getendLocationID()-1).getLongitude());
			view.addLine(first[1], first[0], second[1], second[0]);
		}
		view.addFooter();
		if(opt_m){
			view.addHeader("Distances");
			for(int index = 0; index < model.legs.size(); index++){
				int [] first = view.convertCoords(model.locations.get(model.legs.get(index).getstartLocationID()-1).getLatitude(), model.locations.get(model.legs.get(index).getstartLocationID()-1).getLongitude());
				int [] second = view.convertCoords(model.locations.get(model.legs.get(index).getendLocationID()-1).getLatitude(), model.locations.get(model.legs.get(index).getendLocationID()-1).getLongitude());
				int midX = (first[1] + second[1]) / 2;
				int midY = (first[0] + second[0]) / 2;
				view.addLabel(midX, midY, Integer.toString(model.legs.get(index).getDistance()));
			}
			view.addFooter();
		}
		if(opt_i){
			view.addHeader("Locations");
			for(int index = 0; index < model.legs.size(); index++){
				int [] point = view.convertCoords(model.locations.get(model.legs.get(index).getstartLocationID()-1).getLatitude(), model.locations.get(model.legs.get(index).getstartLocationID()-1).getLongitude());
				view.addLabel(point[1], point[0], Integer.toString(model.locations.get(model.legs.get(index).getstartLocationID()-1).getID()));
			}
			view.addFooter();
		}
		if(opt_n){
			view.addHeader("Locations");
			for(int index = 0; index < model.legs.size(); index++){
				int [] point = view.convertCoords(model.locations.get(model.legs.get(index).getstartLocationID()-1).getLatitude(), model.locations.get(model.legs.get(index).getstartLocationID()-1).getLongitude());
				view.addLabel(point[1], point[0], model.locations.get(model.legs.get(index).getstartLocationID()-1).getName());
			}
			view.addFooter();
		}
		view.finalizeTrip();
	}
}
