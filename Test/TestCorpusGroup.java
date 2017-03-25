package Test;

import java.util.ArrayList;

public class TestCorpusGroup extends ITestCorpus{
	private ArrayList<String> contenu;
	private String type;
	private String result;
	private long time;

	public TestCorpusGroup(Corpus c) {
		super(c);
		this.contenu = new ArrayList<String>();
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void add(String str) {
		this.contenu.add(str);
	}
	
	private double getTime() {
		return this.time / 1000.0;
	}

	@Override
	public void showResult() {
		// to do
	}

	@Override
	public void doJob() {
		if(this.type == null) {
			throw new NullPointerException("Type can't be null");
		}
		long start = System.currentTimeMillis();
		for(int i = 0; i < this.contenu.size(); i++) {
			this.result = this.corpus.analysis(this.contenu.get(i), this.type);
		}
		this.time = System.currentTimeMillis() - start;
	}

}
