package humancomp.task1.naminggame;

import java.util.ArrayList;
import java.util.Random;

import xgeneral.modules.SystemMessage;

public class NamingGame {
	Integer numberOfAgents;
	Integer numberOfRounds;
	Integer numberOfRepeats;
	Random randome;

	ArrayList<AgentsOneObject> listOfAgents;
	Integer currentRound = 1;
	Integer currentNumberOfRepeats = 1;

	Integer communications = 1;
	
	ArrayList<String> diffrentNamePerRound = new ArrayList<>();
	ArrayList<Integer> diffrentSuccCouterPerRound = new ArrayList<>();
	private int successCouter;
	private int norSuccessCounter;

	public NamingGame(Integer numberOfAgents, Integer numberOfRounds, Integer numberOfRepeats) {
		super();
		this.numberOfAgents = numberOfAgents;
		this.numberOfRounds = numberOfRounds;
		this.numberOfRepeats = numberOfRepeats;
		this.randome = new Random();
		checkIfInputsAreCorrect();
		initGame();
	}

	private void checkIfInputsAreCorrect() {
		Integer falseCounter = 0;
		if (numberOfAgents <= 1) {
			SystemMessage.wMessage("Number of Agents is to low. The game needs at least 2 agents. Given agents <"
					+ numberOfAgents + ">");
			falseCounter++;
		}
		if (numberOfRounds <= 0) {
			SystemMessage.wMessage("Number of rounds is to low. The game need at least 1 round. Given rounds to play <"
					+ numberOfRounds + ">");
			falseCounter++;
		}
		if (numberOfRepeats <= 0) {
			SystemMessage.wMessage(
					"Number of repeats per round is to low. The game needs at least 1 repeations per round. Give repeatations <"
							+ numberOfRepeats + ">");
			falseCounter++;
		}
		if (falseCounter != 0) {
			SystemMessage.eMessage("Programm will terminate. Input parameter are invalide.");
			System.exit(1);
		}

	}

	public void initGame() {
		createListOfAgents();
		successCouter = 0;
		norSuccessCounter = 0;
	}

	private void createListOfAgents() {
		listOfAgents = new ArrayList<>();
		for (int i = 0; i < numberOfAgents; i++) {
			AgentsOneObject agent = new AgentsOneObject();
			listOfAgents.add(agent);
		}

	}

	public Boolean commicateBetweenTwoAgents() {
		Integer firstAgentIndex = randome.nextInt(listOfAgents.size());
		Integer secondAgentIndex = randome.nextInt(listOfAgents.size());
		while (firstAgentIndex == secondAgentIndex) {
			secondAgentIndex = randome.nextInt(listOfAgents.size());
		}
		AgentsOneObject firstAgent = listOfAgents.get(firstAgentIndex);
		AgentsOneObject secondAgent = listOfAgents.get(secondAgentIndex);
		return secondAgent.doYouKnow(firstAgent.saySomething().getMessage()); 
	}

	public void playOneRound() {
		
		while (communications <= numberOfRounds) {
			Boolean resultOfCom = commicateBetweenTwoAgents();

			if(resultOfCom) 	successCouter++;
			else       	    	norSuccessCounter++;
			
			communications++;
		}
		currentNumberOfRepeats++;

	}
	
	public void play() {
		
		while(currentNumberOfRepeats<=numberOfRepeats){
			System.out.println(currentNumberOfRepeats +" >"+ numberOfRepeats);
			playOneRound();
			System.out.println(successCouter +" /"+norSuccessCounter);
			resetGame();
		}
	}

	private void resetGame() {
		communications = 1;
		initGame();
		
	}

	public Boolean saveAllInformations() {
		return null;
	}

}
