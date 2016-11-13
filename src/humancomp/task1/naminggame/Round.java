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
	Integer globalWordCount = 0;
	Integer globalUniqueCount = 0;
	Random randome = new Random();

	public Round(Integer numOfAgents, Integer numOfStages) {
		super();
		this.numOfAgents = numOfAgents;
		this.numOfStages = numOfStages;
		this.globalWords = new HashMap<Integer, Integer>();
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
			if(wasKnowing) successfullComps++;
			System.out.print(i + " " + fAgent.ID + ": " + word + "  ");
			System.out.println(sAgent.ID + ": " + wasKnowing);


			calcWordsInSystem(listOfAgents);
		}
	}

	public void calcWordsInSystem(ArrayList<SingleObjectAgents> listOfAgents) {
		reset();
		for (SingleObjectAgents singleObjectAgents : listOfAgents) {
			ArrayList<Integer> wordpool = singleObjectAgents.getWordPool();
			for (Integer integer : wordpool) {
				if (globalWords.containsKey(integer)) {
					globalWords.put(integer, globalWords.get(integer) + 1);
					globalWordCount++;
				} else {
					globalWords.put(integer, 1);
					globalWordCount++;
					globalUniqueCount++;
				}
			}
		}
		System.out.print("Real Global words count: " + globalWordCount + " ");
		System.out.println("Real Global unique words: " + globalUniqueCount);
	}

	private void reset() {
		globalUniqueCount=0;
		globalWordCount=0;
		globalWords.clear();
		
	}

}
