package gestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreationTable 
{
	private static String mySqlDB = "sqletud.univ-mlv.fr/lfeltz_db";
	private static String dbms = "mysql";
	private static String user = "lfeltz";
	private static String password = "Wuunoi6q";
	
	private static String query = 
			"CREATE TABLE COMPTE (" +
			"	NOCOMPTE CHAR(4) NOT NULL, " +
			"	NOM VARCHAR(20), " +
			"	PRENOM VARCHAR(20), " +			
			"	SOLDE DECIMAL(10,2) NOT NULL, " +
			"	PRIMARY KEY(NOCOMPTE);";
	
	public static void main(String args[]) throws SQLException, ClassNotFoundException
	{
		CreationTable ct = new CreationTable();
		ct.executeQuery(query);		
	}
	
	
	private Connection newConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	    final String url = "jdbc:" + dbms + "://" + mySqlDB;
	    Connection conn = DriverManager.getConnection(url, user, password);
	    return conn;
	}
	
	
	public ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException {
	    Connection conn = null;
	    try 
	    {
	        conn = newConnection();
	        Statement st = conn.createStatement();
	        return st.executeQuery(query);
	        
	    } finally 
	    {
	        if (conn != null) conn.close();
	    }
	}
}
