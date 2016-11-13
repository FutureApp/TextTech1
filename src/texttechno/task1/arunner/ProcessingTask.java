package texttechno.task1.arunner;

import java.util.ArrayList;

import xgeneral.modules.SaveResults;

/**
 * Is the processing engine.
 * 
 * @author Michael Czaja
 *
 */
public class ProcessingTask {
	ArrayList<Text> listOfTexts;
	String defaultPathToSave;
	private SaveResults saver;
	String encoding;

	/**
	 * 
	 * @param listOfTexts
	 *            All texts which need to be analyzed.
	 * @param defaultPathToSave
	 *            Sets the output dir of the results.
	 * @param usedEncoding
	 *            Sets the encoding type.
	 */
	public ProcessingTask(ArrayList<Text> listOfTexts, String defaultPathToSave, String usedEncoding) {
		this.listOfTexts = listOfTexts;
		this.defaultPathToSave = defaultPathToSave;
		this.encoding = usedEncoding;
		saver = new SaveResults(encoding);
	}

	/**
	 * Starts the Processing. Loads Texts.
	 */
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
	 * Removes every characters which is not a letter. Has Impacts on all texts
	 * which should be analyzed.
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
	 * Prints all given texts in "clean" version. (This means, that all
	 * non-letters are removed). Attention: Before you run this, be sure that
	 * all texts have a "clean" version already. It is recommended that you run
	 * this method before: {@link #cleanAllTextsOnlyLetters()
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

	/**
	 * Creates the sorted list of tokens in the text data-struc. Has Impacts on
	 * all texts.
	 */
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

	/**
	 * Returns the top k keyword with corresponding count. This method uses the
	 * mixed map of counts.
	 * 
	 * @param k
	 *            Number of values which should be calc.
	 * @return Tuples of top k words with there counts.
	 */
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

	/**
	 * Returns the top k keyword with corresponding count. This method uses the
	 * lower map of counts.
	 * 
	 * @param k
	 *            Number of values which should be calc.
	 * @return Tuples of top k words with there counts.
	 */
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

	/**
	 * Saves the k top words for a text. Impacts on all texts. Calling this will
	 * calc the top k first. When this method will save the results of the
	 * 'fresh' run.
	 * 
	 * @param k
	 *            Top k value.
	 */
	public void saveFirstKHighestCountsPerTextLower(Integer k) {
		for (Text text : listOfTexts) {
			ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
			arrayOfTokenTupels = text.getFirstKHighestTokensLower(k);
			saver.saveTupelIS(arrayOfTokenTupels, defaultPathToSave + "/Result_" + text.getTextNameWithoutSuffix(),
					"LowerTop" + k);
		}

	}

	/**
	 * Saves the k top words for a text. Only list of words which starts with a
	 * upper character. Impacts on all texts. Calling this will calc the top k
	 * first. When this method will save the results of the 'fresh' run.
	 * 
	 * @param k
	 *            Top k value.
	 */
	public void saveFirstKHighestCountsPerTextUpper(Integer k) {
		for (Text text : listOfTexts) {
			ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
			arrayOfTokenTupels = text.getFirstKHighestTokensUpper(k);
			saver.saveTupelIS(arrayOfTokenTupels, defaultPathToSave + "/Result_" + text.getTextNameWithoutSuffix(),
					"UpperTop" + k);
		}

	}

	/**
	 * Saves the k top words for a text. Non case-sensitive. Impacts on all
	 * texts. * Calling this will calc the top k first. When this method will
	 * save the results of the 'fresh' run.
	 * 
	 * @param k
	 *            Top k value.
	 */
	public void saveFirstKHighestCountsPerTextMixed(Integer k) {
		for (Text text : listOfTexts) {
			ArrayList<TupelIS> arrayOfTokenTupels = new ArrayList<>();
			arrayOfTokenTupels = text.getFirstKHighestTokensMixed(k);
			saver.saveTupelIS(arrayOfTokenTupels, defaultPathToSave + "/Result_" + text.getTextNameWithoutSuffix(),
					"MixedTop" + k);
		}

	}

	/**
	 * Returns the top k keyword with corresponding count. This method uses the
	 * upper map of counts.
	 * 
	 * @param k
	 *            Number of values which should be calc.
	 * @return Tuples of top k words with there counts.
	 */
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

	/**
	 * Saves the TTR-Value for all texts. Value is based on the last TTR-Calc.
	 */
	public void saveTTRPerText() {

		for (Text text : listOfTexts) {
			TTRCalculator ttr = text.getTTRCalculator();
			saver.saveTTR(ttr, defaultPathToSave + "/Result_" + text.getTextNameWithoutSuffix(), "TTR-Value");
		}

	}

	/**
	 * Calcs every TTR for all texts.
	 */
	public void calculateTTRPerText() {
		for (Text text : listOfTexts) {
			text.calculateTTR(text.getTokensSortedMixedCase());
		}
	}

	/**
	 * Calcs every MATTR for all texts and saves them in the result dir.Calling
	 * this will trigger a new clac of the MATTR. When this method will save the results of
	 * the 'fresh' run.
	 * 
	 * @param windowSize
	 *            Windowssize which should be used for calc.
	 */
	public void saveMATTRPerText(Integer windowSize) {
		for (Text text : listOfTexts) {
			MATTRCalculator MAttr = new MATTRCalculator(text.getTextTokens(), windowSize);
			MAttr.calcMATTR();
			saver.saveMATTR(MAttr, defaultPathToSave + "/Result_" + text.getTextNameWithoutSuffix(), "MATTR-Value");
		}

	}

}
