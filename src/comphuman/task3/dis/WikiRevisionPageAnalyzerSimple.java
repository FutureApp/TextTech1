package comphuman.task3.dis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import scala.Array;
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
	ArrayList<String> linksToRevisionsPage = new ArrayList<>();
	
	List<List<String>> listOfListRevisions = new ArrayList<>();
	HashMap<String, WikiRevisionUser> revisionMap = new HashMap<>();
	
	private HashMap<String, ArrayList<String>> revisedMap  = new HashMap<>();

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
	public void abstractRevisionItems() {
		//Provides an empty list for each method-call
		if(!listOfListRevisions.isEmpty()) listOfListRevisions.clear();
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
				listOfListRevisions.add(realUserMap);
			}

	}

	public void generateMapsForCalcs() {
		List<List<String>> reversedRevList = new ArrayList<>();
		for (int i = 0; i < listOfListRevisions.size(); i++) {
			List<String> item = listOfListRevisions.get(i);
			reversedRevList.add(item);
		}

		Collections.reverse(reversedRevList);

		System.out.println(reversedRevList.size());
		String activeRevisedUserName = new String("");
		System.out.println(reversedRevList.size() + "+-----------");

		for (int i = 0; i < reversedRevList.size(); i++) {
			List<String> item = reversedRevList.get(i);
			String currentNameOfRevisor = item.get(0);
			Integer currentRevisionAction = Integer.parseInt(item.get(1));

			if (i == 0) {
				activeRevisedUserName = new String(currentNameOfRevisor);
				WikiRevisionUser revUser = new WikiRevisionUser("aut", activeRevisedUserName);
				revUser.addPositiveProcess(1);
				revisionMap.put(activeRevisedUserName, revUser);

			} else {
				if (!revisionMap.containsKey(currentNameOfRevisor)) {
					revisionMap.put(currentNameOfRevisor, new WikiRevisionUser("cur", currentNameOfRevisor));
				}

				WikiRevisionUser userOfRevision = revisionMap.get(currentNameOfRevisor);
				addRevision(activeRevisedUserName, currentRevisionAction, userOfRevision);
				addRevised(activeRevisedUserName,currentNameOfRevisor);
				
				activeRevisedUserName = new String(currentNameOfRevisor);
				revisionMap.put(currentNameOfRevisor, userOfRevision);
				System.out.println(currentNameOfRevisor + "  " + currentRevisionAction);
			}
		}
		
		for (Entry<String, WikiRevisionUser> entry : revisionMap.entrySet()) {
			System.out.printf("user:%s | type: %s | pos: %d | neg: %d | neut: %d | %s", entry.getKey(), entry.getValue().type,
					entry.getValue().getPostitivProcesses(), entry.getValue().getNegativeProcesses(), entry.getValue().getNeutralProcess(),
					entry.getValue().getInteractedOn());
			System.out.println();
			System.out.println("_ _ _ _ _");

		}
		
		System.out.println("........................................");
		System.out.println();
		for (Entry<String,ArrayList<String>> item : revisedMap.entrySet()) {
			System.out.printf("user <%s> revised by <%s>", item.getKey(),item.getValue().toString());
			System.out.println();
		}
	}

	private void addRevised(String activeRevisedUserName, String currentNameOfRevisor) {
		if(!revisedMap.containsKey(activeRevisedUserName)) revisedMap.put(activeRevisedUserName, new ArrayList<>());
		revisedMap.get(activeRevisedUserName).add(currentNameOfRevisor);
	}

	private void addRevision(String activeUser, Integer curActionValue, WikiRevisionUser revision) {
		if (curActionValue < 0) {
			revision.addNegativeProcess(curActionValue);
			negativInteraction += curActionValue;
		} else if (curActionValue > 0) {
			revision.addPositiveProcess(curActionValue);
			positivInteraction += curActionValue;
		} else if (curActionValue == 0) {
			revision.addNeutralProcess(curActionValue);
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
			// if action is unknown then return 0. No impact for further calc.
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
		if (linksToRevisionsPage.isEmpty()) {
			System.out.println("linksToRevisions - is empty");
			abstractRevisionItems();
		}
	}

	public ArrayList<WikiEditNetworkNode> generateEditNodes() {
		ArrayList<WikiEditNetworkNode> editNetworkNode = new ArrayList<>();
		
		for (Entry<String, WikiRevisionUser> entry : revisionMap.entrySet()) {
			System.out.println(entry.getKey());
			WikiEditNetworkNode node;
			if(revisedMap.containsKey(entry.getValue().getUsername())){
				node = new WikiEditNetworkNode(entry.getValue(), revisedMap.get(entry.getValue().getUsername()));
			}else{
				ArrayList<String> emptyElementList = new ArrayList<>();
				emptyElementList.add("<$noElement$>");
				node = new WikiEditNetworkNode(entry.getValue(), emptyElementList);
			}
			editNetworkNode.add(node);
		}
		return editNetworkNode;
	}
}
