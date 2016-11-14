package humancomp.task1.naminggame;

public class NamingGame {
	Integer numAgents;
	Integer numRounds;
	Integer numStages;

	public NamingGame(Integer numAgents, Integer numRounds, Integer numStages) {
		super();
		this.numAgents = numAgents;
		this.numRounds = numRounds;
		this.numStages = numStages;
	}

	public void startGaming() {

		for (int i = 0; i < numRounds; i++) {

			Round round = new Round(numAgents, numStages);
			round.startStages();
		}

	}

}
