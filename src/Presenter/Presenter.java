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
}
