package comphuman.task3.editNetwork;

public class ReviserItem {

	private String nameOfUser;
	private Integer bytesAsInteger;

	public ReviserItem(String nameOfUser, Integer bytesAsInteger) {
		super();
		this.nameOfUser = nameOfUser;
		this.bytesAsInteger = bytesAsInteger;
	}

	public String getNameOfUser() {
		return nameOfUser;
	}

	public void setNameOfUser(String nameOfUser) {
		this.nameOfUser = nameOfUser;
	}

	public Integer getBytesAsInteger() {
		return bytesAsInteger;
	}

	public void setBytesAsInteger(Integer bytesAsInteger) {
		this.bytesAsInteger = bytesAsInteger;
	}

}
