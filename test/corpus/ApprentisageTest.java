package corpus;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

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
	public void testGeneratorBigram() throws FileNotFoundException {
		HashSet<String> alphabet = utils.FileReaderUtils.getHashSetFromFile("res"+File.separator + "en_alphabet.json");
		assertEquals(alphabet.size(), 26);
		HashMap<String, Integer> bigram = Apprentisage.generatorBigram(alphabet);
		System.out.println(bigram);
		System.out.println(bigram.size());
		assertEquals(bigram.size(), 26*26);
	}

}
