package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import scala.util.automata.WordBerrySethi;

public class KokkurenzList {

	ArrayList<StringTuple3> tuples;
	HashMap<String, Word> uWords;
	private int sampleSize;
	public HashMap<String, Integer> physicalAppearancenew = new HashMap<>();

	/**
	 * Contains all co-occurrence.
	 * 
	 * @param tuples
	 *            A list containing Tuples which are containing the
	 *            informations.{@link StringTuple3}
	 */
	public KokkurenzList(ArrayList<StringTuple3> tuples) {
		super();
		this.tuples = tuples;
		this.sampleSize = tuples.size();
		this.uWords = generateUWords();
	}

	/**
	 * Generates a list which is containing words. See class {@link Word}
	 */
	public void genereateWordList() {
		for (int i = 0; i < tuples.size(); i++) {
			ArrayList<StringTuple3> leftSide = NetworkUtils.calcLeftSide(i, tuples);
			ArrayList<StringTuple3> rightSide = NetworkUtils.calcRightSide(i, tuples);
			ArrayList<StringTuple3> mergeNeighbors = NetworkUtils.mergeNeighbors(leftSide, rightSide);
			StringTuple3 primeTuple = tuples.get(i);
			Word currentWord = uWords.get(primeTuple.getLemma());
			currentWord.updateOcc();
			for (StringTuple3 stringTuple3 : mergeNeighbors) {
				currentWord.update(stringTuple3.getLemma());
			}
		}
	}

	/**
	 * Calcs the rate-signature for each word and his collocate.
	 * 
	 * @return A map containing the rate-signature. Represented as
	 *         {@link IntegerSignature}.
	 */
	public HashMap<String, IntegerSignature> calcRateSignatureForAllWords() {
		HashMap<String, IntegerSignature> mapo = new HashMap<>();
		for (Entry<String, Word> entry : uWords.entrySet()) {
			Word wordOne = entry.getValue();
			String wordName = wordOne.getName();
			Set<String> keySet = wordOne.wordsInRange.keySet();
			// Constructs the Signature based on the keyset.
			for (String keyFromSet : keySet) {
				// connection is already investigated.
				if (mapo.containsKey(wordName + "@" + keyFromSet) || mapo.containsKey(keyFromSet + "@" + wordName)) {
				}
				// connection is not investigated.
				else {
					Word wordTwo = uWords.get(keyFromSet);
					Integer kookkurenz = wordOne.wordsInRange.get(keyFromSet);
					IntegerSignature sig = new IntegerSignature(kookkurenz, wordOne.getPhysicalOccurrence(),
							wordTwo.getPhysicalOccurrence(), sampleSize);
					mapo.put(wordName + "@" + keyFromSet, sig);
				}
			}
		}
		return mapo;
	}

	/**
	 * Generates the contingency table for each rateSignature.
	 * 
	 * @param composedKeys
	 *            Set of composed key. (Format: x@a}
	 * @return Returns a map containing the contingency table. Format of table:
	 *         {@linkplain IntegerSignature}
	 */
	public HashMap<String, IntegerSignature> calcContingencyTable(Set<String> composedKeys) {
		HashMap<String, IntegerSignature> mapo = new HashMap<>();
		int totalConnections = 0;
		for (Entry<String, Word> entry : uWords.entrySet()) {
			totalConnections += entry.getValue().getTotalConnections();
		}
		for (String comosedKey : composedKeys) {
			System.out.print(comosedKey + " ");
			String nameOfWord1 = comosedKey.split("@")[0];
			String nameOfWord2 = comosedKey.split("@")[1];

			// Fields of contingency-table
			int Yw1Yw2 = uWords.get(nameOfWord1).getConnectionsBetween(nameOfWord2);
			int Yw1Nw2 = uWords.get(nameOfWord1).totalConnections - Yw1Yw2;
			int Nw1Yw2 = uWords.get(nameOfWord2).totalConnections - Yw1Yw2;
			int Nw1Nw2 = totalConnections - uWords.get(nameOfWord1).getTotalConnections()
					- uWords.get(nameOfWord2).getTotalConnections();

			mapo.put(comosedKey, new IntegerSignature(Yw1Yw2, Yw1Nw2, Nw1Yw2, Nw1Nw2));

			// Shows the results in the console.
			String result = String.format("(%s,%s,%d,%d,%d,%d)", nameOfWord1, nameOfWord2, Yw1Yw2, Yw1Nw2, Nw1Yw2,
					Nw1Nw2);
			System.out.println(result);
		}

		// Show the number of total connections
		System.out.println("TOTAL Connections: " + totalConnections);
		return mapo;
	}

	/**
	 * Returns for each word his followers ( nodes in range).
	 * 
	 * @return String containing followers-information in String representation.
	 */
	public String showEntry() {
		String result = "";
		for (Entry<String, Word> entry : uWords.entrySet()) {
			String line = "First Hash - " + entry.getKey() + " -- " + "Second " + entry.getValue().name + " "
					+ entry.getValue().showValueInLine();
			result += line + System.lineSeparator();
		}
		return result;
	}

