package texttechno.task3.ortograph.newImp;

import java.util.Comparator;

/**
 * A comparator. Highest values are on the lowest indicies. Index[0] -> highest value.
 * @author Michael Czaja
 *
 */
public class NodeComparatorHighestFirst implements Comparator<Nodes> {

	@Override
	public int compare(Nodes o1, Nodes o2) {
		Double node1 = o1.nodeCwValue;
		Double node2 = o2.nodeCwValue;
		return node2.compareTo(node1);
	}
}
