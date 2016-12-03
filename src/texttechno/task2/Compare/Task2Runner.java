package texttechno.task2.Compare;

import java.io.File;

import xgeneral.modules.SystemMessage;

public class Task2Runner {

	static String[] arg;
	static Properties_SingleTone prop = Properties_SingleTone.getInstance();

	public static void main(String[] args) {
		arg = args;
		checkInput();
		validateInputAndSetProps();
		
		

	}

	/**
	 * Validate that each input is correct. Prints error if the method fails. In
	 * this manner a help-message will be printed, too. After successful
	 * validation, the information will be available over {@link Properties_SingleTone}
	 */
	private static void validateInputAndSetProps() {

		String whichCompare = arg[0];
		File file01 = new File(arg[1]);
		File file02 = new File(arg[2]);

		// Check the first param
		switch (whichCompare.toLowerCase()) {
		case "pro":
			prop.setCompare("pro");
			break;
		case "capa":
			prop.setCompare("capa");
			break;
		case "both":
			prop.setCompare("both");
			break;
		default:
			SystemMessage.eMessage("No match for argument 1. Given <" + whichCompare + ">");
			break;
		}
		if (!(file01.exists() || file02.exists())) {
			if (!file01.exists())
				SystemMessage.eMessage("Argument 2: File doesn't extits <" + file01.getAbsolutePath() + ">");
			if (!file01.exists())
				SystemMessage.eMessage("Argument 3: File doesn't extits <" + file02.getAbsolutePath() + ">");
		} else {
			prop.setFile01(file01);
			prop.setFile02(file02);
		}
	}

	/**
	 * Checks if given input could be valid. If not, print help(). If input
	 * <number> matches requirements, then pass.
	 */
	public static void checkInput() {
		if (arg.length < 3) {
			System.err.println("ERROR - More Input is needed.");
			System.out.println();
			for (int i = 0; i < arg.length; i++) {
				System.out.printf("Argument %d: %s", i, arg[i]);
				System.out.println();
			}
			help();
			System.exit(2);
		}
	}

	/**
	 * Prints the usage
	 */
	private static void help() {
		System.out.println("---------- Usage ----------");
		System.out.println("java -jar <name of jar>.jar <pro/capa/both> <path to file 01> <path to file 02>");
	}
}
