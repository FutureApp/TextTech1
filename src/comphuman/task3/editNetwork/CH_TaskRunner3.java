package comphuman.task3.editNetwork;

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
	static String articleName;

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
		articleName = article.getArticleName();
		WikiRevisionPageAnalyzerSimple revisionAnalyzer = new WikiRevisionPageAnalyzerSimple(
				article.getRevisionsPage());
		revisionAnalyzer.startAnalysis();
		revisionAnalyzer.generateMapsForCalcs();
		ArrayList<WikiEditNetworkNode> editNodes = revisionAnalyzer.generateEditNodes();
		ExportToVizTool vizExporter = new ExportToVizTool(editNodes);
		File saveFileSimple = new File(resultDir+"/"+article.getArticleName()+"_SAnalysis.graphml");
		if(saveFileSimple.exists()) saveFileSimple.delete();
		vizExporter.exportToGraphMlFormate(saveFileSimple);
		
		
		/* BYTE Section */
		WikiRevisionPageAnalyzerSimpleByte byteAnalyser = new WikiRevisionPageAnalyzerSimpleByte(
				article.getRevisionsPage());
		byteAnalyser.startExtractionOfReviserItems();
		byteAnalyser.startCreationOfWikiEditNetwork();
		byteAnalyser.startCreateWikiNodeReviser();
		
		ArrayList<WikiEditNetworkNodeByte> exportWikiNodeRevisor = byteAnalyser.exportWikiNodeReviser();
		ExportToVizToolByte vizExporterByte = new ExportToVizToolByte(exportWikiNodeRevisor);
		
		File saveFileByte = new File(resultDir+"/"+article.getArticleName()+"_ByteAnalysis.graphml");
		if(saveFileByte.exists()) saveFileByte.delete();
		
		vizExporterByte.exportToGraphMlFormate(saveFileByte);
		
		
		/* FINISH */
		printFinish();
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
