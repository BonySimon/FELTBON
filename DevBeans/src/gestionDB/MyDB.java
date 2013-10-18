package gestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDB {
	private static String mySqlDB = "sqletud.univ-mlv.fr/lfeltz_db";
	private static String dbms = "mysql";
	private static String user = "lfeltz";
	private static String password = "Wuunoi6q";
	
	private Connection conn;
	private Statement st;
	
	
	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	    final String url = "jdbc:" + dbms + "://" + mySqlDB;
	    this.conn = DriverManager.getConnection(url, user, password);	   
	}
	
	public void close() throws SQLException{
		if (conn != null) conn.close();
		if (st != null) st.close();
		conn=null;
		st=null;
	}
	
	public int executeUpdate(String query) throws SQLException {
		return (int) executeUpdate(query, true);
	}
	
	public ResultSet executeQuery(String query) throws SQLException {
		return (ResultSet) executeUpdate(query, false);
	}
	
	private Object executeUpdate(String query, boolean update) throws SQLException {
	    if(conn==null)
	    	throw new IllegalStateException("The connection must be opened");

	    if(st == null)
	    	st = conn.createStatement();
	    
	    if(update)
	       	return st.executeUpdate(query);
	    else
	    	return st.executeQuery(query);
	}
}
