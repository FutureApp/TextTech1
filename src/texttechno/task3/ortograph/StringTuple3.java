package texttechno.task3.ortograph;

public class StringTuple3 {
	String item01;
	String item02;
	String item03;
	
	
	public StringTuple3(String item01, String item02, String item03) {
		super();
		this.item01 = item01;
		this.item02 = item02;
		this.item03 = item03;
	}
	
	/**
	 * First element in the tuple. Often the lemma.
	 * @return First element.
	 */
	public String getItem01() {
		return item01;
	}
	/**
	 * Second element in the tuple.  Often the lemma-type.
	 * @return Second element.
	 */
	public String getItem02() {
		return item02;
	}
	/**
	 * Third element in the tuple. Often the word itself.
	 * @return Third element.
	 */
	public String getItem03() {
		return item03;
	}
}
