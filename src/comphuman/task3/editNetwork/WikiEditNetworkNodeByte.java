package comphuman.task3.editNetwork;

import java.util.ArrayList;
import java.util.List;

public class WikiEditNetworkNodeByte {

	private WikiReviserEditNetworkNode orginalNode;
	private String role;
	private String name;
	private Double posActions;
	private Double negActions;
	private Double neuActions;
	private Double activityIndex;
	private Double netAdded;
	private Double netAddedRatio;
	private ArrayList<String> revisorFor;
	private ArrayList<String> revisedBy;
	private Double nodeHigh;
	private Double nodeWeight;
	private Double nodeRatio;
	private Double relationOfRevisorAndRevised;

	/**
	 * * Inits a wiki-edit-network-node.
	 * 
	 * @param nameOfNode
	 *            user, which should be added to the edit-network.
	 * @param wikiReviserEditNetworkNode
	 *            The node-information for the given user.
	 */
	public WikiEditNetworkNodeByte(String nameOfNode, WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		super();
		this.orginalNode = wikiReviserEditNetworkNode;
		this.name = nameOfNode;
		this.role = wikiReviserEditNetworkNode.getRole();
		calcOutGoingActions(wikiReviserEditNetworkNode);
		this.activityIndex = calcActivityIndex(wikiReviserEditNetworkNode);
		this.netAdded = calcNetAdded(wikiReviserEditNetworkNode);
		this.netAddedRatio = clacNetAddedRation(this.netAdded, this.activityIndex);
		this.revisorFor = calcReviserForNodes(wikiReviserEditNetworkNode);
		this.revisedBy = calcRevisedByNodes(wikiReviserEditNetworkNode);
		this.nodeHigh = calcReviserBytes(wikiReviserEditNetworkNode);
		this.nodeWeight = calcRevisedBytes(wikiReviserEditNetworkNode);
		this.nodeRatio = calcNodeRatio(this.nodeHigh, this.nodeWeight);
		this.relationOfRevisorAndRevised = this.nodeHigh / this.nodeWeight;

	}

	/**
	 * 
	 * @param nodeHigh2
	 *            Height of the node. Given as a number of revised-action by the
	 *            user.
	 * @param nodeWeight2
	 *            Weight of the node. Given a number of users which revised the
	 *            given user.
	 * @return The ratio between height and weight (height/weight).
	 */
	private Double calcNodeRatio(Double nodeHigh2, Double nodeWeight2) {
		return nodeHigh2 / nodeWeight2;
	}

