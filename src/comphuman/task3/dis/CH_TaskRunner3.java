package comphuman.task3.dis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import comphuman.task2.distanceReading.newOne.WikiArticle;
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
		WikiArticle article = new WikiArticle(arg[0]);
		WikiRevisionPageAnalyzerSimple revisionAnalyzer = new WikiRevisionPageAnalyzerSimple(
				article.getRevisionsPage());
		revisionAnalyzer.startAnalysis();
		revisionAnalyzer.generateMapsForCalcs();
		ArrayList<WikiEditNetworkNode> editNodes = revisionAnalyzer.generateEditNodes();

		Integer actionCounter = 0;
		for (int i = 0; i < editNodes.size(); i++) {
			actionCounter += editNodes.get(i).getActivityIndex();
			WikiEditNetworkNode node = editNodes.get(i);
			System.out.println(node.getUserName());
			System.out.println(node.getRelationOfRevisorAndRevised());
		}
		ExportToVizTool vizExporter = new ExportToVizTool(editNodes);
		vizExporter.exportToGraphMlFormate(new File("C:/Users/admin/Desktop/test.graphml"));
		System.out.println(actionCounter);

		/* BYTE Section*/
		WikiRevisionPageAnalyzerSimpleBytes revisionAnalyzerByte = new WikiRevisionPageAnalyzerSimpleBytes(
				article.getRevisionsPage());
		revisionAnalyzerByte.startAnalysis();
		revisionAnalyzerByte.generateMapsForCalcs();
		ArrayList<WikiEditNetworkNode> editNodesWithByte = revisionAnalyzerByte.generateEditNodes();

		Integer byteActionCounter = 0;
		for (int i = 0; i < editNodesWithByte.size(); i++) {
			byteActionCounter += editNodesWithByte.get(i).getActivityIndex();
			WikiEditNetworkNode node = editNodesWithByte.get(i);
			System.out.println(node.getUserName());
			System.out.println(node.getRelationOfRevisorAndRevised());
		}
		ExportToVizTool vizExporterByte = new ExportToVizTool(editNodesWithByte);
		vizExporterByte.exportToGraphMlFormate(new File("C:/Users/admin/Desktop/testByte.graphml"));
		System.out.println(byteActionCounter);

		/* FINISH */
		printFinish();
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
