package corpus;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import corpus.Apprentisage;

public class ApprentisageTest {
	static Apprentisage apprentisage;
	@BeforeClass
	public static void setUpBeforeClass(){
			try {
				apprentisage = new Apprentisage();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApprentissage() throws FileNotFoundException {

	}

	@Test
	public void testLearnFromFile() throws FileNotFoundException, UnsupportedEncodingException{
		 apprentisage.learnFromFile(Langue.ANGLAIS, 
				 Propriete.PATH_CORPUS_EN+"test.txt");
		 System.out.println("The analyse finished:");
		 System.out.println(apprentisage.getInfo());
	}
	@Test
	public void testExportModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testImportModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testAnalysisString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAnalysisStringString() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetSetFromFile() throws FileNotFoundException{
		HashSet<String> alphabet = Apprentisage.getSetFromFile();
		HashSet<String> alphabetTest;
		System.out.println(alphabet);
		System.out.println(alphabet.size());
		
		String path = "res" + File.separator + "alphabet" + File.separator;
		File file = new File(path);
		File[] list = file.listFiles();
		
		for(int i = 0; i< list.length; i++){
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(list[i]));
			alphabetTest = gson.fromJson(reader,new TypeToken<HashSet<String>>() {}.getType());
			
			for (String x : alphabetTest) {
				assertTrue(alphabet.contains(x));
			}
		}
	}
	
	@Test
	public void testGeneratorBigram() throws FileNotFoundException {
		HashSet<String> alphabet = Apprentisage.getSetFromFile();
		HashMap<String, Integer> bigram = Apprentisage.generatorBigram(alphabet);
		System.out.println(bigram);
		assertEquals(alphabet.size()*alphabet.size(),bigram.size());
	}

}
