package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class FileReaderUtils {
	public static HashSet<String> getHashSetFromFile(String filename) throws FileNotFoundException{
		HashSet<String> alphabet = new HashSet<>();

		Gson gson = new Gson();
		File file = new File("res" + File.separator + "en_alphabet.json");

		JsonReader reader = new JsonReader(new FileReader(file));
		alphabet = gson.fromJson(reader,new TypeToken<HashSet<String>>() {}.getType());

		return alphabet;
	}
}
