package comphuman.task2.distanceReading;

import org.jsoup.nodes.Element;

public class Extractor {
	private String regexWikiGermanDateFormateAnyWhere = ".*[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3}. [0-9][0-9][0-9][0-9].*";
	private String regexWikiGermanDateFormate = "[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3}. [0-9][0-9][0-9][0-9].*";
	private String delimiterForNextLayer = "<dl>";
	
	/**
	 * Checks if a given element contains the information of a user-name.
	 * 
	 * @param e
	 *            Element which should contain the required information.
	 * @return True - if the element contains something like a user-name.
	 */
	public Boolean hasWikiUserName(Element e) {
		Boolean containsUserName = false;
		String ab = e.select("a").get(0).attr("title").toString();
		if (ab != null)
			containsUserName = true;
		return containsUserName;
	}

	/**
	 * Check if a given element contains information about the user and a
	 * creation-date.
	 * 
	 * @param e
	 *            Element which contains the user-name and the creation-date.
	 * @return True- if contains both.
	 */
	public Boolean isUserAndDateExtractable(Element e) {
		return (hasCreationDate(e) && hasWikiUserName(e));
	}

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
		if (e.toString().trim().startsWith(delimiterForNextLayer)){
			initNextLayer = true;
		}
		return initNextLayer;
	}
}
