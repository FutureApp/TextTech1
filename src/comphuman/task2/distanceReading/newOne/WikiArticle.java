
package comphuman.task2.distanceReading.newOne;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.midi.Synthesizer;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xgeneral.modules.SystemMessage;

public class WikiArticle {
	String encoding = "UTF-8";
	File sectionFile = new File("result/curArt/extractedSections.txt");

	String URL;

	private Document wikiArticlePage;
	private Document wikiArticleDiscussionPage;
	private Document wikiArticleHistoryPage;

	/**
	 * 
	 * @param URL
	 *            URL where the article could be located. Link to the
	 *            german-wikipedia.
	 */
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

	/**
	 * Searches for the link to the dis-page of an article and the link to the
	 * history-page of the an article. Will set the values of
	 * wikiArticleDiscussionPage and wikiArticleHistoryPage.
	 */
	public void searchForContent() {
		Integer limit = 1000;
		wikiArticlePage = URL_Handler.getContentOf(URL);
		wikiArticleDiscussionPage = URL_Handler
				.getContentOf(getWikiURL() + wikiArticlePage.select("#ca-talk>span>a").attr("href"));
		wikiArticleHistoryPage = URL_Handler.getContentOf(
				getWikiURL() + wikiArticleDiscussionPage.select("#ca-history>span>a").attr("href") + "&limit=" + limit);
	}

	/**
	 * Returns the wiki article page.
	 * 
	 * @return The article page.
	 */
	public Document getWikiArticlePage() {
		return wikiArticlePage;
	}

	/**
	 * Returns the wiki discussion page of an article.
	 * 
	 * @return The discussion page of an article.
	 */
	public Document getWikiArticleDiscussionPage() {
		return wikiArticleDiscussionPage;
	}

	/**
	 * Returns the wiki history page of an article.
	 * 
	 * @return The history page of an article..
	 */
	public Document getWikiArticleHistoryPage() {
		return wikiArticleHistoryPage;
	}

	/**
	 * Searches in the discussion-pages for the sections and batches them.
	 * 
	 * @return Batched sections of the discussion-page.
	 */
	public ArrayList<String> searchAndSaveSectionsFromDisPage() {
		ArrayList<String> extractSections = extractSections(
				wikiArticleDiscussionPage.getElementById("mw-content-text").toString().split("\n"));
		saveSections(sectionFile, extractSections);
		return extractSections;
	}
	public HashMap<String, ArrayList<String>> searchAndSectionsFromHisDisPage() {
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		Elements aTags = wikiArticleHistoryPage.select(".mw-changeslist-date");

		for (Element element : aTags) {
			String link = element.attr("href");
			String realLink =getWikiURL()+link;
			
			Document doc = URL_Handler.getContentOf(realLink);
			String backUpDate = element.text();
			ArrayList<String> extractSections = extractSections(
					doc.getElementById("mw-content-text").toString().split("\n"));
			if(!map.containsKey(backUpDate)) map.put(backUpDate, extractSections);
			else SystemMessage.wMessage("Entry already exists.");
		}
		return map;
	}

	/**
	 * Saves the sections.
	 * 
	 * @param file
	 *            Location where to save.
	 * @param extractedSections
	 *            Extracted Sections.
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
	 * Extracts all sections from the dis-page of a german wiki-art.
	 * 
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
				sectionContent = new String(string + "\n");
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
