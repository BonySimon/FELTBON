package javaBeans;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

/**
 * This class tests the dates search functionality of BOperations
 * @author BONY Simon - FELTZ Ludovic
 */
public class TestListeParDates {
	
	/**
	 * This function is called to test the dates search functionality of BOperations
	 * @param args parameters of this application. No utility here.
	 */
	public static void main(String[] args) {
		try{
			BOperations bo = new BOperations();
			bo.ouvrirConnexion();
			bo.setNoDeCompte("0002");
			bo.setDateInf("2013-10-15");
			bo.setDateSup("2013-10-17");
			bo.listerParDates();
			for(String[] elems: bo.getOperationsParDates()){
				System.out.print("->   ");
				for(String elem: elems){
					System.out.print(elem + " ");
				}
				System.out.print("\n");
		    }
			bo.fermerConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getMessage()));
		}
	}
}
