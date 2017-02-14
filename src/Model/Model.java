package Model;

import java.util.ArrayList;
import java.util.Scanner;

public class Model{

	private ArrayList<Integer> data;

	public Model(int rows){
		data = new ArrayList<Integer>(rows);
	}
	public int getSize(){
		return data.size();
	}


}