package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Represents a post from the wikipedia article-discussion section. Each post/node gets a unique ID. This is most important for the visualization-step. 
 * @author mcz
 *
 */
public class WikiNodePost {
	String fatherNodeName;
	String nodeName;
	String content;
	String contentCreatedUsername;
	String contentCreatedDate;
	
	ArrayList<WikiNodePost> wikiNodes;
	
	/**
	 * Inits the wiki-post. A unique ID of this node will be generated automatically.
	 * @param fatherNodeName Unique id from the father-node.
	 * @param content Content of this node.
	 * @param contentCreatedUsername The name of the user, which wrote this post.
	 * @param contentCreatedDate The date of creation.
	 */
	public WikiNodePost(String fatherNodeName, String content, String contentCreatedUsername, String contentCreatedDate) {
		super();
		this.fatherNodeName = fatherNodeName;
		this.content = content;
		this.contentCreatedUsername = contentCreatedUsername;
		this.contentCreatedDate = contentCreatedDate;
		this.nodeName = UUID.randomUUID().toString();
	}
	
	/**
	 * Inits the wiki-post.
	 * @param UUID Unique ID for the node. Hint: Use UUID.randome()+"" for this purpose.
	 * @param fatherNodeName Unique id from the father-node.
	 * @param content Content of this node
	 * @param contentCreatedUsername The name of the user, which wrote this post.
	 * @param contentCreatedDate The date of creation.
	 */
	public WikiNodePost(String UUID,String fatherNodeName, String content, String contentCreatedUsername, String contentCreatedDate) {
		super();
		this.fatherNodeName = fatherNodeName;
		this.content = content;
		this.contentCreatedUsername = contentCreatedUsername;
		this.contentCreatedDate = contentCreatedDate;
		this.nodeName = UUID;
	}
	
	/**
	 * Returns the ID of the father-node.
	 * @return The ID of the father-node. 
	 */
	public String getFatherNodeName() {
		return fatherNodeName;
	}
	/**
	 * Returns the name of this node
	 * @return Returns the ID of this node.
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * Returns the content of this node.
	 * @return Returns the content of this node.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * Returns the user, which created for the corresponding post.
	 * @return Returns the name of the user.
	 */
	public String getContentCreatedUsername() {
		return contentCreatedUsername;
	}
	/**
	 * Returns the date of creation for the corresponding post.
	 * @return Returns the date of creation.
	 */
	public String getContentCreatedDate() {
		return contentCreatedDate;
	}

}
