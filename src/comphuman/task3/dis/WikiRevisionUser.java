package comphuman.task3.dis;

import java.util.ArrayList;

public class WikiRevisionUser {

	Integer negativeProcesses = 0;
	Integer neutralProcess = 0;
	Integer postitivProcesses = 0;
	String type;
	ArrayList<String> interactedOn = new ArrayList<>();

	String username;

	public WikiRevisionUser(String type, String username) {
		super();
		this.type = type;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public Integer getNegativeProcesses() {
		return negativeProcesses;
	}
	
	public Integer getNeutralProcess(){
		return neutralProcess;
	}

	public void setNegativeProcesses(Integer negativeProcesses) {
		this.negativeProcesses = negativeProcesses;
	}

	public Integer getPostitivProcesses() {
		return postitivProcesses;
	}

	public void setPostitivProcesses(Integer postitivProcesses) {
		this.postitivProcesses = postitivProcesses;
	}

	public ArrayList<String> getInteractedOn() {
		return interactedOn;
	}

	public void setInteractedOn(ArrayList<String> interactedOn) {
		this.interactedOn = interactedOn;
	}

	public void addPositiveProcess(Integer value) {
		postitivProcesses += value;
	}

	public void interactedWith(String user) {
		if (!interactedOn.contains(user))
			interactedOn.add(user);
	}

	public void addNegativeProcess(Integer value) {
		negativeProcesses -= value;

	}

	public void addNeutralProcess(Integer curActionValue) {
		neutralProcess += curActionValue;
	}
}
