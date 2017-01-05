package comphuman.task2.distanceReading.newOne;

public class Node {

	String name;
	String father;
	String aut;
	String date;
	
	/**
	 * Post in node-representation.
	 * @param name ID of the node.
	 * @param father Father of the node.
	 * @param aut Name of user who created that post.
	 * @param date Creation date of the post.
	 */
	public Node(String name, String father, String aut, String date) {
		super();
		this.name = name;
		this.father = father;
		this.aut = aut;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public String getFather() {
		return father;
	}

	public String getAut() {
		return aut;
	}

	public String getDate() {
		return date;
	}
	
}
