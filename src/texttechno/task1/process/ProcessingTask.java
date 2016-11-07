package texttechno.task1.process;

import java.util.ArrayList;

import texttechno.task1.prototype.types.Text;
import texttechno.task1.prototype.types.TupelIS;

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
	 * Removes every characters which is not letter from all given texts and initializes the field <cleanText> in class Text.
	 * @see Text#Text(java.io.File, String)
	 * Example: <hello      world.> -> <hello world>
	 * 
	 */
	public void cleanAllTextsOnlyLetters() {

		for (Text text : listOfTexts) {
			text.removeNonLetters();
		}

	}

	/**
	 * Prints all given texts in "clean" version. This means, that all non-letters are removed.
	 * Attention: Before you run this, be sure that all texts have a "clean" version already.
	 *It is recommanded that you run this method before: {@link #cleanAllTextsOnlyLetters() cleanAllTextsOnlyLetters}
	 */
	public void printAllCleanTexts() {
		for (Text text : listOfTexts) {
			System.out.println(text.getCleanText());
			System.out.println();
		}

	}

	public void saveAllTextsDefault(Integer pos) {
		for (Text text : listOfTexts) {
			text.saveText(defaultPathToSave + text.getTextNameWithoutSuffix() + "/");
		}

	}

	public void saveAllCleanTextsDefault(Integer pos) {
		for (Text text : listOfTexts) {
			text.saveCleanText(defaultPathToSave + text.getTextNameWithoutSuffix() + "/");
		}

	}

	public void tokenizeAllCleanTexts() {
		for (Text text : listOfTexts) {
			text.tokenizeCleanText();
		}
	}

	public void saveAllTokensOfAllTexts() {
		for (Text text : listOfTexts) {
			text.saveTokens(defaultPathToSave + text.getTextNameWithoutSuffix() + "/");
		}

	}

	public void sortAlphaAllTokens() {
		for (Text text : listOfTexts) {
			text.sortAllTokens();
		}

	}

	public void saveAllTokensSorted() {
		for (Text text : listOfTexts) {
			text.saveAllSortedTokens(defaultPathToSave + text.getTextNameWithoutSuffix() + "/");
		}

	}

	public ArrayList<TupelIS> getFirstKHighestCountsPerTextMixed(Integer k) {
		ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
		for (Text text : listOfTexts) {
			arrayOfTokenTupels = text.getFirstKHighestTokensMixed(k);
		}
		for (TupelIS tupelIS : arrayOfTokenTupels) {
			System.out.println(tupelIS.key +" "+ tupelIS.Value);
		}

		return null;
	}

}
