//alertBox
package main.java.edu.csu2017sp314.dtr18.View;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	static Button ok,no;
	
	public static boolean result, xmlSave=false, runXml=false;		
	public static String fileName, xmlName, xmlNameGiven;
	public Label searchLabel = new Label("displaying locations from "+xmlNameGiven);
	ChoiceBox<String> choiceBox;
	public static String[] selectedLocations;
	public static String[] locations;
	public static boolean opt_m=true;
	public static boolean opt_k=false;
	public static boolean opt_i=false;
	public static boolean opt_d=false;
	public static boolean opt_2=false;
	public static boolean opt_3=false;
	//public DBquery db;
	
	//class variables
	Stage window;
	
			@Override
			//setting up the gui
			
			public void start(Stage primaryStage) throws Exception {
				window = primaryStage;
				primaryStage.setOnCloseRequest(e -> {
					boolean answer = AlertBox.display("Confirm exit");
					if(answer){
						//selectedLocations = new String[1];
						//selectedLocations[0] = "no subselection";
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
				//creating checkboxes, submit button,load button, save button and place for location subselection
				Button submit = new Button("Create Trip");
				Button load = new Button("Move to Selected");
				Button save = new Button("save");
				Button clear = new Button("clear");
				Button remove = new Button("remove");
				remove.setOnAction(e->removeLocation(chosenSubset));
				
				//listView is the object for displaying locations
				subset = new ListView();
				chosenSubset = new ListView();
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
				
				//setting actions for submit, save, clear, and load button
				submit.setOnAction(e->this.checkBoxes(b1, b2, b3, b4,chosenSubset,miles, kilometers));
				save.setOnAction(e->getFileName());
				load.setOnAction(e->this.loadSelection(subset, chosenSubset));
				clear.setOnAction(e->this.clearSelection(chosenSubset));
				
			
	
				//filling list with locations, allowing multiple selections to be a thing
				subset.getItems().addAll(locations);
				subset.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				chosenSubset.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				Label l = new Label("Hold Ctrl while selecting to pick a subset of locations");
				

				Button choice = new Button("Load Subselection");
				choice.setOnAction(e->{
					try {
						loadSubselection(choiceBox.getValue());
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				});
				choiceBox = new ChoiceBox<>();
				choiceBox.getItems().add(xmlNameGiven);
				choiceBox.getItems().add("None");
				choiceBox.setValue("None");

				
				//HBox for radiobuttons and subselection loading dropdown
				HBox buttons = new HBox();
				VBox sub = new VBox();
				sub.getChildren().addAll(choiceBox,choice);
				buttons.setSpacing(10);
				buttons.getChildren().addAll(kilometers,miles,sub);
				
				Label typeLabel = new Label("Select Airport Type");
				//setting up search bar
				ChoiceBox type = new ChoiceBox<>();
				type.getItems().add("small_airport");
				type.getItems().add("medium_airport");
				type.getItems().add("large_airport");
				type.getItems().add("closed");
				
				Label continentLabel = new Label("Select Continent");
				ChoiceBox continent = new ChoiceBox<>();
				continent.getItems().add("Africa");
				continent.getItems().add("Antartica");
				continent.getItems().add("Asia");
				continent.getItems().add("Europe");
				continent.getItems().add("North America");
				continent.getItems().add("Oceania");
				continent.getItems().add("South America");
				
				
				TextField region = new TextField();
				region.setPromptText("search region");
				
				TextField country = new TextField();
				country.setPromptText("search country");
				
				TextField municipality = new TextField();
				municipality.setPromptText("search municipality");
				
				TextField airport = new TextField();
				airport.setPromptText("search airport");
			
				Button  search = new Button("search");
				search.setOnAction(e->{
					try {
						dbSearch(type,continent,region.getText(),municipality.getText(),airport.getText(),country.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				VBox searchBar = new VBox();
				searchBar.getChildren().addAll(typeLabel,type,continentLabel,continent,region,municipality,country,airport,search);
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
				//GridPane.setConstraints(kilometers,4,0);
				//GridPane.setConstraints(choice,3,1);
				//GridPane.setConstraints(choiceBox,3,2);
				GridPane.setConstraints(l,1,5);
				GridPane.setConstraints(l2,3,8);
				GridPane.setConstraints(subset,1,6);
				GridPane.setConstraints(chosenSubset,3,6);
				GridPane.setConstraints(searchBar,0,6);
				GridPane.setConstraints(clearSave,4,6);
				//GridPane.setConstraints(save,4,5);
				GridPane.setConstraints(searchLabel,1,9);
				GridPane.setConstraints(load,1,10);
				GridPane.setConstraints(submit,3,10);
				
				grid.getChildren().addAll(b1,b2,b3,b4,l,l2,buttons,searchLabel,subset,chosenSubset,searchBar,clearSave,load,submit);
				
				
				Scene scene = new Scene(grid,900,500);
				window.setScene(scene);
				window.show();
			}
			private void removeLocation(ListView chosenSubset) {
				ObservableList chosen = chosenSubset.getSelectionModel().getSelectedItems();
				chosenSubset.getItems().removeAll(chosen);
			}
			public void loadSubselection(String value) throws FileNotFoundException {
				// TODO Auto-generated method stub
				if(!value.equals("None")){
				Model m = new Model(new File(xmlNameGiven),'m');
				subset.getItems().clear();
				
				for(int i=0; i<m.locations.size();i++){
					subset.getItems().add(m.locations.get(i).name);
				}
				searchLabel.setText("displaying "+m.locations.size()+"/200 results");
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
				if(choiceBox.getValue()!="None"){
					runXml = true;
				}
				if(choiceBox.getValue() != "None" && !chosenSubset.getSelectionModel().isEmpty()){
					AlertBox.Error("Error- You cannot pick a subset of locations and load a subselection");
					//subset.getSelectionModel().clearSelection();
					choiceBox.setValue("None");
					
				}
				//save selected locations
				if(!chosenSubset.getItems().isEmpty()){
				ObservableList<String> locations;
				locations = chosenSubset.getItems();
				selectedLocations = new String[locations.size()];
				for(int i=0; i<locations.size();i++){
					selectedLocations[i] = locations.get(i);
				}
				}
				if(chosenSubset.getItems().isEmpty()){
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
					opt_d = true;
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
					opt_m = true;
					opt_k = false;
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
	public void dbSearch(ChoiceBox type, ChoiceBox continent, String region,String municipality,String airport,String country) throws SQLException{
	db = new DBquery();
	db.addColumn("airports.name");
	String tables = "airports";
	//System.out.println(continent.getValue());
	
	if(type.getValue()!=null){
		db.addColumn("type");
		db.setWhere("type='"+type.getValue()+"'");
	}
	if(continent.getValue()!=null){
		tables+=" continents";
		db.addColumn("airports.continent");
		db.addColumn("continents.name");
		db.setWhere("continents.name= '"+continent.getValue()+"'");
	}
	if(!region.equals("")){
		tables+=" regions";
		db.addColumn("airports.iso_region");
		db.addColumn("regions.name");
		db.setWhere("regions.name LIKE '%"+region+"%'");
		
	}
	if(!municipality.equals("")){
		db.addColumn("municipality");
		db.setWhere("municipality LIKE '%"+municipality+"%'");
		
	}
	if(!country.equals("")){
		tables+=" countries";
		db.addColumn("airports.iso_country");
		db.addColumn("countries.name");
		db.setWhere("countries.name LIKE '%"+country+"%'");
	}
	
	if(!airport.equals("")){	
		db.setWhere("airports.name LIKE '%"+airport+"%'");
	}
	
	db.setFrom(tables);
	ResultSet rs = db.submit();
	int count = 0;
	subset.getItems().clear();
	while(rs.next()){
		if(count==200){
		break;
		}
		subset.getItems().add(rs.getString("airports.name"));
		count++;
	}
	searchLabel.setText("displaying "+count+"/200 results");
	db.clear();
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
		e.consume();
		saveFile();
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
		System.out.println(chosenSubset.getItems().size());
		System.out.println((chosenSubset.getItems().size())!=0);
		if((chosenSubset.getItems().size())!=0){
	String[] saveMe = new String[chosenSubset.getItems().size()];
	for(int i=0; i<chosenSubset.getItems().size();i++){
		saveMe[i]=(String)chosenSubset.getItems().get(i);
	}
	Model m = new Model(saveMe,'m');
	View v = new View();
	v.initializeSelection(xmlName);
	for(int i=0;i<m.locations.size();i++){
		v.addSelectionID(m.locations.get(i).id);
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
		window.setWidth(450);
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
	
	public static void main(String [ ] args) throws FileNotFoundException{
		AlertBox.locations = new String[]{"Colorado","New Mexico","Arizona","Aurora"}; 
		xmlNameGiven = "testFile";
		
		AlertBox.launch();
		AlertBox.printOpt();
	}
	
	
}