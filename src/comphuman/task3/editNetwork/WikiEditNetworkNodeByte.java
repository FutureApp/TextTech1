package comphuman.task3.editNetwork;

import java.util.ArrayList;
import java.util.List;

public class WikiEditNetworkNodeByte {
	
	private String role;
	private String name;
	private Double posActions ;
	private Double negActions ;
	private Double neuActions ;
	private Double activityIndex;
	private Double netAdded;
	private Double netAddedRatio;
	private ArrayList<String> revisorFor;
	private ArrayList<String> revisedBy;
	private Double nodeHigh;
	private Double nodeWeight;
	private Double nodeRatio; 
	private Double relationOfRevisorAndRevised;

	/*
		this.relationOfRevisorAndRevised = (double) revisorFor.size() / (double) revisedBy.size();
	 */

	public WikiEditNetworkNodeByte(String nameOfNode ,WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		super();
		this.name     = nameOfNode;
		this.role 	  = wikiReviserEditNetworkNode.getRole();
						calcOutGoingActions(wikiReviserEditNetworkNode);
		this.activityIndex = calcActivityIndex(wikiReviserEditNetworkNode);
		this.netAdded = calcNetAdded(wikiReviserEditNetworkNode);
		this.netAddedRatio = clacNetAddedRation(this.netAdded, this.activityIndex);
		this.revisorFor = calcReviserForNodes(wikiReviserEditNetworkNode);
		this.revisedBy = calcRevisedByNodes(wikiReviserEditNetworkNode);
		this.nodeHigh = calcReviserBytes(wikiReviserEditNetworkNode);
		this.nodeWeight = calcRevisedBytes(wikiReviserEditNetworkNode);
		this.nodeRatio = calcNodeRatio(this.nodeHigh, this.nodeWeight);
		this.relationOfRevisorAndRevised = this.nodeHigh/ this.nodeWeight;
		
	}

	private Double calcNodeRatio(Double nodeHigh2, Double nodeWeight2) {
		return nodeHigh2/nodeWeight2;
	}

	private Double calcRevisedBytes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		List<WikiEditNetworkEdge> revisedList = wikiReviserEditNetworkNode.getRevisedList();
		Double value = 0d;
		for (int i = 0; i < revisedList.size(); i++) {
			Integer amountValue = revisedList.get(i).getActionValue();
			if(amountValue<0) amountValue=amountValue*(-1);
			value +=amountValue;
		}
		return value;
	}

	private Double calcReviserBytes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		return activityIndex;
	}

	private ArrayList<String> calcRevisedByNodes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		List<WikiEditNetworkEdge> revisedList = wikiReviserEditNetworkNode.getRevisedList();
		ArrayList<String> nodeNames = new ArrayList<>();
		for (int i = 0; i < revisedList.size(); i++) {
			String target = revisedList.get(i).getTarget();
			nodeNames.add(target);
		}
		return nodeNames;
	}

	private  ArrayList<String> calcReviserForNodes(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		List<WikiEditNetworkEdge> ReviserList = wikiReviserEditNetworkNode.getReviserList();
		ArrayList<String> nodeNames = new ArrayList<>();
		for (int i = 0; i < ReviserList.size(); i++) {
			String source = ReviserList.get(i).getTarget();
			nodeNames.add(source);
		}
		System.out.println(name +" - " + nodeNames);
		return nodeNames;
	}

	private Double clacNetAddedRation(Double netAdded2, Double activityIndex2) {
		return netAdded2/activityIndex2;
	}

	private Double calcNetAdded(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		return posActions+neuActions-negActions;
	}

	private Double calcActivityIndex(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		return posActions+negActions+neuActions;
	}

	private void calcOutGoingActions(WikiReviserEditNetworkNode wikiReviserEditNetworkNode) {
		Double valuePost = 0d;
		Double valueNeg  = 0d;
		Double valueNeut = 0d;
		List<WikiEditNetworkEdge> reviserList = wikiReviserEditNetworkNode.getReviserList();
		for (WikiEditNetworkEdge wikiEditNetworkEdge : reviserList) {
			Integer value = wikiEditNetworkEdge.getActionValue();
			if(value<0)  valueNeg+=(double) (value*(-1));
			if(value>0)  valuePost+= (double)value; 
			if(value==0) valueNeut+= 0.1d;
		}
		posActions=valuePost;
		negActions=valueNeg;
		neuActions=valueNeut;
		
	}

	public String getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

	public Double getPosActions() {
		return posActions;
	}

	public Double getNegActions() {
		return negActions;
	}

	public Double getNeuActions() {
		return neuActions;
	}

	public Double getActivityIndex() {
		return activityIndex;
	}

	public Double getNetAdded() {
		return netAdded;
	}

	public Double getNetAddedRatio() {
		return netAddedRatio;
	}

	public ArrayList<String> getRevisorFor() {
		return revisorFor;
	}

	public ArrayList<String> getRevisedBy() {
		return revisedBy;
	}

	public Double getNodeHigh() {
		return nodeHigh;
	}

	public Double getNodeWeight() {
		return nodeWeight;
	}

	public Double getNodeRatio() {
		return nodeRatio;
	}

	public Double getRelationOfRevisorAndRevised() {
		return relationOfRevisorAndRevised;
	}
}
