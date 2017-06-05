package corpus;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sun.org.apache.xpath.internal.axes.SubContextList;

import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class ApprentisageTest {
	static Apprentisage apprentisage;
	@BeforeClass
	public static void setUpBeforeClass(){
			apprentisage = Apprentisage.getInstance();
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
	    File file = new File(Propriete.PATH_CORPUS);
	    File[] list = file.listFiles();
	    for (File langue : list) {
	        File[] subList = langue.listFiles();
	        for (File file2 : subList) {
	            System.out.println("Analyzer "  + file2.getAbsolutePath());
	            if(apprentisage.hasCorpus(file2.getAbsolutePath())) continue;
                apprentisage.learnFromFile(langue.getName(), file2.getAbsolutePath());
            }
        }
		 System.out.println("The analyse finished:");
		 System.out.println(apprentisage.toString());
	}

	@Ignore
	public void testAnalysisString() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testAnalysisStringString() {
		fail("Not yet implemented");
	}

	

}
