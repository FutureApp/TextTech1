package humancomp.task1.naminggame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RoundSingleObject {

	ArrayList<Integer> wordsInTheSystem;
	Set<Integer> uniqueWordInTheSystem;

	ArrayList<TupelRound> stages;
	Integer numberOfAgents, numberOfStagesPerRound, nonSuccessFullComs, successFullComs, countWordsInTheSystem,
			countUniqueWordInTheSystem;
	Random randome;
	ArrayList<AgentsOneObject> agentsList;

	public RoundSingleObject(Integer numberOfAgents, Integer numberOfStagesPerRound) {
		this.successFullComs = 0;
		this.nonSuccessFullComs = 0;
		this.randome = new Random();
		this.stages = new ArrayList<>();
		this.numberOfAgents = numberOfAgents;
		this.numberOfStagesPerRound = numberOfStagesPerRound;
		this.agentsList = new ArrayList<>();
		this.countWordsInTheSystem = 0;
		this.countUniqueWordInTheSystem = 0;
		this.wordsInTheSystem = new ArrayList<>();
		this.uniqueWordInTheSystem = new HashSet<>();
		for (int i = 0; i < numberOfAgents; i++) {
			this.agentsList.add(new AgentsOneObject());
		}
	}

	public void playStages() {

		for (int i = 0; i < numberOfStagesPerRound; i++) {
			Integer firstAgentIndex = randome.nextInt(numberOfAgents);
			Integer secondAgentIndex = randome.nextInt(numberOfAgents);
			// Prevents that first agent and second agent are the same agents.
			while (firstAgentIndex == secondAgentIndex)
				secondAgentIndex = randome.nextInt(numberOfAgents);

			// Agents are talking to each other
			AgentsOneObject firstAgent = agentsList.get(firstAgentIndex);
			AgentsOneObject secondAgent = agentsList.get(secondAgentIndex);

			if (secondAgent.doYouKnow(firstAgent.saySomething().getMessage()))
				successFullComs++;
			else
				nonSuccessFullComs++;
			calcUniqueWordInTheSystem();
			saveStage();
		}
	}

	private void calcUniqueWordInTheSystem() {
		uniqueWordInTheSystem.clear();
		wordsInTheSystem.clear();
		for (AgentsOneObject agent : agentsList) {
			for (int i = 0; i < agent.getAllKnowingWords().size(); i++) {
				Integer word = Integer.getInteger(agent.getAllKnowingWords().get(i));
				uniqueWordInTheSystem.add(word);
				wordsInTheSystem.add(word);
			}
		}
		countUniqueWordInTheSystem = uniqueWordInTheSystem.size();
		countWordsInTheSystem = wordsInTheSystem.size();
	}

	public void saveStage() {
		TupelRound stage = new TupelRound(successFullComs, nonSuccessFullComs, countWordsInTheSystem,
				countUniqueWordInTheSystem);
		stages.add(stage);
	}

}
