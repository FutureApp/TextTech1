package humancomp.task1.naminggame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Round {

	Integer numOfAgents;
	Integer numOfStages;
	Integer curStage;
	Integer roundID;
	Map<Integer, Integer> globalWords;
	Random randome = new Random();
	TupleRound tRound = new TupleRound();

	int globalCountsWord = 0, globalUniqueCountsWord = 0;
	int goodCommunicatioons, badCommunications, maxCommunications;

	/**
	 * Inits. a Round
	 * 
	 * @param numOfAgents
	 *            Contains number of agents you want to play with.
	 * @param numOfStages
	 *            Pass number of stages per round.
	 * @param roundID
	 *            Pass an ID for the round.
	 */
	public Round(Integer numOfAgents, Integer numOfStages, Integer roundID) {
		super();
		this.curStage = 0;
		this.numOfAgents = numOfAgents;
		this.numOfStages = numOfStages;
		this.globalWords = new HashMap<Integer, Integer>();
		this.roundID = roundID;
		this.goodCommunicatioons = 0;
		this.badCommunications = 0;
		this.maxCommunications = numOfStages;
	}

	/**
	 * Starts the stages of the naming game. The stages are played iterativ.
	 * stage 1 > stage 2 >... >Stage n.
	 */
	public void startStages() {
		// generates list of agents. This agent are playing the game at least.
		ArrayList<SingleObjectAgents> listOfAgents = new ArrayList<>();
		for (int i = 0; i < numOfAgents; i++) {
			listOfAgents.add(new SingleObjectAgents(i));
		}

		// play stages step by step till finish
		for (int i = 0; i < numOfStages; i++) {
			curStage++;
			Integer fAgentIndex = randome.nextInt(listOfAgents.size());
			Integer sAgentIndex = randome.nextInt(listOfAgents.size());
			while (fAgentIndex == sAgentIndex)
				sAgentIndex = randome.nextInt(listOfAgents.size());

			// select 2 agents randomly.
			SingleObjectAgents fAgent = listOfAgents.get(fAgentIndex);
			SingleObjectAgents sAgent = listOfAgents.get(sAgentIndex);
			// communications between agents starts
			Integer word = fAgent.saySomething();
			insertIntoGlobalList(word);
			ArrayList<Integer> dropedWordsByAgentTwo = sAgent.doYouKnow(word);
			updateGlobalInformations(dropedWordsByAgentTwo);
			calcGlobalWords();
			// printCalcs(roundID, i);
			saveStage();
		}
		// checkResults(globalCountsWord, globalUniqueCountsWord, listOfAgents);
	}

	/**
	 * Save the status for a stage. The status tuple will be added to the round
	 * tuple as one item.
	 */
	private void saveStage() {
		TupleStage tStage = new TupleStage(curStage, globalCountsWord, globalUniqueCountsWord, goodCommunicatioons,
				badCommunications);
		tRound.add(tStage);
	}

	/**
	 * Checks if the the current values of words and unique words are matching
	 * with a the clac of these values on the hard way-> Go throw every agent.
	 * This is very time expansive.
	 * 
	 * @param globalWord
	 *            Best if this value represent the current count of words in the
	 *            system.
	 * @param globalUniqueWord
	 *            Best if this value represent the current count of unique words
	 *            in the system.
	 * @param listOfAgents
	 *            List of involved agents.
	 */
	@SuppressWarnings("unused")
	private void checkResults(int globalWord, int globalUniqueWord, ArrayList<SingleObjectAgents> listOfAgents) {
		int localCountsWords = 0, localUniqueCountsWord = 0;

		// Calcs the (true) values of global words and unique words on the hard
		// way.
		HashMap<Integer, Integer> counts = new HashMap<>();
		for (SingleObjectAgents singleObjectAgents : listOfAgents) {
			ArrayList<Integer> wordsList = singleObjectAgents.getWordPool();
			for (Integer integer : wordsList) {
				localCountsWords++;
				if (!counts.containsKey(integer)) {
					counts.put(integer, 0);
				}
			}
		}
		localUniqueCountsWord = counts.size();
		// If values of the calcs doesn't match, stop the further clacs and show
		// error message.
		if (!(localCountsWords == globalWord) || !(localUniqueCountsWord == globalUniqueCountsWord)) {
			System.out.println();
			System.out.println("-------------------------");
			System.out.println("localCoutsWords == globalWord");
			System.out.println(localCountsWords + "==" + globalWord);
			System.out.println("localUniqueCountsWord == globalUniqueWord");
			System.out.println(localUniqueCountsWord + "==" + globalUniqueWord);
			System.err.println("*************");
			System.exit(1);
		}

	}

	/**
	 * Prints the current status/values of GW(Count over all words) and
	 * UGW(Count over all unique words)
	 * 
	 * @param roundID
	 *            The ID of the given round.
	 * @param curStage
	 *            The stage of the current stage in the current round.
	 */
	@SuppressWarnings("unused")
	private void printCalcs(Integer roundID, Integer curStage) {
		System.out.println(
				roundID + "-" + curStage + 1 + " " + "GW:" + globalCountsWord + " " + "UGW:" + globalUniqueCountsWord);
	}

	/**
	 * Removes all counts of given keys. If a key contains now the value 0, when
	 * the key will be removed from the list.
	 * 
	 * @param beforeList
	 *            List for the procedure.
	 */
	private void updateGlobalInformations(ArrayList<Integer> beforeList) {
		// If array contains one ore more items, communications need to
		// successfull.If empty, second agents consumes the word.
		if (beforeList.size() == 0)
			badCommunications++;
		else if (beforeList.size() == 1)
			goodCommunicatioons++;
		else if (beforeList.size() > 1)
			goodCommunicatioons++;

		for (Integer wordRemoved : beforeList) {
			if ((globalWords.get(wordRemoved) - 1) <= 0) {
				globalWords.remove(wordRemoved);
			} else
				globalWords.put(wordRemoved, (globalWords.get(wordRemoved) - 1));
		}

	}

	/**
	 * Inserts one word to the list of global words. If key exits,then
	 * (key,value+1) If the key doesn't exit then insert (key,2). Word is new
	 * for first agent and second agent, so the word will be append in both
	 * agent lists (logical implicit).
	 * 
	 * @param word
	 *            Specific word to be insert into the global list.
	 */
	private void insertIntoGlobalList(Integer word) {
		if (globalWords.containsKey(word))
			globalWords.put(word, globalWords.get(word) + 1);
		else
			globalWords.put(word, 2);
	}

	/**
	 * Calcs. the values for GW and UGW.
	 * 
	 * @see printCalcs
	 */
	private void calcGlobalWords() {
		globalCountsWord = 0;
		globalUniqueCountsWord = 0;
		for (Entry<Integer, Integer> entry : globalWords.entrySet()) {
			globalUniqueCountsWord++;
			globalCountsWord += entry.getValue();
			// System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}

	/**
	 * Returns all played stages in this round. In other word the Tuple of the
	 * round.
	 * 
	 * @return Tuple of stages of the particular round.
	 */
	public TupleRound getPlayedStages() {
		return tRound;
	}
}
