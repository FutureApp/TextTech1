package prototype;

import java.util.NavigableMap;

public class TTRCalculator {

	NavigableMap<String, Integer> tokenMapCount;
	Float TTR;

	/**
	 * 
	 * @param tokenMapCount
	 *            Map which contains tokens and there counts. Style for Map
	 *            (token,countOfToken).
	 */
	public TTRCalculator(NavigableMap<String, Integer> tokenMapCount) {
		super();
		this.tokenMapCount = tokenMapCount;
		
	}

	/**
	 * Calculates the TTR on given Map. This TTR-calculation shift ever uppercase character  to lowercase.
	 * 
	 */
	public Float getTTRIgnoreCase() {
		return TTR;
	}
	
	/**
	 * Calculates the TTR on given Map. This TTR-calculation shift ever uppercase character  to lowercase.
	 * 
	 */
	public Float getTTRCasesensitiv() {
		return TTR;
	}
}