	/**
	 * Calcs the value over all revised bytes.(user is revised)
	 * 
	 * @param wikiReviserEditNetworkNode
	 *            The given node for the given user.
	 * @return Amount of revised bytes.
	 */
	private Double calcRevisedBytes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		List<WikiEditNetworkEdge> revisedList = wikiReviserEditNetworkNode.getRevisedList();
		Double value = 0d;
		for (int i = 0; i < revisedList.size(); i++) {
			Integer amountValue = revisedList.get(i).getActionValue();
			if (amountValue < 0)
				amountValue = amountValue * (-1);
			value += amountValue;
		}
		return value;
	}

	/**
	 * Calcs the value over all revised bytes(user is reviser).
	 * 
	 * @param wikiReviserEditNetworkNode
	 *            The given node for the given user.
	 * @return Amount of revised bytes by given user. Same value as the
	 *         activity-index.
	 */
	private Double calcReviserBytes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		return activityIndex;
	}

	/**
	 * Returns a list for all users which revised the given user.
	 * 
	 * @param wikiReviserEditNetworkNode
	 *            The node-information of the given user.
	 * @return A list of user-names.
	 */
	private ArrayList<String> calcRevisedByNodes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		List<WikiEditNetworkEdge> revisedList = wikiReviserEditNetworkNode.getRevisedList();
		ArrayList<String> nodeNames = new ArrayList<>();
		for (int i = 0; i < revisedList.size(); i++) {
			String target = revisedList.get(i).getTarget();
			nodeNames.add(target);
		}
		return nodeNames;
	}

	/**
	 * 
	 * Returns a list for all users which revised the given user.
	 * 
	 * @param wikiReviserEditNetworkNode
	 *            The node-information of the given user.
	 * @return A list of user-names.
	 */
	private ArrayList<String> calcReviserForNodes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		List<WikiEditNetworkEdge> ReviserList = wikiReviserEditNetworkNode.getReviserList();
		ArrayList<String> nodeNames = new ArrayList<>();
		for (int i = 0; i < ReviserList.size(); i++) {
			String source = ReviserList.get(i).getTarget();
			nodeNames.add(source);
		}
		return nodeNames;
	}

	/**
	 * Calcs the net-added-ratio between netadded and the activity-index.
	 * 
	 * @param netAdded2
	 *            Value of net-added.
	 * @param activityIndex2
	 *            Value of activity-index.
	 * @return netadde/activity-index..
	 */
	private Double clacNetAddedRation(Double netAdded2, Double activityIndex2) {
		return netAdded2 / activityIndex2;
	}

	/**
	 * Calcs the net-added value.
	 * 
	 * @param wikiReviserEditNetworkNode
	 *            The node-information of the given user.
	 * @return The net-added value.
	 */
	private Double calcNetAdded(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		return posActions + neuActions - negActions;
	}

	/**
	 * Calcs the activity-index.
	 * 
	 * @param wikiReviserEditNetworkNode
	 *            The node-information of the given user.
	 * @return The value of the activity-index.
	 */
	private Double calcActivityIndex(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		return posActions + negActions + neuActions;
	}

	/**
	 * Calcs the out-come of the node.Splitted in posActions,negActions and
	 * neuActions VALUES.
	 * 
	 * @param wikiReviserEditNetworkNode
	 *            The node-information of the given user.
	 */
	private void calcOutGoingActions(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		Double valuePost = 0d;
		Double valueNeg = 0d;
		Double valueNeut = 0d;
		List<WikiEditNetworkEdge> reviserList = wikiReviserEditNetworkNode.getReviserList();
		for (WikiEditNetworkEdge wikiEditNetworkEdge : reviserList) {
			Integer value = wikiEditNetworkEdge.getActionValue();
			if (value < 0)
				valueNeg += (double) (value * (-1));
			if (value > 0)
				valuePost += (double) value;
			if (value == 0)
				valueNeut += 0.1d;
		}
		posActions = valuePost;
		negActions = valueNeg;
		neuActions = valueNeut;

	}

	/**
	 * Offers a method to extract information from the edge-list.
	 * 
	 * @param index
	 *            Pointer for pos in the list.
	 * @return The value which is offered by index-position.
	 */
	public Double getEdgeWeight(Integer index) {
		WikiEditNetworkEdge element = orginalNode.getReviserList().get(index);
		Double value = (double) element.getActionValue();
		return value;

	}

	/**
	 * Return the role of the given user. See {@link XSpecialIndicator}
	 * 
	 * @return The role of the user.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return The name of the user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value of the pos-actions.
	 * 
	 * @return Value of positive-actions(byte-representation).
	 */
	public Double getPosActions() {
		return posActions;
	}

	/**
	 * Returns the value of the neg-actions.
	 * 
	 * @return Value of negative-actions(byte-representation).
	 */
	public Double getNegActions() {
		return negActions;
	}

	/**
	 * Returns the value of the neu-actions.
	 * 
	 * @return Value of neutral-actions(byte-representation).
	 */
	public Double getNeuActions() {
		return neuActions;
	}

	/**
	 * Returns the activity-index.
	 * 
	 * @return The activity-index.
	 */
	public Double getActivityIndex() {
		return activityIndex;
	}

	/**
	 * Return the value of net-added.
	 * 
	 * @return value of net-added.
	 */
	public Double getNetAdded() {
		return netAdded;
	}

	/**
	 * Return the value of net-added-ratio.
	 * 
	 * @return value of net-added-ratio.
	 */
	public Double getNetAddedRatio() {
		return netAddedRatio;
	}

	/**
	 * Returns a list of all revised users.(Given user was active)
	 * 
	 * @return user-names of revised users.
	 */
	public ArrayList<String> getRevisorFor() {
		return revisorFor;
	}

	/**
	 * Returns a list of all revising users.(Given user was passive).
	 * 
	 * @return user-names which was revising the given user.
	 */
	public ArrayList<String> getRevisedBy() {
		return revisedBy;
	}

	/**
	 * Returns the height of the node.
	 * 
	 * @return Value of the height of the node.
	 */
	public Double getNodeHeight() {
		return nodeHigh;
	}

	/**
	 * Returns the weight of the node.
	 * 
	 * @return Value of the height of the node.
	 */
	public Double getNodeWeight() {
		return nodeWeight;
	}

	/**
	 * Returns the height of the node
	 * 
	 * @return Value of the height of the node.
	 */
	public Double getNodeRatio() {
		return nodeRatio;
	}

	/**
	 * Returns the relation-value between Reviser and Revised-users.
	 * 
	 * @return Value of relation.
	 */
	public Double getRelationOfReviserAndRevised() {
		return relationOfRevisorAndRevised;
	}
}
