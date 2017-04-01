package corpus;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface Corpus {
	
	/** Cette méthode est destiné à analyser une corpus. 
	 * Cette fonction destiné à analyser une texte dénoté par un chimin d'accès.
	 * Cette fonction va texte en bi-gram et cpmpter les occurences de chaque bi-gram et mettre à jour le corpus.
	 * @param type donner le type de langue
	 * @param filePath le chiemin de corpus.
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * */
	void learnFromFile(Langue type,String filePath) throws FileNotFoundException, UnsupportedEncodingException;
	/** exporter le model de detecteur dans un ficher dénoté par filepath
	 * @param filePath le camion d'accès de model 
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 * **/
	
	void exportModel(String filePath) throws FileNotFoundException;
	
	/** importer le model de detecteur
	 * @param filePath le chemin d'accès de model
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 * */
	void importModel(String filePath) throws FileNotFoundException;
	
	/*
	 * entree: "I love France"
	 * 
	 * sortie:
	 * <"Fr", 0.9>
	 * <"En", 0.6>
	 */
	HashMap<Langue, Double> analysis(String str);
	
	/*
	 * entree: "I love France", "Fr"
	 * 
	 * sortie:
	 * "En"
	 */
	 Langue analysis(String str, Langue langue);
	 
	// other methods ---- team work
}