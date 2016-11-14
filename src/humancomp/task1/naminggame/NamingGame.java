package humancomp.task1.naminggame;

public class NamingGame {
	Integer numAgents,numRounds,numStages;
	TupleNamingGame tNamingGame;
	
	public NamingGame(Integer numAgents, Integer numRounds, Integer numStages) {
		super();
		this.numAgents = numAgents;
		this.numRounds = numRounds;
		this.numStages = numStages;
		tNamingGame = new TupleNamingGame();
	}

	public void startGaming() {

		for (int i = 1; i <= numRounds; i++) {
			System.out.printf("(%d,%d)", i, numRounds);
			System.out.println();
			Round round = new Round(numAgents, numStages, i);
			round.startStages();
			TupleRound tRound = round.getPlayedStages();
			tNamingGame.add(tRound);
		}
	}
}
