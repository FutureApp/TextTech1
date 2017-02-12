package texttechno.task3.ortograph.newImp;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Quality;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import xgeneral.modules.MyMathLib;

public class VizResults {

	private ArrayList<Nodes> filteredNodes;
	private File location;

	String RED = Color.red.toString();
	String BLACK = Color.BLACK.toString();
	String STEELBLUE = javafx.scene.paint.Color.STEELBLUE.toString();
	String BLANCHEDALMOND = javafx.scene.paint.Color.BLANCHEDALMOND.toString();
	String PEACHPUFF = javafx.scene.paint.Color.PEACHPUFF.toString();
	private ArrayList<Nodes> orginialNodes;
	private Integer highestCW_Value;
	private Integer highestLogLike;

	/**
	 * The visualizer-class
	 * 
	 * @param filteredNodess
	 *            This nodes will be shown. Subset of all nodes (original
	 *            nodes).
	 * @param location
	 *            Where to save the Graph.
	 * @param orginialNodes
	 *            All nodes.
	 */
	public VizResults(ArrayList<Nodes> filteredNodess, File location, ArrayList<Nodes> orginialNodes) {
		this.filteredNodes = new ArrayList<>();
		filteredNodes.addAll(filteredNodess);
		this.location = location;
		this.orginialNodes = orginialNodes;
	}

