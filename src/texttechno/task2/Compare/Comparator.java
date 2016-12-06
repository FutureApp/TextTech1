package texttechno.task2.Compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.plaf.synth.SynthSpinnerUI;

import java.util.TreeMap;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.UtilsStrings_SingleTone;

public class Comparator {
	private List<String> indexHolder = new ArrayList<>();
	private HashMap<String, Integer> matrixIndex = new HashMap<>();
	private Integer diffrentCategories = 0;
	private ArrayList<ArrayList<Integer>> matrix;

	File file01;
	File file02;
	Properties_SingleTone prop = Properties_SingleTone.getInstance();
	UtilsStrings_SingleTone stringUtils = UtilsStrings_SingleTone.getInstance();

	private String annotator01;
	private String annotator02;
	private Float matchInPro;
	private Float matchInCapa;
	private String encoding = Encoding.getDefaultEncoding();
	private Integer contentCounter;

	public Comparator(File file01, File file02) {
		super();
		this.file01 = file01;
		this.file02 = file02;
		this.matchInCapa = null;
		this.matchInPro = null;
		this.annotator01 = null;
		this.annotator02 = null;
	}

	private void calcMatchInPro() {
		if(matrix== null) generateMatrix();
		Integer diaSumm = 0;
		for (int i = 0; i < matrix.size(); i++) {
				diaSumm = diaSumm + matrix.get(i).get(i);
		}
		matchInPro=(float)diaSumm/(float)contentCounter;
	}

	private void generateMatrix() {
		ArrayList<ArrayList<String>> matrix = new ArrayList<>();
		List<String> contentF1 = abstractContent(1, file01);
		List<String> contentF2 = abstractContent(2, file02);

		
		checkIfContentCouldMatch(contentF1, contentF2);
		contentCounter=contentF1.size();
		createMatrixIndex();
		createMatrixSchema();
		populateMatrix(contentF1, contentF2);
		printMatrix();
	}

	public void printMatrix() {
		Integer space = 9;
		String header = stringUtils.getXWhiteSpaces(space);
		for (String string : indexHolder) {
			header = header + stringUtils.fillLeftWithWhiteSpaces("A1-"+string, space);
		}
		header=header+stringUtils.fillLeftWithWhiteSpaces("SUM", space);
		System.out.println(header);
		for (int i = 0; i < matrix.size(); i++) {
			String leftSide = stringUtils.fillRightWithWhiteSpaces("A2-"+indexHolder.get(i), space);
			System.out.print(leftSide);
			for (int j = 0; j < matrix.size(); j++) {
				String number = stringUtils.fillLeftWithWhiteSpaces(matrix.get(i).get(j)+"", space);
				System.out.print(number);
			}
			Integer rowSum = 0;
			for (int x = 0; x < matrix.size(); x++) {
				rowSum = rowSum + matrix.get(i).get(x);
			}
			System.out.print(stringUtils.fillLeftWithWhiteSpaces(rowSum+"", space));
			System.out.println();
		}
		System.out.println();
		System.out.print(stringUtils.fillRightWithWhiteSpaces("SUM", space));
		
		for (int i = 0; i < matrix.size(); i++) {
			Integer col = 0;
			for (int j = 0; j < matrix.size(); j++) {
				col = col+ matrix.get(i).get(j);
			}
			System.out.print(stringUtils.fillLeftWithWhiteSpaces(col+"", space));
		}
		System.out.print(stringUtils.fillLeftWithWhiteSpaces(contentCounter+"", space));
		System.out.println();
	
	}

	private void createMatrixSchema() {
		matrix = new ArrayList<>();
		for (int i = 0; i < diffrentCategories; i++) {
			ArrayList<Integer> Axis = new ArrayList<>();
			for (int j = 0; j < diffrentCategories; j++) {
				Axis.add(0);
			}
			matrix.add(Axis);
		}
	}

	private void populateMatrix(List<String> contentF1, List<String> contentF2) {
		for (int i = 0; i < contentF1.size(); i++) {
			String[] splittedCon1 = contentF1.get(i).split(" ");
			String[] splittedCon2 = contentF2.get(i).split(" ");
			String wordIndexCon1 = splittedCon1[0];
			String annotationCon1 = splittedCon1[1];
			String wordIndexCon2 = splittedCon2[0];
			String annotationCon2 = splittedCon2[1];
			if (checkIfIndexMatches(wordIndexCon1, wordIndexCon2)) {
				updateMatrix(annotationCon1, annotationCon2);
			}
		}
	}

	private void updateMatrix(String annotationCon1, String annotationCon2) {
		Integer posX = matrixIndex.get(annotationCon1);
		Integer posY = matrixIndex.get(annotationCon2);
		Integer valueInMatrix = matrix.get(posX).get(posY);
		Integer updatedValue= valueInMatrix+1;
		matrix.get(posX).set(posY, updatedValue);
	}

	private boolean checkIfIndexMatches(String wordIndexCon1, String wordIndexCon2) {
		Boolean isSame = false;
		if (wordIndexCon1.equals(wordIndexCon2))
			isSame = true;
		else {
			SystemMessage.eMessage("At least one word indcie doesn't match");
			SystemMessage.eMessage("File 01 <" + file01.getAbsolutePath() + "> returns <" + wordIndexCon1 + ">");
			SystemMessage.eMessage("File 02 <" + file02.getAbsolutePath() + "> returns <" + wordIndexCon2 + ">");
			SystemMessage.eMessage(
					"This means: Both files serve diffrent word-indicies at the same index-line at the file.");
			SystemMessage.eMessage("Porperly the files containing annotations for diffrent source-textes.");
			SystemMessage.eMessage("Processing futher is nonsense.");
			SystemMessage.eMessage("Program will terminate");
			System.exit(1);
		}
		return isSame;
	}

