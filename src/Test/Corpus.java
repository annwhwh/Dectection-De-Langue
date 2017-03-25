package Test;

import java.util.HashMap;

public interface Corpus {
	
	/** Cette méthode est destiné à analyser une corpus. 
	 * Cette fonction destiné à analyser une texte dénoté par un chimin d'accès.
	 * Cette fonction va texte en bi-gram et cpmpter les occurences de chaque bi-gram et mettre à jour le corpus.
	 * @param type donner le type de langue
	 * @param filePath le chiemin de corpus.
	 * */
	void apprentissage(Langue type,String filePath);
	
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
	 Langue analysis(String str, String type);
	
	// other methods ---- team work
}