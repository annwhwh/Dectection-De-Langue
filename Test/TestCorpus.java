package Test;

import java.util.HashMap;

public class TestCorpus extends ITestCorpus {
	private HashMap<String, Double> result;
	private String msg;//cible de test, ex une phrase
	
	public TestCorpus(Corpus c) {
		super(c);
		this.result = new HashMap<String, Double>();
	}
	
	public void setMsg(String str) {
		this.msg = str;
	}

	@Override
	public void doJob() {
		if(this.msg == null) {
			throw new NullPointerException("msg can't be null");
		}
		this.result = this.corpus.analysis(this.msg);
	}

	@Override
	public void showResult() {
		// to do
	}
}
