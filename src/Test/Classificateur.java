package Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.sun.activation.registries.MailcapParseException;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Classificateur implements Corpus {
	private Map<String, Integer> anglais = new HashMap<>();
	private Map<String, Integer> italian = new HashMap<>();
	private Map<String, Integer> francais = new HashMap<>();
	private Map<String, Integer> allemand = new HashMap<>();
	
	private HashSet<String> en_alphabet = new HashSet<>;
	private HashSet<String> fr_alphabet = new HashMap<>;
	
	public public Classificateur() {

	}
	@Override
	public void apprentissage(Langue type, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		Map<String, Integer> targetMap;
		if(type == Langue.ANGLAIS) targetMap = this.anglais;
		else if(type == Langue.FRANSAIS) targetMap = this.francais;
		else if(type == Langue.ALLEMAND) targetMap = this.allemand;
		else targetMap = this.italian;
		
		File file = new File(filePath);
		if(!file.exists() || file.isDirectory()){
			throw new FileNotFoundException("Can not find the file:" + file.getAbsolutePath());
		}
		
		try(FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
		BufferedReader in = new BufferedReader(inputStreamReader);){
			String inputLine = null;
			while((inputLine = in.readLine())!=null)
				
			
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

	@Override
	public HashMap<String, Double> analysis(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Langue analysis(String str, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
