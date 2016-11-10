package texttechno.task1.process;

import java.util.ArrayList;

import texttechno.task1.calculators.MATTRCalculator;
import texttechno.task1.calculators.TTRCalculator;
import texttechno.task1.prototype.types.Text;
import texttechno.task1.prototype.types.TupelIS;
import xgeneral.modules.SaveResults;

public class ProcessingTask {
	ArrayList<Text> listOfTexts;
	String defaultPathToSave;
	private SaveResults saver;
	String encoding;

	public ProcessingTask(ArrayList<Text> listOfTexts, String defaultPathToSave, String usedEncoding) {
		this.listOfTexts = listOfTexts;
		this.defaultPathToSave = defaultPathToSave;
		this.encoding = usedEncoding;
		saver = new SaveResults(encoding);
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
	 * Removes every characters which is not letter from all given texts and
	 * initializes the field <cleanText> in class Text.
	 * 
	 * @see Text#Text(java.io.File, String) Example: <hello world.> -> <hello
	 *      world>
	 * 
	 */
	public void cleanAllTextsOnlyLetters() {

		for (Text text : listOfTexts) {
			text.removeNonLetters();
		}

	}

	/**
	 * Prints all given texts in "clean" version. This means, that all
	 * non-letters are removed. Attention: Before you run this, be sure that all
	 * texts have a "clean" version already. It is recommanded that you run this
	 * method before: {@link #cleanAllTextsOnlyLetters()
	 * cleanAllTextsOnlyLetters}
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
			text.saveAllSortedTokensByAlpha(defaultPathToSave + text.getTextNameWithoutSuffix() + "/");
		}
	}

	public ArrayList<TupelIS> getFirstKHighestCountsPerTextMixed(Integer k) {
		ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
		for (Text text : listOfTexts) {
			arrayOfTokenTupels = text.getFirstKHighestTokensMixed(k);
		}
		for (TupelIS tupelIS : arrayOfTokenTupels) {
			System.out.println(tupelIS.key + " " + tupelIS.Value);
		}

		return arrayOfTokenTupels;
	}

	public ArrayList<TupelIS> getFirstKHighestCountsPerTextLower(Integer k) {
		ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
		for (Text text : listOfTexts) {
			arrayOfTokenTupels = text.getFirstKHighestTokensLower(k);
		}
		for (TupelIS tupelIS : arrayOfTokenTupels) {
			System.out.println(tupelIS.key + " " + tupelIS.Value);
		}

		return arrayOfTokenTupels;
	}

	public void saveFirstKHighestCountsPerTextLower(Integer k) {
		for (Text text : listOfTexts) {
			ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
			arrayOfTokenTupels = text.getFirstKHighestTokensLower(k);
			saver.saveTupelIS(arrayOfTokenTupels, defaultPathToSave+"/Result_"+text.getTextNameWithoutSuffix(), "LowerTop" + k);
		}
		
	}
	public void saveFirstKHighestCountsPerTextUpper(Integer k) {
		for (Text text : listOfTexts) {
			ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
			arrayOfTokenTupels = text.getFirstKHighestTokensUpper(k);
			saver.saveTupelIS(arrayOfTokenTupels, defaultPathToSave+"/Result_"+text.getTextNameWithoutSuffix(), "UpperTop" + k);
		}
		
	}
	public void saveFirstKHighestCountsPerTextMixed(Integer k) {
		for (Text text : listOfTexts) {
			ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
			arrayOfTokenTupels = text.getFirstKHighestTokensMixed(k);
			saver.saveTupelIS(arrayOfTokenTupels, defaultPathToSave+"/Result_"+text.getTextNameWithoutSuffix(), "MixedTop" + k);
		}
		
	}

	public ArrayList<TupelIS> getFirstKHighestCountsPerTextUpper(Integer k) {
		ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
		for (Text text : listOfTexts) {
			arrayOfTokenTupels = text.getFirstKHighestTokensUpper(k);
		}
		for (TupelIS tupelIS : arrayOfTokenTupels) {
			System.out.println(tupelIS.key + " " + tupelIS.Value);
		}

		return arrayOfTokenTupels;
	}

	public void saveTTRPerText() {
		
		for (Text text : listOfTexts) {
			TTRCalculator ttr = text.getTTRCalculator();
			saver.saveTTR(ttr, defaultPathToSave+"/Result_"+text.getTextNameWithoutSuffix(), "TTR-Value");
		}
		
	}

	public void calculateTTRPerText() {
		for (Text text : listOfTexts) {
			text.calculateTTR(text.getTokensSortedMixedCase());
		}
	}

	public void saveMATTRPerText(Integer windowSize) {
		for (Text text : listOfTexts) {
			MATTRCalculator MAttr = new MATTRCalculator(text.getTextTokens(), windowSize);
			MAttr.calcMATTR();
			saver.saveMATTR(MAttr, defaultPathToSave+"/Result_"+text.getTextNameWithoutSuffix(), "MATTR-Value");
		}
		
	}

}
