package corpus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;


public interface Corpus {
	
	/**
	 * This function allows us to analyze the corpus.
	 * This method will split the corpus into bigrams and count the occurrence number
	 * of each bi-grams and update the data in the corpus.
	 * @param langue the type of language
	 * @param filePath the path of file.
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 * @throws EncodingAlgorithmException 
	 * */
	void learnFromFile(String langue,String filePath) throws FileNotFoundException, UnsupportedEncodingException, EncodingAlgorithmException, IOException;
	
	
	/* Returns the list of correlation coefficient for each language in the corpus.
	 * This corpus will return a TreeMap. The key of the map is the correlation coefficient
	 * and its value is the language.   
	 * For example:
	 * Input
	 *     "I came from China"
	 * Output
	 * {0.6 = FRANCE, 0.75 = English}
	 */
	 Map<String, Double> analysis(String str);
 
}