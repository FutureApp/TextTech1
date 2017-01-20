package comphuman.task3.editNetwork;

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
public class WikiRevisionPageAnalyzerSimpleByte {
	private String revisionContentHolderID = "pagehistory";

	private List<ReviserItem> listOfReviserItems = new ArrayList<>();
	private Document wikiRevisionPage;

	private ArrayList<WikiEditNetworkNodeByte> list;
	private HashMap<String, WikiReviserEditNetworkNode> holder;

	public WikiRevisionPageAnalyzerSimpleByte(Document wikiRevisionPage) {
		this.wikiRevisionPage = wikiRevisionPage;
		this.holder = new HashMap<>();
	}

	public List<ReviserItem> startExtractionOfReviserItems() {
		Element selectedElements = wikiRevisionPage.getElementById(revisionContentHolderID);
		Elements liElements = selectedElements.select("li");
		for (Element element : liElements) {
			Elements containsUserName = element.select(".history-user");
			Elements userNameWiki = containsUserName.select("a");
			String nameOfUser = userNameWiki.select("bdi").text();
			Elements unknownActionValue = element.select("[class*='mw-plusminus-']");
			if (unknownActionValue.size() != 1)
				SystemMessage.wMessage("Action for user <%s> is unknown.");
			else {
				String bytesChanged = unknownActionValue.get(0).text().replaceAll("[().]", "");
				Integer bytesAsInteger = Integer.parseInt(bytesChanged);
				ReviserItem revItem = new ReviserItem(nameOfUser, bytesAsInteger);
				listOfReviserItems.add(revItem);
			}
			System.out.println(nameOfUser);
			// last element is chronicle the first element..
		}
		Collections.reverse(listOfReviserItems);
		return listOfReviserItems;
	}

	/**
	 * Creates nodes for the wiki-edit-network-graph.
	 */
	public void startCreationOfWikiEditNetwork() {
		System.out.println("----");

		for (int i = 0; i < listOfReviserItems.size(); i++) {
			ReviserItem item = listOfReviserItems.get(i);
			Integer actionValue = item.getBytesAsInteger();
			System.out.println(item.getNameOfUser());

			if (i == 0) {
				WikiReviserEditNetworkNode node = new WikiReviserEditNetworkNode(item.getNameOfUser(),
						XSpecialIndicator.roleAuthor);
				WikiEditNetworkEdge reviserEdge = new WikiEditNetworkEdge(item.getNameOfUser(), item.getNameOfUser(),
						actionValue);
				node.addReviserEdge(reviserEdge);
				holder.put(item.getNameOfUser(), node);
			} else {
				WikiReviserEditNetworkNode node;
				WikiEditNetworkEdge reviserEdge;

				if (!holder.containsKey(item.getNameOfUser())) {
					node = new WikiReviserEditNetworkNode(item.getNameOfUser(), XSpecialIndicator.roleContributor);
					reviserEdge = new WikiEditNetworkEdge(item.getNameOfUser(),
							listOfReviserItems.get(i - 1).getNameOfUser(), actionValue);
					node.addReviserEdge(reviserEdge);
					holder.put(item.getNameOfUser(), node);
				} else {
					node = holder.get(item.getNameOfUser());
					reviserEdge = new WikiEditNetworkEdge(item.getNameOfUser(),
							listOfReviserItems.get(i - 1).getNameOfUser(), actionValue);
					node.addReviserEdge(reviserEdge);
				}
				WikiEditNetworkEdge revisedByEdge = new WikiEditNetworkEdge(listOfReviserItems.get(i).getNameOfUser(),
						listOfReviserItems.get(i-1).getNameOfUser(), actionValue);
				WikiReviserEditNetworkNode pastNode = holder.get(listOfReviserItems.get(i-1).getNameOfUser());
				pastNode.addRevisedEdge(revisedByEdge);
			}
			
			if(i== listOfReviserItems.size()-1){
				WikiReviserEditNetworkNode node = holder.get(listOfReviserItems.get(i).getNameOfUser());
				System.out.println(node.getRole());
				if(node.getRole().equals(XSpecialIndicator.roleAuthor)){
					node.setRole(XSpecialIndicator.roleBoth);
				}
				else{
					node.setRole(XSpecialIndicator.roleLastContributor);
				}
			}
		}
		
	}

	public void hot() {
		for (int i = 0; i < listOfReviserItems.size(); i++) {
			System.out.println(listOfReviserItems.get(i).getNameOfUser());
		}
		for (Entry<String, WikiReviserEditNetworkNode> entry : holder.entrySet()) {
			System.out.printf("<%s> <ReviserFor :%s> <RevisedBy :%s", entry.getKey(),
					entry.getValue().getReviserListToString(), entry.getValue().getRevisedListToString());
			System.out.println();
		}

	}

	public void startCreateWikiNodeRevisor() {
		list = new ArrayList<>();
		for (Entry<String, WikiReviserEditNetworkNode> entry : holder.entrySet()) {
			WikiEditNetworkNodeByte realNode = new WikiEditNetworkNodeByte(entry.getKey(), entry.getValue());
			list.add(realNode);
		}
		
	}
	public ArrayList<WikiEditNetworkNodeByte> exportWikiNodeRevisor() {
		return list;
	}
}
