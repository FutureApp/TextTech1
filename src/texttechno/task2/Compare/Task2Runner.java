package texttechno.task2.Compare;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class Task2Runner {

	static String[] arg;
	static Properties_SingleTone prop = Properties_SingleTone.getInstance();
	static String encoding = Encoding.getDefaultEncoding();

	/**
	 * Entry-point for the applications.
	 * 
	 * @param args
	 *            Requires exactly 3 arguments. 1 Argument:
	 *            operations{pro|capa|both} 2 Argument: Path to first
	 *            text(Webanno tsv format) 3 Argument: Path to second
	 *            text(Webanno tsv format)
	 */
	public static void main(String[] args) {
		arg = args;
		checkInput();
		validateInputAndSetProps();
		Comparator comp = new Comparator(prop.getFile01(), prop.getFile02());
		getInformationAsRequired(comp);
		saveAllInformatios(comp);
		
		System.out.println();
		System.out.println(
				"Finish! Results located under: <./result/task2_result.txt>");
	}

	/**
	 * Prints all information from the calculations to a specific result file.
	 * Location of resultFile: ./result/task2_mcz.txt
	 * 
	 * @param comp
	 * The Comparator.
	 */
	private static void saveAllInformatios(Comparator comp) {
		String resultDirPath = "./results";
		File resultDir = new File(resultDirPath);
		File resultFile = new File(resultDir + "/task2_result.txt");
		if (resultFile.exists())
			FileUtils.deleteQuietly(resultFile);
		if (!resultDir.exists())
			resultDir.mkdirs();
		try {
			FileUtils.write(resultFile,                        comp.getMatrixAsString(),                        encoding, true);
			FileUtils.write(resultFile, "Quality(%)      : " + comp.getMatchInPro() + System.lineSeparator(),   encoding, true);
			FileUtils.write(resultFile, "Quality(kappa)  : " + comp.getMatchInKappa() + System.lineSeparator(), encoding, true);
			FileUtils.write(resultFile, "A1 - Annotator1 : " + comp.getAnnotator01() + System.lineSeparator(),  encoding, true);
			FileUtils.write(resultFile, "A2 - Annotator2 : " + comp.getAnnotator02() + System.lineSeparator(),  encoding, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints all information which belongs to the arg[0]-parameter-option.
	 * 
	 * @param comp
	 * The Comparator.
	 */
	private static void getInformationAsRequired(Comparator comp) {
		switch (arg[0]) {
		case "pro":
			System.out.println(comp.getMatchInPro());
			break;
		case "kappa":
			System.out.println(comp.getMatchInKappa());
			break;
		case "all":
			System.out.println(comp.getMatrixAsString());
			System.out.println("Pro: " + comp.getMatchInPro());
			System.out.println("Kappa: " + comp.getMatchInKappa());
			System.out.println("A1 - Annotator 1: " + comp.getAnnotator01());
			System.out.println("A2 - Annotator 2: " + comp.getAnnotator02());
			break;
		default:
			break;
		}
	}

	/**
	 * Validate that each input is correct. Prints error if the method fails. In
	 * this manner a help-message will be printed, too. After successful
	 * validation, the information will be available over
	 * {@link Properties_SingleTone}
	 */
	private static void validateInputAndSetProps() {
		String whichCompare = arg[0];
		File file01 = new File(arg[1]);
		File file02 = new File(arg[2]);

		// Checks the first param
		switch (whichCompare.toLowerCase()) {
		case "pro":
			prop.setCompare("pro");
			break;
		case "kappa":
			prop.setCompare("kappa");
			break;
		case "all":
			prop.setCompare("all");
			break;
		default:
			SystemMessage.eMessage("No match for argument 1. Given <" + whichCompare + ">");
			help();
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
	 * Prints usage
	 */
	private static void help() {
		System.out.println("---------- Usage ----------");
		System.out.println("java -jar <name of jar>.jar <pro/kappa/all> <path to file 01> <path to file 02>");
		System.out.println(
				"After an successfully interaction with this programm, you will find a result file under <./result/task2_result.txt>");
		System.out.println("Feel free to inspect the file. =)");
	}
}
