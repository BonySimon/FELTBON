package javaBeans;

public class TestListeParDates {
	
	public static void main(String[] args) {
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
	}
}
