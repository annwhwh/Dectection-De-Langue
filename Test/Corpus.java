package Test;

import java.util.HashMap;

public interface Corpus {
	/*
	 * entree: "I love France"
	 * 
	 * sortie:
	 * <"Fr", 0.9>
	 * <"En", 0.6>
	 */
	HashMap<String, Double> analysis(String str);
	
	/*
	 * entree: "I love France", "Fr"
	 * 
	 * sortie:
	 * "En"
	 */
	String analysis(String str, String type);
	
	// other methods ---- team work
}