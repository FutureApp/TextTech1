package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;

public class IntegerSignature {

	Integer kookkuRate;
	Integer randsumf1;
	Integer randsumf2;
	Integer sampleSize;

	public IntegerSignature(Integer kookkuRate, Integer randsumf1, Integer randsumf2, Integer sampleSize) {
		super();
		this.kookkuRate = kookkuRate;
		this.randsumf1 = randsumf1;
		this.randsumf2 = randsumf2;
		this.sampleSize = sampleSize;
	}

	public Integer getKookkuRate() {
		return kookkuRate;
	}

	public void setKookkuRate(Integer kookkuRate) {
		this.kookkuRate = kookkuRate;
	}

	public Integer getRandsumf1() {
		return randsumf1;
	}

	public void setRandsumf1(Integer randsumf1) {
		this.randsumf1 = randsumf1;
	}

	public Integer getRandsumf2() {
		return randsumf2;
	}

	public void setRandsumf2(Integer randsumf2) {
		this.randsumf2 = randsumf2;
	}

	public Integer getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(Integer sampleSize) {
		this.sampleSize = sampleSize;
	}

	@Override
	public String toString() {
		String value = String.format("(%d,%d,%d,%d)", kookkuRate, randsumf1, randsumf2, sampleSize);
		return value;
	}

	public ArrayList<Integer> getValuesAsList() {
		ArrayList<Integer> result = new ArrayList<>();
		result.add(kookkuRate);
		result.add(randsumf1);
		result.add(randsumf2);
		result.add(sampleSize);
		return result;
	}

}
