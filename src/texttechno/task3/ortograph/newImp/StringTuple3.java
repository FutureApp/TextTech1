package texttechno.task3.ortograph.newImp;

public class StringTuple3 {
	String item01;
	String item02;
	String item03;

	/**
	 * Generates a Tuple of dimension 3. All fields have the type of string.
	 * 
	 * @param item01
	 *            Often the type of the lemma.
	 * @param item02
	 *            Often the lemma itself.
	 * @param item03
	 *            Often the word.
	 * 
	 */
	public StringTuple3(String item01, String item02, String item03) {
		super();
		this.item01 = item01;
		this.item02 = item02;
		this.item03 = item03;
	}

	/**
	 * First element in the tuple. Often the lemma-type.
	 * 
	 * @return First element.
	 */
	public String getLemmaType() {
		return item01;
	}

	/**
	 * Second element in the tuple. Often the lemma.
	 * 
	 * @return Second element.
	 */
	public String getLemma() {
		return item02;
	}

	/**
	 * Third element in the tuple. Often the word itself.
	 * 
	 * @return Third element.
	 */
	public String getWord() {
		return item03;
	}
}