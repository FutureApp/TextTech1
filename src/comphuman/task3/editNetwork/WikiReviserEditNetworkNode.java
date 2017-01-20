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
	 * Inits. a reviser network Node
	 * 
	 * @param activeUser
	 *            User which is the reviser.
	 * @param userRole
	 *            Role of the user {@link XSpecialIndicator}
	 */
	public WikiReviserEditNetworkNode(String activeUser, String userRole) {
		super();
		this.nameOfUser = activeUser;
		this.role = userRole;
		this.reviserEdges = new ArrayList<>();
		this.revisedEdges = new ArrayList<>();
	}

	/**
	 * Adds an edge into to reviser-edge-list.
	 * 
	 * @param edge
	 *            The edge which should be added.
	 */
	public void addReviserEdge(WikiEditNetworkEdge edge) {
		reviserEdges.add(edge);
	}

	/**
	 * Adds an edge into to revised-edge-list.
	 * 
	 * @param edge
	 *            The edge which should be added.
	 */
	public void addRevisedEdge(WikiEditNetworkEdge edge) {
		revisedEdges.add(edge);
	}

	/**
	 * Returns the list of revised-edges.
	 * 
	 * @return Edges (revised-edges)
	 */
	public List<WikiEditNetworkEdge> getRevisedList() {
		return revisedEdges;
	}

	/**
	 * Returns the list of reviser-edges.
	 * 
	 * @return Edges (reviser-edges)
	 */
	public List<WikiEditNetworkEdge> getReviserList() {
		return reviserEdges;
	}

	/**
	 * A string representation of the revised-edges-list
	 * {@link #getRevisedList()}.
	 * 
	 * @return A string representing the revised-edge-list.
	 */
	public String getRevisedListToString() {
		String returnValue = "[";
		for (WikiEditNetworkEdge wikiEditNetworkEdge : revisedEdges) {
			Formatter formatter = new Formatter();
			String source = wikiEditNetworkEdge.source;
			Integer actionValue = wikiEditNetworkEdge.getActionValue();
			formatter.format("(%s,%d),", source, actionValue);
			returnValue += formatter.toString();
			formatter.close();
		}
		returnValue = returnValue.substring(0, returnValue.length() - 1) + "]";
		return returnValue;
	}

	/**
	 * A string representation of the reviser-edges-list
	 * {@link #getReviserList()}.
	 * 
	 * @return A string representing the reviser-edge-list.
	 */
	public String getReviserListToString() {
		String returnValue = "[";
		for (WikiEditNetworkEdge wikiEditNetworkEdge : reviserEdges) {
			Formatter formatter = new Formatter();
			String target = wikiEditNetworkEdge.target;
			Integer actionValue = wikiEditNetworkEdge.getActionValue();
			formatter.format("(%s,%d),", target, actionValue);
			returnValue += formatter.toString();
			formatter.close();
		}
		returnValue = returnValue.substring(0, returnValue.length() - 1) + "]";
		return returnValue;
	}

	/**
	 * Returns the role of the given user.
	 * @return Role of the user {@link XSpecialIndicator}
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role of the given user.
	 * @param role Role of the user {@link XSpecialIndicator}
	 */
	public void setRole(String role) {
		role = role;
	}

}
