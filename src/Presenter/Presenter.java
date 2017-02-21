package Presenter;

import java.util.ArrayList;
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
			view.addLeg(model.legs.get(index).getstartLocationID(), model.legs.get(index).getStartLocation(), model.legs.get(index).getendLocation(), model.legs.get(index).getDistane());
			//System.out.println("i: " + index);
		}
		
		view.finalizeTrip();
	}
}
