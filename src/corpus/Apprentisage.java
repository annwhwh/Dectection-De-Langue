package corpus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.experimental.theories.Theories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sun.activation.registries.MailcapParseException;
import com.sun.istack.internal.FinalArrayList;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Apprentisage implements Corpus {
	private Map<String, Integer> anglais = new HashMap<>();
	private Map<String, Integer> italian = new HashMap<>();
	private Map<String, Integer> francais = new HashMap<>();
	private Map<String, Integer> allemand = new HashMap<>();
	
	private final HashSet<String> alphabet;

	
	
	public Apprentisage() throws FileNotFoundException {
		this.alphabet = getSetFromFile();
		
		this.anglais =generatorBigram(alphabet);
		this.italian = generatorBigram(alphabet);
		this.allemand = generatorBigram(alphabet);
		this.francais = generatorBigram(alphabet);
		
	}
	
	@Override
	public void learnFromFile(Langue type, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		Map<String, Integer> targetMap;
		if(type == Langue.ANGLAIS) targetMap = this.anglais;
		else if(type == Langue.FRANSAIS) targetMap = this.francais;
		else if(type == Langue.ALLEMAND) targetMap = this.allemand;
		else targetMap = this.italian;
		
		File file = new File(filePath);
/*		if(!file.exists() || file.isDirectory()){
			throw new FileNotFoundException("Can not find the file:" + file.getAbsolutePath());
		}*/
		
		try(FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
		BufferedReader in = new BufferedReader(inputStreamReader);){
			String inputLine = null;
			int begin;
			int end;
			String seg;
			int value;
			
			while((inputLine = in.readLine())!=null){
				begin = 0;
				end = 2;
				
				for(end = 2; end < inputLine.length();end++){
					seg = inputLine.substring(begin,end).toLowerCase();
					if(targetMap.containsKey(seg)){
						value = targetMap.get(seg)+1;;
						targetMap.put(seg, value);
						begin++;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void exportModel(String filePath) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void importModel(String filePath) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	
	/**create a dictionary for a langue denoted from a set of alphabet*/
	public static HashMap<String, Integer> generatorBigram(HashSet<String> alphabet){
		HashMap<String, Integer> bigram = new HashMap<>();
		String item = null;
		for (String c1 : alphabet) {
			for (String c2 : alphabet) {
				item = c1 + c2;
				if(!bigram.containsKey(item))
					bigram.put(item, 0);
			}
		}
		return bigram;
	}

	public static HashSet<String> getSetFromFile() throws FileNotFoundException{
		HashSet<String> alphabet = new HashSet<>();
		
		/** Test the alphabet**/
		File file = new File(Propriete.PATH_ALPHABET);
		
		File[] list = file.listFiles();
		
		for(int i = 0; i< list.length; i++){
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(list[i]));
			
			alphabet.addAll(gson.fromJson(reader,new TypeToken<HashSet<String>>() {}.getType()));

		}
		
		return alphabet;
	}

	@Override
	public Langue analysis(String str, Langue langue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Langue, Double> analysis(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		String resultat = "Anglais" + anglais + "\n" 
				 + "Allemand" + allemand + "\n"
				 + "Francais" + francais + "\n"
				 + "Italian" + italian + "\n";
		return resultat;
	}
	
	public StringBuilder getInfo(){
		StringBuilder resultat = new StringBuilder();
		resultat.append("English:[");
		for ( Entry<String, Integer> entrySet : anglais.entrySet()) {
			if(entrySet.getValue().intValue() > 0)
				resultat.append(entrySet.toString() + " ");
		}
		resultat.append("]\n Italian:[");
		for ( Entry<String, Integer> entrySet : italian.entrySet()) {
			if(entrySet.getValue().intValue() > 0)
				resultat.append(entrySet.toString());
		}
		resultat.append("]\nAllemand:[");
		for ( Entry<String, Integer> entrySet : allemand.entrySet()) {
			if(entrySet.getValue().intValue() > 0)
				resultat.append(entrySet.toString());
		}
		resultat.append("]\nFrancais:[");
		for ( Entry<String, Integer> entrySet : francais.entrySet()) {
			if(entrySet.getValue().intValue() > 0)
				resultat.append(entrySet.toString() + "");
		}
		resultat.append("]\n");
		return resultat;
		
	}
}
