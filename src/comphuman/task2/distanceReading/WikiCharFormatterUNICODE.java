package comphuman.task2.distanceReading;

import java.util.HashMap;


/**
 * Handler to translate some strings to the specific uni-code letters which are used by wikipedia.
 * @author mcz
 *
 */
public class WikiCharFormatterUNICODE {

	private  HashMap<String, String> wikiUnicodeMap = new HashMap<>();
	private static final WikiCharFormatterUNICODE YOURSELF = new WikiCharFormatterUNICODE();

	/**
	 * Call the init. function.
	 */
	private WikiCharFormatterUNICODE() {
		__init();
	}

	/**
	 * Returns the this maper
	 * 
	 * @return This object.
	 */
	public static WikiCharFormatterUNICODE getInstance() {
		return YOURSELF;
	}

	/**
	 * If the input is a 'special' char, then you will get the representation of
	 * this in wiki-format style.
	 * 
	 * @param theCharAsString
	 *            String that should be mapped.
	 * @return String in wiki-format representation.
	 */
	public String getCharMappingForWikiFormat(String theCharAsString) {
		return wikiUnicodeMap.getOrDefault(theCharAsString, theCharAsString);
	}
	public String getComplettStringMapping(String stringToMap) {
		String result = "";
		
		for (int i = 0; i < stringToMap.length(); i++) {
			result = result + getCharMappingForWikiFormat(stringToMap.charAt(i)+"");
		}
		return result;
		
	}

