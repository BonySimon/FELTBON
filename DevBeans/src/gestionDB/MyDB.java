package gestionDB;

import gestionErreurs.TraitementException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains methods to manage a MySQL database with JDBC
 * @author BONY Simon - FELTZ Ludovic
 */
public class MyDB {
	// Variables containing all the informations used during the connection
	private static String mySqlDB = "sqletud.univ-mlv.fr/lfeltz_db";
	private static String dbms = "mysql";
	private static String user = "lfeltz";
	private static String password = "Wuunoi6q";
	
	// Variables representing the links with the database
	private Connection conn;
	private Statement st;
	
	/**
	 * This method initializes this object with a connection to the database
	 * @throws TraitementException Error due to an absence of JDBC or to a problem during the connection to the database
	 */
	public void connect() throws TraitementException {
		final String url = "jdbc:" + dbms + "://" + mySqlDB;
		
		// Loads JDBC
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Erreur dans connect() de la classe MyDB : erreur du chargement du plugin jdbc");
			throw new TraitementException("23");
		}
		
		// Initializes a connection
	    try {
			this.conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.err.println("Erreur dans connect() de la classe MyDB : échec de la connexion");
			throw new TraitementException("21");
		}	   
	}
	
	/**
	 * This method closes the actual connection to the database
	 * @throws TraitementException Error during the closing operation
	 */
	public void close() throws TraitementException{
		try {
			if (conn != null) conn.close();
			if (st != null) st.close();
			conn=null;
			st=null;
		} catch (SQLException e) {
			System.err.println("Erreur dans close() de la classe MyDB : erreur dans la fermeture de la connexion");
			throw new TraitementException("22");
		}
	}
	
	/**
	 * This method execute an update into the opened database
	 * @param query the update to do in SQL
	 * @return a code representing the state of the operation
	 * @throws TraitementException SQL error: the update is incorrect
	 */
	public int executeUpdate(String query) throws TraitementException{
		return (Integer) executeUpdate(query, true);
	}
	
	/**
	 * This method execute a query into the opened database
	 * @param query the query to do in SQL
	 * @return a set containing the result of the query
	 * @throws TraitementException SQL error: the query is incorrect
	 */
	public ResultSet executeQuery(String query) throws TraitementException{
		return (ResultSet) executeUpdate(query, false);
	}
	
	/**
	 * This method execute a query or an update into the opened database
	 * @param query the query or the update to do in SQL
	 * @param update set as true for an update or as false for a query
	 * @return the result of the query or the update
	 * @throws TraitementException SQL error: the query or the update is incorrect
	 */
	private Object executeUpdate(String query, boolean update) throws TraitementException{
		try {
		    if(conn==null)
		    	throw new IllegalStateException("The connection must be opened");
	
		    if(st == null)
				st = conn.createStatement();
	
		    if(update)
		       	return st.executeUpdate(query);
		    else
		    	return st.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Erreur dans executeUpdate(String,boolean) de la classe MyDB : erreur SQL");
			throw new TraitementException("3");
		}
	}
}
