package texttechno.task3.ortograph.newImp;

import java.util.HashMap;

public class Nodes {
	String nodeName;
	Double nodeCwValue;
	HashMap<String, Double> nodeMapEdgeWeight;

	
	/**
	 * A node from a network-cluster.
	 * @param nodeName Name of the node.
	 * @param nodeCwValue The cw-value of the node.
	 * @param lookUpAllWeights All connections of node. (Follower-nodes).
	 */
	public Nodes(String nodeName, Double nodeCwValue, HashMap<String, Double> lookUpAllWeights) {
		super();
		this.nodeName = nodeName;
		this.nodeCwValue = nodeCwValue;
		this.nodeMapEdgeWeight = lookUpAllWeights;
	}

	/**
	 * Returns the name of the node.
	 * @return Name of node
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * Sets the name of the node.
	 * @param nodeName Name of node.
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * Returns the cw-value of the node.
	 * @return cw-value.
	 */
	public Double getNodeCwValue() {
		return nodeCwValue;
	}

	/**
	 * Sets the cw-value of the node.
	 * @param nodeCwValue New cw-value.
	 */
	public void setNodeCwValue(Double nodeCwValue) {
		this.nodeCwValue = nodeCwValue;
	}

	/**
	 * Returns the connection-weights.
	 * @return Map of connection-weights.
	 */
	public HashMap<String, Double> getNodeMapEdgeWeight() {
		return nodeMapEdgeWeight;
	}

	/**
	 * Sets the map of connection-weighs.
	 * @param nodeMapEdgeWeight New Map of connection-weights.
	 */
	public void setNodeMapEdgeWeight(HashMap<String, Double> nodeMapEdgeWeight) {
		this.nodeMapEdgeWeight = nodeMapEdgeWeight;
	}
}
