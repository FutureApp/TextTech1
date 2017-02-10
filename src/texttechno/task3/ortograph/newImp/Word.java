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

	public Word(String name) {
		super();
		this.name = name;
		this.wordsInRange = new HashMap<>();
		totalConnections = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String showValueInLine() {
		String result = "";
		for (Entry<String, Integer> entry : wordsInRange.entrySet()) {
			String value = String.format(" (%s,%d)", entry.getKey(), entry.getValue());
			result = result + value;
		}
		return result;
	}

	public void updateOcc() {
		physicalOccurrence += 1;
	}

	public Integer getPhysicalOccurrence() {
		return physicalOccurrence;
	}
	public Integer getTotalConnections() {
		return totalConnections;
	}
	public Integer getConnectionsBetween(String keyWord){
		return wordsInRange.get(keyWord);
	}

	public int calcTotalConnections() {
		int result =0;
		for (Entry<String, Integer> elem : wordsInRange.entrySet()) {
			result += elem.getValue();
		}
		return result;
	}
	public ArrayList<String> getFollowers(){
		ArrayList<String> followedNodesFromPrime = new ArrayList<>();
		wordsInRange.forEach((a, b) -> {
			followedNodesFromPrime.add(a);
		});
		return followedNodesFromPrime;
		
	}
}
