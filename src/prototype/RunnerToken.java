package prototype;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RunnerToken {
	static final String className = RunnerToken.class.getSimpleName();
	private static String[] arg;
	String pathOfResult = "result/";
	String pathOfTaskResult=pathOfResult+"taskresult/";
	

	static Boolean argumentsCorrect = true;
	static String usedEncoding;
	static ArrayList<Text> listOfTexts = new ArrayList<>();

	Map<String, Integer> wordCounts = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException {
		arg = args;
		Integer pos= 0 ;
		checkAndSetInput();
		ProcessingTask task1Processing = new ProcessingTask(listOfTexts);
		task1Processing.beginnProcessing();
		task1Processing.printEveryOriText();
		task1Processing.printNameOfAllTexts();
		task1Processing.printAllAbsolutPathsOfTexts();
		task1Processing.cleanAllTextsOnlyLetters();
		task1Processing.printAllCleanTexts();
		task1Processing.saveAllTextsDefault(pos);
		task1Processing.saveAllCleanTextsDefault(pos);
		task1Processing.tokenizeAllCleanTexts();
		task1Processing.saveAllTokensOfAllTexts();
		
	}

	private static void checkAndSetInput() {
		if (arg.length == 0) {
			System.out.println("0");
			printUsage();
			argumentsCorrect = false;
		} else {
			if (arg.length >= 2) {
				System.out.println("Step Good");
				checkAndSetEncoding();
				checkAndCreateListOfFiles();

			} else if (arg.length == 1 || arg[0].equals("help")) {
				printUsage();
			}
		}

	}

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
		String encoding = arg[0];
		if (CheckGivenArguments.checkIfPath(encoding)) {
			SystemMessage.eMessage("First argument <" + encoding + "> seems to be a path not a type of enconding.");
			SystemMessage.allArguments(arg);
			printUsage();
			argumentsCorrect = false;
		}
		if (argumentsCorrect) {

			if (Encoding.isEncodingSupported(encoding)) {
				usedEncoding = encoding;
			} else {
				usedEncoding = Encoding.getDefaultEncoding();
			}
		}
	}

	/**
	 * Prints the help-message of this class.
	 * 
	 * @see RunnerToken
	 */
	private static void printUsage() {
		String prefix = System.lineSeparator() + "---- Programm usage ----" + System.lineSeparator()
				+ "Enter this common commands, to use this programm properly:";

		String systemCommand = "java -jar ";
		String classNameJAR = className + ".jar ";
		String commonCMDCommand = systemCommand + classNameJAR;

		String suffix = "Pleas try again.";

		System.out.println(prefix);
		System.out.println();
		System.out.println(commonCMDCommand + " ");
		System.out.println();
		System.out.println(suffix);
	}

}
