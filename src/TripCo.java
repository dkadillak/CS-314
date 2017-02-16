import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Model.Model;
import View.View;
import Presenter.Presenter;

public class TripCo {
	public static void main(String [ ] args) throws FileNotFoundException
	{
		ArrayList<Integer> id = null;
		ArrayList<String> name = null;
		ArrayList<Double> x = null, y = null;
		View view = new View(id, name, x, y);
		int rows = 0;
		File f = new File(args[0]);
		Model model = new Model(f);
		Presenter presenter = new Presenter(view, model);
		
	}	
}