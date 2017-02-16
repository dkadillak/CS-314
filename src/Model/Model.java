package Model;
//reader
//inputstream reader
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Model{
	
	//class variables
	private ArrayList<location> data;
	public ArrayList<String> extra;
	public String Name;
	public int ID;
	private int lineCount=0, NamePosition, IDPosition, LatitudePosition, LongitudePosition;
	public double Latitude, Longitude;
	Scanner scan;

	public Model(File file) throws FileNotFoundException{
	//setting up scanner with inputted file
		scan= new Scanner(file);
	//setting up scanner to parse individual tokens by comma
		scan.useDelimiter(",");
	//lesgo bby
		parseData();
	}
	public int getFileSize(){
		return data.size();
	}
	private void parseData(){
		//setting up count
				int count=0;
		//setting up string 
		String input="";
		//getting information from very first line of file
		if(scan.hasNextLine()){
			firstLineParser(scan.nextLine());
		}
		
	while(scan.hasNext()){
		
		
		//removing all white space from a token
		input =scan.next();
		input=input.replaceAll("\\s", "");
		
		if(count==NamePosition){
			Name=input;
		}
		else if(count==IDPosition){
			ID=Integer.parseInt(input);
		}
		else if(count==LatitudePosition){
			Latitude=LatLongConverter(input);
		}
		else if(count==LongitudePosition){
			Longitude=LatLongConverter(input);
		}
		else{
			extra.add(input);
		}
		
		
		System.out.println(input);
		count++;
		}	
	data.add(new location(Name,ID,Latitude,Longitude,extra));
	extra.clear();
	}

protected void firstLineParser(String firstLine){
	String input="";
	//lowercase everything
	firstLine=firstLine.toLowerCase();
	

	//remove extra spacing if there is any
	firstLine.replaceAll("\\s", "");
	
	//setting up scanner for firstLine string and setting delimiter
	Scanner in = new Scanner(firstLine);
	in.useDelimiter(",");
	
	while(in.hasNext()){
		input=in.next();
		
		if(input.equalsIgnoreCase("name")){
			NamePosition=lineCount;
		}
		if(input.equalsIgnoreCase("id")){
			IDPosition=lineCount;
		}
		if(input.equalsIgnoreCase("latitude")){
			LatitudePosition=lineCount;
		}
		if(input.equalsIgnoreCase("longitude")){
			LongitudePosition=lineCount;
		}
		lineCount++;
	}
	/* Testing to see if variables were set correctly
	System.out.println("lineCount="+lineCount);
	System.out.println("NamePosition="+NamePosition);
	System.out.println("IDPosition="+IDPosition);
	System.out.println("LatitudePosition="+LatitudePosition);
	System.out.println("LongitudePosition="+LongitudePosition);
	*/
}
//still need to implement
private double LatLongConverter(String LatLong){
	
	return 2.12;
}

}