	/**
	 * Instantiate the class.
	 */
	private void __init() {
		wikiUnicodeMap.put(" ", "_");
		wikiUnicodeMap.put("!", ".21");
		wikiUnicodeMap.put("\"", ".22");
		wikiUnicodeMap.put("#", ".23");
		wikiUnicodeMap.put("$", ".24");
		wikiUnicodeMap.put("%", ".25");
		wikiUnicodeMap.put("&", ".26");
		wikiUnicodeMap.put("'", ".27");
		wikiUnicodeMap.put("(", ".28");
		wikiUnicodeMap.put(")", ".29");
		wikiUnicodeMap.put("*", ".2A");
		wikiUnicodeMap.put("+", ".2B");
		wikiUnicodeMap.put(",", ".2C");
//		wikiUnicodeMap.put("-", ".2D");
//		wikiUnicodeMap.put(".", ".2E");
		wikiUnicodeMap.put("/", ".2F");
		wikiUnicodeMap.put(":", ".3A");
		wikiUnicodeMap.put(";", ".3B");
		wikiUnicodeMap.put("<", ".3C");
		wikiUnicodeMap.put("=", ".3D");
		wikiUnicodeMap.put(">", ".3E");
		wikiUnicodeMap.put("?", ".3F");
		wikiUnicodeMap.put("@", ".40");
		wikiUnicodeMap.put("[", ".5B");
		wikiUnicodeMap.put("\\", ".5C");
		wikiUnicodeMap.put("]", ".5D");
		wikiUnicodeMap.put("^", ".5E");
		wikiUnicodeMap.put("_", ".5F");
		wikiUnicodeMap.put("`", ".60");
		wikiUnicodeMap.put("{", ".7B");
		wikiUnicodeMap.put("|", ".7C");
		wikiUnicodeMap.put("}", ".7D");
		wikiUnicodeMap.put("~", ".7E");
		wikiUnicodeMap.put("Â¡", ".C2.A1");
		wikiUnicodeMap.put("Â¢", ".C2.A2");
		wikiUnicodeMap.put("Â£", ".C2.A3");
		wikiUnicodeMap.put("Â¤", ".C2.A4");
		wikiUnicodeMap.put("Â¥", ".C2.A5");
		wikiUnicodeMap.put("Â¦", ".C2.A6");
		wikiUnicodeMap.put("Â§", ".C2.A7");
		wikiUnicodeMap.put("Â¨", ".C2.A8");
		wikiUnicodeMap.put("Â©", ".C2.A9");
		wikiUnicodeMap.put("Âª", ".C2.AA");
		wikiUnicodeMap.put("Â«", ".C2.AB");
		wikiUnicodeMap.put("Â¬", ".C2.AC");
		wikiUnicodeMap.put("Â­", ".C2.AD");
		wikiUnicodeMap.put("Â®", ".C2.AE");
		wikiUnicodeMap.put("Â¯", ".C2.AF");
		wikiUnicodeMap.put("Â°", ".C2.B0");
		wikiUnicodeMap.put("Â±", ".C2.B1");
		wikiUnicodeMap.put("Â²", ".C2.B2");
		wikiUnicodeMap.put("Â³", ".C2.B3");
		wikiUnicodeMap.put("Â´", ".C2.B4");
		wikiUnicodeMap.put("Âµ", ".C2.B5");
		wikiUnicodeMap.put("Â¶", ".C2.B6");
		wikiUnicodeMap.put("Â·", ".C2.B7");
		wikiUnicodeMap.put("Â¸", ".C2.B8");
		wikiUnicodeMap.put("Â¹", ".C2.B9");
		wikiUnicodeMap.put("Âº", ".C2.BA");
		wikiUnicodeMap.put("Â»", ".C2.BB");
		wikiUnicodeMap.put("Â¼", ".C2.BC");
		wikiUnicodeMap.put("Â½", ".C2.BD");
		wikiUnicodeMap.put("Â¾", ".C2.BE");
		wikiUnicodeMap.put("Â¿", ".C2.BF");
		wikiUnicodeMap.put("Ã€", ".C3.80");
		wikiUnicodeMap.put("Ã�", ".C3.81");
		wikiUnicodeMap.put("Ã‚", ".C3.82");
		wikiUnicodeMap.put("Ãƒ", ".C3.83");
		wikiUnicodeMap.put("Ã„", ".C3.84");
		wikiUnicodeMap.put("Ã…", ".C3.85");
		wikiUnicodeMap.put("Ã†", ".C3.86");
		wikiUnicodeMap.put("Ã‡", ".C3.87");
		wikiUnicodeMap.put("Ãˆ", ".C3.88");
		wikiUnicodeMap.put("Ã‰", ".C3.89");
		wikiUnicodeMap.put("ÃŠ", ".C3.8A");
		wikiUnicodeMap.put("Ã‹", ".C3.8B");
		wikiUnicodeMap.put("ÃŒ", ".C3.8C");
		wikiUnicodeMap.put("Ã�", ".C3.8D");
		wikiUnicodeMap.put("ÃŽ", ".C3.8E");
		wikiUnicodeMap.put("Ã�", ".C3.8F");
		wikiUnicodeMap.put("Ã�", ".C3.90");
		wikiUnicodeMap.put("Ã‘", ".C3.91");
		wikiUnicodeMap.put("Ã’", ".C3.92");
		wikiUnicodeMap.put("Ã“", ".C3.93");
		wikiUnicodeMap.put("Ã”", ".C3.94");
		wikiUnicodeMap.put("Ã•", ".C3.95");
		wikiUnicodeMap.put("Ã–", ".C3.96");
		wikiUnicodeMap.put("Ã—", ".C3.97");
		wikiUnicodeMap.put("Ã˜", ".C3.98");
		wikiUnicodeMap.put("Ã™", ".C3.99");
		wikiUnicodeMap.put("Ãš", ".C3.9A");
		wikiUnicodeMap.put("Ã›", ".C3.9B");
		wikiUnicodeMap.put("Ãœ", ".C3.9C");
		wikiUnicodeMap.put("Ã�", ".C3.9D");
		wikiUnicodeMap.put("Ãž", ".C3.9E");
		wikiUnicodeMap.put("ÃŸ", ".C3.9F");
		wikiUnicodeMap.put("Ã ", ".C3.A0");
		wikiUnicodeMap.put("Ã¡", ".C3.A1");
		wikiUnicodeMap.put("Ã¢", ".C3.A2");
		wikiUnicodeMap.put("Ã£", ".C3.A3");
		wikiUnicodeMap.put("Ã¤", ".C3.A4");
		wikiUnicodeMap.put("Ã¥", ".C3.A5");
		wikiUnicodeMap.put("Ã¦", ".C3.A6");
		wikiUnicodeMap.put("Ã§", ".C3.A7");
		wikiUnicodeMap.put("Ã¨", ".C3.A8");
		wikiUnicodeMap.put("Ã©", ".C3.A9");
		wikiUnicodeMap.put("Ãª", ".C3.AA");
		wikiUnicodeMap.put("Ã«", ".C3.AB");
		wikiUnicodeMap.put("Ã¬", ".C3.AC");
		wikiUnicodeMap.put("Ã­", ".C3.AD");
		wikiUnicodeMap.put("Ã®", ".C3.AE");
		wikiUnicodeMap.put("Ã¯", ".C3.AF");
		wikiUnicodeMap.put("Ã°", ".C3.B0");
		wikiUnicodeMap.put("Ã±", ".C3.B1");
		wikiUnicodeMap.put("Ã²", ".C3.B2");
		wikiUnicodeMap.put("Ã³", ".C3.B3");
		wikiUnicodeMap.put("Ã´", ".C3.B4");
		wikiUnicodeMap.put("Ãµ", ".C3.B5");
		wikiUnicodeMap.put("Ã¶", ".C3.B6");
		wikiUnicodeMap.put("Ã·", ".C3.B7");
		wikiUnicodeMap.put("Ã¸", ".C3.B8");
		wikiUnicodeMap.put("Ã¹", ".C3.B9");
		wikiUnicodeMap.put("Ãº", ".C3.BA");
		wikiUnicodeMap.put("Ã»", ".C3.BB");
		wikiUnicodeMap.put("Ã¼", ".C3.BC");
		wikiUnicodeMap.put("Ã½", ".C3.BD");
		wikiUnicodeMap.put("Ã¾", ".C3.BE");
		wikiUnicodeMap.put("Ã¿", ".C3.BF");
	}
}
