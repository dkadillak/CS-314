package main.java.edu.csu2017sp314.dtr18.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBquery {
	private Connection conn;
	private Statement st;
	final static String limit = "LIMIT 500";
	private String columns;
	private String from;
	private String join;
	private String where;
	
	public DBquery(){
		columns = null;
		from = "";
		join = "";
		where = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cs314","paultrap","830051314");
			st = conn.createStatement();
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
	}
	
	public void setFrom(String string){
		from = " FROM " + string + " ";
	}
	
	public void addJoin(String table, String link){
		join += "inner join ";
		join += table + " on " + link;
	}
	
	public void addColumn(String string){
		if(columns == null){
			columns = "SELECT " + string;
		} else{
			columns += "," + string;
		}
	}
	
	public void close(){
		try {
			conn.close();
			st.close();
		} catch (SQLException e) {
			System.err.print("Error: ");
			System.err.println(e.getMessage());
		}
	}

}
