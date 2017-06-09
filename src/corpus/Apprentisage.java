package corpus;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;

import main.XMLUtils;

public class Apprentisage implements Corpus {
	private static Apprentisage apprentisage = null;
	
    private final Map<String, Bigrams> languesList;	

	private Apprentisage() throws FileNotFoundException {
	    languesList = XMLUtils.getLangueList();
	}
	
	public boolean addLangue(String langue){
	        
	    if(languesList.containsKey(langue)){
	        return false;
	    }else{
	        this.languesList.put(langue, new Bigrams());
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        File file = new File(Propriete.PATH_PROFIL + langue + ".json");

                try {
                    if(!file.exists()){
                        file.getParentFile().mkdirs();
                    file.createNewFile();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                
	        try(PrintWriter pWriter = new PrintWriter(file);){
	            file.createNewFile();
	            gson.toJson(this,pWriter);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        XMLUtils.addLangue(langue, file.getPath());
	        return true;
	    }
	}
	
	public boolean hasCorpus(String filePath){
	    return XMLUtils.hasCorpus(filePath);   
	}
	
	/** Get language set 
	 * @return */
	public  ArrayList<String> getLangueList(){
	    return new ArrayList<String>(XMLUtils.getLangueList().keySet());
	}
	
	@Override
	public void learnFromFile(String langue, String filePath) 
	        throws FileNotFoundException, EncodingAlgorithmException, IOException{
		if(!XMLUtils.hasLangue(langue)) addLangue(langue);
		
	    XMLUtils.addCorpus(langue, filePath);
	    Bigrams newCorpus = Bigrams.getBigrams(new File(filePath));
	    languesList.get(langue).addFrom(newCorpus);
	    languesList.get(langue).toJson(Propriete.PATH_PROFIL + langue + ".json");
	}

	public static Apprentisage getInstance(){
	    if(apprentisage == null)
            try {
                apprentisage = new Apprentisage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        return apprentisage; 
	}
	
	@Override
	public Map<String, Double> analysis(String str) {
	    Map<String, Double> resultat =
	            new HashMap<String,Double>();
	    
	    Bigrams input = Bigrams.getBigrams(str);
	    
		for (Entry<String, Bigrams> langue : languesList.entrySet()) {
		    
		    resultat.put(langue.getKey(),langue.getValue().similarity(input));
	          System.out.println(resultat);
		    
        }
		
		return resultat.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1,  
	                LinkedHashMap::new
	              ));
	}
	
	@Override
	public String toString() {
		StringBuilder resultat = new StringBuilder();
		for (Entry<String, Bigrams> langue : languesList.entrySet()) {
			resultat.append(langue.getKey() + ":" + langue.getValue() + "\n");
		}
		return resultat.toString();
	}
	
	public Map<String, Bigrams> getBigrams() {
		return languesList;
	}


}
