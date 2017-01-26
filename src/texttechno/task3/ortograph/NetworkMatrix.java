package texttechno.task3.ortograph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.util.MathUtils;
import org.apache.commons.math3.util.Precision;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.UtilsStrings_SingleTone;

public class NetworkMatrix {
	ArrayList<StringTuple3> tuples;
	ArrayList<StringTuple3> cleandTuples;

	private LemmaAbstractor lemmaFilter = new LemmaAbstractor();

	private HashMap<String, Integer> xAxis;
	public ArrayList<ArrayList<Integer>> orginialMatrix;
	public ArrayList<ArrayList<Integer>> cleanMatrix;
	private ArrayList<String> indexHolder;
	private ArrayList<ArrayList<Double>> expectedMatrix;

	public NetworkMatrix(ArrayList<StringTuple3> tuples) {
		super();
		this.tuples = tuples;
		this.xAxis = generateMatrixHeader();
		this.indexHolder = generateIndexHolder(this.xAxis);
		this.orginialMatrix = generateMatrix(this.tuples);
		this.cleandTuples = cleanTuples(this.tuples);
		this.cleanMatrix = generateMatrix(this.cleandTuples);
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

	public Double unweighetClusterValue(ArrayList<ArrayList<Integer>> matrix) {
		Double value = 0d;
		Double secondTerm = 0d;
		for (int i = 0; i < matrix.size(); i++) {
			Integer vertexDegree = calcVertexDegree(matrix, i);
			Integer adjaDegree = calcVertexAdja(matrix, i);
			System.out.println("VertexDegree of " + indexHolder.get(i) + ": " + vertexDegree);
			System.out.println("ADJADegree of " + indexHolder.get(i) + ": " + adjaDegree);
			if(vertexDegree == 0 && adjaDegree == 0) secondTerm += 0;
			else{
				secondTerm += adjaDegree / CombinatoricsUtils.binomialCoefficientDouble(vertexDegree, 2);
			}
		}
		value = secondTerm / matrix.size();
		System.out.println("Cluser Value Calc is finished!");
		return value;

	}

	private Integer calcVertexAdja(ArrayList<ArrayList<Integer>> matrix, int i) {
		return countColumnSum(matrix, i);
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

	/**
	 * Writes the StringTupleOf3(the cleaned data) to the given file.
	 * 
	 * @param file
	 *            Destination where to save the content which is represented as
	 *            file.
	 */
	public void writeMatrixRawDataCleanedVersion(File file) {
		Formatter formatter = new Formatter();
		String content = "";
		if (file.exists())
			file.delete();
		for (int i = 0; i < cleandTuples.size(); i++) {
			content = String.format("(%s,%s,%s)", cleandTuples.get(i).item01, cleandTuples.get(i).item02,
					cleandTuples.get(i).item03);
			try {
				FileUtils.write(file, content + System.lineSeparator(), Encoding.getDefaultEncoding(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		formatter.close();
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

	private Integer calcVertexDegree(ArrayList<ArrayList<Integer>> matrix, int i) {
		Integer rowCount = countRowSum(matrix, i);
		Integer columnCount = countColumnSum(matrix, i);

		return rowCount + columnCount;
	}

	private Integer countRowSum(ArrayList<ArrayList<Integer>> matrix, int i) {
		Integer result = 0;
		for (int j = 0; j < matrix.size(); j++) {
			for (int j2 = i; j2 < i + 1; j2++) {
				result += matrix.get(j).get(j2);
			}
		}
		return result;
	}

	private Integer countColumnSum(ArrayList<ArrayList<Integer>> matrix, int i) {
		Integer result = 0;
		for (int j = i; j < i + 1; j++) {
			for (int j2 = 0; j2 < matrix.size(); j2++) {
				result += matrix.get(j).get(j2);
			}
		}
		return result;
	}

	private ArrayList<StringTuple3> cleanTuples(ArrayList<StringTuple3> tuples2) {
		ArrayList<StringTuple3> cleanTuples = new ArrayList<>();
		for (int i = 0; i < tuples2.size(); i++) {
			if (!tuples2.get(i).item01.equalsIgnoreCase("--"))
				cleanTuples.add(tuples2.get(i));
		}
		return cleanTuples;
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

	private ArrayList<ArrayList<Double>> populateMatrixSizeFeldDouble(Integer sizeOfMatrix) {
		ArrayList<ArrayList<Double>> matrix = new ArrayList<>();

		for (int i = 0; i < sizeOfMatrix; i++) {
			ArrayList<Double> row = new ArrayList<>();
			for (int j = 0; j < sizeOfMatrix; j++) {
				row.add(0d);
			}
			matrix.add(row);
		}
		return matrix;
	}

	public Double calcExpectedValueForCell(ArrayList<ArrayList<Integer>> matrix, Integer x, Integer y) {
		Double value = 0d;
		Integer rowCount = countRowSum(matrix, y);
		Integer columnCount = countColumnSum(matrix, x);
		Integer totalRowCount = countTotalRawCount(matrix);
		Double calcRelativValue = (double) rowCount * (double) columnCount / (double) totalRowCount;
		value = Precision.round(calcRelativValue, 4);
		return value;

	}

	private Integer countTotalRawCount(ArrayList<ArrayList<Integer>> matrix) {
		Integer value = 0;
		for (int i = 0; i < matrix.size(); i++) {
			value += countRowSum(matrix, i);
		}
		return value;
	}

	public ArrayList<ArrayList<Double>> generateExpectedMatrix(ArrayList<ArrayList<Integer>> matrix) {
		System.out.println("Starting calc of expected Matrix.");
		ArrayList<ArrayList<Double>> preMatrix = populateMatrixSizeFeldDouble(matrix.size());
		for (int i = 0; i < preMatrix.size(); i++) {
			for (int j = 0; j < preMatrix.size(); j++) {
				System.out.printf("%d-%d / &d-%d", i,j,preMatrix.size(),preMatrix.size());
				System.out.println();
				//TODO Speed Up this section!
				preMatrix.get(i).set(j, calcExpectedValueForCell(matrix, i, j));
			}
		}
		expectedMatrix = preMatrix;
		System.out.println("Finished calc of expected matrix.");
		return preMatrix;
	}

	public void printNandNMatrix(ArrayList<ArrayList<Double>> expecMatrix) {
		System.out.println("-- Start Printing --");
		for (int i = 0; i < expecMatrix.size(); i++) {
			for (int j = 0; j < expecMatrix.size(); j++) {
				System.out.print(expecMatrix.get(i).get(j) + "   ");
			}
			System.out.println();
		}

	}

	public ArrayList<ArrayList<Double>> generateLogLiklyHoodMatrix(ArrayList<ArrayList<Integer>> cleanMatrix2,
			ArrayList<ArrayList<Double>> expecMatrix) {

		ArrayList<ArrayList<Double>> logLiMatrix = populateMatrixSizeFeldDouble(expecMatrix.size());
		Double logLikelihood = 0d;
		for (int i = 0; i < expecMatrix.size(); i++) {
			for (int j = 0; j < expecMatrix.size(); j++) {
				Double orginialRate = (double) cleanMatrix2.get(i).get(j);
				Double expectedRate = expecMatrix.get(i).get(j);

				if (orginialRate == 0d) {
					logLiMatrix.get(i).set(j, 0d);
					logLikelihood += 0d;
				} else {
					Double resultEntry = 0d;
					Double resultLog = Math.log(orginialRate / expectedRate) / Math.log(2);
					resultLog = Precision.round(resultLog, 4);
					resultEntry= 2*(orginialRate * resultLog);
					resultEntry = Precision.round(resultEntry, 4);
					
					logLiMatrix.get(i).set(j, resultEntry);
					logLikelihood += resultEntry;
				}
			}
		}
		return logLiMatrix;
	}
}
