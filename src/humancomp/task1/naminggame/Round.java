package humancomp.task1.naminggame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.security.auth.callback.LanguageCallback;

import java.util.Random;

public class Round {

	Integer numOfAgents;
	Integer numOfStages;
	Integer successfullComps;
	Map<Integer, Integer> globalWords;
	Random randome = new Random();
	
	Integer myGlobalWords =0;
	Integer myGlobalUWords=0;
	Integer round = 0;

	public Round(Integer numOfAgents, Integer numOfStages) {
		super();
		this.numOfAgents = numOfAgents;
		this.numOfStages = numOfStages;
		this.globalWords = new HashMap<Integer, Integer>();
		this.successfullComps = 0;
	}

	public void startStages() {
		ArrayList<SingleObjectAgents> listOfAgents = new ArrayList<>();
		for (int i = 0; i < numOfAgents; i++) {
			listOfAgents.add(new SingleObjectAgents(i));
		}

		for (int i = 0; i < numOfStages; i++) {
			Integer fAgentIndex = randome.nextInt(listOfAgents.size());
			Integer sAgentIndex = randome.nextInt(listOfAgents.size());
			while (fAgentIndex == sAgentIndex)
				sAgentIndex = randome.nextInt(listOfAgents.size());

			SingleObjectAgents fAgent = listOfAgents.get(fAgentIndex);
			SingleObjectAgents sAgent = listOfAgents.get(sAgentIndex);
			// communications between agents starts
			Integer word = fAgent.saySomething();
			Boolean wasKnowing = sAgent.doYouKnow(word);
			if (wasKnowing)
				successfullComps++;
			System.out.print(i + " " + fAgent.ID + ": " + word + "  ");
			System.out.print(sAgent.ID + ": " + wasKnowing + " ");

			ArrayList<SingleObjectAgents> smallListOfAgents = new ArrayList<>();
			smallListOfAgents.add(fAgent);
			smallListOfAgents.add(sAgent);
			updateWordsInSystem(smallListOfAgents);
			calcGlobalWords();
			// System.out.println("GW: " + globalCountWords);
			round++;
		}

		checkResults(listOfAgents);
	}

	private void checkResults(ArrayList<SingleObjectAgents> listOfAgents) {

		Integer unWords = 0;
		Integer words = 0;
		HashMap<Integer, Integer> counts = new HashMap<>();

		for (SingleObjectAgents singleObjectAgents : listOfAgents) {
			ArrayList<Integer> wordsList = singleObjectAgents.getWordPool();
			for (Integer integer : wordsList) {
				if (counts.containsKey(integer)) {
					words++;
				} else {
					counts.put(integer, 1);
					unWords++;
					words++;
				}
			}

		}

		System.out.println("GLOBAL WORDS: "+words);
		System.out.println("U GLOBAL WORDS: "+unWords);
	}

	private void calcGlobalWords() {
		Integer globalCountsWord = 0;
		Integer globalUniqueCountsWord = 0;

		for (Entry<Integer, Integer> entry : globalWords.entrySet()) {
			globalUniqueCountsWord++;
			globalCountsWord += entry.getValue();
			// System.out.println(entry.getKey()+" "+entry.getValue());
		}
		System.out.print(" GW: " + globalCountsWord);
		System.out.print(" GUW: " + globalUniqueCountsWord);
		System.out.println();

	}

	private void updateWordsInSystem(ArrayList<SingleObjectAgents> smallListOfAgents) {
		
		
		for (SingleObjectAgents agent : smallListOfAgents) {
			ArrayList<Integer> agentPrevList = agent.getBeforeList();
			ArrayList<Integer> agentActualList = agent.getWordPool();
			for (Integer word : agentActualList) {
				if (globalWords.containsKey(word)) {
					globalWords.put(word, (globalWords.get(word) + 1));
				} else {
					globalWords.put(word, 1);
				}
			}

			for (Integer word : agentPrevList) {
				if (globalWords.containsKey(word)) {
					globalWords.put(word, (globalWords.get(word) - 1));
				} else {
					System.err.println("Word should exits");
				}
			}
		}
	}

}
