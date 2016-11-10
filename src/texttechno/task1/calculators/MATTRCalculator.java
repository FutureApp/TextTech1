package texttechno.task1.calculators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class MATTRCalculator {
	List<String> listOfTextTokens;
	Integer windowSize;
	Float MATTRLastCalc = -1F;
	TTRCalculator TTRCalc = new TTRCalculator();
	public ArrayList<Float> getListOfWindows() {
		return listOfWindows;
	}

	ArrayList<Float> listOfWindows;
	HashMap<String, Integer> windowCount;

	public MATTRCalculator(List<String> listOfTextTokens, Integer windowSize) {
		super();
		this.listOfTextTokens = listOfTextTokens;
		this.windowSize = windowSize;
	}

	public void calcMATTR() {
		listOfWindows = new ArrayList<>();
		windowCount = new HashMap<>();
		for (int i = 0; i < windowSize; i++) {
			String keyOfToken = listOfTextTokens.get(i);
			if (windowCount.containsKey(keyOfToken)) {
				windowCount.put(keyOfToken, windowCount.get(keyOfToken) + 1);
			} else {
				windowCount.put(keyOfToken, 1);
			}
		}
		TTRCalc.setTokenMapCount(windowCount);
		listOfWindows.add(TTRCalc.getTTRIgnoreCase());

		for (int x = windowSize; x < listOfTextTokens.size(); x++) {
//			System.out.print("Before:");
//			printEachHashMapValue(windowCount);
//			System.out.print(" --> " + TTRCalc.getTTRLastCalc());
//			System.out.println();
			String keyFromLastIntervall = listOfTextTokens.get(x - windowSize);
			/*
			 * decrease key-value form key-word of hash map which get lose by
			 * window-slide. If the new value of the key-value equals 0, remove
			 * the key from the list.
			 */
			windowCount.put(keyFromLastIntervall, windowCount.get(keyFromLastIntervall) - 1);
			if (windowCount.get(keyFromLastIntervall) <= 0)
				windowCount.remove(keyFromLastIntervall);

			// Add new word to the HashMap-List
			String keyToken = listOfTextTokens.get(x);
			if (windowCount.containsKey(keyToken)) {
				windowCount.put(keyToken, windowCount.get(keyToken) + 1);
			} else {
				windowCount.put(keyToken, 1);
			}
			TTRCalc.setTokenMapCount(windowCount);
			listOfWindows.add(TTRCalc.getTTRIgnoreCase());
//			System.out.print("After:");
//			printEachHashMapValue(windowCount);
//			System.out.print(" --> " + TTRCalc.getTTRLastCalc());
//			System.out.println();
		}

		MATTRLastCalc = calcResultOfMATTR();
	}

	private Float calcResultOfMATTR() {
		float summOfFloats = 0F;
		for (Float TTRResult : listOfWindows) {
			summOfFloats += TTRResult;
		}
		MATTRLastCalc = summOfFloats / listOfWindows.size();
		return MATTRLastCalc;
	}

	private void printMATTR() {
		float summOfFloats = 0F;
		for (Float TTRResult : listOfWindows) {
			summOfFloats += TTRResult;
		}
		MATTRLastCalc = summOfFloats / listOfWindows.size();
		System.out.print("  " + MATTRLastCalc);

	}

	private void printEachHashMapValue(HashMap<String, Integer> windowCount) {
		for (Entry<String, Integer> entry : windowCount.entrySet()) {
			System.out.print(entry.getKey() + "," + entry.getValue() + "|");
		}
	}

	public Float getMATTRLastRun() {
		return MATTRLastCalc;
	}

}
