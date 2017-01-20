package comphuman.task3.editNetwork;

public class WikiEditNetworkEdge {

	String source;
	String target; 
	Integer actionValue;
	
	/**
	 * Init the wiki-network-edge.
	 * @param source Source of the directed edge.
	 * @param target Target of the directed edge.
	 * @param actionValue Value of action. Most: Impact in byte-representation.
	 */
	public WikiEditNetworkEdge(String source, String target, Integer actionValue) {
		super();
		this.source = source;
		this.target = target;
		this.actionValue = actionValue;
	}
	/**
	 * Returns the name of the source.
	 * @return Name of source.
	 */
	public String getSource() {
		return source;
	}
	/**
	 * Returns the name of the target.
	 * @return Name of target.
	 */
	public String getTarget() {
		return target;
	}
	
	/**
	 * Returns the action value.
	 * @return Value of action.
	 */
	public Integer getActionValue() {
		return actionValue;
	}
}
