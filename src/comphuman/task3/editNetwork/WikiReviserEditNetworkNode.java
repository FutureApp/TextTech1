package comphuman.task3.editNetwork;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class WikiReviserEditNetworkNode {
	private List<WikiEditNetworkEdge> reviserEdges;
	private List<WikiEditNetworkEdge> revisedEdges;
	private String nameOfUser;
	private String role;

	/**
	 * 
	 * @param activeUser
	 * @param userRole
	 */
	public WikiReviserEditNetworkNode(String activeUser, String userRole) {
		super();
		this.nameOfUser = activeUser;
		this.role = userRole;
		this.reviserEdges = new ArrayList<>();
		this.revisedEdges = new ArrayList<>();
	}

	public void addReviserEdge(WikiEditNetworkEdge edge) {
		reviserEdges.add(edge);
	}

	public void addRevisedEdge(WikiEditNetworkEdge edge) {
		revisedEdges.add(edge);
	}

	public List<WikiEditNetworkEdge> getRevisedList() {
		return revisedEdges;
	}

	public List<WikiEditNetworkEdge> getReviserList() {
		return reviserEdges;
	}

	public String getRevisedListToString() {
		String returnValue = "[";
		for (WikiEditNetworkEdge wikiEditNetworkEdge : revisedEdges) {
			Formatter formatter = new Formatter();
			String source = wikiEditNetworkEdge.source;
			Integer actionValue = wikiEditNetworkEdge.getActionValue();
			formatter.format("(%s,%d),", source,actionValue);
			returnValue += formatter.toString();
			formatter.close();
		}
		returnValue = returnValue.substring(0, returnValue.length()-1)+"]";
		return returnValue;
	}

	public String getReviserListToString() {
		String returnValue = "[";
		for (WikiEditNetworkEdge wikiEditNetworkEdge : reviserEdges) {
			Formatter formatter = new Formatter();
			String target = wikiEditNetworkEdge.target;
			Integer actionValue = wikiEditNetworkEdge.getActionValue();
			formatter.format("(%s,%d),", target,actionValue);
			returnValue += formatter.toString();
			formatter.close();
		}
		returnValue = returnValue.substring(0, returnValue.length()-1)+"]";
		return returnValue;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String roleboth) {
		role = roleboth;
	}

}
