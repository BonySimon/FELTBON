package javaBeans;

import gestionDB.MyDB;
import gestionErreurs.TraitementException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class contains methods to manage bank's account into a MySQL database
 * @author BONY Simon - FELTZ Ludovic
 */
public class BOperations {
	// The MySQL database
	MyDB db = null;

	// Information about the account to manage
	private String noDeCompte, nom, prenom, op, dateInf, dateSup;
	private BigDecimal solde, ancienSolde, nouveauSolde, valeur;
	private ArrayList<String[]> operationsParDates;

	///////////////////////////////////////////////////////////////////////////////
	//////	   Setters  and Getters                           
	//////////////////////////////////
	
	// Account number
	
	/**
	 * Sets the account number
	 * @param noDeCompte the account number to set
	 */
	public void setNoDeCompte(String noDeCompte) {
		this.noDeCompte = noDeCompte;
	}
	
	// Operator

	/**
	 * Gets the operator to use in the next operation
	 * @return the operator to use in the next operation
	 */
	public String getOp() {
		return op;
	}

	/**
	 * Sets the operator to use in the next operation
	 * @param op the operator to use in the next operation to set
	 */
	public void setOp(String op) {
		if (!(op.equals("+") || op.equals("-")))
			throw new IllegalArgumentException("op must be + or -");
		this.op = op;
	}

	// Next change value
	
	/**
	 * Gets the value of the next change
	 * @return the value of the next change
	 */
	public String getValeur() {
		return valeur.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
	}

	/**
	 * Sets the value of the next change
	 * @param valeur the value of the next change to set
	 */
	public void setValeur(String valeur) {
		this.valeur = new BigDecimal(valeur);
	}

	// Account balance
	
	/**
	 * Gets the actual balance
	 * @return the actual balance
	 */
	public BigDecimal getSolde() {
		return solde;
	}

	/**
	 * Gets the old balance
	 * @return the old balance
	 */
	public BigDecimal getAncienSolde() {
		return ancienSolde;
	}

	/**
	 * Gets the new balance
	 * @return the new balance
	 */
	public BigDecimal getNouveauSolde() {
		return nouveauSolde;
	}
	
	// Identity

	/**
	 * Gets the owner family name
	 * @return the owner family name
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Gets the owner first name
	 * @return the owner first name
	 */
	public String getPrenom() {
		return prenom;
	}
	
	// Dates

	/**
	 * Gets the first date to search
	 * @return the first date to search
	 */
	public String getDateInf() {
		return dateInf;
	}

	/**
	 * Sets the first date to search
	 * @param dateInf the first date to search to set
	 */
	public void setDateInf(String dateInf) {
		this.dateInf = dateInf;
	}

	/**
	 * Gets the last date to search
	 * @return the last date to search
	 */
	public String getDateSup() {
		return dateSup;
	}

	/**
	 * Sets the last date to search
	 * @param dateSup the last date to search to set
	 */
	public void setDateSup(String dateSup) {
		this.dateSup = dateSup;
	}
	
	// Operation

	/**
	 * Gets all the operations for this configuration
	 * @return a list containing all the operations for this configuration
	 */	
	public ArrayList<String[]> getOperationsParDates() {
		return operationsParDates;
	}

	///////////////////////////////////////////////////////////////////////////////
	//////	   Actions                          
	/////////////////////
	
	/**
	 * This methods update the informations about the actual account
	 * @throws TraitementException Error during the operation
	 */
	public void consulter() throws TraitementException {
		ResultSet rs;
		try {
			if (db != null && noDeCompte != null) {
				rs = db.executeQuery("SELECT DISTINCT NOM,PRENOM,SOLDE FROM COMPTE WHERE NOCOMPTE = "
						+ noDeCompte);
				rs.next();
				
				nom = rs.getString("NOM");
				prenom = rs.getString("PRENOM");
				solde = rs.getBigDecimal("SOLDE");
			}
		} catch (SQLException e) {
			System.err.println("Erreur dans consulter() de la classe BOperations : mauvais nom de colonne");
			throw new TraitementException("3");
		}
	}
	
	/**
	 * This method apply the treatment specified in this configuration and update the informations about the actual account
	 * @throws TraitementException Error during the operation
	 */
	public void traiter() throws TraitementException {
		if (db != null && noDeCompte != null && valeur != null	&& op != null) {
			consulter();
			ancienSolde = new BigDecimal(solde.toString());
			nouveauSolde = new BigDecimal(solde.toString());
			
			if (op.equals("+"))
				nouveauSolde = nouveauSolde.add(valeur);
			else
				nouveauSolde = nouveauSolde.subtract(valeur);
			
			if (nouveauSolde.signum() == -1) {
				System.err.println("Erreur dans traiter(): solde négatif");
				nouveauSolde = new BigDecimal(solde.toString());
				throw new TraitementException("24");
			}

			solde = new BigDecimal(nouveauSolde.toString());
			db.executeUpdate("UPDATE COMPTE SET SOLDE=" + solde
					+ " WHERE NOCOMPTE = " + noDeCompte);
			db.executeUpdate("INSERT INTO OPERATION VALUES (" + noDeCompte
					+ ", NOW(), NOW(), '" + op + "', " + getValeur() + ");");
		}
	}

	/**
	 * This method opens a connection with the database
	 * @throws TraitementException Error during the connection
	 */
	public void ouvrirConnexion() throws TraitementException {
		if (db == null)
			db = new MyDB();

		db.connect();
	}

	/**
	 * This method closes the connection with the database
	 * @throws TraitementException Error during the closing operation
	 */
	public void fermerConnexion() throws TraitementException {
		if (db != null)
			db.close();
	}

	/**
	 * This method update the account table with the dates placed in this configuration
	 * @throws TraitementException Error during the operation
	 */
	public void listerParDates() throws TraitementException {
		try {
			if (db != null && noDeCompte != null && dateInf != null && dateSup != null) {
				operationsParDates = new ArrayList<String[]>();
				ResultSet rs = db.executeQuery("SELECT DATE, OP, VALEUR FROM OPERATION WHERE NOCOMPTE="+ noDeCompte+ " AND DATE>='" + dateInf + "' AND DATE<='" + dateSup + "';");
				while(rs.next()){
					operationsParDates.add(new String[]{rs.getString("DATE"), rs.getString("OP"), rs.getString("VALEUR")});
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur dans listerParDates() de la classe BOperations : mauvais nom de colonne");
			throw new TraitementException("3");
		}
	}
}
