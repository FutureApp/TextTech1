package prototype;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class ComparatorTreeMapDistinctOrder implements Comparator<Map.Entry<Integer, ArrayList<String>>>{

	@Override
	public int compare(Entry<Integer, ArrayList<String>> o1, Entry<Integer, ArrayList<String>> o2) {
		// TODO Auto-generated method stub
		return o2.getKey().compareTo(o1.getKey());
	}

}
