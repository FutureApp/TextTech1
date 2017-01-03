package comphuman.task2.distanceReading;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GermanWikiArticleDiscussionHistoryAnalyzer {
	static String cssSelectorAllHistoryLinkGERMAN = ".mw-changeslist-date";
	static String basisURL=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String URL = "https://de.wikipedia.org/w/index.php?title=Diskussion:MongoDB&action=history";
		basisURL = URL.substring(0,URL.indexOf("/", 8));
		System.out.println(basisURL);
		URL = attacheLimit(URL, 10000);

		ArrayList<Element> linksToHistory = extractHistoryLinks(URL);

		for (Element element : linksToHistory) {

			callDiscussionAnalyser(element);
			
			break;
		}

	}

	private static void callDiscussionAnalyser(Element element) {
		String linkToHis = basisURL+element.attr("href");
		GermanWikiArticleDiscussionAnalyzer.main(new String[] {linkToHis});
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
	private static ArrayList<Element> extractHistoryLinks(String URL) {
		ArrayList<Element> linksToHistorys = new ArrayList<>();
		Document doc = URL_Handler.getContentOf(URL);
		Elements elements = doc.select(cssSelectorAllHistoryLinkGERMAN);
		elements.forEach((element) -> linksToHistorys.add(element));
		return linksToHistorys;
	}
	private static String attacheLimit(String URL, Integer limit) {
		return URL += "&limit=" + limit;
	}

}
