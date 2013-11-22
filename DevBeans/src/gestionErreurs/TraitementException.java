package gestionErreurs;

/**
 * This class is a exception used to signal an error to search in MessagesDErreurs
 * @author BONY Simon - FELTZ Ludovic
 */
public class TraitementException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * This constructor instantiates this Exception with a code corresponding to the message to give
	 * @param messageCode the code of the message to give
	 */
	public TraitementException(String messageCode) {
		super(messageCode);
	}
}
