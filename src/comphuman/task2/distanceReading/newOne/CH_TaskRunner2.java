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
	static String locationOfArticleNodes = "/article/nodeInformations.txt";
	static String locationOfArticleVis = "/article/vis.txt";

	/**
	 * Entry-point of application. TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		arg = args;
		validateAmountOfGivenInput();
		cleanResultDir(resultDir);
		ArrayList<ArrayList<Node>> resultAnalysis = runDisAnalysis(arg[0]);
		vizAnalyseResults(resultAnalysis);
		saveNodeInformations(resultAnalysis);
		saveNodeAnalyse(resultAnalysis);

	}

	/**
	 * Saves all node information to a file. File is located under
	 * ./article/nodeInformations.txt.
	 * 
	 * @param resultAnalysis
	 *            Content to save(List of List of Nodes).
	 */
	private static void saveNodeInformations(ArrayList<ArrayList<Node>> resultAnalysis) {
		File saveContentTo = new File(resultDir + locationOfArticleNodes);
		String headerStructure = "Name of node | Name of father node | Created by User(user-name) | Creation Date";
		if (saveContentTo.exists())
			FileUtils.deleteQuietly(saveContentTo);
		saveContentTo.mkdirs();
		try {
			FileUtils.write(saveContentTo, headerStructure, encoding, false);
			for (int i = 0; i < resultAnalysis.size(); i++) {
				ArrayList<Node> section = resultAnalysis.get(i);
				FileUtils.write(saveContentTo, System.lineSeparator(), encoding, true);
				for (Node node : section) {
					String nodeName = node.getName();
					String fatherName = node.getFather();
					String userName = node.getAut();
					String creationDate = node.getDate();
					String statement = String.format("%d|%d|%d|%d|%d|", nodeName, fatherName, userName, creationDate);
					FileUtils.write(saveContentTo, statement + System.lineSeparator(), encoding, true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param resultAnalysis
	 */
	private static void saveNodeAnalyse(ArrayList<ArrayList<Node>> resultAnalysis) {

	}

	/**
	 * Analyzes a given german wiki-link to an article(analyze ofdiscussion-page).
	 * @param URL URL to an article in the german wikipedia.
	 * @return Returns the posts from the discussion-page as nodes.
	 */
	private static ArrayList<ArrayList<Node>> runDisAnalysis(String URL) {
		WikiArticle wikiArticle = new WikiArticle(URL);
		System.out.println(wikiArticle.getArticleName());
		System.out.println(wikiArticle.getWikiURL());
		wikiArticle.searchForContent();
		wikiArticle.getWikiArticlePage();
		ArrayList<String> SectionsFromDisPage = wikiArticle.searchAndSaveSectionsFromDisPage();
		ArtNodeExtractor nodeExtractor = new ArtNodeExtractor(SectionsFromDisPage, wikiArticle.getArticleName());
		nodeExtractor.extractNodes();
		return nodeExtractor.getAllNodesAllSections();

	}

	private static void vizAnalyseResults(ArrayList<ArrayList<Node>> resultAnalysis) {
		VisRichArtDis vis = new VisRichArtDis(resultAnalysis);
		vis.startVizRichDis();
	}

	/**
	 * Cleans the location of  the result-dir.
	 * @param dir Dir where to save the results.
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
		System.out.println("java -jar <name of jar>.jar <options> <wikipedia baseurl to topic>");
	}
}
