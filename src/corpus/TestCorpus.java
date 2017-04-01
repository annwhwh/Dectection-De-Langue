package corpus;

import java.util.ArrayList;
import java.util.HashMap;


public class TestCorpus extends ITestCorpus {
	private HashMap<String, HashMap<Langue, Double>> result;  // table in PPT page 7
	//HashMap<text, HashMap<langue, 0.5>

	public TestCorpus(Corpus c) {
		super(c);
		this.result = new HashMap<String, HashMap<Langue, Double>>();
	}
	
	public void add(String msg) {
		this.result.put(msg, new HashMap<Langue, Double>());
	}
	
	@Override
	public void doJob() {
		for (String key : this.result.keySet()) {  
			//key : les phrases testeront
			 this.result.put(key, this.corpus.analysis(key)); 
		}  
		// text1  <Fr, 0.4>  <En, 0.7>
	}

	@Override
	public void showResult() {
		ArrayList<Langue> langues = new ArrayList<Langue>();//tous les genres de langue ordonne.
		for(Langue l : Langue.values()) {
			langues.add(l);
		}
		System.out.print("              ");
		for(int i = 0; i < langues.size(); i++) {
		      System.out.printf("%8s", langues.get(i).name());
		}
		System.out.println();
		for (String key : this.result.keySet()) {
			System.out.printf("%s", key);
			for(int i = 0; i < langues.size(); i++) {
				System.out.printf("8.2f", this.result.get(key).get(langues.get(i)));
			}
			System.out.println();
		}
	}
}
