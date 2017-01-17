package comphuman.task3.dis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xgeneral.modules.SystemMessage;

/**
 * @author Michael Czaja
 *
 */
public class WikiRevisionPageAnalyzerSimple {
	private String revisionContentHolderID = "pagehistory";
	private Integer negativInteraction = 0;
	private Integer positivInteraction = 0;
	private Integer neutralInteraction = 0;

	Document wikiRevisionPage;
	ArrayList<String> linksToRevisions = new ArrayList<>();
	List<List<String>> revisorUserMap = new ArrayList<>();
	HashMap<String, WikiRevisionUser> revisionMap = new HashMap<>();

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
	 * Method abstracts the users and analyzes the action impact(pos,neg or
	 * null) of the revision-wiki-page. Possible action-Values: <1> : User
	 * reacts positive of the last comment. User added some bytes. <-1>: User
	 * reacts negative on the last comment. User deletes some bytes. <0> : User
	 * didn't change anything. Returns the same value if an action wasn't
	 * recordnize,too.
	 * 
	 * @return Array of an Arrays which containing the map [user,actionValue].
	 */
	public ArrayList<String> abstractRevisionItems() {
		if (linksToRevisions.isEmpty()) {
			Element selectedElements = wikiRevisionPage.getElementById(revisionContentHolderID);
			Elements liElements = selectedElements.select("li");
			System.out.println(liElements.size());
			for (Element element : liElements) {
				Elements containsUserName = element.select(".history-user");
				Elements userNameWiki = containsUserName.select("a");
				String userName = userNameWiki.select("bdi").text();
				Elements s = element.select("[class*='mw-plusminus-']");
				Integer actionValue = 0;
				if (s.size() != 1)
					SystemMessage.wMessage("Action for user <%s> is unknown.");
				else
					actionValue = detActionValue(s);
				ArrayList<String> realUserMap = new ArrayList<>();
				realUserMap.add(userName);
				realUserMap.add(actionValue + "");
				revisorUserMap.add(realUserMap);
			}

		}
		return linksToRevisions;
	}

	public void calcEditNetwork() {
		List<List<String>> reversedRevList = new ArrayList<>();
		for (int i = 0; i < revisorUserMap.size(); i++) {
			List<String> item = revisorUserMap.get(i);
			reversedRevList.add(item);
		}

		Collections.reverse(reversedRevList);

		System.out.println(reversedRevList.size());
		String activeUser = new String("");
		System.out.println(reversedRevList.size() + "+-----------");

		for (int i = 0; i < reversedRevList.size(); i++) {
			List<String> item = reversedRevList.get(i);
			String curUser = item.get(0);
			Integer curActionValue = Integer.parseInt(item.get(1));

			if (i == 0) {
				activeUser = new String(curUser);
				WikiRevisionUser revUser = new WikiRevisionUser("aut", activeUser);
				revUser.addPositiveProcess(1);
				revisionMap.put(activeUser, revUser);

			} else {
				if (!revisionMap.containsKey(curUser)) {
					revisionMap.put(curUser, new WikiRevisionUser("cur", curUser));
				}

				WikiRevisionUser revision = revisionMap.get(curUser);
				addRevision(activeUser, curActionValue, revision);
				activeUser = new String(curUser);
				revisionMap.put(curUser, revision);
				System.out.println(curUser + "  " + curActionValue);
			}

			// if (revisionMap.containsKey("83.228.60.175")) {
			// if (revisionMap.get("83.228.60.175").postitivProcesses > 1) {
			// System.out.println("STPO");
			// System.exit(1);
			// }
			// }
		}
		for (Entry<String, WikiRevisionUser> entry : revisionMap.entrySet()) {
			System.out.printf("user:%s | type: %s | pos: %d | neg: %d | %s", entry.getKey(), entry.getValue().type,
					entry.getValue().getPostitivProcesses(), entry.getValue().getNegativeProcesses(),
					entry.getValue().getInteractedOn());
			System.out.println();
			System.out.println("_ _ _ _ _");

		}

	}

	private void addRevision(String activeUser, Integer curActionValue, WikiRevisionUser revision) {
		if (curActionValue < 0) {
			revision.addNegativeProcess(curActionValue);
			negativInteraction += curActionValue;
		} else if (curActionValue > 0) {
			revision.addPositiveProcess(curActionValue);
			positivInteraction += curActionValue;
		} else if (curActionValue == 0) {
			neutralInteraction += curActionValue;
		}
		revision.interactedWith(activeUser);
	}

	/**
	 * Starts the analysis. If you miss to
	 */
	public void startAnalysis() {
		startAbstractionIfNeeded();
	}

	/**
	 * Determines which kind of the action the user did. <1>: User reacts
	 * positive of the last comment. User added some bytes. <-1>: User reacts
	 * negative on the last comment. User deletes some bytes. <0>: User didn't
	 * change anything. Returns the same value if an action wasn't recordnize,
	 * too.
	 * 
	 * @param element
	 *            Section which contains the impact of the reviser
	 *            post(pos,neg,null).
	 * @return Possible Values {-1,0,1}
	 */
	private Integer detActionValue(Elements element) {
		String valueToInvestigate = element.attr("class");
		Integer valueOfAction;
		switch (valueToInvestigate) {
		case "mw-plusminus-null":
			valueOfAction = 0;
			break;
		case "mw-plusminus-pos":
			valueOfAction = 1;
			break;
		case "mw-plusminus-neg":
			valueOfAction = -1;
			break;
		default:
			// if action is unknown then return 0. No impact in the coming calc.
			valueOfAction = 0;
			SystemMessage.wMessage("Action is unkown. Content:" + element.toString() + " " + this.getClass().getName());
			break;
		}
		return valueOfAction;
	}

	/**
	 * Executes all aggregations methods if needed. If everything is set
	 * correctly then this method will do nothing.
	 */
	private void startAbstractionIfNeeded() {
		if (linksToRevisions.isEmpty()) {
			System.out.println("linksToRevisions - is empty");
			abstractRevisionItems();
		}
	}

}
