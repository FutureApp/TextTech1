package comphuman.task2.distanceReading.newOne;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class CH_TaskRunner2 {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();

	static String resultDir = "CompHuman/result";

	/**
	 * Entry-point of application. TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		arg = args;
		validateAmountOfGivenInput();
		cleanResultDir(resultDir);
		runAnalysis(arg[0]);

	}

	private static void runAnalysis(String URL) {
		WikiArticle wikiArticle = new WikiArticle(URL);
		System.out.println(wikiArticle.getArticleName());
		System.out.println(wikiArticle.getWikiURL());
		wikiArticle.searchForContent();
		wikiArticle.getWikiArticlePage();
		ArrayList<String> SectionsFromDisPage = wikiArticle.searchAndSaveSectionsFromDisPage();
		ArtNodeExtractor nodeExtractor = new ArtNodeExtractor(SectionsFromDisPage,wikiArticle.getArticleName());
		nodeExtractor.extractNodes();
		ArrayList<ArrayList<Node>> allNodesAllSections = nodeExtractor.getAllNodesAllSections();
		VisRichArtDis vis = new VisRichArtDis(allNodesAllSections);
		vis.startVizRichDis();
		
	}

	private static void cleanResultDir(String dir) {
		File file = new File(dir);
		if (!file.exists())
			file.mkdirs();
		try {
			FileUtils.cleanDirectory(new File(dir));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	 * Prints usage
	 */
	private static void usage() {
		System.out.println("---------- Usage ----------");
		System.out.println("java -jar <name of jar>.jar <options> <wikipedia baseurl to topic>");
	}
}
