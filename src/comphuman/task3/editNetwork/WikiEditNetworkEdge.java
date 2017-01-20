package comphuman.task3.editNetwork;

public class WikiEditNetworkEdge {

	String source;
	String target; 
	Integer actionValue;
	public WikiEditNetworkEdge(String source, String target, Integer actionValue) {
		super();
		this.source = source;
		this.target = target;
		this.actionValue = actionValue;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Integer getActionValue() {
		return actionValue;
	}
	public void setActionValue(Integer actionValue) {
		this.actionValue = actionValue;
	}
}
