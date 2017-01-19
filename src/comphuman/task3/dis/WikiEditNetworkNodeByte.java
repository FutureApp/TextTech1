package comphuman.task3.dis;

import java.util.ArrayList;

public class WikiEditNetworkNodeByte {

	String userName;
	String userRole;
	Integer activityIndex = 0;

	Integer positivActions;
	Integer negativActions;
	Integer neutralActions;
	Integer nodeHigh;
	Integer nodeWeight;
	Integer netAdded = 0;
	
	
	Double netAddedRatio = 0d;
	Double relationOfRevisorAndRevised = 0d;
	Double nodeRatio;

	ArrayList<String> revisorFor;
	ArrayList<String> revisedBy;

	public WikiEditNetworkNodeByte(WikiRevisionUser wikiRevisionUser, ArrayList<String> revisedBy) {
		super();
		this.userName 		= wikiRevisionUser.getUsername();
		this.userRole 		= wikiRevisionUser.userRole;
		this.activityIndex  = calcActivityIndex(wikiRevisionUser);
		this.positivActions = wikiRevisionUser.getPostitivProcesses();
		this.negativActions = wikiRevisionUser.getNegativeProcesses();
		this.neutralActions = wikiRevisionUser.getNeutralProcess();
		this.netAdded 		= calcNetAdded(wikiRevisionUser);
		this.netAddedRatio  = clacNetAddedRation(this.netAdded, this.activityIndex);
		this.revisorFor 	= wikiRevisionUser.getInteractedOn();
		this.revisedBy 		= revisedBy;
		this.nodeHigh 		= revisorFor.size();
		this.nodeWeight     = revisedBy.size();
		this.nodeRatio 		= calcNodeRatio(this.nodeHigh, this.nodeWeight);
		this.relationOfRevisorAndRevised = (double)revisorFor.size()/(double)revisedBy.size();
	}
	private Double calcNodeRatio(Integer innerNodeHigh, Integer innerNodeWeight) {
		return  (double)innerNodeHigh/(double)innerNodeWeight;
	}

	private Double clacNetAddedRation(Integer innerNetAdded, Integer innerActivtyIndex) {
		return netAddedRatio/activityIndex;
	}

	private Integer calcNetAdded(WikiRevisionUser wikiRevisionUser) {
		Integer calcInnerNetAdded = wikiRevisionUser.getPostitivProcesses() + wikiRevisionUser.getNeutralProcess()
				- wikiRevisionUser.getNegativeProcesses();
		return calcInnerNetAdded;
	}

	private Integer calcActivityIndex(WikiRevisionUser wikiRevisionUser) {
		Integer innerActivityIndex = wikiRevisionUser.getNegativeProcesses() + wikiRevisionUser.getPostitivProcesses()
				+ wikiRevisionUser.getNeutralProcess();
		return innerActivityIndex;
	}










	public String getUserName() {
		return userName;
	}










	public Integer getActivityIndex() {
		return activityIndex;
	}










	public Integer getPositivActions() {
		return positivActions;
	}










	public Integer getNegativActions() {
		return negativActions;
	}










	public Integer getNeutralActions() {
		return neutralActions;
	}










	public Integer getNodeHigh() {
		return nodeHigh;
	}










	public Integer getNodeWeight() {
		return nodeWeight;
	}










	public Integer getNetAdded() {
		return netAdded;
	}










	public Double getNetAddedRatio() {
		return netAddedRatio;
	}










	public Double getRelationOfRevisorAndRevised() {
		return relationOfRevisorAndRevised;
	}










	public Double getNodeRatio() {
		return nodeRatio;
	}










	public ArrayList<String> getRevisorFor() {
		return revisorFor;
	}










	public ArrayList<String> getRevisedBy() {
		return revisedBy;
	}
}
