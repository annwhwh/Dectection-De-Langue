package corpus;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCorpusGroup extends ITestCorpus{
	private HashMap<Langue, TestCorpusElement> tests;
	private Langue langue;
	
	public TestCorpusGroup(Corpus c) {
		super(c);
		this.tests = new HashMap<Langue, TestCorpusElement>();
	}
	
	public void setLangue(Langue langue) {
		this.langue = langue;
	}
	
	public void add(String str) {
		add(str, this.langue);
	}
	
	public void add(String str, Langue langue) {
		if(!this.tests.containsKey(langue)) {
			//si on a un deja un key, il renvoie true
			this.tests.put(langue, new TestCorpusElement(this.corpus, langue));
			//creer espace pour tester
			this.tests.get(langue).add(str);
		}
		else {
			this.tests.get(langue).add(str);
		}
	}

	@Override
	public void doJob() {
		//teste tous les graoupe de langue, car testecorpuselement est un groupe de langue, ex: un tableau contien des resultats de FR
		for(TestCorpusElement tce : this.tests.values()) {
			tce.doJob();
		}
	}

	@Override
	public void showResult() {
		//garder les ordres de langues, car map n'a pas d'ordre.
		ArrayList<Langue> langues = new ArrayList<Langue>();
		for(Langue l : this.tests.keySet()) {
			langues.add(l);
		}
		System.out.print("              ");
		for(int i = 0; i < langues.size(); i++) {
		      System.out.printf("%8s", langues.get(i).name());
		}
		System.out.println();
		for(int i = 0; i < langues.size(); i++) {
			System.out.printf("%8d", this.tests.get(langues.get(i)).getNombre());
		}
		System.out.println();
		for(int i = 0; i < langues.size(); i++) {
			System.out.printf("%8d", this.tests.get(langues.get(i)).getNombreOfSuccess());
		}
		System.out.println();
		for(int i = 0; i < langues.size(); i++) {
			System.out.printf("%8d", this.tests.get(langues.get(i)).getNombreOfFailure());
		}
		System.out.println();
		for(int i = 0; i < langues.size(); i++) {
			System.out.printf("%8d", this.tests.get(langues.get(i)).getRateOfSuccess());
		}
		System.out.println();
		for(int i = 0; i < langues.size(); i++) {
			System.out.printf("%8.2fs", this.tests.get(langues.get(i)).getTime());
		}
		for(TestCorpusElement tce : this.tests.values()) {
			tce.showResult();
			System.out.println();
		}
	}
}
