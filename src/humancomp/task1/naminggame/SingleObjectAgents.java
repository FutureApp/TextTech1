package humancomp.task1.naminggame;

import java.util.ArrayList;
import java.util.Random;

public class SingleObjectAgents {

	public ArrayList<Integer> getWordPool() {
		return wordPool;
	}

	Integer ID;
	ArrayList<Integer> wordPool;
	ArrayList<Integer> beforeList;
	Random random;

	public SingleObjectAgents(Integer ID) {
		super();
		wordPool = new ArrayList<>();
		beforeList = new ArrayList<>();
		random = new Random();
		this.ID = ID;
	}

	public Integer saySomething() {
		Integer wordToSay;
		if (wordPool.size() == 0) {
			wordToSay = createNewWord();
			wordPool.add(wordToSay);
		} else {
			wordToSay = wordPool.get(random.nextInt(wordPool.size()));
		}
		return wordToSay;
	}

	private Integer createNewWord() {
		return random.nextInt();
	}

	public ArrayList<Integer> doYouKnow(Integer word) {
		ArrayList<Integer> listDroped = new ArrayList<>();

		if (wordPool.contains(word)) {
			listDroped.addAll(wordPool);
			wordPool.clear();
			wordPool.add(word);
		} else {
			wordPool.add(word);
		}
		return listDroped;
	}

	public ArrayList<Integer> getBeforeList() {
		return beforeList;
	}

}
