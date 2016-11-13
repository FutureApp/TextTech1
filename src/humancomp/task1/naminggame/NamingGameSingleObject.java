package humancomp.task1.naminggame;

import java.util.Random;

import xgeneral.modules.SystemMessage;

public class NamingGameSingleObject {
	Integer numberOfAgents;
	Integer numberOfRounds;
	Integer numberOfStagesPerRound;
	Random randome;
	
	public NamingGameSingleObject(Integer numberOfAgents, Integer numberOfRounds, Integer numberOfStagesPerRound) {
		super();
		this.numberOfAgents = numberOfAgents;
		this.numberOfRounds = numberOfRounds;
		this.numberOfStagesPerRound = numberOfStagesPerRound;
		this.randome = new Random();
		checkIfInputsAreCorrect();
	}
	
	
	public void play() {
		
		for (int i = 0; i < numberOfRounds; i++) {
			RoundSingleObject round = new RoundSingleObject(numberOfAgents,numberOfStagesPerRound);
			round.playStages();
			System.out.println(i+1 +" / "+numberOfRounds);
		}
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
		if (numberOfStagesPerRound <= 0) {
			SystemMessage.wMessage(
					"Number of repeats per round is to low. The game needs at least 1 repeations per round. Give repeatations <"
							+ numberOfStagesPerRound + ">");
			falseCounter++;
		}
		if (falseCounter != 0) {
			SystemMessage.eMessage("Programm will terminate. Input parameter are invalide.");
			System.exit(1);
		}

	}





	
}
