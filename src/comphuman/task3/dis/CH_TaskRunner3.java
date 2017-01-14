package comphuman.task3.dis;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

import comphuman.xgeneral.URL_Handler;
import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class CH_TaskRunner3 {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();

	static String resultDir = "CompHuman/result";
	static String locationOfArticleNodes = "/discussion/nodeInformations.txt";
	static String locationOfArticleVis = "/discussion/graph.png";

	/**
	 * Entry-point of the application.
	 * 
	 * @param args
	 *            Only args[0] - Link to a german wiki article.
	 */
	public static void main(String[] args) {
		arg = args;
		validateAmountOfGivenInput();
		cleanResultDir(resultDir);
		Document articlePage = getArticlePage(arg[0]);
		
		
		printFinish();
	}

	private static Document getArticlePage(String URL) {
		Document doc = URL_Handler.getContentOf(URL);
		return doc;
	}

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
