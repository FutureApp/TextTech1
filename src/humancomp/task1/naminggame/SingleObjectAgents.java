package humancomp.task1.naminggame;

import java.util.ArrayList;
import java.util.Random;

public class SingleObjectAgents {

	public ArrayList<Integer> getWordPool() {
		return wordPool;
	}

	Integer ID;
	ArrayList<Integer> wordPool;
	ArrayList<Integer> prevSet;
	ArrayList<Integer> removeMents;
	Random random;

	public SingleObjectAgents(Integer ID) {
		super();
		wordPool = new ArrayList<>();
		prevSet = new ArrayList<>();
		removeMents =new ArrayList<>();
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

	public Boolean doYouKnow(Integer word) {
	Boolean wordPoolContainsWord = false;
	
	if(wordPool.contains(word)){
		wordPoolContainsWord = true;
		prevSet.clear();
		prevSet.addAll(wordPool);
		wordPool.clear();
		wordPool.add(word);
		removeMents = prevSet;
		removeMents.remove(word);
		
	}
	else{
		wordPoolContainsWord = false;
		wordPool.add(word);
		
	}
	return wordPoolContainsWord;
	}

	public ArrayList<Integer> getLastRemovements() {
		return removeMents;
	}

}
