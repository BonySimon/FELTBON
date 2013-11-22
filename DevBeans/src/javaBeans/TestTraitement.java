package javaBeans;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

/**
 * This class tests the treatment functionality of BOperations
 * @author BONY Simon - FELTZ Ludovic
 */
public class TestTraitement {
	
	/**
	 * This function is called to test the treatment functionality of BOperations
	 * @param args parameters of this application. No utility here.
	 */
	public static void main(String[] args) {
		try{
			BOperations bo = new BOperations();
			bo.ouvrirConnexion();
			bo.setNoDeCompte("0002");
			bo.setOp("-");
			bo.setValeur("100");
			bo.traiter();
			System.out.println("Nom : " + bo.getNom() + "\nPrenom : " + bo.getPrenom() + "\nSolde : " + bo.getSolde());
			bo.fermerConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getMessage()));
		}
	}
}