	private void checkIfContentCouldMatch(List<String> contentF1, List<String> contentF2) {
		if (contentF1.size() != contentF2.size()) {
			SystemMessage.eMessage("The ammount of annotations doesn't match.");
			SystemMessage
					.eMessage("File <" + file01.getAbsolutePath() + "> contains " + contentF1.size() + " annotations");
			SystemMessage
					.eMessage("File <" + file02.getAbsolutePath() + "> contains " + contentF2.size() + " annotations");
			SystemMessage.eMessage("To compare both is nonsense!");
			System.exit(1);
		}
	}

	private void createMatrixIndex() {
		Collections.sort(indexHolder);
		for (String string : indexHolder) {
			if (!matrixIndex.containsKey(string)) {
				matrixIndex.put(string, diffrentCategories);
				diffrentCategories++;
			}
		}
	}

	/**
	 * This method will pre-process the given files. This method also figures
	 * out the author and will set it, too. File need to follow the
	 * 'WebAnno-TSV-Format' format. Otherwise no warranty of successful
	 * processing.
	 * 
	 * @param authorIndex
	 *            All information belongs to author <1> OR <">
	 * @param file
	 *            File of annotations alias tagged-file. The file need to follow
	 *            the 'WebAnno-TSV-Format'.
	 *            {@link http://webanno.hucompute.org/login.html;jsessionid=D8EBDE2ED6B03E9A5F283B464C447A6C.jvm1?0}
	 * @return List which represents the content of a given file <tagged-file>.
	 */
	private List<String> abstractContent(int authorIndex, File file) {
		List<String> content = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				line = line.trim();
				if (line.startsWith("#"))
					line = "";
				else if (line.isEmpty())
					line = "$";
				else if (line.startsWith("_") || line.startsWith("-"))
					line = abstractAuthor(authorIndex, line);
				else if (Character.isDigit(line.charAt(0))) {
					line = abstractInformations(file.getAbsolutePath(), line);
					content.add(line);
				} else {
					System.out.println("Documents dont have the correct format.");
					System.exit(1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * Returns word-id and category of the word. The format is given in 3
	 * fields. This order of fields is required: <wordId word category>. If the
	 * schema doesn't match then the program will be terminate with an error
	 * message.
	 * 
	 * This method will also create the indices for each category's. This
	 * indices will be used to populate the matrix. TODO
	 * 
	 * @param filePath
	 *            Location of the file which the line is from. Matters only for
	 *            error handling.
	 * @param line
	 *            Line which contains the required Tag.
	 * @return Returns a string with this form: <wordId category>
	 */
	private String abstractInformations(String filePath, String line) {
		String resultLine = stringUtils.normalizeWhiteSpaces(line);
		String[] splitted = resultLine.split(" ");
		String wordIndex = splitted[0];
		String annotationCategory = splitted[2];

		if (splitted.length != 3) {
			SystemMessage.eMessage("Formate is corrupted. See line: <" + line + ">.See file <" + filePath + ">");
			System.exit(1);
		}
		// Informations of interest are only id and categories
		resultLine = wordIndex + " " + annotationCategory;
		addToIndexHolder(annotationCategory);
		return resultLine;
	}

	/**
	 * Looks up if the category exits. If the category doesn't exit then calc
	 * new index and insert the category and the index to the indexHolder.
	 * 
	 * @param annotationCategory
	 *            The category which should be added to index if not already
	 *            added.
	 */

	private void addToIndexHolder(String annotationCategory) {
		if (!indexHolder.contains(annotationCategory))
			indexHolder.add(annotationCategory);
	}

	/**
	 * Abstracts the author informations
	 * 
	 * @param authorIndex
	 *            Which file is given. Assignment to the file.
	 * @param line
	 *            Line which contains name of the author.
	 * @return An empty string. All informations are abstracted and the line has
	 *         no value anymore.
	 */
	private String abstractAuthor(int authorIndex, String line) {
		String whichFile = "";
		switch (authorIndex) {
		case 1:
			whichFile = file01.getAbsolutePath();
			if (line.length() > 1)
				annotator01 = line.substring(1);
			break;
		case 2:
			whichFile = file02.getAbsolutePath();
			if (line.length() > 1)
				annotator02 = line.substring(1);
			break;
		default:
			break;
		}
		// Prints a warning-message if the author-field starts with '-'
		if (line.startsWith("-")) {
			SystemMessage.wMessage(
					"The formate of a file is corrupted. " + "The line with the name of the author starts with '-'");
			SystemMessage.wMessage("See file: " + whichFile);
			SystemMessage.wMessage("See line: " + line);
		}
		// Returns an empty string. This line has no meaning for the matrix.
		return "";
	}

	private void calcMatchInCapa() {
		// TODO Auto-generated method stub

	}

	public Float getMatchInPro() {
		if (matchInPro == null)
			calcMatchInPro();
		
		return matchInPro;
	}

	public Float getMatchInCapa() {
		if (matchInCapa == null)
			calcMatchInCapa();
		return matchInCapa;
	}

}
