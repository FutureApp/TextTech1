package texttechno.task3.ortograph.newImp;

import java.util.HashMap;

public class Nodes {
	String nodeName;
	Double nodeCwValue;
	HashMap<String, Double> nodeMapEdgeWeight;

	public Nodes(String nodeName, Double nodeCwValue, HashMap<String, Double> lookUpAllWeights) {
		super();
		this.nodeName = nodeName;
		this.nodeCwValue = nodeCwValue;
		this.nodeMapEdgeWeight = lookUpAllWeights;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Double getNodeCwValue() {
		return nodeCwValue;
	}

	public void setNodeCwValue(Double nodeCwValue) {
		this.nodeCwValue = nodeCwValue;
	}

	public HashMap<String, Double> getNodeMapEdgeWeight() {
		return nodeMapEdgeWeight;
	}

	public void setNodeMapEdgeWeight(HashMap<String, Double> nodeMapEdgeWeight) {
		this.nodeMapEdgeWeight = nodeMapEdgeWeight;
	}
}
