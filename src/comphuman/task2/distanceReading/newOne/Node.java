package comphuman.task2.distanceReading.newOne;

public class Node {

	String name;
	String father;
	String aut;
	String date;
	
	/**
	 * 
	 * @param name
	 * @param father
	 * @param aut
	 * @param date
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
