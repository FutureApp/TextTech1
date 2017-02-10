package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;

public class IntegerSignature {

	Integer kookkuRate;
	Integer randsumf1;
	Integer randsumf2;
	Integer sampleSize;

	
	/**
	 * Holder-class for Integer based signatures.
	 * @param kookkuRate Field-Value 00. Mostly the value of kookkuren-value.
	 * @param randsumf1 Field-Value 01. Mostly the randsum f1.
	 * @param randsumf2 Field-Value 10. Mostly the randsum f2
	 * @param sampleSize Field-Value 11. Mostly the sample Size
	 */
	public IntegerSignature(Integer kookkuRate, Integer randsumf1, Integer randsumf2, Integer sampleSize) {
		super();
		this.kookkuRate = kookkuRate;
		this.randsumf1 = randsumf1;
		this.randsumf2 = randsumf2;
		this.sampleSize = sampleSize;
	}

	/**
	 * Retruns the kookkurence-value. Field 00
	 * @return Value of kookkurence-value. Field 00
	 */
	public Integer getKookkuRate() {
		return kookkuRate;
	}

	/**
	 * Sets the kookkurence-value Field 00
	 * @param kookkuRate Sets the kookkurence-value. Field 00
	 */
	public void setKookkuRate(Integer kookkuRate) {
		this.kookkuRate = kookkuRate;
	}

	/**
	 * Returns the randsum f1. Field 01.
	 * @return Value of randsum f1. Field 01.
	 */
	public Integer getRandsumf1() {
		return randsumf1;
	}

	/**
	 * Sets the value of randsum f1. Field 01
	 * @param randsumf1 New Value of field 01 ( randsum f1).
	 */
	public void setRandsumf1(Integer randsumf1) {
		this.randsumf1 = randsumf1;
	}

	/**
	 * Retunrs the value of randsum f2. Field 10
	 * @return Value of randsum f2. Field 10
	 */
	public Integer getRandsumf2() {
		return randsumf2;
	}

	/**
	 * Sets the value of randsum f2. Field 10.
	 * @param randsumf2 New Value of randsum f2. Field 10.
	 */
	public void setRandsumf2(Integer randsumf2) {
		this.randsumf2 = randsumf2;
	}

	/**
	 * Returns the sample-size. Field 11.
	 * @return Value of sample-size. Field 11
	 */
	public Integer getSampleSize() {
		return sampleSize;
	}
/**
 * Sets the value of the sample-size. Field 11.
 * @param sampleSize New Value of sample-size.
 */
	public void setSampleSize(Integer sampleSize) {
		this.sampleSize = sampleSize;
	}

	/**
	 * Retruns the field-value as a string. Field-order 00,01,10,11
	 */
	@Override
	public String toString() {
		String value = String.format("(%d,%d,%d,%d)", kookkuRate, randsumf1, randsumf2, sampleSize);
		return value;
	}

	/**
	 * Returns all field-values as a list. Order: 00,01,10,11
	 * @return All field-values a list.
	 */
	public ArrayList<Integer> getValuesAsList() {
		ArrayList<Integer> result = new ArrayList<>();
		result.add(kookkuRate);
		result.add(randsumf1);
		result.add(randsumf2);
		result.add(sampleSize);
		return result;
	}

}
