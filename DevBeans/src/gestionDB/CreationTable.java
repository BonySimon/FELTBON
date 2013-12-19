package gestionDB;
import java.sql.SQLException;

public class CreationTable 
{
	public static void main(String args[]) throws SQLException, ClassNotFoundException
	{
		MyDB db = new MyDB();
		db.connect();
		
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
		
		db.executeUpdate(createCOMPTE);
		db.executeUpdate(createOPERATION);		
		db.close();
	}
}
