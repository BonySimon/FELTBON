package javaBeans;

import gestionDB.MyDB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BOperations {
	MyDB db = null;

	private String noDeCompte, nom, prenom, op, dateInf, dateSup;
	private BigDecimal solde, ancienSolde, nouveauSolde, valeur;
	private ArrayList<String[]> operationsParDates;

	public void setNoDeCompte(String noDeCompte) {
		this.noDeCompte = noDeCompte;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		if (!(op.equals("+") || op.equals("-")))
			throw new IllegalArgumentException("op must be + or -");
		this.op = op;
	}

	public String getValeur() {
		return valeur.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
	}

	public void setValeur(String valeur) {
		this.valeur = new BigDecimal(valeur);
	}

	public BigDecimal getSolde() {
		return solde;
	}

	public BigDecimal getAncienSolde() {
		return ancienSolde;
	}

	public BigDecimal getNouveauSolde() {
		return nouveauSolde;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getDateInf() {
		return dateInf;
	}

	public void setDateInf(String dateInf) {
		this.dateInf = dateInf;
	}

	public String getDateSup() {
		return dateSup;
	}

	public void setDateSup(String dateSup) {
		this.dateSup = dateSup;
	}

	public ArrayList<String[]> getOperationsParDates() {
		return operationsParDates;
	}

	public void consulter() {
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
			e.printStackTrace();
		}
	}

	public void traiter() {
		try {
			if (db != null && noDeCompte != null && valeur != null	&& op != null) {
				consulter();
				ancienSolde = new BigDecimal(solde.toString());
				nouveauSolde = new BigDecimal(solde.toString());
				if (op.equals("+"))
					nouveauSolde = nouveauSolde.add(valeur);
				else
					nouveauSolde = nouveauSolde.subtract(valeur);
				if (nouveauSolde.signum() == -1) {
					System.out.println("Solde négatif: opération refusée");
					nouveauSolde = new BigDecimal(solde.toString());
					return;
				}

				solde = new BigDecimal(nouveauSolde.toString());
				db.executeUpdate("UPDATE COMPTE SET SOLDE=" + solde
						+ " WHERE NOCOMPTE = " + noDeCompte);
				db.executeUpdate("INSERT INTO OPERATION VALUES (" + noDeCompte
						+ ", NOW(), NOW(), '" + op + "', " + getValeur() + ");");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ouvrirConnexion() {
		if (db == null)
			db = new MyDB();

		try {
			db.connect();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void fermerConnexion() {
		try {
			if (db != null)
				db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listerParDates() {
		try {
			if (db != null && noDeCompte != null && dateInf != null && dateSup != null) {
				operationsParDates = new ArrayList<>();
				ResultSet rs = db.executeQuery("SELECT DATE, OP, VALEUR FROM OPERATION WHERE NOCOMPTE="+ noDeCompte+ " AND DATE>='" + dateInf + "' AND DATE<='" + dateSup + "';");
				while(rs.next()){
					operationsParDates.add(new String[]{rs.getString("DATE"), rs.getString("OP"), rs.getString("VALEUR")});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
