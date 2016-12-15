package comphuman.task1.naminggame;

import java.util.ArrayList;
import java.util.Random;

public class SingleObjectAgents {

	Integer ID;
	ArrayList<Integer> wordPool;
	ArrayList<Integer> beforeList;
	Random random;

	/**
	 * Creates a simple agent. Agent could handle only one item.
	 * 
	 * @param ID
	 *            The ID of the agent.
	 */
	public SingleObjectAgents(Integer ID) {
		super();
		wordPool = new ArrayList<>();
		beforeList = new ArrayList<>();
		random = new Random();
		this.ID = ID;
	}

	/**
	 * Returns the word list which the agent currently has.
	 * 
	 * @return The wordpool of the agent.
	 */
	public ArrayList<Integer> getWordPool() {
		return wordPool;
	}

	/**
	 * Force the agent to say something. If the agent has couple word(at least
	 * 2) in the word pool, one of these word will be said. Calc is randomly. If
	 * the agent don't have any word in his pool, he will creates a new word by
	 * his own. This word is generated randomly on the range of INTEGERS.
	 * 
	 * @return
	 */
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

	/**
	 * Creates a new word. Calc is randomly and based on the value range of
	 * INTEGERS
	 * 
	 * @return Returns a word.
	 */
	private Integer createNewWord() {
		return random.nextInt();
	}

	/**
	 * This method will look at word pool if a practical is in the word pool
	 * list. If so, the agent will drop any other word except the word he was
	 * listen too.
	 *
	 * @param word
	 * Word which should be looked up.
	 * @return
	 * A list words which are dropped because the agent recognized the given word.
	 */
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
}
