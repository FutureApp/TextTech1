package texttechno.task3.ortograph.newImp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class VizResults {
	Integer numberOfCW_Results;
	Integer numberOfFollowers;
	File location;
	private ArrayList<Nodes> nodes;

	public VizResults(Integer numberOfCW_Results, Integer numberOfFollowers, File location, ArrayList<Nodes> nodes) {
		super();
		this.numberOfCW_Results = numberOfCW_Results;
		this.numberOfFollowers = numberOfFollowers;
		this.location = location;
		this.nodes = nodes;
	}

	public void startViz() {
		Map<String, Double> toSort = sortData(nodes);
		
		
		
		
		

	}

	private Map<String, Double> sortData(ArrayList<Nodes> nodes2) {
		HashMap<String, Double> map = new HashMap<>();
		for (int i = 0; i < nodes2.size(); i++) {
			Nodes elem = nodes2.get(i);
			map.put(elem.nodeName, elem.getNodeCwValue());
		}
		Map<String, Double> sortByValueMap = sortByValue(map);
		return sortByValueMap;
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
