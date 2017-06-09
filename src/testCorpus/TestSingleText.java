package testCorpus;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;

import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;

import corpus.Corpus;
import main.XMLUtils;



public class TestSingleText extends ITestCorpus {
	private Map<String, Double> result = null; 
	private String text;
	public TestSingleText(Corpus c) {
		super(c);
		this.text = null;
	}
	
	/*** Set the text which will be detected
	 * @param msg the text will be detected
	 * */
	public void setText(String msg) {
		this.text = msg;
	}
	
	/*** Analyze the text 
	 * @return 
	 * */
	@Override
	public  void doJob() {
	    result = this.corpus.analysis(text);
	}
	
	public String testFile(String filePath) throws EncodingAlgorithmException{
	    File file = new File(filePath);
	    try(FileInputStream fi = new FileInputStream(file);
	        InputStreamReader ir = new InputStreamReader(fi,XMLUtils.detectEncoding(file));
	        BufferedReader in = new BufferedReader(ir) ){
	        String inputLine = null;
	        StringBuilder text = new StringBuilder();
	        
	        while(!((inputLine=in.readLine())==null)){
	            text.append(inputLine);
	        }

	       setText(text.toString());
	       doJob();
	       return Collections
	               .max(result.entrySet(), Map.Entry.comparingByValue())
	               .getKey();     
	    } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    return null;
	}
	/*** 
	 * 
	 *
	 */
	public Map<String, Double> getResult() {
	    return result;
	}

    @Override
    public void showResult() {
        result.toString();
        
    }


}
