import java.util.ArrayList;
import Model.Model;
import View.View;
import Presenter.Presenter;

public class TripCo {
	public static void main(String [ ] args)
	{
		ArrayList<Integer> id = null;
		ArrayList<String> name = null;
		ArrayList<Double> x = null, y = null;
		View view = new View(id, name, x, y);
		int rows = 0;
		Model model = new Model(rows);
		Presenter presenter = new Presenter(view, model);
	}	
}