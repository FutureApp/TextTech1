package texttechno.task3.ortograph.old;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class TxT_TaskRunner3OLD {

	static String[] arg;
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
		System.out.println("key");
		arg = args;
		validateAmountOfGivenInput();
		checkIfFileExists(arg[0]);
		cleanResultDir(resultDir);

		RawDataNewOLD docTei = new RawDataNewOLD(new File(arg[0]));
		NetworkMatrixOLD matrix = new NetworkMatrixOLD(docTei.transformToTupleOfThree());
//		matrix.printMatrixRawData();
		matrix.writeMatrixRawData(new File("matrixRawData.txt"));
		matrix.writeMatrixRawDataCleanedVersion(new File("matrixRawDataCleaned.txt"));

//		matrix.printMatrix(matrix.cleanMatrix);
//		matrix.unweighetClusterValue(matrix.cleanMatrix);
//		Double unweighetClusterValue = matrix.unweighetClusterValue(matrix.cleanMatrix);
//		ArrayList<ArrayList<Double>> expecMatrix = matrix.generateExpectedMatrix(matrix.cleanMatrix);
//		matrix.printNandNMatrix(expecMatrix);
//		System.out.println("Cuw: " + unweighetClusterValue);
//		ArrayList<ArrayList<Double>> logLiklyhood = matrix.generateLogLiklyHoodMatrix(matrix.cleanMatrix, expecMatrix);
//		System.out.println("Liklyhood Matrix:");
//		matrix.printNandNMatrix(logLiklyhood);
		// docTei.printMatrix();
		// docTei.generateMatrix();
		//
		// System.out.println("One row " + docTei.calcSumForRow(0));
		// System.out.println(docTei.calcUnweightClusterValue());
		/* FINISH */
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
