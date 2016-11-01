package prototype;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class Text {

	File location;
	String enconding;

	Map<String, Integer> tokensSortedMixedCase = new TreeMap<>();
	NavigableMap<Integer, String> countOfTokensSortedMixedCase = new TreeMap<>();

	Map<String, Integer> tokensSortedLowerCase = new TreeMap<>();
	NavigableMap<Integer, ArrayList<String>> countOfTokensSortedLowerCase = new TreeMap<>();

	Map<String, Integer> tokensSortedUpperCase = new TreeMap<>();
	NavigableMap<Integer, String> countOfTokensSortedUpperCase = new TreeMap<>();

	Map<String, Integer> tokensOfNumbers = new TreeMap<>();
	NavigableMap<Integer, String> countOfNumbers = new TreeMap<>();

	String text;

	/**
	 * This is represents a clean version of a given text. This field will be
	 * initialized after calling {@link Text#removeNonLetters()
	 * removeNonLetters}
	 * 
	 * If you don't call the method before, this field will be empty. For
	 * further processing, you will need to fill this field. Most other methods
	 * depending on this field!
	 */
	String cleanText;
	List<String> textTokens;

	/**
	 * 
	 * @param textFile
	 * @param enconding
	 */
	public Text(File textFile, String enconding) {
		super();
		this.location = textFile;
		this.enconding = enconding;
	}

	public String getTextNameWithoutSuffix() {
		return getNameOfText().substring(0, getNameOfText().lastIndexOf("."));
	}

	public void loadText() {

		try {
			text = FileUtils.readFileToString(location, enconding);
		} catch (IOException e) {
			SystemMessage.eMessage("Couldn't load this file <" + location.getAbsolutePath() + ">");
			e.printStackTrace();
		}
	}

	public String getOriText() {
		return text;

	}

	public void printCleanText() {
		System.out.println(text);

	}

	public String getNameOfText() {
		return location.getName();

	}

	public String getLocation() {
		return location.getAbsolutePath();
	}

	public void removeNonLetters() {

		cleanText = text.replaceAll("[^a-zA-Zäöüß\\d]", " ").replace("\\n", " ").replace("\\d", " ")
				.replace("    ", " ").replace("   ", " ").replace("  ", " ").replace("  ", " ").replace("  ", " ")
				.trim();

	}

	public String getCleanText() {
		return cleanText;
	}

	public void saveText(String defaultPathToSave) {
		File locationToSave = new File(defaultPathToSave + this.getNameOfText());
		save(locationToSave, text);
	}

	public void saveCleanText(String defaultPathToSave) {
		File locationToSave = new File(defaultPathToSave + "CLEAN_" + this.getNameOfText());
		save(locationToSave, cleanText);
	}

	private void save(File file, String content) {
		try {
			FileUtils.write(file, content, enconding, false);
			System.out.println(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			SystemMessage.eMessage("Couldn't write to file <" + file.getAbsolutePath() + ">");
		}

	}

	private void addToFile(File file, String content) {
		try {
			FileUtils.write(file, content, enconding, true);
		} catch (IOException e) {
			e.printStackTrace();
			SystemMessage.eMessage("Couldn't write to file <" + file.getAbsolutePath() + ">");
		}

	}

	public void tokenizeText() {
		String[] tokens = text.split(" ");
		textTokens = Arrays.asList(tokens);
	}

	public void tokenizeCleanText() {
		String[] tokens = cleanText.split(" ");
		textTokens = Arrays.asList(tokens);
	}

	public void saveTokens(String defaultPathToSave) {
		File locationToSave = new File(defaultPathToSave + "TOKENS_" + this.getNameOfText());
		if (locationToSave.exists())
			FileUtils.deleteQuietly(locationToSave);
		for (String token : textTokens) {
			addToFile(locationToSave, token + System.lineSeparator());
		}
	}

	public void sortAllTokens() {

		for (String token : textTokens) {
			insertTokenIntoSpecificMaps(token);

		}
	}

	private void insertTokenIntoSpecificMaps(String token) {
		Boolean isLowerCase = false;
		Boolean isUpperCase = false;
		Boolean isNumber = false;
		if (('a' <= token.charAt(0) && token.charAt(0) <= 'z') || ('ß' <= token.charAt(0) && token.charAt(0) <= 'ü')) {
			isLowerCase = true;
		}
		if (('A' <= token.charAt(0) && token.charAt(0) <= 'Z') || ('Ä' <= token.charAt(0) && token.charAt(0) <= 'Ü')) {
			isUpperCase = true;
		}
		if (('0' <= token.charAt(0) && token.charAt(0) <= '9')) {
			isNumber = true;
		}

		if (!isLowerCase && !isUpperCase && !isNumber && !false) {
			SystemMessage
					.wMessage("Token <" + token + "> matches either upper nor lower case. This token will be ignored.");
			System.exit(0);
		} else if (isLowerCase && isUpperCase && isNumber && true) {
			SystemMessage.wMessage("Token <" + token + "> matches upper and lower case. This token will be ignored.");
			System.exit(0);
		} else {
			putToMixedCaseMap(token);
			if (isLowerCase)
				putToLowerCaseMap(token);
			if (isUpperCase)
				putToUpperCaseMap(token);
			if (isNumber)
				putToNumberMap(token);
		}
	}

	private void putToNumberMap(String token) {
		if (tokensOfNumbers.containsKey(token)) {
			tokensOfNumbers.put(token, tokensOfNumbers.get(token) + 1);
		} else {
			tokensOfNumbers.put(token, 1);
		}

	}

	private void putToUpperCaseMap(String token) {
		if (tokensSortedUpperCase.containsKey(token)) {
			tokensSortedUpperCase.put(token, tokensSortedUpperCase.get(token) + 1);
		} else {
			tokensSortedUpperCase.put(token, 1);
		}

	}

	private void putToLowerCaseMap(String token) {
		if (tokensSortedLowerCase.containsKey(token)) {
			tokensSortedLowerCase.put(token, tokensSortedLowerCase.get(token) + 1);
		} else {
			tokensSortedLowerCase.put(token, 1);
		}

	}

	private void putToMixedCaseMap(String token) {
		if (tokensSortedMixedCase.containsKey(token)) {
			tokensSortedMixedCase.put(token, tokensSortedMixedCase.get(token) + 1);
		} else {
			tokensSortedMixedCase.put(token, 1);
		}

	}

	public void saveAllSortedTokens(String defaultPathToSave) {
		File locationToSaveMixed = new File(defaultPathToSave + "TOKENSSORTED_MIXED");
		File locationToSaveLower = new File(defaultPathToSave + "TOKENSSORTED_LOWER");
		File locationToSaveUpper = new File(defaultPathToSave + "TOKENSSORTED_UPPER");
		File locationToSaveNums = new File(defaultPathToSave + "TOKENSSORTED_NUM");

		FileUtils.deleteQuietly(locationToSaveMixed);
		FileUtils.deleteQuietly(locationToSaveLower);
		FileUtils.deleteQuietly(locationToSaveUpper);
		FileUtils.deleteQuietly(locationToSaveNums);

		for (Map.Entry<String, Integer> mapEntry : tokensSortedMixedCase.entrySet()) {
			String content = "(" + mapEntry.getKey() + ", " + mapEntry.getValue() + ")";
			addToFile(locationToSaveMixed, content + System.lineSeparator());
		}

		for (Map.Entry<String, Integer> mapEntry : tokensSortedLowerCase.entrySet()) {
			String content = "(" + mapEntry.getKey() + ", " + mapEntry.getValue() + ")";
			addToFile(locationToSaveLower, content + System.lineSeparator());
		}
		for (Map.Entry<String, Integer> mapEntry : tokensSortedUpperCase.entrySet()) {
			String content = "(" + mapEntry.getKey() + ", " + mapEntry.getValue() + ")";
			addToFile(locationToSaveUpper, content + System.lineSeparator());
		}
		for (Map.Entry<String, Integer> mapEntry : tokensOfNumbers.entrySet()) {
			String content = "(" + mapEntry.getKey() + ", " + mapEntry.getValue() + ")";
			addToFile(locationToSaveNums, content + System.lineSeparator());
		}

	}

	public ArrayList<TupelIS> getFirstKHighestTokensMixed(Integer k) {
		ArrayList<TupelIS> kTop = new ArrayList<>();
		Integer inserted = 0;
		initialiseMixedTokenSortByValue();

		IterateOverTokenList: for (Entry<Integer, String> mapEntry : countOfTokensSortedMixedCase.descendingMap()
				.entrySet()) {

			Integer countID = mapEntry.getKey();
			String tokens = mapEntry.getValue();
			String[] token = tokens.split("[|]");

			for (int i = 0; i < token.length; i++) {
				if (inserted < k) {
					TupelIS tup = new TupelIS(countID, token[i]);
					kTop.add(tup);
					inserted++;
				} else {
					break IterateOverTokenList;
				}
			}
		}
		return kTop;

	}

	private void initialiseMixedTokenSortByValue() {
		for (Entry<String, Integer> mapEntry : tokensSortedMixedCase.entrySet()) {
			String token = mapEntry.getKey();
			Integer tokenCount = mapEntry.getValue();
			if (countOfTokensSortedMixedCase.containsKey(tokenCount)) {
				String tokensWithExplicitCount = countOfTokensSortedMixedCase.get(tokenCount);
				tokensWithExplicitCount = tokensWithExplicitCount + "|" + token;
				countOfTokensSortedMixedCase.put(tokenCount, tokensWithExplicitCount);
			} else {
				String tokensWithExplicitCount = token;
				countOfTokensSortedMixedCase.put(tokenCount, tokensWithExplicitCount);
			}
		}
	}

}
