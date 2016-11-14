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

		for (int i = 1; i <= numRounds; i++) {
			System.out.printf("(%d,%d)", i, numRounds);
			System.out.println();
			Round round = new Round(numAgents, numStages, i);
			round.startStages();
		}
	}
}