	/**
	 * Generates a word-hash. Each word is unique.
	 * 
	 * @return Returns a hash-map. Format: <nameOfUniqueword:Word itself>
	 */
	private HashMap<String, Word> generateUWords() {
		HashMap<String, Word> map = new HashMap<>();
		for (StringTuple3 stringTuple3 : tuples) {
			String lemma = stringTuple3.getLemma();
			if (!map.containsKey(lemma)) {
				Word word = new Word(lemma);
				map.put(lemma, word);
			}
		}
		return map;
	}

	/**
	 * Returns a String containing all information given by {@link StringTuple3}
	 * 
	 * @return String containing informations for all {@link StringTuple3}
	 */
	public String showTuples() {
		String result = "";
		for (StringTuple3 stringTuple3 : tuples) {
			String formate = String.format("(%s,%s,%s)", stringTuple3.getLemma(), stringTuple3.getLemmaType(),
					stringTuple3.getWord());
			result += formate + System.lineSeparator();
		}
		return result;
	}

	/**
	 * Calcs. the expected rate signature for each unique-word.
	 * 
	 * @param theContingencyTable
	 *            The table containing contingency's.
	 * @return Returns a map containing the expected-rate values. Format:
	 *         <nameOfUniqueWord:EpectedValueSignature> The format of the
	 *         EpectedValueSignature is given by {@link FloatSignature}.
	 */
	public HashMap<String, FloatSignature> calcExpectedValue(HashMap<String, IntegerSignature> theContingencyTable) {
		HashMap<String, FloatSignature> resultMap = new HashMap<>();
		for (Entry<String, IntegerSignature> entry : theContingencyTable.entrySet()) {
			IntegerSignature values = entry.getValue();

			// All field-values
			Float field00 = (float) values.kookkuRate;
			Float field01 = (float) values.randsumf1;
			Float field10 = (float) values.randsumf2;
			Float field11 = (float) values.sampleSize;

			// All values for the rand-sums
			Float R1 = field00 + field01;
			Float R2 = field10 + field11;
			Float C1 = field00 + field10;
			Float C2 = field01 + field11;

			Float summN = R1 + R2;
			Float SummX = C1 + C2;
			if (!(summN - SummX == 0f)) {
				System.out.println("Error");
				System.out.println(summN + "=" + SummX);
				System.exit(1);
			}

			// Expected values for each field.
			Float E00 = R1 * C1 / summN;
			Float E01 = R1 * C2 / summN;
			Float E10 = R2 * C1 / summN;
			Float E11 = R2 * C2 / summN;

			// Prints infos to the console
			String current = String.format("(%s.%.2f.%.2f.%.2f.%.2f)", entry.getKey(), field00, field01, field10,
					field11);
			String midStep = String.format("(%s.%.2f.%.2f.%.2f.%.2f)", entry.getKey(), R1, R2, C1, C2);
			String expected = String.format("(%s.%.2f.%.2f.%.2f.%.2f)", entry.getKey(), E00, E01, E10, E11);
			System.out.println(current);
			System.out.println(midStep);
			System.out.println(expected);
			// Generate the float-signature.
			resultMap.put(entry.getKey(), new FloatSignature(E00, E01, E10, E11));
		}
		return resultMap;

	}

	/**
	 * Calcs. the Log Likelihood Values for each connection between words.
	 * 
	 * @param ContingencyTable
	 *            The contingency table(map)
	 * @param ExpectedValue
	 *            The Expected values map.
	 * @return Map containing the LogLikelihoodValues for each word. Base
	 *         word(prime) is given throw unique word.
	 */
	public HashMap<String, Double> calcLogLikelihoodValues(HashMap<String, IntegerSignature> ContingencyTable,
			HashMap<String, FloatSignature> ExpectedValue) {
		HashMap<String, Double> result = new HashMap<>();
		for (Entry<String, IntegerSignature> entry : ContingencyTable.entrySet()) {
			String key = entry.getKey();
			IntegerSignature contingencySignature = entry.getValue();
			FloatSignature expectedSignature = ExpectedValue.get(key);

			Double logLikelihoodValue = 0d;
			for (int i = 0; i < contingencySignature.getValuesAsList().size(); i++) {
				Double contiValue = (double) contingencySignature.getValuesAsList().get(i);
				Double expectedValue = (double) expectedSignature.getValuesAsList().get(i);
				double tempInnerValue = Math.log(contiValue / expectedValue);
				double realInnerValue = tempInnerValue / Math.log(2d);
				logLikelihoodValue += (2 * contiValue * realInnerValue);
			}
			result.put(key, logLikelihoodValue);
		}
		return result;
	}

	/**
	 * Calcs the avg-value on a map.
	 * @param map Map containing the values.
	 * @return The avg-value of the map.
	 */
	public Double calcAvgWeight(HashMap<String, Double> map) {
		Double result = 0d;
		Double counter = 0d;
		for (Entry<String, Double> entry : map.entrySet()) {
			result += entry.getValue();
			counter++;
		}
		return result / counter;
	}
}
