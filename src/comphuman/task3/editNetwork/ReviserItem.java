package comphuman.task3.editNetwork;

public class ReviserItem {

	private String nameOfUser;
	private Integer bytesAsInteger;

	
	/**
	 * Will create a Reviser-item. Reviser-item contains the name and the byte-impact.
	 * @param nameOfUser name of the given user.
	 * @param bytesAsInteger value of impact in bytes.
	 */
	public ReviserItem(String nameOfUser, Integer bytesAsInteger) {
		super();
		this.nameOfUser = nameOfUser;
		this.bytesAsInteger = bytesAsInteger;
	}

	/**
	 * Returns the name of the given user.
	 * @return Name of user.
	 */
	public String getNameOfUser() {
		return nameOfUser;
	}

	/**
	 * Returns the value of impact(bytes)
	 * @return Value of impact in bytes-representation.
	 */
	public Integer getBytesAsInteger() {
		return bytesAsInteger;
	}

}
