package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class NodeFilter {

	private ArrayList<Nodes> nodes;

	/**
	 * The node-filter class.
	 * @param nodes All nodes which should be filterd.
	 */
	public NodeFilter(ArrayList<Nodes> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Add nodes only to the result-list if they meets the requirements.
	 * @param cw_nodes Will add only the k-highest nodes to the result-list.
	 * @param edgeWeight Will add only the k-highest edges-weight(edges) to the result-list.
	 * @return List which contains the k-highest nodes with their k-highest edges.
	 */
	public ArrayList<Nodes> filter(int cw_nodes, int edgeWeight) {

		ArrayList<Nodes> sortNodes = sortNodes(nodes);
		ArrayList<Nodes> applyCWFilter = applyCWFilter(sortNodes, cw_nodes);
		ArrayList<Nodes> applyEdgeFilter = applyEdgeFilter(applyCWFilter, edgeWeight);

		applyEdgeFilter.forEach((a -> {
			System.out.println("Name: " + a.nodeName + " " + a.nodeCwValue +" "+a.nodeMapEdgeWeight);
		}));
		return applyEdgeFilter;
	}

	
	/**
	 * Filters the k-highest edges.
	 * @param sortNodes Nodes 
	 * @param numberOfEdges Value of k for the k-highest edges.
	 * @return Nodes only containing there k-highest edges.
	 */
	private ArrayList<Nodes> applyEdgeFilter(ArrayList<Nodes> sortNodes, int numberOfEdges) {
		ArrayList<Nodes> resultList = new ArrayList<>();
		for (int i = 0; i < sortNodes.size(); i++) {
			//Det the k-highest edges.
			Nodes node = sortNodes.get(i);
			HashMap<String, Double> nodeMapEdgeWeight = node.nodeMapEdgeWeight;
			Map<String, Double> sortByValue = sortByValue(nodeMapEdgeWeight);
			HashMap<String, Double> resultMap = new HashMap<>();
			Integer counter = 0;

			// while under the critical treshold -> add.
			for (Entry<String, Double> entry : sortByValue.entrySet()) {
				if (numberOfEdges == 0)
					resultMap.put(entry.getKey(), entry.getValue());
				else {
					if (counter < numberOfEdges) {
						resultMap.put(entry.getKey(), entry.getValue());
						counter++;
					}
				}
			}
			node.nodeMapEdgeWeight = resultMap;
			resultList.add(node);
		}

		return resultList;

	}

	
	/**
	 * Returns the k-highest cw-nodes.
	 * @param sortNodes Nodes where to look for the k-highest.
	 * @param filterValue Number of nodes which are legetim in the result-list.
	 * @return List containing the k-highst cw-nodes.
	 */
	private ArrayList<Nodes> applyCWFilter(ArrayList<Nodes> sortNodes, Integer filterValue) {
		ArrayList<Nodes> filteredList = new ArrayList<>();

		if (filterValue == 0)
			filteredList.addAll(sortNodes);
		else {
			for (int i = 0; i < sortNodes.size(); i++) {
				if (i < filterValue) {
					filteredList.add(sortNodes.get(i));
				} else {
					break;
				}
			}
		}

		return filteredList;

	}

	/**
	 * Sorts the nodes by their cw-value. Highst first.
	 * @param nodes2 nodes.
	 * @return Sorted list. Highest values first.
	 */
	private ArrayList<Nodes> sortNodes(ArrayList<Nodes> nodes2) {
		ArrayList<Nodes> sortedList = new ArrayList<>();
		sortedList.addAll(nodes2);
		Collections.sort(sortedList, new NodeComparatorHighestFirst());

		return sortedList;

	}

	/*
	 * http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-
	 * java
	 * 
	 */
	/**
	 * Sort Map by their values.
	 * @param map A Map
	 * @return A sorted map.
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

}
