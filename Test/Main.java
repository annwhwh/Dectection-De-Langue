package Test;

public class Main {
	public static void main(String[] args) {
		Corpus c = new Corpus();

		// table 1
		TestCorpus[] t = {
				new TestCorpus(c), 	
				new TestCorpus(c), 	
				new TestCorpus(c), 	
				new TestCorpus(c), 	
		};
		t[0].setMsg("texte 1");
		t[1].setMsg("texte 2");
		t[2].setMsg("texte 3");
		t[3].setMsg("texte 4");
		for(int i = 0; i < t.length; i++) {
			t[i].doJob();
		}
		for(int i = 0; i < t.length; i++) {
			t[i].showResult();
		}
		
		
		// table 2
		TestCorpusGroup[] tg ={
				new TestCorpusGroup(c),
				new TestCorpusGroup(c),
				new TestCorpusGroup(c),
				new TestCorpusGroup(c),
		};
		// en
		tg[0].setType("En");
		tg[0].add("texte 1");
		tg[0].add("texte 2");
		tg[0].add("texte 3");
		tg[0].add("texte 4");
		tg[0].add("texte 5");
		tg[0].add("texte 6");
		tg[0].add("texte 7");
		tg[0].add("texte 8");
		tg[0].add("texte 9");
		
		// fr
		tg[1].setType("Fr");
		tg[1].add("texte 1");
		tg[1].add("texte 2");
		tg[1].add("texte 3");
		tg[1].add("texte 4");
		tg[1].add("texte 5");
		tg[1].add("texte 6");
		tg[1].add("texte 7");
		tg[1].add("texte 8");
		tg[1].add("texte 9");
		
		// Italien
		tg[2].setType("It");
		tg[2].add("texte 1");
		tg[2].add("texte 2");
		tg[2].add("texte 3");
		tg[2].add("texte 4");
		tg[2].add("texte 5");
		tg[2].add("texte 6");
		tg[2].add("texte 7");
		tg[2].add("texte 8");
		tg[2].add("texte 9");
		
		// Allemand
		tg[3].setType("Al");
		tg[3].add("texte 1");
		tg[3].add("texte 2");
		tg[3].add("texte 3");
		tg[3].add("texte 4");
		tg[3].add("texte 5");
		tg[3].add("texte 6");
		tg[3].add("texte 7");
		tg[3].add("texte 8");
		tg[3].add("texte 9");
			
		for(int i = 0; i < tg.length; i++) {
			tg[i].doJob();
		}
		for(int i = 0; i < tg.length; i++) {
			tg[i].showResult();
		}
	}
}
