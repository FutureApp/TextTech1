package comphuman.task2.distanceReading;

import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Extractor3 {
	// REGEX
	private String regexWikiGermanDateFormateAnyWhere = ".*[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3} [0-9][0-9][0-9][0-9].*";
	private String regexWikiGermanDateFormate = "[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3} [0-9][0-9][0-9][0-9].*";
	private String findRegexWikiGermanDateFormate = "[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3}.*[0-9][0-9][0-9][0-9] .*\\)";
	private String regExForUser = ".*Benutzer.*:.*";
	private String regExForTitle = "title=.*[\"]{1}";

	// CSS-Classes
	private String classNameForRedirect = "mw-redirect";

	// HTML-TAGS
	private String delimiterForNextLayer = "<dl>";

	/**
	 * Checks if a element contains information which look like a creation-date.
	 * 
	 * @param e
	 *            Element which should be checked.
	 * @return True - if element contains information represented like a
	 *         creatoin-date.
	 */
	public Boolean hasCreationDate(Element e) {
		return e.html().matches(regexWikiGermanDateFormateAnyWhere);
	}

	/**
	 * Abstracts the information of the user name.
	 * 
	 * @param Element
	 *            which contains the user name.
	 * @return The user name.
	 */
	public String getUserName(Element e) {
		String user = "unkown";
		if (e == null) {
			System.err.println("Nothing given");
		} else {
			String userNameInTitle = e.select("a").last().attr("title").toString();
			user = userNameInTitle.substring(userNameInTitle.lastIndexOf(":") + 1);

		}
		return user;
	}

	/**
	 * Abstracts the date of creation.
	 * 
	 * @param Element
	 *            this contains a date of creation.
	 * @return The date of creation.
	 */
	public String getCreationDate(Element e) {
		String result = "unkown";
		String eAsHTML = e.html();
		for (int j = 0; j < eAsHTML.length(); j++) {
			String toCheck = eAsHTML.substring(j);
			if (toCheck.matches(regexWikiGermanDateFormate)) {
				result = new String(toCheck);
				break;
			}
		}
		return result;
	}

	public Boolean initNextLayer(Element e) {
		Boolean initNextLayer = false;
		if (e.toString().trim().startsWith(delimiterForNextLayer)) {
			initNextLayer = true;
		}
		return initNextLayer;
	}

	/**
	 * Checks if the element contains something like a user-name.
	 * 
	 * @param e
	 *            Element to check.
	 * @return True - If contains.
	 */
	public Boolean containsSomethingLikeUserName(Element e) {
		Boolean containsSomethingLikeUserName = false;
		Elements selectedElements = e.select("a");
		if (selectedElements.size() >= 1) {
			for (Element element : selectedElements) {
				if (element.hasClass(classNameForRedirect)
						&& element.getElementsByAttribute("title").toString().matches(regExForUser)) {
					containsSomethingLikeUserName = true;
				}
			}
		}
		return containsSomethingLikeUserName;
	}

	public Boolean containsSomethingLikeUserName(String content) {
		Boolean containsSomethingLikeUserName = false;
		if (content.contains("title") && content.matches(regExForUser)) {
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
		String user = "";
		Pattern word = Pattern.compile("title");
		Matcher match = word.matcher(currContent.toString());
		if (match.find()) {
			Integer matchStart = match.start();
			String con = new String();
			int secondQuate=0;
			for (int i = matchStart; i < currContent.length(); i++) {
				if(secondQuate>=2){
					user = con;
					break;
				}
				else{
					Character charAt = currContent.charAt(i);
					if(charAt.equals('\"')) secondQuate++;
					else{
						con = con+charAt;
					}
				}
			}
		}
		return user = user.split(":")[1];
	}
}
