package corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class Apprentisage implements Corpus {
	private Map<Langue,Map<String,Integer>> bigrams = 
			new EnumMap<Langue,Map<String,Integer>>(Langue.class);
	
	private final HashSet<String> alphabet;

	public Apprentisage() throws FileNotFoundException {
		this.alphabet = getSetFromFile(); 	
		for(Langue langue : Langue.values()){
			bigrams.put(langue, new HashMap<String, Integer>());
		}
		generatorBigram();
	}
	
	@Override
	public void learnFromFile(Langue type, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		Map<String, Integer> targetMap = bigrams.get(type);
		
		File file = new File(filePath);
		/*if(!file.exists() || file.isDirectory()){
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
	public void exportModel(String fileParentPath) throws FileNotFoundException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss_ddMMyyyy");
		String name = LocalDateTime.now().format(dtf) + ".json";
		exportModel(fileParentPath,name);

	}
	public void exportModel(String fileParentPath,String name) throws FileNotFoundException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();		
		File file = new File(fileParentPath + name);
		if(!file.getParentFile().isDirectory()) throw new FileNotFoundException("filePaht hava to be a path of directory");
		
		try(PrintWriter pWriter = new PrintWriter(file);){
			file.createNewFile();
			gson.toJson(this,pWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**create a dictionary for a langue denoted from a set of alphabet*/
	public void generatorBigram(){
		String item;
		for (String c1 : alphabet) {
			for (String c2 : alphabet) {
				for(Entry<Langue, Map<String, Integer>> langue: this.bigrams.entrySet()){
					item = c1 + c2;
					if(!langue.getValue().containsKey(item))
						langue.getValue().put(item, 0);
				}
			}
		}
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
		StringBuilder resultat = new StringBuilder();
		for (Entry<Langue,Map<String, Integer>> langue : bigrams.entrySet()) {
			resultat.append(langue.getKey().name() + ":" + langue.getValue() + "\n");
		}
		return resultat.toString();
	}
	
	public String getInfo(){
		StringBuilder resultat = new StringBuilder();
		for (Entry<Langue,Map<String, Integer>> langue : bigrams.entrySet()) {
			resultat.append(langue.getKey() + ":[");
			for ( Entry<String, Integer> entrySet :langue.getValue().entrySet()) {
				if(entrySet.getValue().intValue() > 0)
					resultat.append(entrySet.toString() +",");
			}
			if(resultat.charAt(resultat.length()-1) == ',')
				resultat.deleteCharAt(resultat.length()-1);
			resultat.append("]\n");
		}
		return resultat.toString();
	}
	
	/** importer le model de detecteur
	 * @param filePath le chemin d'accès de model
	 * @return 
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 * */
	public static Apprentisage importModel(String filePath) throws FileNotFoundException {
		Gson gson = new Gson();		
		File file = new File(filePath);
		Apprentisage scrModel = null;
		
		try(FileReader fReader = new FileReader(file);){
			scrModel = gson.fromJson(fReader, new TypeToken<Apprentisage>() {}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scrModel;
	}

	public Map<Langue, Map<String, Integer>> getBigrams() {
		return bigrams;
	}
}
