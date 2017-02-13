package texttechno.task3.ortograph.newImp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.Writer;

public class TxT_TaskRunner3 {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();
	public static String resultDir = "result";
	static String articleName;

	/**
	 * Entry-point of the application.
	 * 
	 * @param args
	 *            Only args[0] - Link to a german wiki article.
	 */
	public static void main(String[] args) {
		// Verify-Input
		String hot = new File("TXT1_").getAbsolutePath();
		arg = args;
		validateAmountOfGivenInput();
		validateInput();
		checkIfFileExists(arg[2]);
		cleanResultDir(resultDir);

		/*
		 * --------------------- Start of analysis --------------------
		 */

		// Start of preparation
		System.out.println("--- Prepare-phase ---");
		RawDataTei rawData = new RawDataTei(new File(arg[2]));
		ArrayList<StringTuple3> dataOfInterest = rawData.getDataOfInterest();
		KokkurenzList list = new KokkurenzList(dataOfInterest);
		String showTuples = list.showTuples();
		list.genereateWordList();

		// Start of 'heavy' analysis
		System.out.println("--- Heave-analysis Phase ---");
		HashMap<String, IntegerSignature> calcRateSignatureForAllWords = list.calcRateSignatureForAllWords();
		HashMap<String, IntegerSignature> calcContingencyTable = list
				.calcContingencyTable(calcRateSignatureForAllWords.keySet());
		HashMap<String, FloatSignature> calcExpectedValue = list.calcExpectedValue(calcContingencyTable);
		HashMap<String, Double> calcLogLikelihoodValues = list.calcLogLikelihoodValues(calcContingencyTable,
				calcExpectedValue);

		Double avgWeight = list.calcAvgWeight(calcLogLikelihoodValues);

		// Generation of network-matrix
		NetworkMatrix matrix = new NetworkMatrix(calcLogLikelihoodValues, list);
		matrix.generateNetworkMatrix();
		ArrayList<Nodes> nodes = matrix.calcNodesClusterWeight();

		// Double calcClusterWeight = matrix.calcClusterWeight(nodes);

		/*
		 * ------------------ Saving all informations --------------------
		 */
		System.out.println("--- Storing-Phase ---");
		Writer.delAndWrite(new File("log/IdentData.txt"), showTuples);
		Writer.delAndWrite(new File("log/RateSignature.txt"), calcRateSignatureForAllWords);
		Writer.delAndWriteHash(new File("log/LogLike.txt"), calcLogLikelihoodValues);
		Writer.delAndWriteNodeList(new File(resultDir+"/ClusterNodes.txt"), nodes);
		Writer.saveResult(new File(resultDir + "/resultsCalc.txt"), matrix);

		/*
		 * ------------------ Viz. of results --------------------
		 */

		System.out.println("--- Visualisation-Phase ---");
		NodeFilter nodeFilter = new NodeFilter(nodes);
		ArrayList<Nodes> filteredNodes = nodeFilter.filter(Integer.parseInt(arg[0]), Integer.parseInt(arg[1]));
		VizResults viz = new VizResults(filteredNodes, new File(resultDir + "/resultGraph"), nodes);
		viz.startViz();
		printFinish();
	}

	/**
	 * Checks if the parameter meets the requirements;
	 */
	private static void validateInput() {
		Boolean systemError = false;
		if (!containsOnlyNumbers(arg[0])) {
			SystemMessage.eMessage(String.format(
					"First argument contains more then only number. Execution will stop. Change the value to meet the requirements.Given<%s>",
					arg[0]));
			systemError = true;
		}
		if (!containsOnlyNumbers(arg[1])) {
			SystemMessage.eMessage(String.format(
					"Second argument contains more then only number. Execution will stop. Change the value to meet the requirements.Given<%s>",
					arg[1]));
			systemError = true;
		}
		if (Integer.parseInt(arg[0]) < 0) {
			SystemMessage.eMessage(String.format(
					"First argument is ne negative number.Negative numbers arn't support at this point.Given<%s>",
					arg[0]));
			systemError = true;
		}
		if (systemError) {
			SystemMessage.eMessage("Execution is stopped");
			System.exit(1);
		}
	}

	/**
	 * Checks if a given path correlate to a file.
	 * 
	 * @param pathToFile
	 *            Path to file.
	 */
	private static void checkIfFileExists(String pathToFile) {
		File file = new File(pathToFile);
		System.out.println();
		if (!file.exists()) {
			SystemMessage.eMessage("File doesn't exists. Path given <" + pathToFile + ">");
			System.exit(1);
		}
	}

	/**
	 * Prints the finish-message.
	 */
	private static void printFinish() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Programm has finished!!!");
		System.out.println("Feel free to close all open windows and/or explore the results.");
	}

	/**
	 * Cleans the location of the result-dir.
	 * 
	 * @param dir
	 *            Dir where to save the results.
	 */
	private static void cleanResultDir(String dir) {
		File file = new File(dir);
		if (!file.exists())
			file.mkdirs();
		try {
			FileUtils.cleanDirectory(new File(dir));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	private static void validateAmountOfGivenInput() {
		if (arg.length < 3) {
			SystemMessage.eMessage("More input is needed.");
			System.out.println();
			for (int i = 0; i < arg.length; i++) {
				System.out.printf("Argument %d: %s", i, arg[i]);
				System.out.println();
			}
			usage();
			System.exit(2);
		}
	}

	/**
	 * Prints the usage
	 */
	private static void usage() {
		System.out.println("---------- Usage ----------");
		System.out.println("java -jar <name of jar>.jar <wikipedia url to article>");
	}

	/**
	 * Check if a given string contains only numbers. Supports negativ
	 * numbers,too.
	 * 
	 * @param str
	 *            String for verification.
	 * @return True - Contains only numbers.
	 */
	private static boolean containsOnlyNumbers(String str) {
		Boolean isNumber = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				if (str.startsWith("-")) {
					isNumber = true;
				} else {
					isNumber = false;
				}
			}
		}
		return isNumber;
	}
}
