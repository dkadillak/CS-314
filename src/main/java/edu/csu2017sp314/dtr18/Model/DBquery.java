package main.java.edu.csu2017sp314.dtr18.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBquery {
	private Connection conn;
	private Statement st;
	private static final String limit = " LIMIT 500";
	private String columns;
	private String from;
	private String where;
	ResultSet rs;
	
	public DBquery(){
		columns = null;
		from = "";
		where = "";
		conn = null;
		st = null;
		rs = null;
	}
	
	public String getColumns(){
		return columns;
	}
	
	public String getFrom(){
		return from;
	}
	
	//tables is a single string containing the names of all tables you need joined in the query separated by spaces
	//if you want all 4 tables use the string 'all'
	public void setFrom(String tables){
		tables = tables.toLowerCase();
		
		boolean airports = false;
		boolean countries = false;
		boolean regions = false;
		boolean continents = false;
		
		if(tables.contentEquals("all")){
			airports = true;
			countries = true;
			regions = true;
			continents = true;
		} else {
			if(tables.contains("airports")){
				airports = true;
			}
			if(tables.contains("countries")){
				countries = true;
			}
			if(tables.contains("regions")){
				regions = true;
			}
			if(tables.contains("continents")){
				continents = true;
			}
		}
		
		generateJoin(airports,regions,countries,continents);
	}
	
	public String getWhere(){
		return where;
	}
	
	public void setWhere(String string){
		where = " WHERE " + string;
	}
	
	//buckle-up buttercup
	private void generateJoin(boolean airports, boolean regions, boolean countries, boolean continents){
		if(continents){
			from = " from continents";
			if(countries){
				from += " inner join countries on "
						+ "countries.continent = continents.id";
				if(regions){
					from += " inner join regions on "
							+ "regions.iso_country = countries.code";
					if(airports){
						from += " inner join airports on "
								+ "airports.iso_region = regions.code";
					}
					//regions && !airports
				}else if(airports){	//countries && !regions
					from += " inner join airports on "
							+ "airports.iso_country = countries.code";
				}
				//countries && !regions && !airports
			}else if(regions){//!countries
				from += " inner join regions on "
						+ "regions.continent = continents.id";
				if(airports){
					from += " inner join airports on "
							+ "airports.iso_region = regions.code";
				}
			}else if(airports){//!countries && !regions						
				from += " inner join airports on "
						+ "airports.continent = continents.id";
			}
		}else if(countries){//!continents
			from = " from countries";
			if(regions){
				from += " inner join regions on "
						+ "regions.iso_country = countries.code";
				if(airports){
					from += " inner join airports on "
							+ "airports.iso_region = regions.code";
				}
			}else if(airports){//!regions
				from += " inner join airports on "
						+ "airports.iso_country = countries.code";
			}
		}else if(regions){//!continents && !countries
			from = " from regions";
			if(airports){
				from += " inner join airports on "
						+ "airports.iso_region = regions.code";
			}
		}else if(airports){
			from = " from airports";
		}else{
			noTableError();
		}
	}
	
	public void addColumn(String string){
		if(columns == null){
			columns = "SELECT " + string;
		} else{
			columns += "," + string;
		}
	}
	
	private void noTableError(){
		System.err.println("Error: cannot make a database query without providing a table!");
		System.exit(-1);
	}
	
	//output is in the same order that columns were added to the query 
	public ResultSet submit(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cs314","paultrap","830051314");
			st = conn.createStatement();
			String query = columns + from + where + limit;
			rs = st.executeQuery(query);
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return rs;
	}
	
	public void close(){
		try {
			if(conn != null){
				conn.close();
			}
			if(st != null){
				st.close();
			}
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			System.err.print("Error: ");
			System.err.println(e.getMessage());
		}
	}

}
