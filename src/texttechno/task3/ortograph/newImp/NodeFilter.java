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

	public NodeFilter(ArrayList<Nodes> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<Nodes> filter(int i, int j) {

		ArrayList<Nodes> sortNodes = sortNodes(nodes);
		ArrayList<Nodes> applyCWFilter = applyCWFilter(sortNodes, i);
		ArrayList<Nodes> applyEdgeFilter = applyEdgeFilter(applyCWFilter, j);

		applyEdgeFilter.forEach((a -> {
			System.out.println("Name: " + a.nodeName + " " + a.nodeCwValue +" "+a.nodeMapEdgeWeight);
		}));
		return applyEdgeFilter;
	}

	private ArrayList<Nodes> applyEdgeFilter(ArrayList<Nodes> sortNodes, int j) {
		ArrayList<Nodes> resultList = new ArrayList<>();
		for (int i = 0; i < sortNodes.size(); i++) {
			Nodes node = sortNodes.get(i);
			HashMap<String, Double> nodeMapEdgeWeight = node.nodeMapEdgeWeight;
			Map<String, Double> sortByValue = sortByValue(nodeMapEdgeWeight);
			HashMap<String, Double> resultMap = new HashMap<>();
			Integer counter = 0;

			for (Entry<String, Double> entry : sortByValue.entrySet()) {
				if (j == 0)
					resultMap.put(entry.getKey(), entry.getValue());
				else {
					if (counter < j) {
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
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

}
