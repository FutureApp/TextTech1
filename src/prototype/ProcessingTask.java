package prototype;

import java.util.ArrayList;

public class ProcessingTask {
	ArrayList<Text> listOfTexts;
	String defaultPathToSave = "Result/Task/";

	public ProcessingTask(ArrayList<Text> listOfTexts) {
		this.listOfTexts = listOfTexts;
	}

	public void beginnProcessing() {

		for (Text text : listOfTexts) {
			text.loadText();
		}

	}

	public void printEveryOriText() {
		for (Text text : listOfTexts) {
			System.out.println(text.getOriText());
		}

	}

	public void printNameOfAllTexts() {
		for (Text text : listOfTexts) {
			System.out.println(text.getNameOfText());
		}

	}

	public void printAllAbsolutPathsOfTexts() {
		for (Text text : listOfTexts) {
			System.out.println(text.getLocation());
		}

	}

	/**
	 * Removes every non letters characters.
	 */
	public void cleanAllTextsOnlyLetters() {

		for (Text text : listOfTexts) {
			text.removeNonLetters();
		}

	}

	public void printAllCleanTexts() {
		for (Text text : listOfTexts) {
			System.out.println(text.getCleanText());
			System.out.println();
		}

	}


	public void saveAllTextsDefault(Integer pos) {
		for (Text text : listOfTexts) {
			text.saveText(defaultPathToSave);
		}

	}

	public void saveAllCleanTextsDefault(Integer pos) {
		for (Text text : listOfTexts) {
			text.saveCleanText(defaultPathToSave);
		}

	}

	public void tokenizeAllCleanTexts() {
		for (Text text : listOfTexts) {
			text.tokenizeCleanText();
		}
	}

	public void saveAllTokensOfAllTexts() {
		for (Text text : listOfTexts) {
			text.saveTokens(defaultPathToSave);
		}
		
	}

}
