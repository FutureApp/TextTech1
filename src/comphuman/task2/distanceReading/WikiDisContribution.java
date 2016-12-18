package comphuman.task2.distanceReading;

public class WikiDisContribution {

	String id;
	String content;
	String user;
	String creationTime;

	public WikiDisContribution(String id) {
		super();
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

}
