package humancomp.task1.namingGame;

public class Message {
	
	String objectName; 
	String name;
	public Message(String objectName, String name) {
		super();
		this.objectName = objectName;
		this.name = name;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
