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

	public Integer getNeutralProcess() {
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

	/**
	 * Adds the value of a negative process. Negative process means, that the
	 * author deletes some bytes of the article. By design, the value of +1 will
	 * be passed. See {@link ActionValue}
	 * 
	 * @param value.
	 *           See {@link ActionValue} and look for positive.
	 */
	public void addPositiveProcess(Integer value) {
		postitivProcesses += value;
	}
	

	
	public void addRevisedUser(String user) {
		if (!interactedOn.contains(user))
			interactedOn.add(user);
	}

	/**
	 * Adds the value of a negative process. Negative process means, that the
	 * author deletes some bytes of the article. By design, the value of -1 will
	 * be passed. See {@link ActionValue}. Method will convert the given negative number to a positive one.
	 * 
	 * @param value.
	 *           See {@link ActionValue} and look for negative
	 */
	public void addNegativeProcess(Integer value) {
		Integer valueToPush = 0; 
		if(value < 0 ) valueToPush = value*(-1);
		else valueToPush = value;
		negativeProcesses += valueToPush;
	}

	/**
	 * Adds the value of a neutral process. Neutral process means, that the
	 * author didn't change the amount of the article-bytes. By design, this
	 * method will  get the value of 0. Method will push +1;
	 * 
	 * @param value.
	 *           See {@link ActionValue} and look for neutral.
	 */
	public void addNeutralProcess(Integer value) {
		neutralProcess += value + 1;
	}
}
