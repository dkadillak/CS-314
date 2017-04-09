package main.java.edu.csu2017sp314.dtr18.Presenter;
import javafx.application.Application;
import main.java.edu.csu2017sp314.dtr18.TripCo;
import main.java.edu.csu2017sp314.dtr18.Model.*;
import main.java.edu.csu2017sp314.dtr18.View.*;

public class Presenter{
	private View view;
	public Model model;
	public String fileName;
	public Presenter(View view, Model model, String fileName) {
		this.view = view;
		this.model = model;
		this.fileName = fileName;
		
	}
	
	private String[] createArray(){
		String ret[] = new String[model.locations.size()];
		
		for(int i=0; i<model.locations.size();i++){
			ret[i] = model.locations.get(i).getName();
		}
		
		return ret;
	}
	
//so AlertBox has a String[] called selectedLocations, use this and the model object
//to create the subset model object, will need to update model object in view
//also need to check if selectedLocations is Null (don't make new model obj)
	public void runGui(){
		AlertBox.fileName = fileName;
		AlertBox.locations = createArray();	
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
			model.setUnits(units);
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
			view.addLeg(index+1, model.legs.get(index).getStart(),
					model.legs.get(index).getEnd(),
					model.legs.get(index).getDistance(), units);
		}
		view.addHeader("Legs");
		for(int index = 0; index < model.legs.size(); index++){
			//double lat lon
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
				//view.addLabel(int x, int y, String label);
				view.addLabel(point[1], point[0], model.legs.get(index).getStart().getID());
			}
			view.addFooter();
		}

		view.finalizeTrip();
		if(opt_g){
		view.displayXml(model.legs, "units");
		view.displaySVG();
		}
		return;	
	}
}
