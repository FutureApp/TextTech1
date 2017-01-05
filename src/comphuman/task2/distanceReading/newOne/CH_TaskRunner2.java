package comphuman.task2.distanceReading.newOne;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class CH_TaskRunner2 {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();

	static String resultDir = "CompHuman/result";
	static String locationOfArticleNodes = "/article/nodeInformations.txt";
	static String locationOfArticleVis = "/article/vis.png";

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
		WikiArticle wikiArticle = new WikiArticle(arg[0]);
		wikiArticle.searchForContent();
		ArrayList<ArrayList<Node>> resultAnalysis = runDisAnalysis(wikiArticle);
		String fileLocation = resultDir + locationOfArticleVis;
		vizAnalyseResults(resultAnalysis,fileLocation);
		saveNodeInformations(resultAnalysis, resultDir + locationOfArticleNodes);
		//
		runHisAnalysis(wikiArticle);
	}

	/**
	 * Analyzes a given german wiki-link to an article(analyze
	 * ofdiscussion-page).
	 * 
	 * @param URL
	 *            URL to an article in the german wikipedia.
	 * @return Returns the posts from the discussion-page as nodes.
	 */
	private static ArrayList<ArrayList<Node>> runDisAnalysis(WikiArticle wikiArticle) {
		ArrayList<String> SectionsFromDisPage = wikiArticle.searchAndSaveSectionsFromDisPage();
		ArtNodeExtractor nodeExtractor = new ArtNodeExtractor(SectionsFromDisPage, wikiArticle.getArticleName());
		nodeExtractor.extractNodes();
		return nodeExtractor.getAllNodesAllSections();

	}

	private static void runHisAnalysis(WikiArticle wikiArticle) {
		HashMap<String, ArrayList<String>> map = wikiArticle.searchAndSectionsFromHisDisPage();
		map.forEach((dateOfHis, sections) -> {
			String location = resultDir + "/history/" + Normalizer.normalizeDateForFileName(dateOfHis);
			System.out.print(dateOfHis);
			System.out.println("-- " + sections);
			ArtNodeExtractor hisExtractor = new ArtNodeExtractor(sections,
					wikiArticle.getArticleName() + "(" + dateOfHis + ")");

			hisExtractor.extractNodes();
			ArrayList<ArrayList<Node>> allNodesAllSections = hisExtractor.getAllNodesAllSections();
			saveNodeInformations(allNodesAllSections, location + "/nodeInformations.txt");
			// TODO
			vizAnalyseResults(allNodesAllSections, location);
		});
	}

	private static void vizAnalyseResults(ArrayList<ArrayList<Node>> resultAnalysis, String fileLocation) {
		File saveContentTo = new File(resultDir + locationOfArticleVis);
		VisRichArtDis vis = new VisRichArtDis(resultAnalysis);
		vis.startVizRichDis(saveContentTo, true);
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
	 * Saves all node information to a file. File is located under
	 * ./article/nodeInformations.txt.
	 * 
	 * @param resultAnalysis
	 *            Content to save(List of List of Nodes).
	 * @param location
	 */
	private static void saveNodeInformations(ArrayList<ArrayList<Node>> resultAnalysis, String location) {
		File saveContentTo = new File(location);
		System.out.println(saveContentTo.getAbsolutePath());
		String headerStructure = "Name of node | Name of father node | Created by User(user-name) | Creation Date";
		if (saveContentTo.exists())
			FileUtils.deleteQuietly(saveContentTo);
		try {
			saveContentTo.getParentFile().mkdirs();
			FileUtils.write(saveContentTo, headerStructure, encoding, false);
			for (int i = 0; i < resultAnalysis.size(); i++) {
				ArrayList<Node> section = resultAnalysis.get(i);
				FileUtils.write(saveContentTo, System.lineSeparator(), encoding, true);
				for (Node node : section) {
					// Assign var's for better understanding
					String nodeName = node.getName();
					String fatherName = node.getFather();
					String userName = node.getAut();
					String creationDate = node.getDate();
					
					String statement = String.format("%s|%s|%s|%s", nodeName, fatherName, userName, creationDate);
					FileUtils.write(saveContentTo, statement + System.lineSeparator(), encoding, true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
