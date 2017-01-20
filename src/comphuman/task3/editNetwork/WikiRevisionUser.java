package comphuman.task3.editNetwork;

import java.util.ArrayList;

public class WikiRevisionUser {

	Integer negativeProcesses = 0;
	Integer neutralProcess = 0;
	Integer postitivProcesses = 0;
	String userRole;
	ArrayList<String> interactedOn = new ArrayList<>();
	ArrayList<Double> EdgesWight = new ArrayList<>();

	String username;

	public WikiRevisionUser(String userRole, String username) {
		super();
		this.userRole = userRole;
		this.username = username;
	}
/**
 * Returns the user-name.
 * @return name of user.
 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the number of negative- processes caused by the user himself.
	 * @return Number of negative-processes.
	 */
	public Integer getNegativeProcesses() {
		return negativeProcesses;
	}

	/**
	 * Returns the number of neutral- processes caused by the user himself.
	 * @return Number of neutral-processes.
	 */
	public Integer getNeutralProcess() {
		return neutralProcess;
	}

	/**
	 * Sets the number of negative-processes.
	 * @param negativeProcesses Number of negative-processes.
	 */
	public void setNegativeProcesses(Integer negativeProcesses) {
		this.negativeProcesses = negativeProcesses;
	}

	/**
	 * Returns the number of positive- processes caused by the user himself.
	 * @return Number of positive-processes.
	 */
	public Integer getPostitivProcesses() {
		return postitivProcesses;
	}

	/**
	 * Sets the number of positive-processes.
	 * @param negativeProcesses Number of postivie-processes.
	 */
	public void setPostitivProcesses(Integer postitivProcesses) {
		this.postitivProcesses = postitivProcesses;
	}

	/**
	 * Returns a list of all revised-users.
	 * @return List of revised users.
	 */
	public ArrayList<String> getInteractedOn() {
		return interactedOn;
	}
	/**
	 * Sets the number of negative-processes.
	 * @param negativeProcesses Number of negative-processes.
	 */
	public void setInteractedOn(ArrayList<String> interactedOn) {
		this.interactedOn = interactedOn;
	}

	/**
	 * Adds the value of a negative process. Negative process means, that the
	 * author deletes some bytes of the article. By design, the value of +1 will
	 * be passed. See {@link ActionValue}
	 * 
	 * @param value.
	 *            See {@link ActionValue} and look for positive.
	 */
	public void addPositiveProcess(Integer value) {
		postitivProcesses += value;
	}

	/**
	 * Add a user-name to the list of revised-users.
	 * @param user Name of user who get revised by the given user.
	 */
	public void addRevisedUser(String user) {
		if (!interactedOn.contains(user))
			interactedOn.add(user);
	}

	/**
	 * Adds the value of a negative process. Negative process means, that the
	 * author deletes some bytes of the article. By design, the value of -1 will
	 * be passed. See {@link ActionValue}. Method will convert the given
	 * negative number to a positive one.
	 * 
	 * @param value.
	 *            See {@link ActionValue} and look for negative
	 */
	public void addNegativeProcess(Integer value) {
		Integer valueToPush = 0;
		if (value < 0){
			valueToPush = value * (-1);
		}
		else{
			valueToPush = value;
			
		}
		negativeProcesses += valueToPush;
	}

	/**
	 * Adds the value of a neutral process. Neutral process means, that the
	 * author didn't change the amount of the article-bytes. By design, this
	 * method will get the value of 0. Method will push +1;
	 * 
	 * @param value.
	 *            See {@link ActionValue} and look for neutral.
	 */
	public void addNeutralProcess(Integer value) {
		neutralProcess += value + 1;
	}

	/**
	 * Sets the role of the given user. Most used values: aut,cur,both
	 * @param role aut|cur|both
	 */
	public void setRole(String role) {
		userRole = role;
	}
	
	public ArrayList<Double> getListOfRelations() {
		return EdgesWight;
	}
	public void addEdgeWeight(Integer curActionValue) {
		EdgesWight.add((double)curActionValue);
	}
	public void addEdgeWeight(Double curActionValue) {
		EdgesWight.add(curActionValue);
	}
}
