package comphuman.task3.dis;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xgeneral.modules.SystemMessage;

/**
 * @author Michael Czaja
 *
 */
public class WikiRevisionPageAnalyzer {
	private String revisionLinkSelector = ".mw-history-histlinks";
	Document wikiRevisionPage;
	ArrayList<String> linksToRevisions = new ArrayList<>();

	/**
	 * An analyzer for the revisions of a wiki-article.
	 * 
	 * @param wikiRevisionPage
	 *            Document- which contains the wiki-article-revision page.
	 */
	public WikiRevisionPageAnalyzer(Document wikiRevisionPage) {
		super();
		this.wikiRevisionPage = wikiRevisionPage;
	}

	/**
	 * Abstracts the links for further analysis.
	 * 
	 * @return A list which contains the links to the given pages, which
	 *         contains the information of the revision.
	 */
	public ArrayList<String> getRevisionLinks() {
		if (linksToRevisions.isEmpty())
			abstractRevisionLinks();
		return linksToRevisions;
	}

	/**
	 * Abstracts the links which are linking to each page of revision. The last
	 * element in the list which is returned, contains the link <noLink>. The
	 * last element in the list is the initial element. A revision for this
	 * element is not possible. After execution, you can use the
	 * {@link #getRevisionLinks()} method to get the links. You can use the
	 * output of the method, too.
	 * 
	 * @return A list of links, which are pointing to revisions.
	 */
	public ArrayList<String> abstractRevisionLinks() {
		Integer noPrevLink = 0;
		if (linksToRevisions.isEmpty()) {
			Elements selectedElements = wikiRevisionPage.select(revisionLinkSelector);
			Boolean firstEntry = true;

			for (Element element : selectedElements) {
				Elements selectedLinks = element.select("a");

				// If element is the first item in the list then this element
				// represents the actual article.
				// Because of this the element contains only one link. This link
				// points to the revision.
				if (firstEntry) {
					if (selectedLinks.size() == 1) {
						linksToRevisions.add(selectedLinks.get(0).toString());
					} else {
						SystemMessage.wMessage(
								"The First entry of the revision-page contains more then one link. First entry should only have 1 link.");
					}
					firstEntry = false;
				}
				// Handles the non first elements.
				else {
					// if you find less then 1 link you are at the end of list.
					// This last element contains only the actual-link not an
					// prev-link.
					if (selectedLinks.size() < 2) {
						noPrevLink++;
						linksToRevisions.add("noLink");
					}

					// Element contains both, link to prev-version and link to
					// actual-version of the article.
					else
						linksToRevisions.add(selectedLinks.get(1).toString());
				}
			}
			// error handling -Broken or an faulty limit-value.
			if (noPrevLink < 1)
				SystemMessage.wMessage("Revison-Starting point wasn't found. Check the limit for the revisoin-page.");
			else if (noPrevLink > 1)
				SystemMessage.wMessage("More then one starting-point was found. "
						+ "This is a indicator for broken links for previous content. Check that out!");
		}
		return linksToRevisions;
	}

	/**
	 * Starts the analysis. If you miss to
	 */
	public void startAnalysis() {
		startAbstractionIfNeeded();
		

	}

	/**
	 * Executes all aggregations methods if needed. If everything is set
	 * correctly then this method will do nothing.
	 */
	private void startAbstractionIfNeeded() {
		if (linksToRevisions.isEmpty())
			abstractRevisionLinks();
	}

}