	/**
	 * Starts the viz.
	 */
	public void startViz() {
		String style = "ui.style";
		HashMap<String, Nodes> nodeTouched = new HashMap<>();
		HashMap<String, Double> nodeMap = new HashMap<>();
		for (Nodes nodes : filteredNodes) {
			nodeMap.put(nodes.nodeName, nodes.nodeCwValue);
		}
		/*
		 * Graph-options.
		 */
		Graph graph = new MultiGraph("Dicussions");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.setStrict(false);
		graph.setAutoCreate(true);

		// Calcs dynamic options.
		highestCW_Value = MyMathLib.round(filteredNodes.get(0).nodeCwValue, 0).intValue() / 2;
		highestLogLike = detHighestLogLik(filteredNodes);
		System.out.println("Visualisation-Modul: Highest CW " + highestCW_Value);
		System.out.println("Visualisation-Modul: Highest Log " + highestLogLike);

		/*
		 * Draws all nodes and endges.
		 */
		for (int i = 0; i < filteredNodes.size(); i++) {
			Nodes node = filteredNodes.get(i);

			String nodeName = node.nodeName;
			graph.addNode(nodeName).addAttribute("label", nodeName + String.format("[%.3f]", node.nodeCwValue));
			nodeTouched.put(nodeName, node);
			for (Entry<String, Double> entry : node.nodeMapEdgeWeight.entrySet()) {
				//Generating edges.
				String edgeID = UUID.randomUUID() + "";
				String nameOfNode2 = entry.getKey();
				Double logLikiValue = entry.getValue();
				Double cwValue = cwValueOfNode(nameOfNode2);

				// If node is new, then add node to graph.
				if (!nodeTouched.containsKey(nameOfNode2)) {
					String secondNodeName = nameOfNode2;
					Integer nodeSize = getLightSize("node", cwValue);
					String colorStyle = String.format("fill-color: '%s';", BLACK);
					String sizeStyle = String.format("size: %dpx", nodeSize);
					String secondNodeStyle = nodeSize + colorStyle + sizeStyle;
					graph.addNode(secondNodeName).addAttribute("label",
							String.format("%s[%.3f]", secondNodeName, cwValue));
					graph.getNode(secondNodeName).addAttribute(style, secondNodeStyle);
				}

				// Adds an edge.
				graph.addEdge(edgeID, nodeName, nameOfNode2, true);
				graph.getEdge(edgeID).setAttribute("label", String.format("%.3f", logLikiValue));

				// Adds styles to the edge.
				String edgeStyle = "";
				Integer numberEdgeSize = getLightSize("edge", logLikiValue);
				String edgeColorStyle = String.format("fill-color: '%s';", PEACHPUFF);
				Integer arrowSizeStyle = getLightArrowSize(numberEdgeSize);
				String edgeArrowSize = String.format("arrow-size: %dpx,%dpx;", arrowSizeStyle, arrowSizeStyle);
				String edgeSizeStylee = String.format("size: %dpx;", numberEdgeSize);
				edgeStyle = edgeColorStyle + edgeArrowSize + edgeSizeStylee;
				graph.getEdge(edgeID).setAttribute(style, edgeStyle);
			}
		}

		// All nodes in filter-range (CW-Value)
		for (int i = 0; i < filteredNodes.size(); i++) {
			Nodes node = filteredNodes.get(i);
			Integer nodeSize = getLightSize("node", node.getNodeCwValue());
			String nodeName = node.getNodeName();
			String sizeStyle = String.format("size: %dpx;", nodeSize);
			String colorStyle = String.format("fill-color: '%s';", STEELBLUE);
			String styleo = sizeStyle + colorStyle;
			graph.getNode(nodeName).addAttribute(style, styleo);

		}
		
		/*
		// Connect all cw-nodes if needed
		for (Entry<String, Nodes> primeTouchedNode : nodeTouched.entrySet()) {
			String primeTouchedNodeName = primeTouchedNode.getKey();
			Nodes primeNode = primeTouchedNode.getValue();
			
			for (Entry<String, Double> nodesFollowed : primeNode.getNodeMapEdgeWeight().entrySet()) {
				String nameOfFollowedNode = nodesFollowed.getKey();
				if(nodeTouched.containsKey(nameOfFollowedNode)){
					String edgeID = UUID.randomUUID()+"";

					graph.addEdge(edgeID, primeTouchedNodeName, nameOfFollowedNode, true);
					Double weightConnection = primeNode.getNodeMapEdgeWeight().get(nameOfFollowedNode);
					graph.getEdge(edgeID).setAttribute("label", String.format("%.3f", weightConnection));
					
					// Adds styles to the edge.
					String edgeStyle = "";
					Integer numberEdgeSize = getLightSize("edge", weightConnection);
					String edgeColorStyle = String.format("fill-color: '%s';", RED);
					Integer arrowSizeStyle = getLightArrowSize(numberEdgeSize);
					String edgeArrowSize = String.format("arrow-size: %dpx,%dpx;", arrowSizeStyle, arrowSizeStyle);
					String edgeSizeStylee = String.format("size: %dpx;", numberEdgeSize);
					edgeStyle = edgeColorStyle + edgeArrowSize + edgeSizeStylee;
					graph.getEdge(edgeID).setAttribute(style, edgeStyle);
				}
			}
		}*/

		// show graph or not.
		graph.display(true);

		
		// Saving graph.
		FileSinkImages pic = new FileSinkImages(OutputType.JPG, Resolutions.HD720);
		pic.setAutofit(true);
		pic.setQuality(Quality.HIGH);
		pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		try {
			pic.writeAll(graph, location.getAbsolutePath()+".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calc the size of the arrow
	 * 
	 * @param value
	 *            The weight of the object. (log likelihood, CW-Value,...)
	 * @return the value +3 or 1 if value<1.
	 */
	private Integer getLightArrowSize(Integer value) {
		Integer result = 0;
		result = value + 3;
		if (value < 1)
			result = 1;
		return result;
	}

	/**
	 * Dets. the highest Value log likelihood. Based on given nodes.
	 * 
	 * @param nodesForCalc
	 *            Nodes where to det the highest log likelihood-value.
	 * @return The highest log likelihood value .
	 */
	private Integer detHighestLogLik(ArrayList<Nodes> nodesForCalc) {
		Integer value = 0;
		for (Nodes nodes : nodesForCalc) {
			HashMap<String, Double> nodeMapEdgeWeight = nodes.getNodeMapEdgeWeight();

			for (Entry<String, Double> nodes2 : nodeMapEdgeWeight.entrySet()) {
				if (nodes2.getValue() > value)
					value = MyMathLib.round(nodes2.getValue(), 0).intValue();
			}
		}
		return value;
	}

	/**
	 * Calcs the size for a given object.
	 * 
	 * @param type
	 *            Type of the object (node,edge)
	 * @param value
	 *            The Weight of the given object (log likelihood, CW-Value, ...)
	 * @return The size of the object in [px].
	 */
	public Integer getLightSize(String type, Double value) {
		Integer result = 0;
		Double round = MyMathLib.round(value, 0);
		result = round.intValue();
		if (type.equals("edge")) {
			result /= 4;
			if (result < 1)
				result = 1;
		}
		if (type.equals("node")) {
			if (highestCW_Value < 10) {
				result *= 10;
			}
		}
		return result;
	}

	/**
	 * Dets. the CW-Value for the node.
	 * 
	 * @param theNameOfNode
	 *            Name of node.
	 * @return CW-Value of the given node.
	 */
	public Double cwValueOfNode(String theNameOfNode) {
		Double value = 0d;
		for (Nodes nodes : orginialNodes) {
			if (nodes.nodeName.equals(theNameOfNode)) {
				value = nodes.nodeCwValue;
				break;
			}
		}
		return value;
	}

}
