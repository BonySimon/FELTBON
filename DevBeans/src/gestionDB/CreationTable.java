package gestionDB;
import java.sql.SQLException;

/**
 * This class contains a main method to create MySQL tables.
 * This class cannot be instantiate
 * @author BONY Simon - FELTZ Ludovic
 */
public class CreationTable 
{
	/**
	 * This statement prevents the class from being instantiated
	 */
	private CreationTable(){}
	
	/**
	 * This main method creates two tables into a MySQL Database: COMPTE and OPERATION
	 * @param args An array containing command-line arguments
	 * @throws SQLException An error occurs with SQL statements during the creation
	 * @throws ClassNotFoundException An error occurs during the JDBC class initialization
	 */
	public static void main(String args[]) throws SQLException, ClassNotFoundException
	{
		String createCOMPTE = 
				"CREATE TABLE IF NOT EXISTS COMPTE (" +
				"	NOCOMPTE CHAR(4) NOT NULL, " +
				"	NOM VARCHAR(20), " +
				"	PRENOM VARCHAR(20), " +			
				"	SOLDE DECIMAL(10,2) NOT NULL, " +
				"	PRIMARY KEY(NOCOMPTE));";
		
		String createOPERATION = 
				"CREATE TABLE IF NOT EXISTS OPERATION (" +
				"	NOCOMPTE CHAR(4) NOT NULL, " +
				"	DATE DATE NOT NULL, " +
				"	HEURE TIME NOT NULL, " +			
				"	OP CHAR(1) NOT NULL, " +
				"	VALEUR DECIMAL(10,2) NOT NULL);";
		
		// A MyDB object is used to open a connection and to create the tables 
		MyDB db = new MyDB();
		db.connect();
		db.executeUpdate(createCOMPTE);
		db.executeUpdate(createOPERATION);		
		db.close();
	}
}
