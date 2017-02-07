package texttechno.task3.ortograph.old;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NetworkUtilsOLD {
	
	/**
	 * Adds all needed members to an array and returns it. In this case, all
	 * members on left side.
	 * 
	 * @param i
	 *            actual index of the analysis.
	 * @param fileAsTuple2
	 *            The list where the members could be find.
	 * @return All member left from the actual position.
	 */
	public static ArrayList<StringTuple3OLD> calcLeftSide(int i, ArrayList<StringTuple3OLD> tuples) {
		ArrayList<StringTuple3OLD> leftSide = new ArrayList<>();
		if (!(i - 3 < 0))
			leftSide.add(tuples.get(i - 3));
		if (!(i - 2 < 0))
			leftSide.add(tuples.get(i - 2));
		if (!(i - 1 < 0))
			leftSide.add(tuples.get(i - 1));
		return leftSide;
	}
	
	/**
	 * Add all needed members to an array and returns it. In this case, all
	 * members on right side.
	 * 
	 * @param i
	 *            actual index of the analysis.
	 * @param fileAsTuple2
	 *            The list where the members could be find.
	 * @return All member left from the actual position.
	 */
	public static ArrayList<StringTuple3OLD> calcRightSide(int i, ArrayList<StringTuple3OLD> tuples) {
		ArrayList<StringTuple3OLD> rightSide = new ArrayList<>();
		if ((i + 3 < tuples.size()))
			rightSide.add(tuples.get(i + 3));
		if ((i + 2 < tuples.size()))
			rightSide.add(tuples.get(i + 2));
		if ((i + 1 < tuples.size()))
			rightSide.add(tuples.get(i + 1));
		return rightSide;
	}
	
	/**
	 * Merges to array-lits and returns them as one.
	 * 
	 * @param leftSide
	 *            List to merge(1).
	 * @param rightSide
	 *            List to merge(2).
	 * @return The merge of both lists.
	 */
	public static ArrayList<StringTuple3OLD> mergeNeighbors(ArrayList<StringTuple3OLD> leftSide,
			ArrayList<StringTuple3OLD> rightSide) {
		leftSide.addAll(rightSide);
		return leftSide;
	}
	
	/**
	 * Code taken from:
	 * {@link http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-
	 * java}
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet().stream()
				.sorted(Map.Entry
						.comparingByValue(/* Collections.reverseOrder() */))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
}
