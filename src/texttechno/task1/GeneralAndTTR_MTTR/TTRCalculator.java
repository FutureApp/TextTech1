package texttechno.task1.GeneralAndTTR_MTTR;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Engine to calc the TTR of a given input.
 * 
 * @author Michael Czaja
 *
 */
public class TTRCalculator {

	Map<String, Integer> tokenMapCount;
	Integer tokensLastCalc = -1;
	Integer uniqueTokensLastCalc = -1;
	Float TTRLastCalc = -1F;

	/**
	 * 
	 * @param tokensSortedMixedCase
	 *            Map which contains tokens and there counts. Style for Map
	 *            (token,countOfToken).
	 */
	public TTRCalculator(Map<String, Integer> tokensSortedMixedCase) {
		super();
		this.tokenMapCount = tokensSortedMixedCase;
	}

	public TTRCalculator() {
		super();
	}

	public Map<String, Integer> getTokenMapCount() {
		return tokenMapCount;
	}

	public void setTokenMapCount(Map<String, Integer> tokenMapCount) {
		this.tokenMapCount = tokenMapCount;
	}

	/**
	 * Calculates the TTR on given Map. This TTR-calculation shift ever
	 * uppercase character to lowercase.
	 * 
	 */
	public Float getTTRIgnoreCase() {
		Float TTR = 0F;
		Integer tokens = 0;
		HashSet<String> uniqueTokenSet = new HashSet<>();

		for (Entry<String, Integer> token : tokenMapCount.entrySet()) {
			String key = token.getKey();
			Integer value = token.getValue();
			tokens += value;
			uniqueTokenSet.add(key.toLowerCase());
		}

		TTR = (uniqueTokenSet.size() / (float) tokens);
		// Saves every value to look up if results of last calc. are needed.
		uniqueTokensLastCalc = uniqueTokenSet.size();
		tokensLastCalc = tokens;
		TTRLastCalc = TTR;

		return TTR;
	}

	/**
	 * Calculates the TTR on given Map. This TTR-calculation shift ever
	 * uppercase character to lowercase.
	 * 
	 */
	public Float getTTRCasesensitiv() {
		Float TTR = 0F;
		Integer tokens = 0;
		HashSet<String> uniqueTokenSet = new HashSet<>();

		for (Entry<String, Integer> token : tokenMapCount.entrySet()) {
			String key = token.getKey();
			Integer value = token.getValue();
			tokens += value;
			uniqueTokenSet.add(key);
		}

		TTR = (uniqueTokenSet.size() / (float) tokens);
		// Saves every value to look up if results of last calc are needed.
		uniqueTokensLastCalc = uniqueTokenSet.size();
		tokensLastCalc = tokens;
		TTRLastCalc = TTR;

		return TTR;
	}

	public Integer getTokensLastCalc() {
		return tokensLastCalc;
	}

	public Integer getUniqueTokensLastCalc() {
		return uniqueTokensLastCalc;
	}

	public Float getTTRLastCalc() {
		return TTRLastCalc;
	}

}
