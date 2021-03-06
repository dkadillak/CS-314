package main.java.edu.csu2017sp314.dtr18.Presenter;
import javafx.application.Application;
import main.java.edu.csu2017sp314.dtr18.TripCo;
import main.java.edu.csu2017sp314.dtr18.Model.*;
import main.java.edu.csu2017sp314.dtr18.View.*;

public class Presenter{
	private View view;
	public Model model;
	
	public Presenter() {
		this.model = new Model('k');

	}
	
	public Presenter(Presenter p, View view){
		this.model = p.model;
		
		this.view = view;
	}
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
	}


	public void runGui(){

		AlertBox.launch();
		char units = 'z';
		if(AlertBox.opt_m){
			units = 'm';
		}else if(AlertBox.opt_k){
			units = 'k';
		}else{
			System.err.println("Error: units were neither k or m in Presenter.runGui()");
			System.exit(-1);
		}
		if(!(AlertBox.selectedLocations[0].equals("no subselection"))){
			Model subSelectModel = new Model(AlertBox.selectedLocations,units);
			model = subSelectModel;
			model.computeDistances();
		}else{
			Model subModel = new Model(AlertBox.selectedLocations,units);
			
			model.computeDistances();
		}
		
		if(AlertBox.opt_2){
			model.twoOpt();
		}
		else if(AlertBox.opt_3){
			model.threeOpt();
		}else{
			model.bestNearestNeighbor();
		}
	}
	
	public void makeTrip(boolean opt_i, boolean opt_d, boolean opt_g){
		String units = model.getUnits();
		for(int index = 0; index < model.legs.size(); index++){
			view.addLeg(model.legs.get(index).getStart());
			if(index == model.legs.size() - 1)
			{
				view.addLeg(model.legs.get(index).getEnd());
			}
		}
		view.addHeader("Legs");
		for(int index = 0; index < model.legs.size(); index++){
			int [] first = view.convertCoords(model.legs.get(index).getStart().getLatitude(),model.legs.get(index).getStart().getLongitude());
			int [] second = view.convertCoords(model.legs.get(index).getEnd().getLatitude(),model.legs.get(index).getEnd().getLongitude());
			view.addLine(first[1], first[0], second[1], second[0]);
		}
		view.addFooter();
		if(opt_d){
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
				view.addLabel(point[1], point[0], model.legs.get(index).getStart().getID());
			}
			view.addFooter();
		}

		view.finalizeTrip();
		if(opt_g){
		view.displayXml(model.legs, units);
		view.displaySVG();
		}
		return;	
	}
}
