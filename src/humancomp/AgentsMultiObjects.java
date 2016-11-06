package humancomp;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

public class AgentsMultiObjects {

	Map<String, String> wordPool;
	Random randome = new Random();

	public AgentsMultiObjects() {
		super();
		wordPool = new TreeMap<>();
	}

	public Boolean containingWordsForObject(String objectName) {
		Boolean foundAtLeastOneEntry = false;
		if (wordPool.containsKey(objectName)) {
			foundAtLeastOneEntry = true;
		}
		return foundAtLeastOneEntry;
	}

	public Boolean containsSameWordForObject(String word, String objectName) {
		Boolean wordForObjectNameFound = false;
		if (wordPool.containsKey(objectName)) {
			String wordPoolFromObject = wordPool.get(objectName);
			String[] wordsSplitted = wordPoolFromObject.split("[|]");
			for (String singleWord : wordsSplitted) {
				if (singleWord.equals(word)) {
					wordForObjectNameFound = true;
					break;
				}
			}
		}

		return wordForObjectNameFound;
	}

	public Message declareWordForObject() {
		ArrayList<String> keys = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		Message message;

		for (Entry<String, String> entry : wordPool.entrySet()) {
			keys.add(entry.getKey());
			values.add(entry.getValue());
		}
		Integer keyIndex = randome.nextInt(keys.size());
		String[] possibleValues = values.get(keyIndex).split("[|]");
		String declaredName = possibleValues[randome.nextInt(possibleValues.length)];
		return new Message(keys.get(keyIndex), declaredName);
	}

	private String createNewName() {
		Random randome = new Random();
		String newName = randome.nextInt() + "";
		return newName;
	}
}
