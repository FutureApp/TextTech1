package comphuman.task3.dis;

import org.jsoup.nodes.Document;

import comphuman.xgeneral.URL_Handler;

public class WikiRevisionPage {

	String linkToRevisionPage;

	public WikiRevisionPage(String linkToRevisionPage) {
		super();
		this.linkToRevisionPage = linkToRevisionPage;
	};

	/**
	 * Abstracts the content from the revision-page and calcs the relation
	 * between revision-user & revised-user
	 */
	public void abstractContentFromWikiRevisionPage() {
		Document revisionPage = URL_Handler.getContentOf(linkToRevisionPage);
		//TODO
		WikiRevision revision = new WikiRevision(null, null, null);
	}
}
