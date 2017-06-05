package testCorpus;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;

import corpus.Corpus;



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
	
	public String testFile(String filePath){
	    File file = new File(filePath);
	    try{
	        
	        String text = new String(
	                Files.readAllBytes(file.toPath())
	                , StandardCharsets.UTF_8);
	       setText(text);
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
