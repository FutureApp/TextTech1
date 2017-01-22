package texttechno.task3.ortograph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import scala.Array;
import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

/**
 * @author Michael Czaja
 *
 */
public class RawData {

	File teiFile;
	List<String> teiFileAsList;
	private LemmaAbstractor lemmaFilter;
	private ArrayList<StringTuple3> fileAsTuple;

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
		HashMap<String, Integer> xAxis = generateMatrixHeader();
		ArrayList<ArrayList<Integer>> matrix = populateMatrixSize(xAxis);

		int size = matrix.size();
		System.out.println(size);
		System.out.println(matrix);
//
//		Integer scope = 3;
//		Integer maxSize = fileAsTuple.size();
//
//		for (int i = 0; i < maxSize; i++) {
//
//			ArrayList<StringTuple3> leftSide = calcLeftSide(i, fileAsTuple);
//			ArrayList<StringTuple3> rightSide = calcRightSide(i, fileAsTuple);
//			StringTuple3 primeTuple = fileAsTuple.get(i);
//			updateMatrix(matrix, xAxis, primeTuple, mergeNeighbors(leftSide, rightSide));
//		}
		return matrix;
	}

	private HashMap<String, Integer> generateMatrixHeader() {
		HashMap<String, Integer> map = new HashMap<>();
		for (StringTuple3 stringTuple3 : fileAsTuple) {
			String lemma = stringTuple3.getItem01();
			String lemmaType = stringTuple3.getItem02();
			if (lemmaFilter.containsSpecialLemma(lemmaType)) {
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
		String word = primeTuple.getItem03();
		if (lemmaFilter.containsSpecialLemma(lemmaType)) {
			System.out.println("Searhed <" + lemmaType + "> lemma <" + lemma + ">");

			/*
			 * If header doesn't contain key then add it.
			 */
			if (!xAxis.containsKey(lemma)) {
				Integer index = xAxis.size();
				xAxis.put(lemma, index);
			}
			lemmaFilter.filterUnneadedElements(mergedNeighbors);
			for (StringTuple3 stringTuple3 : mergedNeighbors) {
				if (!xAxis.containsKey(stringTuple3.getItem01())) {
					xAxis.put(stringTuple3.getItem01(), xAxis.size());
				}
			}
		}

		// Update-Step. TODO
		/*
		 * Checke was on prime angeschaut werden muss. lösche alles aus merge
		 * was nicht gebraucht wird. füge alle lemma in hash ein wenn nötig.
		 * besorge dir für jedes lemma die pose und date das prime element ab.
		 */

	}

	private void popNewEntryToMatrix(ArrayList<ArrayList<Integer>> matrix) {
		Integer size = matrix.size();

		for (int i = 0; i < matrix.size(); i++) {
			ArrayList<Integer> row = matrix.get(i);
			for (int j = row.size(); j < size; j++) {
				row.add(0);
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
		fileAsTuple = lemmaFilter.extractTupleOfThree(teiFileAsList);
		return fileAsTuple;
	}
}
