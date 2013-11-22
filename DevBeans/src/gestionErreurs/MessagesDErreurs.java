package gestionErreurs;

import java.util.HashMap;

/**
 * This class contains statics methods to manage the error messages with String codes
 * @author BONY Simon - FELTZ Ludovic
 */
public class MessagesDErreurs {
	// This HashMap links the codes with the messages
	private static HashMap<String, String> messages;
	
	/**
	 * This statement prevents the class from being instantiated
	 */
	private MessagesDErreurs(){}
	
	/**
	 * This methods initializes the HashMap with the codes and the messages
	 */
	private static void initialize(){
		messages = new HashMap<String,String>();
		messages.put("3", "Problème pour accéder à ce compte client, vérifiez qu'il est bien valide");
		messages.put("21", "Problème d'accès à la base de données, veuillez le signaler à votre administrateur");
		messages.put("22", "Problème après traitement. Le traitement a été effectué correctement mais il y a eu un problème à signaler à votre administrateur");
		messages.put("23", "Problème du chargement d'un plugin. Veuillez le signaler à votre administrateur");
		messages.put("24", "Opération refusée, débit demandé supérieur au crédit du compte");
	}
	
	/**
	 * This methods offers the message corresponding to the given code
	 * @param noMessage The code corresponding to the asked message
	 * @return the message corresponding to the given code
	 */
	public static String getMessageDerreur(String noMessage){
		if(messages == null)
			initialize();
		return messages.get(noMessage);
	}
}

