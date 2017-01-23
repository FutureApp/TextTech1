package texttechno.task3.ortograph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.UtilsStrings_SingleTone;

/**
 * @author Michael Czaja
 *
 */
public class RawData {

	File teiFile;
	List<String> teiFileAsList;
	private LemmaAbstractor lemmaFilter;
	private ArrayList<StringTuple3> fileAsTuple;
	private HashMap<String, Integer> xAxis;
	private ArrayList<ArrayList<Integer>> matrix;
	TeiHAHALoader loader = new TeiHAHALoader();
	public RawData() {
	}

	public RawData(File file) {
		this.teiFile = file;
		this.teiFileAsList = loadFile();
		this.lemmaFilter = new LemmaAbstractor();
	}

	public List<String> loadFile() {
		List<String> readLines = new ArrayList<>();
		try {
			readLines = FileUtils.readLines(teiFile, Encoding.getDefaultEncoding());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (readLines.isEmpty())
			SystemMessage
					.wMessage("Input is empty. " + "Maybe system couldn't read the file. Given File <" + teiFile + ">");

		return readLines;
	}

	public ArrayList<ArrayList<Integer>> generateMatrix() {
		xAxis = generateMatrixHeader();
		matrix = populateMatrixSize(xAxis);

		for (int i = 0; i < fileAsTuple.size(); i++) {
			ArrayList<StringTuple3> leftSide = calcLeftSide(i, fileAsTuple);
			ArrayList<StringTuple3> rightSide = calcRightSide(i, fileAsTuple);
			StringTuple3 primeTuple = fileAsTuple.get(i);
			updateMatrix(matrix, xAxis, primeTuple, mergeNeighbors(leftSide, rightSide));
		}

		//DEBGUG
//		System.out.println("hallo " + matrix);
		return matrix;
	}

	private HashMap<String, Integer> generateMatrixHeader() {
		HashMap<String, Integer> map = new HashMap<>();
		for (StringTuple3 stringTuple3 : fileAsTuple) {
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

	/**
	 * Merges to array-lits and returns them as one.
	 * 
	 * @param leftSide
	 *            List to merge(1).
	 * @param rightSide
	 *            List to merge(2).
	 * @return The merge of both lists.
	 */
	private ArrayList<StringTuple3> mergeNeighbors(ArrayList<StringTuple3> leftSide,
			ArrayList<StringTuple3> rightSide) {
		leftSide.addAll(rightSide);
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
	private ArrayList<StringTuple3> calcRightSide(int i, ArrayList<StringTuple3> fileAsTuple2) {
		ArrayList<StringTuple3> rightSide = new ArrayList<>();
		if ((i + 3 < fileAsTuple2.size()))
			rightSide.add(fileAsTuple.get(i + 3));
		if ((i + 2 < fileAsTuple2.size()))
			rightSide.add(fileAsTuple.get(i + 2));
		if ((i + 1 < fileAsTuple2.size()))
			rightSide.add(fileAsTuple.get(i + 1));
		return rightSide;
	}

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
	private ArrayList<StringTuple3> calcLeftSide(int i, ArrayList<StringTuple3> fileAsTuple2) {
		ArrayList<StringTuple3> leftSide = new ArrayList<>();
		if (!(i - 3 < 0))
			leftSide.add(fileAsTuple.get(i - 3));
		if (!(i - 2 < 0))
			leftSide.add(fileAsTuple.get(i - 2));
		if (!(i - 1 < 0))
			leftSide.add(fileAsTuple.get(i - 1));
		return leftSide;
	}

	/**
	 * Calcs the unweightet value for the cluster.
	 * 
	 * @return Value for the unweighted matrix.
	 */
	public Double calcUnweightClusterValue() {
		Double value = 0d;

		return value;
	}

	/**
	 * Calcs the collocation between the given items in the matrix. Calculation
	 * is based on log-likelihood.
	 * 
	 * @return Value of collocation based on the matrix.
	 */
	public Double calcKollokation() {
		Double value = 0d;
		return value;
	}

	/**
	 * Transform the list into a tupleOf3 format. This format will be
	 * used:(LEMMA,TYPE,WORD).
	 * 
	 * Example: Given -
	 * <w xml:id="xd1_wo1289" lemma="Wirt" type="NN" function="dic">Wirtes</w>
	 * Result - Wirt, NN, Wirtes)
	 * 
	 * @return List containing tupleOf3;
	 */
	public ArrayList<StringTuple3> transformToTupleOfThree() {
		
		fileAsTuple = loader.abstractTuplesOf3(teiFile);
		return fileAsTuple;
	}


	public void printMatrix() {
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
		for (int i = 0; i < matrix.size(); i++) {
			String leftSide = stringUtils.fillRightWithWhiteSpaces(indexHolder.get(i), space);
			content = content + leftSide;
			for (int j = 0; j < matrix.size(); j++) {
				String number = stringUtils.fillLeftWithWhiteSpaces(matrix.get(i).get(j) + "", space);
				content = content + number;
			}
			Integer rowSum = 0;
			content = content + "|";
			for (int x = 0; x < matrix.size(); x++) {
				rowSum = rowSum + matrix.get(i).get(x);
			}
			content = content + stringUtils.fillLeftWithWhiteSpaces(rowSum + "", space);
			content = content + System.lineSeparator();
		}
		for (int i = 0; i < header.length(); i++) {
			content = content + "_";
		}

		content = content + System.lineSeparator();
		content = content + stringUtils.fillRightWithWhiteSpaces("SUM", space);
		for (int y = 0; y < matrix.size(); y++) {
			Integer col = 0;
			for (int x = 0; x < matrix.size(); x++) {
				int fieldValue = matrix.get(x).get(y);
				col += fieldValue;
			}
			content = content + stringUtils.fillLeftWithWhiteSpaces(col + "", space);
//			System.out.println(content);
			content = new String();
		}
		// CalcEntrys
		
		Double sumOverallRows = sumOverAllRows(matrix); 
		Double sumOverallCols = sumOverAllColumns(matrix); 
//		System.out.println("Rows " + sumOverallRows);
//		System.out.println("Cols " + sumOverallCols);
		
		if(!(sumOverallCols - sumOverallRows == 0 )){
			SystemMessage.eMessage("Sums doesn't matches");
			content = content + stringUtils.fillLeftWithWhiteSpaces(" ?", space);
		}
		else{
			content = content + stringUtils.fillLeftWithWhiteSpaces(sumOverallCols+"", space);
			content = content + System.lineSeparator();
		}

		String matrixAsString = header + content;
//		System.out.println(matrixAsString);
		try {
			System.out.println("Writing");
			FileUtils.write(new File("textMatrix"), matrixAsString, Encoding.getDefaultEncoding(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return matrixAsString;
	}

	private Double sumOverAllRows(ArrayList<ArrayList<Integer>> matrix2) {
		Double value = 0d; 
		for (int i = 0; i < matrix2.size(); i++) {
			value += calcSumForRow(i);
		}
		return value;
	}
	
	private Double sumOverAllColumns(ArrayList<ArrayList<Integer>> matrix2) {
		Double value = 0d; 
		for (int i = 0; i < matrix2.size(); i++) {
			value += calcSumForColumn(i);
		}
		return value;
	}


	/**
	 * Calcs the sum of the given index. Fields need to be Integers.
	 * 
	 * @param index
	 *            Index of row.
	 * @return Sum over row as double.
	 */
	public Double calcSumForRow(Integer index) {
		Double value = 0d;
		for (int i = 0; i < matrix.size(); i++) {
			value += (double) matrix.get(index).get(i);
		}
		return value;
	}

	/**
	 * Calcs the sum of the given index. Fields need to be Integers.
	 * 
	 * @param index
	 *            Index of calcSumForColumn.
	 * @return Sum of the calcSumForColumn as double.
	 */
	public Double calcSumForColumn(Integer index) {
		Double value = 0d;
		for (int i = 0; i < matrix.size(); i++) {
			value += (double) matrix.get(i).get(index);
		}
		return value;
	}

	/**
	 * Creates a index-list. Creation is based on the xAxis-Hashmap.
	 * 
	 * @param xAxis2
	 *            The hashmap containt (keyword, indexInList) map.
	 * @return An Arraylist containt the keywords, sorted by their indexes.
	 */
	private ArrayList<String> generateIndexHolder(HashMap<String, Integer> xAxis2) {
		Map<String, Integer> sortByValue = sortByValue(xAxis2);
		ArrayList<String> indexSorted = new ArrayList<>();
		for (Entry<String, Integer> entry : sortByValue.entrySet()) {
			indexSorted.add(entry.getKey());
		}
		return indexSorted;
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
