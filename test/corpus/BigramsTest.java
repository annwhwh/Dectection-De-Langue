package corpus;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;
import sun.net.www.content.audio.x_aiff;

public class BigramsTest extends Bigrams {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testBigrams() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddFrom() {
        fail("Not yet implemented");
    }

    @Test
    public void testSimilarity() {
        Bigrams inputLine1 = Bigrams.getBigrams("I come from China");
        Bigrams inputLine2 = Bigrams.getBigrams("Je viens de Chine");
        
        double resultat = inputLine1.similarity(inputLine2);
        System.out.println(inputLine1);
        System.out.println(inputLine2);
        System.out.println(resultat);
    }

    @Test
    public void testToString() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToJson() {
        String inputline = "I  came from chine,Ne haha comm jiji.";
        Bigrams bigrams = Bigrams.getBigrams(inputline);
        bigrams.toJson("josntest.json");
    }

    
    @Test
    public void testFileUri(){
        File file = new File("res/corpus/ALLEMAND/example2.txt");
        System.out.println(file.toURI());       
    }

}
