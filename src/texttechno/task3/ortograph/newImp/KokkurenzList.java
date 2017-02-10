package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class KokkurenzList {

	ArrayList<StringTuple3> tuples;
	HashMap<String, Word> uWords;
	private int sampleSize;
	public HashMap<String, Integer> physicalAppearancenew = new HashMap<>();

	public KokkurenzList(ArrayList<StringTuple3> tuples) {
		super();
		this.tuples = tuples;
		this.sampleSize = tuples.size();
		this.uWords = generateUWords();
	}

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

	public HashMap<String, IntegerSignature> calcRateSignatureForAllWords() {
		HashMap<String, IntegerSignature> mapo = new HashMap<>();
		for (Entry<String, Word> entry : uWords.entrySet()) {
			Word wordOne = entry.getValue();
			String wordName = wordOne.getName();
			Set<String> keySet = wordOne.wordsInRange.keySet();
			for (String keyFromSet : keySet) {
				if (mapo.containsKey(wordName + "@" + keyFromSet) || mapo.containsKey(keyFromSet + "@" + wordName)) {
				} else {
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

	public HashMap<String, IntegerSignature> calcContingencyTable(Set<String> composedKeys) {

		HashMap<String, IntegerSignature> mapo = new HashMap<>();
		int totalConnections = 0;
		int debugOcc = 0;
		for (Entry<String, Word> entry : uWords.entrySet()) {
			totalConnections += entry.getValue().getTotalConnections();
		}
		for (String comosedKey : composedKeys) {
			System.out.print(comosedKey + " ");
			String nameOfWord1 = comosedKey.split("@")[0];
			String nameOfWord2 = comosedKey.split("@")[1];

			int Yw1Yw2 = uWords.get(nameOfWord1).getConnectionsBetween(nameOfWord2);
			int Yw1Nw2 = uWords.get(nameOfWord1).totalConnections - Yw1Yw2;
			int Nw1Yw2 = uWords.get(nameOfWord2).totalConnections - Yw1Yw2;
			int Nw1Nw2 = totalConnections - uWords.get(nameOfWord1).getTotalConnections()
					- uWords.get(nameOfWord2).getTotalConnections();
			mapo.put(comosedKey, new IntegerSignature(Yw1Yw2, Yw1Nw2, Nw1Yw2, Nw1Nw2));
			debugOcc += Yw1Yw2;
			String result = String.format("(%s,%s,%d,%d,%d,%d)", nameOfWord1, nameOfWord2, Yw1Yw2, Yw1Nw2, Nw1Yw2,
					Nw1Nw2);
			System.out.println(result);
		}

		System.out.println("TOTAL " + totalConnections);
		System.out.println("Debug TOTAL " + debugOcc);
		return mapo;
	}

	public String showEntry() {
		String result = "";
		for (Entry<String, Word> entry : uWords.entrySet()) {
			String line = "First Hash - " + entry.getKey() + " -- " + "Second " + entry.getValue().name + " "
					+ entry.getValue().showValueInLine();
			result += line + System.lineSeparator();
			System.out.print(line);
			System.out.println();
		}
		return result;
	}

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

	public String showTuples() {
		String result = "";
		for (StringTuple3 stringTuple3 : tuples) {
			String formate = String.format("(%s,%s,%s)", stringTuple3.getLemma(), stringTuple3.getLemmaType(),
					stringTuple3.getWord());
			result += formate + System.lineSeparator();
		}
		return result;
	}

	public HashMap<String, FloatSignature> calcExpectedValue(HashMap<String, IntegerSignature> calcContingencyTable) {
		HashMap<String, FloatSignature> resultMap = new HashMap<>();
		for (Entry<String, IntegerSignature> entry : calcContingencyTable.entrySet()) {
			IntegerSignature values = entry.getValue();
			Float field00 = (float) values.kookkuRate;
			Float field01 = (float) values.randsumf1;
			Float field10 = (float) values.randsumf2;
			Float field11 = (float) values.sampleSize;

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
			Float E00 = R1 * C1 / summN;
			Float E01 = R1 * C2 / summN;
			Float E10 = R2 * C1 / summN;
			Float E11 = R2 * C2 / summN;

			String current = String.format("(%s.%.2f.%.2f.%.2f.%.2f)", entry.getKey(), field00, field01, field10,
					field11);
			String midStep = String.format("(%s.%.2f.%.2f.%.2f.%.2f)", entry.getKey(), R1, R2, C1, C2);
			String expected = String.format("(%s.%.2f.%.2f.%.2f.%.2f)", entry.getKey(), E00, E01, E10, E11);
			System.out.println(current);
			System.out.println(midStep);
			System.out.println(expected);
			resultMap.put(entry.getKey(), new FloatSignature(E00, E01, E10, E11));
		}
		return resultMap;

	}

	public HashMap<String, Double> calcLogLikelihoodValues(HashMap<String, IntegerSignature> calcContingencyTable,
			HashMap<String, FloatSignature> calcExpectedValue) {
		HashMap<String, Double> result = new HashMap<>();
		for (Entry<String, IntegerSignature> entry : calcContingencyTable.entrySet()) {
			String key = entry.getKey();
			IntegerSignature contingencySignature = entry.getValue();
			FloatSignature expectedSignature = calcExpectedValue.get(key);

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

	public Double calcAvgWeight(HashMap<String, Double> calcLogLikelihoodValues) {
		Double result = 0d;
		Double counter = 0d;
		for (Entry<String, Double> entry : calcLogLikelihoodValues.entrySet()) {
			result += entry.getValue();
			counter++;
		}
		return result / counter;
	}

	public void calcClusterWeight(HashMap<String, Double> calcLogLikelihoodValues) {
		Double avgWeight = calcAvgWeight(calcLogLikelihoodValues);
		ArrayList<String> nodes = calcNodes(calcLogLikelihoodValues);
	}

	private ArrayList<String> calcNodes(HashMap<String, Double> calcLogLikelihoodValues) {
		ArrayList<String> result = new ArrayList<>();
		HashMap<String, Integer> map = new HashMap<>();
		for (Entry<String, Double> entry : calcLogLikelihoodValues.entrySet()) {
			String[] split = entry.getKey().split("@");
			if (!map.containsKey(split[0]))
				map.put(split[0], 1);
			if (!map.containsKey(split[1]))
				map.put(split[1], 1);
		}
		for (Entry<String, Integer> mapEntry : map.entrySet()) {
			result.add(mapEntry.getKey());
		}
		System.out.println("ResultEntrys " + result.size());
		return result;
	}
}
