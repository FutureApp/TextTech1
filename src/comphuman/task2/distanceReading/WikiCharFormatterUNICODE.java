package comphuman.task2.distanceReading;

import java.util.HashMap;

import texttechno.task2.Compare.Properties_SingleTone;

public class WikiCharFormatterUNICODE {

	private  HashMap<String, String> wikiUnicodeMap = new HashMap<>();
	private static final WikiCharFormatterUNICODE YOURSELF = new WikiCharFormatterUNICODE();

	/**
	 * Call the init function.
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
		wikiUnicodeMap.put("¡", ".C2.A1");
		wikiUnicodeMap.put("¢", ".C2.A2");
		wikiUnicodeMap.put("£", ".C2.A3");
		wikiUnicodeMap.put("¤", ".C2.A4");
		wikiUnicodeMap.put("¥", ".C2.A5");
		wikiUnicodeMap.put("¦", ".C2.A6");
		wikiUnicodeMap.put("§", ".C2.A7");
		wikiUnicodeMap.put("¨", ".C2.A8");
		wikiUnicodeMap.put("©", ".C2.A9");
		wikiUnicodeMap.put("ª", ".C2.AA");
		wikiUnicodeMap.put("«", ".C2.AB");
		wikiUnicodeMap.put("¬", ".C2.AC");
		wikiUnicodeMap.put("­", ".C2.AD");
		wikiUnicodeMap.put("®", ".C2.AE");
		wikiUnicodeMap.put("¯", ".C2.AF");
		wikiUnicodeMap.put("°", ".C2.B0");
		wikiUnicodeMap.put("±", ".C2.B1");
		wikiUnicodeMap.put("²", ".C2.B2");
		wikiUnicodeMap.put("³", ".C2.B3");
		wikiUnicodeMap.put("´", ".C2.B4");
		wikiUnicodeMap.put("µ", ".C2.B5");
		wikiUnicodeMap.put("¶", ".C2.B6");
		wikiUnicodeMap.put("·", ".C2.B7");
		wikiUnicodeMap.put("¸", ".C2.B8");
		wikiUnicodeMap.put("¹", ".C2.B9");
		wikiUnicodeMap.put("º", ".C2.BA");
		wikiUnicodeMap.put("»", ".C2.BB");
		wikiUnicodeMap.put("¼", ".C2.BC");
		wikiUnicodeMap.put("½", ".C2.BD");
		wikiUnicodeMap.put("¾", ".C2.BE");
		wikiUnicodeMap.put("¿", ".C2.BF");
		wikiUnicodeMap.put("À", ".C3.80");
		wikiUnicodeMap.put("Á", ".C3.81");
		wikiUnicodeMap.put("Â", ".C3.82");
		wikiUnicodeMap.put("Ã", ".C3.83");
		wikiUnicodeMap.put("Ä", ".C3.84");
		wikiUnicodeMap.put("Å", ".C3.85");
		wikiUnicodeMap.put("Æ", ".C3.86");
		wikiUnicodeMap.put("Ç", ".C3.87");
		wikiUnicodeMap.put("È", ".C3.88");
		wikiUnicodeMap.put("É", ".C3.89");
		wikiUnicodeMap.put("Ê", ".C3.8A");
		wikiUnicodeMap.put("Ë", ".C3.8B");
		wikiUnicodeMap.put("Ì", ".C3.8C");
		wikiUnicodeMap.put("Í", ".C3.8D");
		wikiUnicodeMap.put("Î", ".C3.8E");
		wikiUnicodeMap.put("Ï", ".C3.8F");
		wikiUnicodeMap.put("Ð", ".C3.90");
		wikiUnicodeMap.put("Ñ", ".C3.91");
		wikiUnicodeMap.put("Ò", ".C3.92");
		wikiUnicodeMap.put("Ó", ".C3.93");
		wikiUnicodeMap.put("Ô", ".C3.94");
		wikiUnicodeMap.put("Õ", ".C3.95");
		wikiUnicodeMap.put("Ö", ".C3.96");
		wikiUnicodeMap.put("×", ".C3.97");
		wikiUnicodeMap.put("Ø", ".C3.98");
		wikiUnicodeMap.put("Ù", ".C3.99");
		wikiUnicodeMap.put("Ú", ".C3.9A");
		wikiUnicodeMap.put("Û", ".C3.9B");
		wikiUnicodeMap.put("Ü", ".C3.9C");
		wikiUnicodeMap.put("Ý", ".C3.9D");
		wikiUnicodeMap.put("Þ", ".C3.9E");
		wikiUnicodeMap.put("ß", ".C3.9F");
		wikiUnicodeMap.put("à", ".C3.A0");
		wikiUnicodeMap.put("á", ".C3.A1");
		wikiUnicodeMap.put("â", ".C3.A2");
		wikiUnicodeMap.put("ã", ".C3.A3");
		wikiUnicodeMap.put("ä", ".C3.A4");
		wikiUnicodeMap.put("å", ".C3.A5");
		wikiUnicodeMap.put("æ", ".C3.A6");
		wikiUnicodeMap.put("ç", ".C3.A7");
		wikiUnicodeMap.put("è", ".C3.A8");
		wikiUnicodeMap.put("é", ".C3.A9");
		wikiUnicodeMap.put("ê", ".C3.AA");
		wikiUnicodeMap.put("ë", ".C3.AB");
		wikiUnicodeMap.put("ì", ".C3.AC");
		wikiUnicodeMap.put("í", ".C3.AD");
		wikiUnicodeMap.put("î", ".C3.AE");
		wikiUnicodeMap.put("ï", ".C3.AF");
		wikiUnicodeMap.put("ð", ".C3.B0");
		wikiUnicodeMap.put("ñ", ".C3.B1");
		wikiUnicodeMap.put("ò", ".C3.B2");
		wikiUnicodeMap.put("ó", ".C3.B3");
		wikiUnicodeMap.put("ô", ".C3.B4");
		wikiUnicodeMap.put("õ", ".C3.B5");
		wikiUnicodeMap.put("ö", ".C3.B6");
		wikiUnicodeMap.put("÷", ".C3.B7");
		wikiUnicodeMap.put("ø", ".C3.B8");
		wikiUnicodeMap.put("ù", ".C3.B9");
		wikiUnicodeMap.put("ú", ".C3.BA");
		wikiUnicodeMap.put("û", ".C3.BB");
		wikiUnicodeMap.put("ü", ".C3.BC");
		wikiUnicodeMap.put("ý", ".C3.BD");
		wikiUnicodeMap.put("þ", ".C3.BE");
		wikiUnicodeMap.put("ÿ", ".C3.BF");
	}
}
