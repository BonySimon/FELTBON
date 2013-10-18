package javaBeans;

public class TestConsultation {
	
	public static void main(String[] args) {
		BOperations bo = new BOperations();
		bo.ouvrirConnexion();
		bo.setNoDeCompte("0002");
		bo.consulter();
		System.out.println("Nom : " + bo.getNom() + "\nPrenom : " + bo.getPrenom() + "\nSolde : " + bo.getSolde());
		bo.fermerConnexion();
	}
}
