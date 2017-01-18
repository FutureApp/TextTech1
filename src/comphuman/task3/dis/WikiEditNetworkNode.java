package comphuman.task3.dis;

import java.util.ArrayList;

public class WikiEditNetworkNode {

	String userName;
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

	public WikiEditNetworkNode(WikiRevisionUser wikiRevisionUser, ArrayList<String> revisedBy) {
		super();
		this.userName 		= wikiRevisionUser.getUsername();
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
}
