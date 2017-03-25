package Test;


public abstract class ITestCorpus {
	protected Corpus corpus;
	
	public ITestCorpus(Corpus c) {
		this.corpus = c;
	}

	public abstract void doJob(); 
	public abstract void showResult();
}
