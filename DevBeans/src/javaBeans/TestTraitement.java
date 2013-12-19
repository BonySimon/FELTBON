package javaBeans;

public class TestTraitement {
	
	public static void main(String[] args) {
		BOperations bo = new BOperations();
		bo.ouvrirConnexion();
		bo.setNoDeCompte("0002");
		bo.setOp("-");
		bo.setValeur("100");
		bo.traiter();
		System.out.println("Nom : " + bo.getNom() + "\nPrenom : " + bo.getPrenom() + "\nSolde : " + bo.getSolde());
		bo.fermerConnexion();
	}
}
