package texttechno.task1.prototype.types;

public class TupelIS {
	public Integer key;
	public String Value;
	public TupelIS(Integer key, String value) {
		super();
		this.key = key;
		Value = value;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}

}