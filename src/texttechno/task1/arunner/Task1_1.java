package texttechno.task1.arunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import texttechno.task1.checker.CheckGivenArguments;
import texttechno.task1.process.ProcessingTask;
import texttechno.task1.prototype.types.Text;
import texttechno.task1.prototype.types.TupelIS;
import xgeneral.modules.Encoding;
import xgeneral.modules.SaveResults;
import xgeneral.modules.SystemMessage;

public class Task1_1 {
	static Integer numberOfTopK = 20;
	static final String className = Task1_1.class.getSimpleName();
	private static String[] arg;
	static String pathOfResult = "result/";
	static String pathOfTaskResults = pathOfResult + "task1result/";
	static SaveResults saver = new SaveResults("iso");
	static Boolean argumentsCorrect = true;
	static String usedEncoding;
	static ArrayList<Text> listOfTexts = new ArrayList<>();

	Map<String, Integer> wordCounts = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException {
		Integer pos = 0;
		checkAndSetInput(args);

		String locationToSave= "Result/Task1/";
		ProcessingTask task1Processing = new ProcessingTask(listOfTexts,locationToSave, usedEncoding);
		System.out.println();
		System.out.println("Analyse beginnt!");
		
		//Starts the processing
		task1Processing.beginnProcessing();
		task1Processing.printAllAbsolutPathsOfTexts();
		task1Processing.cleanAllTextsOnlyLetters();
		task1Processing.saveAllTextsDefault(pos);
		task1Processing.saveAllCleanTextsDefault(pos);
		task1Processing.tokenizeAllCleanTexts();
		task1Processing.saveAllTokensOfAllTexts();
		task1Processing.sortAlphaAllTokens();
		task1Processing.saveAllTokensSorted();
		task1Processing.saveFirstKHighestCountsPerTextLower(20);
		task1Processing.saveFirstKHighestCountsPerTextUpper(20);
		task1Processing.saveFirstKHighestCountsPerTextMixed(20);
		task1Processing.calculateTTRPerText();
		task1Processing.saveTTRPerText();
		task1Processing.saveMATTRPerText(300);
		
		System.out.println();
		System.out.println("Analyse beendet!");
		System.out.println("Ergebnisse gespeichert unter: "+new File(locationToSave).getAbsolutePath());
		System.out.println("Aufgabenbezogene Ergebnisse unter: "+new File(locationToSave+"/Result_TEXTNAME").getAbsolutePath());
		
	}

	/**
	 * Checks all given inputs and will set the properties if the inputs are
	 * valid.
	 */
	private static void checkAndSetInput(String[] args) {
		arg = args;
		if (arg.length == 0) {
			printUsage();
			argumentsCorrect = false;
			System.exit(1);
		} else {
			if (arg.length >= 2) {
				checkAndSetEncoding();
				checkAndCreateListOfFiles();
			} else if (arg.length == 1 || arg[0].equals("help")) {
				printUsage();
				System.exit(1);
			}
		}

	}

	/**
	 * Checks if a passed URL of file matches a file at least. In this manner
	 * this method tests also if a given URL is at least a valid construct for a
	 * file system.
	 * 
	 * If the given URL don't match a file, the will be ignored. In this case a
	 * message will be given.
	 */
	private static void checkAndCreateListOfFiles() {
		for (int i = 1; i < arg.length; i++) {
			String path = arg[i];
			if (!CheckGivenArguments.checkIfPath(path)) {
				SystemMessage.messageForArgument(arg, i, "Is not a Path. This argument will be ignored");
			} else if (!CheckGivenArguments.checkIfFileExits(path)) {
				SystemMessage.messageForArgument(arg, i, "File doesn't exits. Will be ignored");

			} else {
				Text text = new Text(new File(arg[i]), usedEncoding);
				listOfTexts.add(text);
			}
		}
	}

	/**
	 * Checks and sets the encoding.
	 * 
	 * @param encoding
	 *            Pass the type of encoding which should be used.
	 */
	private static void checkAndSetEncoding() {

		if (Encoding.isEncodingSupported(arg[0])) {
			usedEncoding = arg[0];
		} else {
			usedEncoding = Encoding.getDefaultEncoding();
		}
	}

	/**
	 * Prints the help-message of this class.
	 * 
	 * @see Task1_1
	 */
	private static void printUsage() {
		String prefix = System.lineSeparator() + "---- Programm usage ----" + System.lineSeparator()
				+ "Enter this common commands, to use this programm properly:";

		String systemCommand = "java -jar ";
		String classNameJAR = className + ".jar ";
		String commonCMDCommand = systemCommand + classNameJAR +"enconding file1 file2 ...";

		String suffix = "Pleas try again.";

		System.out.println(prefix);
		System.out.println();
		System.out.println(commonCMDCommand + " ");
		System.out.println();
		System.out.println(suffix);
		System.exit(1);
	}

}
