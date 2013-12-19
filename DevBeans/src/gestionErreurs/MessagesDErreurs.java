package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {
	private static HashMap<String, String> messages;
	
	private static void initialize(){
		messages = new HashMap<String, String>();
		messages.put("21", "Erreur n°21");
	}
	
	public static String getMessageDerreur(String noMessage){
		if(messages == null)
			initialize();
		return messages.get(noMessage);
	}
}
