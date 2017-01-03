package comphuman.task2.distanceReading;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GermanWikiArticleDiscussionHistoryAnalyzer {
	private String URL;
	private String articleName;
	String cssSelectorAllHistoryLinkGERMAN = ".mw-changeslist-date";

	public GermanWikiArticleDiscussionHistoryAnalyzer(String URL, String articleName) {
		super();
		this.URL = attacheLimit(URL, 10000);
		this.articleName = articleName;
	}

	public void startAnalyser() {
		ArrayList<Element> linksToHistory = extractHistoryLinks(URL);
		for (Element element : linksToHistory) {
			startDiscussionAnalyserAndSaveResults(element);
			//TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//Remove that shit.
			break;
		}
	}

	private void startDiscussionAnalyserAndSaveResults(Element element) {
		String URLtoDisHis = RunnerHelper.extractBaseURL(URL)+element.attr("href");
		System.out.println(element.attr("date"));
		GermanWikiArticleDiscussionAnalyzer disAnal = new GermanWikiArticleDiscussionAnalyzer(URLtoDisHis, articleName);
		disAnal.runAnalyses();
		disAnal.saveResults("history/");
	}

	/**
	 * Extracts links which are pointing to a history-page of a wiki
	 * topic-discussion.
	 * 
	 * @param URL
	 *            URL where all history-links are listed.
	 * @return Array of elements which contains a link to specific history-page.
	 *         Empty if no history-link could be found.
	 */
	private ArrayList<Element> extractHistoryLinks(String URL) {
		ArrayList<Element> linksToHistorys = new ArrayList<>();
		Document doc = URL_Handler.getContentOf(URL);
		Elements elements = doc.select(cssSelectorAllHistoryLinkGERMAN);
		for (Element myElements : elements) {
			String date = myElements.text();
			myElements.attr("date", date);
			linksToHistorys.add(myElements);
		}
		return linksToHistorys;

	}

	private String attacheLimit(String URL, Integer limit) {
		return URL += "&limit=" + limit;
	}

}
