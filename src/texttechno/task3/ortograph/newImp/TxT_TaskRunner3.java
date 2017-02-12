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

	// ---
	// Change that TODO
	// static String[] arg = {"TextTechno/03Task/ressources/testSmall.tei"};
	// static String[] arg = {
	// "TextTechno/03Task/ressources/kafkaTextImagerOut.tei" };
	static String[] arg = { "TextTechno/03Task/ressources/test.tei" };
	// ----

	static String encoding = Encoding.getDefaultEncoding();

	static String resultDir = "txt/result";
	static String articleName;

	/**
	 * Entry-point of the application.
	 * 
	 * @param args
	 *            Only args[0] - Link to a german wiki article.
	 */
	public static void main(String[] args) {
		System.out.println("New Runner");
		String hot = new File("TXT1_").getAbsolutePath();
		// arg = args;
		validateAmountOfGivenInput();
		checkIfFileExists(arg[0]);
		cleanResultDir(resultDir);

		RawDataTei rawData = new RawDataTei(new File(arg[0]));
		ArrayList<StringTuple3> dataOfInterest = rawData.getDataOfInterest();
		KokkurenzList list = new KokkurenzList(dataOfInterest);
		String showTuples = list.showTuples();
		list.genereateWordList();
		HashMap<String, IntegerSignature> calcRateSignatureForAllWords = list.calcRateSignatureForAllWords();

		HashMap<String, IntegerSignature> calcContingencyTable = list
				.calcContingencyTable(calcRateSignatureForAllWords.keySet());
		HashMap<String, FloatSignature> calcExpectedValue = list.calcExpectedValue(calcContingencyTable);
		HashMap<String, Double> calcLogLikelihoodValues = list.calcLogLikelihoodValues(calcContingencyTable,
				calcExpectedValue);

//		Double avgWeight = list.calcAvgWeight(calcLogLikelihoodValues);

		NetworkMatrix matrix = new NetworkMatrix(calcLogLikelihoodValues, list);
		matrix.generateNetworkMatrix();
		ArrayList<Nodes> nodes = matrix.calcNodesClusterWeight();
	
		
//		Double calcClusterWeight = matrix.calcClusterWeight(nodes);

		Writer.delAndWrite(new File(hot + "IdentData.txt"), showTuples);
		Writer.delAndWrite(new File(hot + "RateSignature.txt"), calcRateSignatureForAllWords);
		Writer.delAndWriteHash(new File(hot + "LogLike.txt"), calcLogLikelihoodValues);
		Writer.delAndWriteNodeList(new File(hot + "ClusterNodes.txt"), nodes);
		printFinish();
	}

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
		if (arg.length < 1) {
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
}
