package prototype;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Text {

	File location;
	String enconding;
	HashMap<String, Integer> wordMapCount;
	Map<String, Integer> sortByWord;

	String text;
	String cleanText;
	List<String> textTokens;

	public Text(File textFile, String enconding) {
		super();
		this.location = textFile;
		this.enconding = enconding;
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
				.replace("    ", " ").replace("   ", " ").replace("  ", " ").replace("  ", " ");
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

		// TODO "" don't match to anything
//		for (int i = 0; i < tokens.length; i++) {
//			if (!tokens[i].equals(""))
//				textTokens.add(tokens[i]);
//		}
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

}
