package corpus;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCorpusElement extends ITestCorpus{
	private Langue langue;
	private ArrayList<String> contenu;//qqch sera tster.
	private HashMap<String, Langue> miss;
	private long time;

	//creer des expaces pour mettre des resultats
	public TestCorpusElement(Corpus c, Langue langue) {
		super(c);
		this.contenu = new ArrayList<String>();
		this.miss = new HashMap<String, Langue>();
		this.langue = langue;
	}

	//Ajouter les contenues qui testera apres
	public void add(String str) {
		this.contenu.add(str);
	}

	//combien element tester
	public int getNombre() {
		return this.contenu.size();
	}

	//les nombres successs
	public int getNombreOfSuccess() {
		return this.contenu.size() - this.miss.size();
	}

	public int getNombreOfFailure() {
		return this.miss.size();
	}

	public double getTime() {
		return this.time / 1000.0;
	}

	//precission d'une cologne de test
	public double getRateOfSuccess() {
		return (this.contenu.size() - this.miss.size()) * 100.0 / this.contenu.size();
	}

	@Override
	public void doJob() {
		long start = System.currentTimeMillis();
		for(int i = 0; i < this.contenu.size(); i++) {
			Langue l = this.corpus.analysis(this.contenu.get(i), this.langue);
			//pour varifier le test est correspondant que la langue--this.langue
			//si ils ne sont pas corresspant, on ajoute les dans le champe miss.
			if(this.langue != l) {			
				this.miss.put(this.contenu.get(i), l);
			}
		}
		this.time = System.currentTimeMillis() - start;
	}

	@Override
	//tous les tests faills, 
	public void showResult() {
		System.out.println(this.langue.name());
		for (String key : this.miss.keySet()) {  
			System.out.printf("%s: %s", key, this.miss.get(key));
		}
	}
}
