package testCorpus;

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
	 * */
	@Override
	public Object doInBackground() {
	    result = this.corpus.analysis(text);
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
