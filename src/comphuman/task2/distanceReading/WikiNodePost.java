package comphuman.task2.distanceReading;

import java.util.ArrayList;
import java.util.UUID;

public class WikiNodeDiscussion {
	String fatherNodeName;
	String nodeName;
	String content;
	String contentCreatedUsername;
	String contentCreatedDate;
	
	ArrayList<WikiNodeDiscussion> wikiNodes;
	public WikiNodeDiscussion(String fatherNodeName, String content, String contentCreatedUsername, String contentCreatedDate) {
		super();
		this.fatherNodeName = fatherNodeName;
		this.content = content;
		this.contentCreatedUsername = contentCreatedUsername;
		this.contentCreatedDate = contentCreatedDate;
		this.nodeName = UUID.randomUUID().toString();
	}
	public WikiNodeDiscussion(String UUID,String fatherNodeName, String content, String contentCreatedUsername, String contentCreatedDate) {
		super();
		this.fatherNodeName = fatherNodeName;
		this.content = content;
		this.contentCreatedUsername = contentCreatedUsername;
		this.contentCreatedDate = contentCreatedDate;
		this.nodeName = UUID;
	}
	public String getFatherNodeName() {
		return fatherNodeName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public String getContent() {
		return content;
	}
	public String getContentCreatedUsername() {
		return contentCreatedUsername;
	}
	public String getContentCreatedDate() {
		return contentCreatedDate;
	}
	
	public void hot() {
		System.out.println();
	}

}
