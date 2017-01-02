package comphuman.task2.distanceReading;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

public class Extractor {
	// REGEX
	private String findRegexWikiGermanDateFormate = "[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Zä-ö]{3}.*[0-9][0-9][0-9][0-9] .*\\)";
	private String regExForUser = ".*Benutzer.*:.*";
	private String regExForSpezial = ".*Spezial:.*";

	// CSS-Classes

	// HTML-TAGS

	public Boolean containsSomethingLikeUserName(String content) {
		Boolean containsSomethingLikeUserName = false;
		if (content.contains("title") && (content.matches(regExForUser) || content.matches(regExForSpezial))) {
			containsSomethingLikeUserName = true;
		}
		return containsSomethingLikeUserName;
	}

	public Boolean containsSomethigLikeCreationDate(Element e) {
		Boolean containsSomethingLikeDate = containsSomethigLikeCreationDate(e.toString());
		return containsSomethingLikeDate;
	}

	public Boolean containsSomethigLikeCreationDate(String content) {
		Boolean containsSomethingLikeDate = false;
		Pattern word = Pattern.compile(findRegexWikiGermanDateFormate);
		Matcher match = word.matcher(content.toString());
		while (match.find()) {
			containsSomethingLikeDate = true;
			break;
		}
		return containsSomethingLikeDate;
	}

	public String findCreationDate(String currContent) {
		String wordToReturn = new String();
		Pattern word = Pattern.compile(findRegexWikiGermanDateFormate);
		Matcher match = word.matcher(currContent.toString());
		if (match.find()) {
			wordToReturn = currContent.substring(match.start(), match.end());
		}
		return wordToReturn;
	}

	public String findUserName(String currContent) {

		String wordToReturn = extractName(currContent);
		return wordToReturn;
	}

	private String extractName(String currContent) {
		// TODO Spezial tag abarbetiten
		String user = "";

		if (currContent.toLowerCase().contains("spezial")) {
			Pattern word = Pattern.compile("\"Spezial.*\"");
			Matcher match = word.matcher(currContent.toString());
			if (match.find()) {
				user = currContent.substring(match.start() + 1, match.end() - 1).split(":")[1].split("/")[1];
			}
		} else {

			Pattern word = Pattern.compile("title");
			Matcher match = word.matcher(currContent.toString());
			if (match.find()) {
				Integer matchStart = match.start();
				String con = new String();
				int secondQuate = 0;
				for (int i = matchStart; i < currContent.length(); i++) {
					if (secondQuate >= 2) {
						user = con;
						break;
					} else {
						Character charAt = currContent.charAt(i);
						if (charAt.equals('\"'))
							secondQuate++;
						else {
							con = con + charAt;
						}
					}
				}
				user = user.split(":")[1];
			}

		}
		return user;
	}
}
