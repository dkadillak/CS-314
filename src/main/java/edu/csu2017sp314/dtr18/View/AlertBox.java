package main.java.edu.csu2017sp314.dtr18.View;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.io.FileNotFoundException;

import javax.swing.Action;

import org.omg.Messaging.SyncScopeHelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class AlertBox extends Application implements EventHandler<ActionEvent>{
	//Static class variables
	static Button ok,no;
	static boolean result;		
	public static String fileName;
	static ListView subset;
	public static String[] selectedLocations;
	public static String[] locations;
	//class variables
			Button button;
			Stage window;
	
			public static boolean opt_m=true,opt_k=false, opt_i=false, opt_d=false, opt_2=false,opt_3=false;
			@Override
			//setting up the gui
			
			public void start(Stage primaryStage) throws Exception {
				window = primaryStage;
				primaryStage.setOnCloseRequest(e -> {
					boolean answer = AlertBox.display("By closing this window you are running file "+fileName+" with no options.");
					if(answer){
						selectedLocations = new String[1];
						selectedLocations[0] = "no subselection";
					e.consume();
					Platform.exit();
					}
					else{
					e.consume();
					}
				});
				window.setTitle("TripCo");
				button = new Button("submit");
				CheckBox b1 = new CheckBox("Display Distances");
				CheckBox b2 = new CheckBox("Display ID's");
				CheckBox b3 = new CheckBox("Run 2opt");
				CheckBox b4 = new CheckBox("Run 3opt");
				
				ToggleGroup group = new ToggleGroup();
				RadioButton miles = new RadioButton("Miles");
				miles.setToggleGroup(group);
				RadioButton kilometers = new RadioButton("Kilometers");
				kilometers.setToggleGroup(group);
				miles.fire();
				button.setOnAction(e->this.checkBoxes(b1, b2, b3, b4, miles, kilometers));
				subset = new ListView();
				subset.getItems().addAll(locations);
				subset.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				VBox layout = new VBox(5);
				Label l = new Label("Hold Ctrl while selecting to pick a subset of locations");
				layout.getChildren().addAll(b1, b2, b3, b4, miles, kilometers, l,subset,button);
				
				BorderPane bp = new BorderPane();
				bp.setCenter(layout);
				
				Scene scene = new Scene(bp,350,450);
				window.setScene(scene);
				window.show();
			}
			
			//this is the processing code for the gui checkboxes and submit button
			public void checkBoxes(CheckBox b1,CheckBox b2,CheckBox b3,CheckBox b4,RadioButton miles, RadioButton kilometers){
				//save selected locations
				if(!subset.getSelectionModel().isEmpty()){
				ObservableList<String> locations;
				locations = subset.getSelectionModel().getSelectedItems();
				selectedLocations = new String[locations.size()];
				for(int i=0; i<locations.size();i++){
					selectedLocations[i] = locations.get(i);
				}
				}
				else if(subset.getSelectionModel().isEmpty()){
					selectedLocations = new String[1];
					selectedLocations[0] = "no subselection";
				}
				
				//creating string that will display in gui for user confirmation
				String selection = "You are running file "+fileName+" with arguments: ";
				
				//making sure just -3 is used in the case of both -3 and -2
				if(b4.isSelected()&&b3.isSelected()){
					opt_3 = true;
					selection+=" -3";
				}
				
				//if distance option is selected
				if(b1.isSelected()){
					opt_m = true;
					selection+=" -d";
				}
				
				//if id option is selected
				if(b2.isSelected()){
					opt_i = true;
					selection+=" -i";
				}
				
				//if twoOpt is selected
				if(b3.isSelected()){
					opt_2 = true;
					selection+=" -2";
				}
				
				//if threeOpt is selected and twoOpt isn't selected
				if(b4.isSelected()&&!b3.isSelected()){
					opt_3 = true;
					selection+=" -3";
				}
				
				if(miles.isSelected()){
					selection += " -m";
				}else if(kilometers.isSelected()){
					opt_m = false;
					opt_k = true;
					selection += " -k";
				}else{
					System.err.println("Miles/kilometers button error");
					Platform.exit();
					System.exit(-1);
				}
				//no options were selected, add that to user confirmation string
				if(selection.length()==43){
					selection+="NO ARGUMENTS";
				}
				//display confirmation window. If they hit confirm, close gui. Else keep it open
				Boolean answer = AlertBox.display(selection);
				if(answer){
					window.close();
				}
				
			}
			
			//runs the gui. Needs to be static apparently 
			public static void launch(){
				Application.launch();
			}
			
			//action handling method, for testing purposes
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					
				}
	//method to check if the correct booleans get set to true via the gui
	public static void printOpt(){
		System.out.println("opt m: "+opt_m);
		System.out.println("opt k: "+opt_k);
		System.out.println("opt i: "+opt_i);
		System.out.println("opt n: "+opt_d);
		System.out.println("opt 2: "+opt_2);
		System.out.println("opt 3: "+opt_3);
		}
						
	//method to display confirmation window
	public static boolean display(String message){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setWidth(450);
		
		Label label = new Label();
		label.setText(message);
		ok = new Button("confirm");
		ok.setOnAction(e->{
		result =  true;
		e.consume();
		window.close();
		});
		no = new Button("go back");
		no.setOnAction(e->{
		result = false;
		e.consume();
		window.close();
				});
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,ok,no);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return result;
	}

	//method to display error window
	public static void Error(String message){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setWidth(450);
		
		ok = new Button("confirm");
		ok.setOnAction(e->{
		e.consume();
		window.close();
		});
		Label label = new Label();
		label.setText(message);
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,ok);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	public static void main(String [ ] args) throws FileNotFoundException{
		AlertBox.launch();
		AlertBox.printOpt();
	}
}