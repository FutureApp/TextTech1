package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import scala.Array;

public class Word {

	String name;
	HashMap<String, Integer> wordsInRange;
	Integer totalConnections;
	Integer physicalOccurrence = 0;

	/**
	 * The word-class
	 * 
	 * @param name
	 *            Unique-name.
	 */
	public Word(String name) {
		super();
		this.name = name;
		this.wordsInRange = new HashMap<>();
		totalConnections = 0;
	}

	/**
	 * Returns the unique name of the word.
	 * 
	 * @return The name of the word.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the word.
	 * 
	 * @param name
	 *            New name for the word.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Updates the list which hold the information of word which are in range.
	 */
	public void update(String lemma) {
		if (wordsInRange.containsKey(lemma)) {
			Integer currentValue = wordsInRange.get(lemma);
			wordsInRange.put(lemma, currentValue + 1);
			totalConnections++;
		} else {
			wordsInRange.put(lemma, 1);
			totalConnections++;
		}
	}

	/**
	 * Returns all words in range.
	 * 
	 * @return All words in range as String.
	 */
	public String showValueInLine() {
		String result = "";
		for (Entry<String, Integer> entry : wordsInRange.entrySet()) {
			String value = String.format(" (%s,%d)", entry.getKey(), entry.getValue());
			result = result + value;
		}
		return result;
	}

	/**
	 * Increases the occurrence-counter by +1;
	 */
	public void updateOcc() {
		physicalOccurrence += 1;
	}

	/**
	 * Returns the number of the physical occurrence.
	 * 
	 * @return Number of physical occurrence
	 */
	public Integer getPhysicalOccurrence() {
		return physicalOccurrence;
	}

	/**
	 * Returns the number of total connections to other words.
	 * 
	 * @return Amount of connections.
	 */
	public Integer getTotalConnections() {
		return totalConnections;
	}

	/**
	 * Returns the number of connections between this word and a given word.
	 * 
	 * @param keyWord
	 *            Name of second node.
	 * @return Number of connections.
	 */
	public Integer getConnectionsBetween(String keyWord) {
		return wordsInRange.get(keyWord);
	}

	/**
	 * Calcs the total number of connections of the word.
	 * 
	 * @return Amount of all connection.
	 */
	public int calcTotalConnections() {
		int result = 0;
		for (Entry<String, Integer> elem : wordsInRange.entrySet()) {
			result += elem.getValue();
		}
		return result;
	}

	/**
	 * Returns a list, which contains all followers of this word. Followers =
	 * All nodes which are connected with this word.
	 * 
	 * @return List containiong all followers.
	 */
	public ArrayList<String> getFollowers() {
		ArrayList<String> followedNodesFromPrime = new ArrayList<>();
		wordsInRange.forEach((a, b) -> {
			followedNodesFromPrime.add(a);
		});
		return followedNodesFromPrime;
	}
}
