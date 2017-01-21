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
	private ArrayList<StringTupel3> fileAsTuple;

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
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
		HashMap<String, Integer> xAxis = new HashMap<>();
		HashMap<String, Integer> yAxis = new HashMap<>();

		Integer scope = 3;
		Integer maxSize = fileAsTuple.size();
		for (int i = 0; i < maxSize; i++) {

			ArrayList<StringTupel3> leftSide = calcLeftSide(i, fileAsTuple);
			ArrayList<StringTupel3> rightSide = calcRightSide(i, fileAsTuple);
			StringTupel3 primeTuple = fileAsTuple.get(i);
			updateMatrix(matrix, xAxis, primeTuple, leftSide, rightSide);

		}

		return matrix;
	}

	private void updateMatrix(ArrayList<ArrayList<Integer>> matrix, HashMap<String, Integer> xAxis,
			StringTupel3 primeTuple, ArrayList<StringTupel3> leftSide, ArrayList<StringTupel3> rightSide) {

		ArrayList<StringTupel3> mergedNeighbors = mergeNeighbors(leftSide, rightSide);
		
		// Update-Step.
		/*
		 * Checke was on prime angeschaut werden muss. 
		 * lösche alles aus merge was nicht gebraucht wird.
		 * füge alle lemma in hash ein wenn nötig. 
		 * besorge dir für jedes lemma die pose und date das prime element ab.		
		 */
		
		
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
	private ArrayList<StringTupel3> mergeNeighbors(ArrayList<StringTupel3> leftSide,
			ArrayList<StringTupel3> rightSide) {
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
	private ArrayList<StringTupel3> calcRightSide(int i, ArrayList<StringTupel3> fileAsTuple2) {
		ArrayList<StringTupel3> rightSide = new ArrayList<>();
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
	private ArrayList<StringTupel3> calcLeftSide(int i, ArrayList<StringTupel3> fileAsTuple2) {
		ArrayList<StringTupel3> leftSide = new ArrayList<>();
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
	public ArrayList<StringTupel3> transformToTupleOfThree() {
		fileAsTuple = lemmaFilter.extractTupeOfThree(teiFileAsList);
		return fileAsTuple;
	}
}
