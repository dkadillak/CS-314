import java.util.ArrayList;
import View.View;
import Presenter.Presenter;

public class TripCo {
	public static void main(String [ ] args)
	{
		ArrayList<Integer> id = null;
		ArrayList<String> name = null;
		ArrayList<Double> x = null, y = null;
		View view = new View(id, name, x, y);
		Presenter presenter = new Presenter(view);
	}	
}