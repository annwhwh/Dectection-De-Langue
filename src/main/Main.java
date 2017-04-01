package main;

import corpus.Langue;
import corpus.TestCorpus;
import corpus.TestCorpusGroup;

public class Main {
	public static void main(String[] args) {

		// table of PPT page 7
		TestCorpus t = new TestCorpus(c);
		t.add("texte 1");
		t.add("texte 2");
		t.add("texte 3");
		t.add("texte 4");
		t.doJob();
		t.showResult();

		// table 2
		TestCorpusGroup tcg = new TestCorpusGroup(c);
		tcg.setLangue(Langue.ANGLAIS);
		tcg.add("this should be english");
		tcg.add("this should be english");
		tcg.add("this should be english");
		tcg.add("this should be english");
		tcg.add("this should be french", Langue.FRANSAIS);
		tcg.add("this should be english");
		
		tcg.setLangue(Langue.FRANSAIS);
		tcg.add("this should be french");
		tcg.add("this should be french");
		tcg.add("this should be french");
		tcg.add("this should be french");
		
		tcg.doJob();
		tcg.showResult();
	}
}
