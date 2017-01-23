package texttechno.task3.ortograph;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.SysexMessage;

import xgeneral.modules.SystemMessage;

public class LemmaAbstractor {

	private String indiNomen = "N";
	private String indiVerb = "V";
	private String indiAdje = "ADJ";

	private String[] indisOfSpecialLemma = { indiAdje, indiVerb, indiNomen };
	private String indiLineHasLemma = "lemma=";

	public LemmaAbstractor() {
		super();
	}

	public Boolean containsLemmaLine(String line) {
		Boolean hasContent = false;
		if (line.contains(indiLineHasLemma))
			hasContent = true;
		return hasContent;
	}

	public Boolean startsWithSpecialLemma(String line) {
		Boolean hasContent = false;
		loop: for (int i = 0; i < indisOfSpecialLemma.length; i++) {
			if (line.startsWith(indisOfSpecialLemma[i])) {
				hasContent = true;
				break loop;
			}
		}
		return hasContent;
	}

	public ArrayList<StringTuple3> extractTupleOfThree(List<String> teiFileAsList) {
		ArrayList<StringTuple3> listOfTupel3 = new ArrayList<>();
		for (String line : teiFileAsList) {
			if (containsLemmaLine(line)) {
				StringTuple3 tupel;
				System.out.println(line);
				String lemma = extractLemma(line);
				String type = extractType(line);
				String word = extractWord(line);
				System.out.printf(" %s %s %s", lemma, type, word);
				System.out.println();
				tupel = new StringTuple3(lemma, type, word);
				listOfTupel3.add(tupel);
			}
		}
		return listOfTupel3;
	}

	private String extractWord(String line) {
		String result = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
		return result;
	}

	private String extractType(String line) {
		return extract(line, "type", " function=\"");
	}

	private String extractLemma(String line) {
		return extract(line, "lemma", "type=\"");
	}

	/**
	 * Extracts the value of the given attributes.
	 * 
	 * @param line
	 *            line which contains the needed att. and needed att.-value.
	 * @param attName
	 *            Name of the att.
	 * @param followedAttrName
	 *            Name of next att. to det. where the value-description is
	 *            finished.
	 * @return the value of the given att.
	 */
	private String extract(String line, String attName, String followedAttrName) {
		String result = "";
		String firstIndi = attName;
		String secondIndi = followedAttrName;
		int indexOf = line.indexOf(firstIndi);
		int lengthOfLine = line.length();
		if (lengthOfLine > firstIndi.length() + 2) {
			String substring = line.substring(indexOf + firstIndi.length() + 1);
			if (substring.startsWith("\"")) {
				String wordStart = substring.substring(1);
				int wordend = wordStart.indexOf(secondIndi);
				String nearEnd = wordStart.substring(0, wordend);
				int realEnd = nearEnd.lastIndexOf("\"");
				String finalWord = nearEnd.substring(0, realEnd);
				result = new String(finalWord);
			}
		} else {
			SystemMessage.wMessage(
					"Lemma-tag was found but the length of line containing the lemma is too low. Line will be ignored.");
		}
		return result;
	}

	/**
	 * Deletes every element(tuple) which not contains the special lemma. See
	 * {@link #indisOfSpecialLemma};
	 * 
	 * @param mergedNeighbors
	 *            List of StringTuple3 element where to apply the
	 *            filter-function.
	 * @return A cleaned list where every element is important for the analysis.
	 */
	public ArrayList<StringTuple3> filterUnneadedElements(ArrayList<StringTuple3> mergedNeighbors) {
		ArrayList<StringTuple3> newList = new ArrayList<>();
		for (int i = 0; i < mergedNeighbors.size(); i++) {
			String lemmaType = mergedNeighbors.get(i).item02;
//			System.out.println(lemmaType +" "+startsWithSpecialLemma(lemmaType));
			if(startsWithSpecialLemma(lemmaType)) newList.add(mergedNeighbors.get(i));
		}
		return newList;
	}

}
