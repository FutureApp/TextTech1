package texttechno.task3.ortograph.newImp;

public class ExpectedRate {

	Integer Yw1Yw2;
	Integer Yw1Nw2;
	Integer Nw1Yw2;
	Integer Nw1Nw2;

	/**
	 * Holder-class for the expectation-signature.
	 * @param yw1Yw2
	 * Field 00. Contains [Yes -> Word1, Yes->Word2]
	 * @param yw1Nw2
	 * Field 01. Contains [Yes -> Word1, No->Word2]
	 * @param nw1Yw2
	 * Field 10. Contains [No -> Word1, Yes->Word2]
	 * @param nw1Nw2
	 * Field 11. Contains [No -> Word1, No->Word2]
	 */
	public ExpectedRate(Integer yw1Yw2, Integer yw1Nw2, Integer nw1Yw2, Integer nw1Nw2) {
		super();
		Yw1Yw2 = yw1Yw2;
		Yw1Nw2 = yw1Nw2;
		Nw1Yw2 = nw1Yw2;
		Nw1Nw2 = nw1Nw2;
	}

	/**
	 * Returns field-value of signature 00.
	 * @return Field-Value of 00.
	 */
	public Integer getYw1Yw2() {
		return Yw1Yw2;
	}

	/**
	 * Sets field-value 00 by given parameter.
	 * @param yw1Yw2 Value for field 00.
	 */
	public void setYw1Yw2(Integer yw1Yw2) {
		Yw1Yw2 = yw1Yw2;
	}
	/**
	 * Returns field-value of signature 10.
	 * @return Field-Value of 10.
	 */
	public Integer getYw1Nw2() {
		return Yw1Nw2;
	}
	/**
	 * Sets field-value 10 by given parameter.
	 * @param yw1Nw2 Value for field 10.
	 */
	public void setYw1Nw2(Integer yw1Nw2) {
		Yw1Nw2 = yw1Nw2;
	}
	/**
	 * Returns field-value of signature 01.
	 * @return Field-Value of 01.
	 */
	public Integer getNw1Yw2() {
		return Nw1Yw2;
	}

	/**
	 * Sets field-value 01 by given parameter
	 * @param nw1Yw2 Value for field 01
	 */
	public void setNw1Yw2(Integer nw1Yw2) {
		Nw1Yw2 = nw1Yw2;
	}
	/**
	 * Returns field-value of signature 11.
	 * @return Field-Value of 11.
	 */
	public Integer getNw1Nw2() {
		return Nw1Nw2;
	}

	/**
	 * Sets field-value 00 by given parameter
	 * @param nw1Nw2 Value for field 00
	 */
	public void setNw1Nw2(Integer nw1Nw2) {
		Nw1Nw2 = nw1Nw2;
	}

}
