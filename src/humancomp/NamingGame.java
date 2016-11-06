package humancomp;

import java.util.ArrayList;
import java.util.Random;

public class NamingGame {
	Integer numberOfAgents;
	Integer numberOfRounds;
	Integer numberOfRepeats;
	Random randome;

	ArrayList<AgentsOneObject> listOfAgents;
	Integer round = 0;
	Integer repeat = 0;

	public NamingGame(Integer numberOfAgents, Integer numberOfRounds, Integer numberOfRepeats) {
		super();
		this.numberOfAgents = numberOfAgents;
		this.numberOfRounds = numberOfRounds;
		this.numberOfRepeats = numberOfRepeats;
		this.randome = new Random();
	}

	public void initiliezeGame() {
		createListOfAgents();
	}

	private void createListOfAgents() {
		for (int i = 0; i < numberOfAgents; i++) {
			AgentsOneObject agent = new AgentsOneObject();
			listOfAgents.add(agent);
		}

	}

	public void playOneRound() {

	}

	public void play() {

	}

	public Boolean saveAllInformations() {
		return null;
	}

}
