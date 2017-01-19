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

	private HashMap<String, ArrayList<String>> revisedMap = new HashMap<>();

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
	 * recognized.
	 * 
	 * @return Array of an Arrays which contains the map [user,actionValue].
	 */
	public void abstractRevisionItems() {
		// Provides an empty list for each method-call
		if (!listOfListRevisions.isEmpty())
			listOfListRevisions.clear();
		Element selectedElements = wikiRevisionPage.getElementById(revisionContentHolderID);
		Elements liElements = selectedElements.select("li");

		/* iterate throw all elements and det. the action-impact. */
		for (Element element : liElements) {
			Elements containsUserName = element.select(".history-user");
			Elements userNameWiki = containsUserName.select("a");
			String userName = userNameWiki.select("bdi").text();
			Elements unknownActionValue = element.select("[class*='mw-plusminus-']");
			Integer actionValue = 0;
			if (unknownActionValue.size() != 1)
				SystemMessage.wMessage("Action for user <%s> is unknown.");
			else
				actionValue = detActionValue(unknownActionValue);
			ArrayList<String> realUserMap = new ArrayList<>();
			realUserMap.add(userName);
			realUserMap.add(actionValue + "");
			listOfListRevisions.add(realUserMap);
		}
	}

	/**
	 * Generate the map for the calcs. The calcs. will be done by class
	 * {@link WikiEditNetworkNode}:
	 */
	public void generateMapsForCalcs() {
		List<List<String>> reversedRevList = new ArrayList<>();
		for (int i = 0; i < listOfListRevisions.size(); i++) {
			List<String> item = listOfListRevisions.get(i);
			reversedRevList.add(item);
		}
		/*
		 * Starts at the first user, which produced content. The author of the
		 * wiki-article-page
		 */
		Collections.reverse(reversedRevList);
		String activeRevisedUserName = new String("");

		for (int i = 0; i < reversedRevList.size(); i++) {
			List<String> item = reversedRevList.get(i);
			String currentNameOfRevisor = item.get(0);
			Integer currentRevisionAction = Integer.parseInt(item.get(1));

			/* inserts the first user. Special because index-1 doesn't exists */
			if (i == 0) {
				activeRevisedUserName = new String(currentNameOfRevisor);
				WikiRevisionUser revUser = new WikiRevisionUser("aut", activeRevisedUserName);
				revUser.addPositiveProcess(1);
				revisionMap.put(activeRevisedUserName, revUser);

				// For all other user exluded first user.
			} else {

				// The last user which added some content.
				if (i == reversedRevList.size() - 1) {

					// User is new
					if (!revisionMap.containsKey(currentNameOfRevisor)) {
						revisionMap.put(currentNameOfRevisor, new WikiRevisionUser("last", currentNameOfRevisor));
						WikiRevisionUser userOfRevision = revisionMap.get(currentNameOfRevisor);
						addRevision(activeRevisedUserName, currentRevisionAction, userOfRevision);
						addRevised(activeRevisedUserName, currentNameOfRevisor);
						activeRevisedUserName = new String(currentNameOfRevisor);
						revisionMap.put(currentNameOfRevisor, userOfRevision);
					} else {
						// First user and last user are the same. New Tag is
						// given.
						if (revisionMap.get(currentNameOfRevisor).userRole.equals("aut")) {
							revisionMap.get(currentNameOfRevisor).setRole("both");
						} else {
							// User contains the type cur. Replace that with
							// last.
							revisionMap.put(currentNameOfRevisor, new WikiRevisionUser("last", currentNameOfRevisor));
							WikiRevisionUser userOfRevision = revisionMap.get(currentNameOfRevisor);
							addRevision(activeRevisedUserName, currentRevisionAction, userOfRevision);
							addRevised(activeRevisedUserName, currentNameOfRevisor);
							activeRevisedUserName = new String(currentNameOfRevisor);
							revisionMap.put(currentNameOfRevisor, userOfRevision);
						}
					}
					/* Do if non-special case. */
				} else {
					if (!revisionMap.containsKey(currentNameOfRevisor)) {
						revisionMap.put(currentNameOfRevisor, new WikiRevisionUser("cur", currentNameOfRevisor));
					}

					/*
					 * Inserts the user.
					 */
					WikiRevisionUser userOfRevision = revisionMap.get(currentNameOfRevisor);
					addRevision(activeRevisedUserName, currentRevisionAction, userOfRevision);
					addRevised(activeRevisedUserName, currentNameOfRevisor);

					activeRevisedUserName = new String(currentNameOfRevisor);
					revisionMap.put(currentNameOfRevisor, userOfRevision);
				}
			}
		}

		/*
		 * Prints some results of this method to the screen.
		 */
		for (Entry<String, WikiRevisionUser> entry : revisionMap.entrySet()) {
			System.out.printf("user:%s | type: %s | pos: %d | neg: %d | neut: %d | %s", entry.getKey(),
					entry.getValue().userRole, entry.getValue().getPostitivProcesses(),
					entry.getValue().getNegativeProcesses(), entry.getValue().getNeutralProcess(),
					entry.getValue().getInteractedOn());
			System.out.println();
			System.out.println(entry.getValue().getNeutralProcess() + "!!!");
			System.out.println("_ _ _ _ _");
		}
	}

	/**
	 * Adds a user to the revised-list
	 * @param activeRevisedUserName name of the user, which is currently active.
	 * @param currentNameOfRevisor name of user, which revised by the given(active) user.
	 */
	private void addRevised(String activeRevisedUserName, String currentNameOfRevisor) {
		if (!revisedMap.containsKey(activeRevisedUserName))
			revisedMap.put(activeRevisedUserName, new ArrayList<>());
		revisedMap.get(activeRevisedUserName).add(currentNameOfRevisor);
	}

	/**
	 * Adds the action-value to the user. 
	 * @param activeUser name of current active user.
	 * @param curActionValue the action-value.
	 * @param revision The user himself as a wiki-revision-user.
	 */
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
		revision.addRevisedUser(activeUser);
	}

	/**
	 * Starts the analysis.
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
			valueOfAction = ActionValue.neutral.getValue();
			break;
		case "mw-plusminus-pos":
			valueOfAction = ActionValue.positiv.getValue();
			break;
		case "mw-plusminus-neg":
			valueOfAction = ActionValue.negative.getValue();
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

	/**
	 * Generates a list of Nodes which contain to the edit-network-graph.
	 * 
	 * @return A list of nodes for the edit-network-graph.
	 */
	public ArrayList<WikiEditNetworkNode> generateEditNodes() {
		ArrayList<WikiEditNetworkNode> editNetworkNode = new ArrayList<>();
		for (Entry<String, WikiRevisionUser> entry : revisionMap.entrySet()) {
			WikiEditNetworkNode node;
			if (revisedMap.containsKey(entry.getValue().getUsername())) {
				node = new WikiEditNetworkNode(entry.getValue(), revisedMap.get(entry.getValue().getUsername()));
			} else {
				/*
				 * generates a dummy element to prevent linking to an
				 * null-object or more worst, division by zero. To determine
				 * this element by viz-step, a noElement-tag is used. (Last User
				 * could have non revisers).
				 */
				ArrayList<String> emptyElementList = new ArrayList<>();
				emptyElementList.add(XSpecialIndicator.noElement);
				node = new WikiEditNetworkNode(entry.getValue(), emptyElementList);
			}
			editNetworkNode.add(node);
		}
		return editNetworkNode;
	}
}
