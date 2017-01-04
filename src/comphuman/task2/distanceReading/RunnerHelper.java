package comphuman.task2.distanceReading;

import org.jsoup.nodes.Document;

import comphuman.task2.distanceReading.newOne.URL_Handler;

public class RunnerHelper {

	public static String extractArticleName(String wikiLink) {
		Integer index = wikiLink.lastIndexOf("/")+1;
		String articleName = wikiLink.substring(index);
		return articleName;
	}

	public static String extractBaseURL(String wikiLink) {
		return wikiLink = wikiLink.substring(0,wikiLink.indexOf("/", 8));
	}

	public static String extractDiscussionLink(String wikiLink) {
		Document doc = URL_Handler.getContentOf(wikiLink);
		String discussionLink = doc.select("#ca-talk>span>a").attr("href");
		
		return getRealBasis(wikiLink)+discussionLink;
	}

	public static String extractHisDiscussionLink(String wikiLink) {
		Document doc = URL_Handler.getContentOf(wikiLink);
		String hisDiscussionLink = doc.select("#ca-history>span>a").attr("href");
		return getRealBasis(wikiLink)+hisDiscussionLink;
	}

	
	/**
	 * Returns the truly base-URL.
	 * 
	 * @param URI
	 *            URL which contains a base-URL.
	 * @return The real BASE-URL
	 */
	private  static String getRealBasis(String URI) {
		return URI.substring(0, URI.replace("//", "$").indexOf('/') + 1);
	}
}
