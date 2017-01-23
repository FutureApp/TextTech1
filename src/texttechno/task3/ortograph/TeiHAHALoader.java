package texttechno.task3.ortograph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class TeiHAHALoader {
	private static final Pattern W_TAG_REGEX = Pattern.compile("<w(.+?)</w>");
	private static final Pattern Text_REGEX = Pattern.compile(">(.+?)</w>");
	private static final Pattern LEMMA_PATTERN = Pattern.compile("lemma=\"(.+?)\" ");
	private static final Pattern LEMMA_TYPE_PATTERN = Pattern.compile("type=\"(.+?)\" ");

	public TeiHAHALoader() {
		super();
	}

	public ArrayList<StringTuple3> abstractTuplesOf3(String pathToTeiFile) {
		ArrayList<StringTuple3> fileAsListOfTuple = new ArrayList<>();
		File file = new File(pathToTeiFile);

		String readFileToString = null;
		try {
			readFileToString = FileUtils.readFileToString(file, Encoding.getDefaultEncoding());
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> tagValues = getWTagValues(readFileToString);

		for (int i = 0; i < tagValues.size(); i++) {
			System.out.println("IN: " + tagValues.get(i));
			String indiTag = tagValues.get(i);
			StringTuple3 tupleOf3 = abstractTupleOf3(indiTag);
			fileAsListOfTuple.add(tupleOf3);
		}
		return fileAsListOfTuple;
	}

	/**
	 * Abstracts the information from a line to build a tupleOf3 -object. See
	 * {@link StringTuple3}.
	 * 
	 * @param line
	 *            line containing the information to bould a StringTupleOf3.
	 * @return The information as StringTupleOf3.
	 */
	private StringTuple3 abstractTupleOf3(String line) {
		String lemma = abstractPattern(line, LEMMA_PATTERN, "lemma");
		String lemmaType = abstractPattern(line, LEMMA_TYPE_PATTERN, "lemma-type");
		String word = abstractPattern(line, Text_REGEX, "word");

		return new StringTuple3(lemma, lemmaType, word);
	}

	/**
	 * Abstracts information in a given tag based on a pattern-match. Will
	 * return only one value. If no match was found then the method will return
	 * a list with an empty string.
	 * 
	 * @param indiTag
	 *            line that contains required information embed in a tag.
	 * @param pattern
	 *            Pattern to indicate the tag.
	 * @param patternName
	 *            Name to fire a specific-error message. Debugging purpose.
	 * @return List of abstracted Information. Will contain only one Value.
	 */
	private String abstractPattern(String indiTag, Pattern pattern, String patternName) {
		List<String> tagValues = new ArrayList<String>();
		Matcher matcher = pattern.matcher(indiTag);
		while (matcher.find()) {
			tagValues.add(matcher.group(1));
		}
		if (!(tagValues.size() == 1)) {
			if (tagValues.size() < 1) {
				SystemMessage.wMessage(
						"Less then one " + patternName + " was found for <w>-Tag. Empty string will be returned.");
				tagValues.add("");
			}
			if (tagValues.size() > 1)
				SystemMessage.wMessage("More then one " + patternName
						+ " or less then one  for a <w>-Tag was found. First finding will be returned.");
		}
		System.out.println("OUT-" + patternName + ": " + Arrays.toString(tagValues.toArray()));
		return tagValues.get(0);
	}

	private static List<String> getWTagValues(String str) {
		List<String> tagValues = new ArrayList<String>();
		Matcher matcher = W_TAG_REGEX.matcher(str);
		while (matcher.find()) {
			tagValues.add(matcher.group(0));
		}
		return tagValues;
	}
}
