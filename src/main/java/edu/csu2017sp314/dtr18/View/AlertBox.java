//alertBox
package main.java.edu.csu2017sp314.dtr18.View;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Action;

import org.omg.Messaging.SyncScopeHelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.edu.csu2017sp314.dtr18.Model.DBquery;
import main.java.edu.csu2017sp314.dtr18.Model.Model;




public class AlertBox extends Application implements EventHandler<ActionEvent>{
	//Static class variables
	public DBquery db;
	public ListView subset;
	public ListView chosenSubset;
	static Button ok;
	static Button no;
	
	public static boolean result;
	public static boolean xmlSave=false;
	public static boolean runXml=false;	
	
	public static String xmlName;
	public static String outputFileName="";
	public Label searchLabel = new Label("Welcome to TripCo");
	ChoiceBox<String> choiceBox;
	public static String[] selectedLocations;
	public static String[] locations;
	
	public static ArrayList<String> directoryXMLs;
	public static boolean opt_m=true;
	public static boolean opt_k=false;
	public static boolean opt_i=false;
	public static boolean opt_d=false;
	public static boolean opt_2=false;
	public static boolean opt_3=false;
	
	//class variables
	Stage window;
	
			@Override
			//setting up the gui
			
			public void start(Stage primaryStage) throws Exception {
				window = primaryStage;
				primaryStage.setOnCloseRequest(e -> {
					boolean answer = AlertBox.display("Confirm exit");
					if(answer){
					e.consume();
					Platform.exit();
					System.exit(0);
					}
					else{
					e.consume();
					}
				});
				//creating label at top of gui
				window.setTitle("TripCo");
				GridPane grid = new GridPane();
				grid.setPadding(new javafx.geometry.Insets(5,5,5,5));
				grid.setVgap(5);
				grid.setHgap(2);
				
				
				/*CREATING ALL GUI VARIABLES*/
				
				//button creation
				Button submit = new Button("Create Trip");
				Button load = new Button("Move to Selected");
				Button save = new Button("save");
				Button clear = new Button("clear");
				Button remove = new Button("remove");
				Button choice = new Button("Load Subselection");
				
				//listView is the object for displaying locations
				subset = new ListView();
				chosenSubset = new ListView();
				
				//checkbox creation
				CheckBox b1 = new CheckBox("Display Distances");
				CheckBox b2 = new CheckBox("Display ID's");
				CheckBox b3 = new CheckBox("Run 2opt");
				CheckBox b4 = new CheckBox("Run 3opt");
				
				//Toggle Button
				ToggleGroup group = new ToggleGroup();
				RadioButton miles = new RadioButton("Miles");
				miles.setToggleGroup(group);
				RadioButton kilometers = new RadioButton("Kilometers");
				kilometers.setToggleGroup(group);
				miles.fire();
				
				//setting actions for submit, save, clear, load, remove, and choice buttons
				submit.setOnAction(e->this.checkBoxes(b1, b2, b3, b4,chosenSubset,miles, kilometers));
				save.setOnAction(e->getFileName());
				load.setOnAction(e->this.loadSelection(subset, chosenSubset));
				clear.setOnAction(e->this.clearSelection(chosenSubset));
				remove.setOnAction(e->removeLocation(chosenSubset));
				choice.setOnAction(e->{
					try {
						loadSubselection(choiceBox.getValue());
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				});
	
				//filling list to choose from with locations, allowing multiple selections to be a thing
				//for choosing the subset and removing from the chosen subset
				subset.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				chosenSubset.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				Label l = new Label("Hold Ctrl while selecting to pick a subset of locations");
				
				//call method which will populate dropdown for loading XML files
				populateXMLDropDown();
			
				
				//HBox for radiobuttons and subselection loading dropdown
				HBox buttons = new HBox();
				VBox sub = new VBox();
				sub.getChildren().addAll(choiceBox,choice);
				buttons.setSpacing(10);
				buttons.getChildren().addAll(kilometers,miles,sub);
				
				Label typeLabel = new Label("Search Airport");
				//setting up search bar
				
				
				
				TextField airport = new TextField();
				airport.setPromptText("search");
			
				Button  search = new Button("search");
				search.setOnAction(e->{
					try {
						dbSearch(airport.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				VBox searchBar = new VBox();
				searchBar.getChildren().addAll(typeLabel,airport,search);
				searchBar.setSpacing(2);
				//end search bar creation
				
				VBox clearSave = new VBox();
				clearSave.getChildren().addAll(save,remove,clear);
			

				Label l2 = new Label("                  Selected Locations");
				//setting GridPane constraints
				GridPane.setConstraints(b1,0,0);
				GridPane.setConstraints(b2,1,0);
				GridPane.setConstraints(b3,0,1);
				GridPane.setConstraints(b4,1,1);
				GridPane.setConstraints(buttons,3,0);
				GridPane.setConstraints(l,1,5);
				GridPane.setConstraints(l2,3,8);
				GridPane.setConstraints(subset,1,6);
				GridPane.setConstraints(chosenSubset,3,6);
				GridPane.setConstraints(searchBar,0,6);
				GridPane.setConstraints(clearSave,4,6);
				GridPane.setConstraints(searchLabel,1,9);
				GridPane.setConstraints(load,1,10);
				GridPane.setConstraints(submit,3,10);
				
				grid.getChildren().addAll(b1,b2,b3,b4,l,l2,buttons,searchLabel,subset,chosenSubset,searchBar,clearSave,load,submit);
				
				
				Scene scene = new Scene(grid,1000,600);
				window.setScene(scene);
				window.show();
			}
			
			private void populateXMLDropDown(){
				choiceBox = new ChoiceBox<>();
				
				for(int i=0;i<directoryXMLs.size();i++){
					choiceBox.getItems().add(directoryXMLs.get(i));
				}
				/*
				choiceBox.getItems().add(xmlNameGiven);
				*/
				choiceBox.getItems().add("None");
				choiceBox.setValue("None");
				
			}
			
			private void removeLocation(ListView chosenSubset) {
				ObservableList chosen = chosenSubset.getSelectionModel().getSelectedItems();
				chosenSubset.getItems().removeAll(chosen);
			}
			
			public void loadSubselection(String value) throws FileNotFoundException {
				// TODO Auto-generated method stub
				if(!value.equals("None")){
				Model m = new Model(new File(value),'m');
				subset.getItems().clear();
				
				for(int i=0; i<m.locations.size();i++){
					subset.getItems().add(m.locations.get(i).name+" ~ "+m.locations.get(i).country+" ~ "+m.locations.get(i).municipality+" ~ "+m.locations.get(i).id);
				}
				
			}
				else{
					subset.getItems().clear();
				}
		}
			public void loadSelection(ListView subset, ListView chosenSubset){
				chosenSubset.getItems().addAll(checkDuplicates(subset.getSelectionModel().getSelectedItems(),chosenSubset.getItems()));		
			}
			
			public ObservableList checkDuplicates(ObservableList subset, ObservableList chosenSubset){
				ObservableList<String> l = FXCollections.observableArrayList();
				for(int i=0;i<subset.size();i++){
					if(!chosenSubset.contains(subset.get(i))){
						l.add((String)subset.get(i));
					}
				}
			return l;
			}
	
			public void clearSelection(ListView chosenSubset){
				chosenSubset.getItems().clear();
			}
			//this is the processing code for the gui checkboxes and submit button
			public void checkBoxes(CheckBox b1,CheckBox b2,CheckBox b3,CheckBox b4, ListView subset, RadioButton miles, RadioButton kilometers){
				String outputFile = "";
				if(choiceBox.getValue()!="None"){
					runXml = true;
					outputFile +=choiceBox.getValue().substring(0,choiceBox.getValue().length()-4);
				}
				else if(choiceBox.getValue()=="None"){
					outputFile +="tripCo";
				}
				
				if(chosenSubset.getItems().isEmpty()){
					AlertBox.Error("Error- You must pick a subset of locations before running");
					
				}
				else{
				//save selected locations
				if(!chosenSubset.getItems().isEmpty()){
				parseID();
				}
				if(chosenSubset.getItems().isEmpty()){
					selectedLocations = new String[1];
					selectedLocations[0] = "no subselection";
				}
				
				//creating string that will display in gui for user confirmation
				String selection = "You are running with arguments: ";
				
				
				//making sure just -3 is used in the case of both -3 and -2
				if(b4.isSelected()&&b3.isSelected()){
					opt_3 = true;
					selection+=" -3";
					outputFile +="-3";
				}
				
				//if distance option is selected
				if(b1.isSelected()){
					opt_d = true;
					selection+=" -d";
					outputFile +="-d";
				}
				
				//if id option is selected
				if(b2.isSelected()){
					opt_i = true;
					selection+=" -i";
					outputFile +="-i";
				}
				
				//if twoOpt is selected
				if(b3.isSelected()){
					opt_2 = true;
					selection+=" -2";
					outputFile +="-2";
				}
				
				//if threeOpt is selected and twoOpt isn't selected
				if(b4.isSelected()&&!b3.isSelected()){
					opt_3 = true;
					selection+=" -3";
					outputFile +="-3";
				}
				
				if(miles.isSelected()){
					selection += " -m";
					outputFile +="-m";
					opt_m = true;
					opt_k = false;
				}else if(kilometers.isSelected()){
					opt_m = false;
					opt_k = true;
					selection += " -k";
					outputFile +="-k";
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
					
					outputFile+="t18";
					//this should handle cases where user does go back and we need to reset outPutFileName
					outputFileName = outputFile;
					window.close();
					}
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
	public void dbSearch(String airport) throws SQLException{
	db = new DBquery();
	db.addColumn("airports.name");
	db.addColumn("airports.id");
	db.addColumn("municipality");
	db.addColumn("countries.name");
	String tables = "airports";
	tables += " countries";
	
	db.setFrom(tables);
	db.setWhere("airports.name LIKE '%" + airport + "%'");
	db.setWhere("airports.id LIKE '%" + airport + "%'");
	db.setWhere("municipality LIKE '%" + airport + "%'");
	db.setWhere("countries.name LIKE '%" + airport + "%'");
	ResultSet rs = db.submit();
	int count = 0;
	subset.getItems().clear();
	while(rs.next()){
		if(count==200){
		break;
		}
		subset.getItems().add(rs.getString("airports.name")+" ~ "+rs.getString("countries.name")+" ~ "+rs.getString("municipality")+" ~ "+rs.getString("airports.id"));
		count++;
	}
	db.clear();
	db.close();
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
	
	public void parseID(){
		selectedLocations = new String[chosenSubset.getItems().size()];
		
		
		String[] split;
		for(int i=0;i<chosenSubset.getItems().size();i++){
			split =  chosenSubset.getItems().get(i).toString().split("~");
			selectedLocations[i] =split[split.length-1].trim();
		}
		
		
	
	}
	
	
	//method to get name from user after they hit 'save'			
	public  void getFileName(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setWidth(550);
		window.setHeight(110);
		
		Label l = new Label("Please enter a name for this subselection: ");
		TextField t = new TextField();
		t.setPromptText("name");

		HBox h = new HBox();
		
		
		Button ok = new Button("confirm");
		ok.setOnAction(e->{
		xmlName = t.getText();
		String message = "Your subselection file name is: ";
		if(display((message+=xmlName+".xml"))==true){
		choiceBox.getItems().add(xmlName+".xml");
		e.consume();
		saveFile();
		choiceBox.setValue(xmlName+".xml");
		window.close();
		}
		});
		
		h.getChildren().addAll(l,t, ok);
		h.setSpacing(10);
		h.setAlignment(Pos.CENTER);
		Scene s = new Scene(h);
		window.setScene(s);
		window.showAndWait();
	}
	private void saveFile() {
		
		if((chosenSubset.getItems().size())!=0){
	String[] saveMe = new String[chosenSubset.getItems().size()];
	String[] split; 
	for(int i=0; i<chosenSubset.getItems().size();i++){
		split =  chosenSubset.getItems().get(i).toString().split("~");
		saveMe[i]=split[split.length-1].trim();
	}
	View v = new View();
	v.initializeSelection(xmlName);
	for(int i=0;i<saveMe.length;i++){
		
		v.addSelectionID(saveMe[i]);
	}
	v.finalizeSelection();

		}
		else{
		Error("Select one or more locations before you save!");
		}
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
		window.setWidth(550);
		Button ok = new Button("confirm");
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
}