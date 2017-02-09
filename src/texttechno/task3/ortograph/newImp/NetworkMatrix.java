package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import xgeneral.modules.UtilsStrings_SingleTone;

public class NetworkMatrix {
	ArrayList<StringTuple3> tuples;

	private LemmaAbstractor lemmaFilter = new LemmaAbstractor();
	private HashMap<String, Integer> xAxis;
	public ArrayList<ArrayList<Integer>> orginialMatrix;
	private ArrayList<String> indexHolder;

	public NetworkMatrix(ArrayList<StringTuple3> tuples) {
		super();
		this.tuples = tuples;
		this.xAxis = generateMatrixHeader();
		this.indexHolder = generateIndexHolder(this.xAxis);
		this.orginialMatrix = generateMatrix(this.tuples);
	}

	private HashMap<String, Integer> generateMatrixHeader() {
		HashMap<String, Integer> map = new HashMap<>();
		for (StringTuple3 stringTuple3 : tuples) {
			String lemma = stringTuple3.getLemma();
			if (!map.containsKey(lemma))
				map.put(lemma, map.size());
		}
		return map;
	}

	/**
	 * Creates a index-list. Creation is based on the xAxis-Hashmap.
	 * 
	 * @param xAxis2
	 *            The hashmap containing (keyword, indexInList) map.
	 * @return An Arraylist containing the keywords, sorted by their indexes.
	 */
	private ArrayList<String> generateIndexHolder(HashMap<String, Integer> xAxis2) {
		Map<String, Integer> sortByValue = NetworkUtils.sortByValue(xAxis2);
		ArrayList<String> indexSorted = new ArrayList<>();
		for (Entry<String, Integer> entry : sortByValue.entrySet()) {
			indexSorted.add(entry.getKey());
		}
		return indexSorted;
	}

	public ArrayList<ArrayList<Integer>> generateMatrix(ArrayList<StringTuple3> tuples) {
		xAxis = generateMatrixHeader();
		orginialMatrix = populateMatrixSize(xAxis);
		for (int i = 0; i < tuples.size(); i++) {
			ArrayList<StringTuple3> leftSide = NetworkUtils.calcLeftSide(i, tuples);
			ArrayList<StringTuple3> rightSide = NetworkUtils.calcRightSide(i, tuples);
			StringTuple3 primeTuple = tuples.get(i);
			updateMatrix(orginialMatrix, xAxis, primeTuple, NetworkUtils.mergeNeighbors(leftSide, rightSide));
		}
		return orginialMatrix;
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

	
	
	
	
	private void updateMatrix(ArrayList<ArrayList<Integer>> matrix, HashMap<String, Integer> xAxis,
			StringTuple3 primeTuple, ArrayList<StringTuple3> mergedNeighbors) {

		String lemma = primeTuple.getLemma();
		Integer matrixIndexPrime = xAxis.get(lemma);
		System.out.println("prim - " + matrixIndexPrime + " primeValue " + lemma);
		System.out.println("SIZE list " + mergedNeighbors.size());
		for (int i = 0; i < mergedNeighbors.size(); i++) {
			StringTuple3 tuple = mergedNeighbors.get(i);
			String lemmaNeighbor = tuple.getLemma();
			Integer indexNeighbor = xAxis.get(lemmaNeighbor);
			Integer currentValueInMatrix = matrix.get(matrixIndexPrime).get(indexNeighbor);
			System.out.println("Current: " + currentValueInMatrix);
			Integer newValueInMatrix = currentValueInMatrix + 1;
			System.out.println("new: " + newValueInMatrix);
			matrix.get(matrixIndexPrime).set(indexNeighbor, newValueInMatrix);
		}
	}

	public void printMatrix(ArrayList<ArrayList<Integer>> cleanMatrix2) {
		UtilsStrings_SingleTone stringUtils = UtilsStrings_SingleTone.getInstance();

		Integer space = 12;
		String header = stringUtils.getXWhiteSpaces(space);
		ArrayList<String> indexHolder = generateIndexHolder(xAxis);

		for (String string : indexHolder) {
			header = header + stringUtils.fillLeftWithWhiteSpaces(string, space);
		}
		header = header + stringUtils.fillLeftWithWhiteSpaces("SUM", space);
		header = header + System.lineSeparator();
		String content = "";
		/*
		 */
		for (int i = 0; i < cleanMatrix2.size(); i++) {
			String leftSide = stringUtils.fillRightWithWhiteSpaces(indexHolder.get(i), space);
			content = content + leftSide;
			for (int j = 0; j < cleanMatrix2.size(); j++) {
				String number = stringUtils.fillLeftWithWhiteSpaces(cleanMatrix2.get(i).get(j) + "", space);
				content = content + number;
			}
			Integer rowSum = 0;
			content = content + "|";
			for (int x = 0; x < cleanMatrix2.size(); x++) {
				rowSum = rowSum + cleanMatrix2.get(i).get(x);
			}
			content = content + stringUtils.fillLeftWithWhiteSpaces(rowSum + "", space);
			content = content + System.lineSeparator();
		}
		for (int i = 0; i < header.length(); i++) {
			content = content + "_";
		}

		content = content + System.lineSeparator();
		content = content + stringUtils.fillRightWithWhiteSpaces("SUM", space);
		for (int y = 0; y < cleanMatrix2.size(); y++) {
			Integer col = 0;
			for (int x = 0; x < cleanMatrix2.size(); x++) {
				int fieldValue = cleanMatrix2.get(x).get(y);
				col += fieldValue;
			}
			content = content + stringUtils.fillLeftWithWhiteSpaces(col + "", space);
		}
		// CalcEntrys

		// Double sumOverallCols = sumOverAllColumns(matrix);
		// System.out.println("Rows " + sumOverallRows);
		// System.out.println("Cols " + sumOverallCols);

		// if(!(sumOverallCols - sumOverallRows == 0 )){
		// SystemMessage.eMessage("Sums doesn't matches");
		// content = content + stringUtils.fillLeftWithWhiteSpaces(" ?", space);
		// }
		// else{
		// content = content +
		// stringUtils.fillLeftWithWhiteSpaces(sumOverallCols+"", space);
		// content = content + System.lineSeparator();
		// }

		String matrixAsString = header + content;
		System.out.println(matrixAsString);

	}
}
