package comphuman.task2.distanceReading;

import org.jsoup.nodes.Element;

public class Extractor {
	String regexWikiGermanDateFormateAnyWhere = ".*[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3}. [0-9][0-9][0-9][0-9].*";
	String regexWikiGermanDateFormate = "[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3}. [0-9][0-9][0-9][0-9].*";

	public  Boolean hasWikiUserName(Element e) {
		Boolean containsUserName = false;
		String ab = e.select("a").get(0).attr("title").toString();
		if (ab != null)
			containsUserName = true;
		return containsUserName;
	}
	
	public Boolean isUserAndDateExtractable(Element e) {
		return (hasCreationDate(e)&&hasWikiUserName(e));
	}

	public  Boolean hasCreationDate(Element e) {
		return e.html().matches(regexWikiGermanDateFormateAnyWhere);
	}

	public  String getUserName(Element e) {
		String user = "unkown";
		if (e == null) {
			System.err.println("Nothing given");
		} else {
			String userNameInTitle = e.select("a").last().attr("title").toString();
			user = userNameInTitle.substring(userNameInTitle.lastIndexOf(":") + 1);

		}
		return user;
	}

	public  String getCreationDate(Element e) {
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
}
