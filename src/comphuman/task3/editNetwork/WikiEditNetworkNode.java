package comphuman.task3.editNetwork;

import java.util.ArrayList;

public class WikiEditNetworkNode {

	String userName;
	String userRole;
	Integer activityIndex = 0;

	Integer positivActions;
	Integer negativActions;
	Integer neutralActions;
	Integer nodeHigh;
	Double nodeWeight;
	Integer netAdded = 0;

	Double netAddedRatio = 0d;
	Double relationOfRevisorAndRevised = 0d;
	Double nodeRatio;

	ArrayList<String> revisorFor;
	ArrayList<String> revisedBy;

	/**
	 * Creates a wiki-edit-network-node
	 * 
	 * @param wikiRevisionUser
	 *            user, which should be added to the edit-network.
	 * @param revisedBy
	 *            A list of all reviser for the given user.
	 */
	public WikiEditNetworkNode(WikiRevisionUser wikiRevisionUser, ArrayList<String> revisedBy) {
		super();
		this.userName = wikiRevisionUser.getUsername();
		this.userRole = wikiRevisionUser.userRole;
		this.positivActions = wikiRevisionUser.getPostitivProcesses();
		this.negativActions = wikiRevisionUser.getNegativeProcesses();
		this.neutralActions = wikiRevisionUser.getNeutralProcess();
		this.activityIndex = calcActivityIndex(wikiRevisionUser);
		this.netAdded = calcNetAdded(wikiRevisionUser);
		this.netAddedRatio = clacNetAddedRation(this.netAdded, this.activityIndex);
		this.revisorFor = wikiRevisionUser.getInteractedOn();
		this.revisedBy = revisedBy;
		this.nodeHigh = revisorFor.size();
		this.nodeWeight = checkEmptyElementAndReturnSize(revisedBy);
		this.nodeRatio = calcNodeRatio(this.nodeHigh, this.nodeWeight);
		this.relationOfRevisorAndRevised = (double) revisorFor.size() / (double) revisedBy.size();
	}

	/**
	 * Checks if the user contains an empty-user element in the reviser-list. If
	 * the user contains the empty-user element then the method will return 0.1
	 * else the method will return the size of the reviser list.
	 * 
	 * @param revisedby
	 *            A list of all reviser for the given user.
	 * @return A number between 0.1,1,...,n-1.
	 */
	private Double checkEmptyElementAndReturnSize(ArrayList<String> revisedby) {
		Double value = 0d;
		if (revisedby.contains(XSpecialIndicator.noElement))
			value = 0.1d;
		else
			value = (double) revisedby.size();
		return value;
	}

	/**
	 * Calculates the node-ratio.
	 * 
	 * @param innerNodeHeight
	 *            Height of the node. Given as a number of revised-action by the
	 *            user.
	 * @param innerNodeWeight
	 *            Weight of the node. Given a number of users which revised the
	 *            given user.
	 * @return The ratio between height and weight (height/weight).
	 */
	private Double calcNodeRatio(Integer innerNodeHeight, Double innerNodeWeight) {
		return (double) innerNodeHeight / (double) innerNodeWeight;
	}

	/**
	 * Calculates the net-added ratio
	 * 
	 * @param innerNetAdded
	 *            The value of netAdded-Actions.
	 * @param innerActivtyIndex
	 *            The value of the user-acitivty.
	 * @return Number between -1 and 1;
	 */
	private Double clacNetAddedRation(Integer innerNetAdded, Integer innerActivtyIndex) {
		return (double) innerNetAdded / (double) activityIndex;
	}

	/**
	 * Calculates the net-added value.
	 * 
	 * @param wikiRevisionUser
	 *            The user where to aggregate from.
	 * @return The number of net-addeds.
	 */
	private Integer calcNetAdded(WikiRevisionUser wikiRevisionUser) {
		Integer calcInnerNetAdded = positivActions + neutralActions - negativActions;
		return calcInnerNetAdded;
	}

	/**
	 * Calculates the activity of the user.
	 * 
	 * @param wikiRevisionUser
	 *            The user where to aggregate from.
	 * @return A number of activity.
	 */
	private Integer calcActivityIndex(WikiRevisionUser wikiRevisionUser) {
		Integer innerActivityIndex = positivActions + neutralActions + negativActions;
		return innerActivityIndex;
	}

	/**
	 * Returns the name of the given user.
	 * 
	 * @return Name of user.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the activity-value.
	 * 
	 * @return Value of activity.
	 */
	public Integer getActivityIndex() {
		return activityIndex;
	}

	/**
	 * Returns the number of the positive actions form the given user.
	 * 
	 * @return Number of positive-actions.
	 */
	public Integer getPositivActions() {
		return positivActions;
	}

	/**
	 * Returns the number of the negative-actions form the given user.
	 * 
	 * @return Number of negative-actions
	 */
	public Integer getNegativActions() {
		return negativActions;
	}

	/**
	 * Returns the number of neutral-actions form the given user.
	 * 
	 * @return Number of neutral-actions.
	 */
	public Integer getNeutralActions() {
		return neutralActions;
	}

	/**
	 * Returns the height from the node.
	 * 
	 * @return Height from node.
	 */
	public Integer getNodeHigh() {
		return nodeHigh;
	}

	/**
	 * Returns the weight from the node.
	 * 
	 * @return Weight from the node.
	 */
	public Double getNodeWeight() {
		return nodeWeight;
	}

	/**
	 * Returns the net-added-ratio value.
	 * 
	 * @return Returns the net-added-ratio value.
	 */
	public Double getNetAddedRatio() {
		return netAddedRatio;
	}

	/**
	 * Returns the correlation between the reviser-role and the revised-role.
	 * 
	 * @return Ratio between reviser/revised.
	 */
	public Double getRelationOfRevisorAndRevised() {
		return relationOfRevisorAndRevised;
	}

	/**
	 * Returns the node-ratio.
	 * 
	 * @return Value of node-ratio.
	 */
	public Double getNodeRatio() {
		return nodeRatio;
	}

	/**
	 * Returns the list where the user take the role of a reviser.
	 * 
	 * @return List of revised-users.
	 */
	public ArrayList<String> getRevisorFor() {
		return revisorFor;
	}

	/**
	 * Returns a list of user-names which revised the given user.
	 * 
	 * @return List of reviser.
	 */
	public ArrayList<String> getRevisedBy() {
		return revisedBy;
	}
}
