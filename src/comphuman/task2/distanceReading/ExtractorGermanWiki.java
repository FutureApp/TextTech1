package comphuman.task2.distanceReading;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

/**
 * This class contains the logic to extract informations from Elements or
 * Strings. Based on the section, this class could extract the user-name(user
 * which post the content) and the creation-date of the content.
 * 
 * Attention: Use this extractor only for the German Wikipedia. Otherwise it is
 * possible that the extractor couldn't extract the content because the
 * extractor uses regex's which correlate only to the German Wikipedia.
 * 
 * @author mcz
 *
 */
public class ExtractorGermanWiki {
	// REGEX
	private String findRegexWikiGermanDateFormate = "[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Zä-ö]{3}.*[0-9][0-9][0-9][0-9] .*\\)";
	private String regExForUser = ".*Benutzer.*:.*";
	private String regExForSpezial = ".*Spezial:.*";

	/**
	 * Searches if the content could have the information of the user-name.
	 * @param content Content which could contain the specific information(user-name)
	 * @return True - if content contains something familiar to a user-name.
	 */
	public Boolean containsSomethingLikeUserName(String content) {
		Boolean containsSomethingLikeUserName = false;
		if (content.contains("title") && (content.matches(regExForUser) || content.matches(regExForSpezial))) {
			containsSomethingLikeUserName = true;
		}
		return containsSomethingLikeUserName;
	}

	/**
	 * Searches if the given element contains something similar to the creation date.
	 * @param e An element which should contain the date-information of the post.
	 * @return True - if element contains something similar to a wiki-date.
	 */
	public Boolean containsSomethigLikeCreationDate(Element e) {
		Boolean containsSomethingLikeDate = containsSomethigLikeCreationDate(e.toString());
		return containsSomethingLikeDate;
	}

	/**
	 * Searches if the content contains something similar to the creation date.
	 * @param content An element which should contain the date-information of the post.
	 * @return True - if element contains something similar to a wiki-date.
	 */
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

	/**
	 * Searches in the given content for the wiki-post creation date.
	 * @param currContent Content which contains a wiki-post creation-date.
	 * @return Returns the wiki-post creation date. Empty if the date couldn't be found.
	 */
	public String findCreationDate(String currContent) {
		String wordToReturn = new String();
		Pattern word = Pattern.compile(findRegexWikiGermanDateFormate);
		Matcher match = word.matcher(currContent.toString());
		if (match.find()) {
			wordToReturn = currContent.substring(match.start(), match.end());
		}
		wordToReturn = ContentCleaner.clean(wordToReturn);
		return wordToReturn;
	}

	/**
	 * Searches for the user-name  in the given content.
	 * @param currContent Content which contains the user-name
	 * @return Returns an empty String if the user-name couldn't be extracted.
	 */
	public String findUserName(String currContent) {
		String wordToReturn = extractName(currContent);
		return wordToReturn;
	}

	/**
	 * Extracts the user-name of a given content.
	 * 
	 * @param currContent Content which contains the user-name
	 * @return Returns an empty String if the user-name couldn't be found -> extracted.
	 */
	private String extractName(String currContent) {
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
		
		user = ContentCleaner.clean(user);
		return user;
	}

	
}
