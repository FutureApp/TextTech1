
package comphuman.task2.distanceReading.newOne;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

public class WikiArticle {
	String encoding = "UTF-8";
	File sectionFile = new File("result/curArt/extractedSections.txt");

	String URL;

	private Document wikiArticlePage;
	private Document wikiArticleDiscussionPage;
	private Document wikiArticleHistoryPage;

	public WikiArticle(String URL) {
		super();
		this.URL = URL;
	}

	public String getArticleName() {
		return URL.substring(URL.lastIndexOf("/") + 1);
	}

	public String getWikiURL() {
		return URL.substring(0, URL.replace("//", "$$").indexOf("/"));
	}

	public void searchForContent() {
		Integer limit = 1000;
		wikiArticlePage = URL_Handler.getContentOf(URL);
		wikiArticleDiscussionPage = URL_Handler
				.getContentOf(getWikiURL() + wikiArticlePage.select("#ca-talk>span>a").attr("href"));
		wikiArticleHistoryPage = URL_Handler.getContentOf(
				getWikiURL() + wikiArticleDiscussionPage.select("#ca-history>span>a").attr("href") + "&limit=" + limit);
	}

	public Document getWikiArticlePage() {
		return wikiArticlePage;
	}

	public Document getWikiArticleDiscussionPage() {
		return wikiArticleDiscussionPage;
	}

	public Document getWikiArticleHistoryPage() {
		return wikiArticleHistoryPage;
	}

	
	public ArrayList<String> searchAndSaveSectionsFromDisPage() {
		ArrayList<String> extractSections = extractSections(
				wikiArticleDiscussionPage.getElementById("mw-content-text").toString().split("\n"));
		saveSections(sectionFile, extractSections);
		return extractSections;
	}

	/**
	 * Saves the sections.
	 * @param file Location where to save.
	 * @param extractedSections Extracted Sections.
	 */
	private void saveSections(File file, ArrayList<String> extractedSections) {
		try {
			FileUtils.write(file, "", encoding, false);
			for (int i = 1; i < extractedSections.size(); i++) {
				FileUtils.write(file, extractedSections.get(i) + System.lineSeparator(), encoding, true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Extracts all section from the art.
	 * @param strings 
	 * @return List of List which contains content for node-extraction.
	 */
	private ArrayList<String> extractSections(String[] strings) {
		ArrayList<String> result = new ArrayList<>();
		String sectionContent = new String();
		big: for (int i = 0; i < strings.length; i++) {
			String string = strings[i];
			if (string.contains("class=\"mw-headline\"")) {
				result.add(sectionContent);
				sectionContent = new String(string+"\n");
			} else if (string.contains("NewPP limit report")) {
				result.add(sectionContent.substring(0, sectionContent.indexOf("<!--")));
				break big;
			} else {
				sectionContent += string + "\n";
			}
		}
		return result;

	}
}
