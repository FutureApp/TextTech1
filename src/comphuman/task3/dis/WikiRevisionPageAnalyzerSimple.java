package comphuman.task3.dis;

import java.util.ArrayList;
import java.util.Formatter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.SystemException;

import xgeneral.modules.SystemMessage;

/**
 * @author Michael Czaja
 *
 */
public class WikiRevisionPageAnalyzerSimple {
	private Formatter formatter = new Formatter();
	private String revisionContentHolderID = "pagehistory";

	Document wikiRevisionPage;
	ArrayList<String> linksToRevisions = new ArrayList<>();

	/**
	 * An analyzer for the revisions of a wiki-article.
	 * 
	 * @param wikiRevisionPage
	 *            Document- which contains the wiki-article-revision page.
	 */
	public WikiRevisionPageAnalyzerSimple(Document wikiRevisionPage) {
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
			if(linksToRevisions.isEmpty()){
				

				
				Element selectedElements = wikiRevisionPage.getElementById(revisionContentHolderID);
				Elements liElements = selectedElements.select("li");
				System.out.println(liElements.size());
			
				for (Element element : liElements) {
					Elements containsUserName = element.select(".history-user");
					Elements userNameWiki = containsUserName.select("a");
					String userName = userNameWiki.select("bdi").text();

					Elements s = element.select("[class*='mw-plusminus-']");
					Integer actionValue=0;

					if(s.size()!=1) SystemMessage.wMessage("Action for user <%s> is unknown.");
					else actionValue = detActionValue(s);
					
					System.out.println(userName + " "+ actionValue);
				}
			
			}
		return linksToRevisions;
	}

	/**
	 * Determines which kind of the action the user did. 
	 * <1>: User reacts positive of the last comment. User added some bytes.
	 * <-1>: User reacts negative on the last comment. User deletes some bytes.
	 * <0>: User didn't change anything. Returns the same value if an action wasn't recordnize, too.
	 * @param s
	 * @return
	 */
	private Integer detActionValue(Elements s) {
		String valueToInvestigate = s.attr("class");
		Integer valueOfAction = -2;
		switch (valueToInvestigate) {
		case "mw-plusminus-null":
			valueOfAction=0;
			break;
		case "mw-plusminus-pos":
			valueOfAction=1;
			break;
		case "mw-plusminus-neg":
			valueOfAction=1;
			break;
		default:
			//if action is unknown then return 0. No impact in the coming calc.
			valueOfAction=0;
			SystemMessage.wMessage("Action is unkown. Content:"+s.toString()+ " "+ this.getClass().getName());
			break;
		}
		return valueOfAction;
	}

	private Integer checkInfluence(String userName, Element containsUserName) {
		Integer actionValue = -10;
		Integer triggerCount = 0;
		if(containsUserName.select("span > .mw-plusminus-null").size() == 1) {actionValue=0;triggerCount++;}
		if(containsUserName.select("span > .mw-plusminus-pos").size() == 1) {actionValue=1;triggerCount++;}
		if(containsUserName.select("span > .mw-plusminus-neg").size() == 1) {actionValue=-1;triggerCount++;}
		
		if(triggerCount < 1) 
			SystemMessage.wMessage(formatter.format("Action of user <%s> is unknown. Will return <%d>", userName,actionValue).toString());
		else if(triggerCount>2) 
			SystemMessage.wMessage(formatter.format("More then one action for user <%s> found. Will return <%d>", userName,actionValue).toString());
		else{/*No message. Everything matches */}
		return actionValue;
		
		
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
