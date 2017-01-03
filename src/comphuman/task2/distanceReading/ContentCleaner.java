package comphuman.task2.distanceReading;

/**
 * Contains methods to clean a given content.
 * @author mcz
 *
 */
public class ContentCleaner {
	
	/**
	 * Clean a given content.
	 * 1. Cuts off everything behind &amp. (&amp included)
	 * @param content Content which need to be cleaned.
	 * @return clean content.
	 */
	public static String clean(String content) {
		content = cutOffAtamp(content);
		return content;
	}
	
	public static String cutOffAtamp(String string) {
		if (string.contains("&amp"))
			string = string.substring(0, string.indexOf("&amp"));
		return string;
	}

}
