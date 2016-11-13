package humancomp.task1.naminggame;

import java.util.ArrayList;
import java.util.Random;

public class AgentsOneObject {

	ArrayList<String> wordPool;
	Random randome;

	/**
	 * Generates a new agent with empty wordlist.
	 * 
	 */
	public AgentsOneObject() {
		super();
		wordPool = new ArrayList<>();
		randome = new Random();
	}

	/**
	 * Generates an agent with given wordlist.
	 * 
	 * @param wordPool
	 *            Wordlist which the agent should use.
	 */
	public AgentsOneObject(ArrayList<String> wordPool) {
		super();
		this.wordPool = wordPool;
		randome = new Random();
	}

	private Boolean wordExists(String word) {
		return wordPool.contains(word);
	}

	public SingleMessage saySomething() {
		String stringMessage = "";
		if (wordPool.size() <= 0) {
			stringMessage = generateNewWord();
			wordPool.add(stringMessage);
		} else {
			stringMessage = wordPool.get(randome.nextInt(wordPool.size()));

		}

		SingleMessage messsage = new SingleMessage(stringMessage);
		return messsage;
	}

	private String generateNewWord() {
		Integer randomeInt = randome.nextInt();
		return randomeInt + "";
	}

	public Boolean doYouKnow(String word) {
		Boolean isKnowing = wordExists(word);
		if (isKnowing) {
			wordPool.clear();
			wordPool.add(word);
		} else {
			wordPool.add(word);
		}
		return isKnowing;
	}

	public ArrayList<String> getAllKnowingWords() {
		return wordPool;
	}
}
