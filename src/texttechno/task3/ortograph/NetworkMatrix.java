package texttechno.task3.ortograph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;

public class NetworkMatrix {
	ArrayList<StringTuple3> tuples;

	private LemmaAbstractor lemmaFilter = new LemmaAbstractor();

	private HashMap<String, Integer> xAxis;
	private ArrayList<ArrayList<Integer>> matrix;
	private ArrayList<String> indexHolder;

	public NetworkMatrix(ArrayList<StringTuple3> tuples) {
		super();
		this.tuples = tuples;
		this.xAxis = generateMatrixHeader();
		this.indexHolder = generateIndexHolder(this.xAxis);
		this.matrix = generateMatrix();
	}

	
	


	public ArrayList<ArrayList<Integer>> generateMatrix() {
		xAxis = generateMatrixHeader();
		indexHolder = generateIndexHolder(xAxis);
		matrix = populateMatrixSize(xAxis);

		for (int i = 0; i < tuples.size(); i++) {
			ArrayList<StringTuple3> leftSide = NetworkUtils.calcLeftSide(i, tuples);
			ArrayList<StringTuple3> rightSide = NetworkUtils.calcRightSide(i, tuples);
			StringTuple3 primeTuple = tuples.get(i);
			updateMatrix(matrix, xAxis, primeTuple, NetworkUtils.mergeNeighbors(leftSide, rightSide));
		}
		return matrix;
	}

	

	/**
	 * Prints the StringTupleOf3(the rawData) to the screen.
	 * 
	 */
	public void printMatrixRawData() {
		for (int i = 0; i < tuples.size(); i++) {
			System.out.printf("(%s,%s,%s)", tuples.get(i).item01, tuples.get(i).item02, tuples.get(i).item03);
			System.out.println();
		}
	}

	/**
	 * Writes the StringTupleOf3(the rawData) to the given file.
	 * 
	 * @param file
	 *            Destination where to save the content which is represented as
	 *            file.
	 */
	public void writeMatrixRawData(File file) {
		Formatter formatter = new Formatter();
		String content = "";
		if (file.exists())
			file.delete();
		for (int i = 0; i < tuples.size(); i++) {
			content = String.format("(%s,%s,%s)", tuples.get(i).item01, tuples.get(i).item02, tuples.get(i).item03);
			try {
				FileUtils.write(file, content + System.lineSeparator(), Encoding.getDefaultEncoding(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		formatter.close();
	}
	private HashMap<String, Integer> generateMatrixHeader() {
		HashMap<String, Integer> map = new HashMap<>();
		for (StringTuple3 stringTuple3 : tuples) {
			String lemma = stringTuple3.getItem01();
			String lemmaType = stringTuple3.getItem02();
			if (lemmaFilter.startsWithSpecialLemma(lemmaType)) {
				if (!map.containsKey(lemma)) {
					map.put(lemma, map.size());
				}
			}
		}
		return map;
	}

	/**
	 * Creates a index-list. Creation is based on the xAxis-Hashmap.
	 * 
	 * @param xAxis2
	 *            The hashmap containt (keyword, indexInList) map.
	 * @return An Arraylist containt the keywords, sorted by their indexes.
	 */
	private ArrayList<String> generateIndexHolder(HashMap<String, Integer> xAxis2) {
		Map<String, Integer> sortByValue = NetworkUtils.sortByValue(xAxis2);
		ArrayList<String> indexSorted = new ArrayList<>();
		for (Entry<String, Integer> entry : sortByValue.entrySet()) {
			indexSorted.add(entry.getKey());
		}
		return indexSorted;
	}
	private void updateMatrix(ArrayList<ArrayList<Integer>> matrix, HashMap<String, Integer> xAxis,
			StringTuple3 primeTuple, ArrayList<StringTuple3> mergedNeighbors) {
		String lemma = primeTuple.getItem01();
		String lemmaType = primeTuple.getItem02();
		if (lemmaFilter.startsWithSpecialLemma(lemmaType)) {
			ArrayList<StringTuple3> filterUnneadedElements = lemmaFilter.filterUnneadedElements(mergedNeighbors);
			Integer matrixIndexPrime = xAxis.get(lemma);
			System.out.println("prim - " + matrixIndexPrime + " primeValue " + lemma);
			System.out.println("SIZE list " + filterUnneadedElements.size());
			for (int i = 0; i < filterUnneadedElements.size(); i++) {
				StringTuple3 tuple = filterUnneadedElements.get(i);
				String lemmaNeighbor = tuple.getItem01();
				Integer indexNeighbor = xAxis.get(lemmaNeighbor);
				Integer currentValueInMatrix = matrix.get(matrixIndexPrime).get(indexNeighbor);
				System.out.println("Current: " + currentValueInMatrix);
				Integer newValueInMatrix = currentValueInMatrix + 1;
				System.out.println("new: " + newValueInMatrix);
				;
				matrix.get(matrixIndexPrime).set(indexNeighbor, newValueInMatrix);
			}
		}
	}





	private ArrayList<ArrayList<Integer>> populateMatrixSize(HashMap<String, Integer> xAxis) {
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
	
		for (int i = 0; i < xAxis.size(); i++) {
			ArrayList<Integer> row = new ArrayList<>();
			for (int j = 0; j < xAxis.size(); j++) {
				row.add(0);
			}
			matrix.add(row);
		}
		return matrix;
	}
}
