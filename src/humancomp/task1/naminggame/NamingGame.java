package humancomp.task1.naminggame;

public class NamingGame {
	Integer numAgents, numRounds, numStages;
	TupleNamingGameSmallSize tNamingGame;

	public NamingGame(Integer numAgents, Integer numRounds, Integer numStages) {
		super();
		this.numAgents = numAgents;
		this.numRounds = numRounds;
		this.numStages = numStages;
		tNamingGame = new TupleNamingGameSmallSize();
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

		for (int i = 0; i < numStages; i++) {
			int stageNumber = i+1;
			float avgTotalWords = tNamingGame.getAVGofGlobalWordsInTheSystem(i);
			float avgTotalUWords = tNamingGame.getAVGofGlobalUWordsInTheSystem(i);
			float successfull =tNamingGame.getAVGofSuccessfullComunications(i);
			float uncessfull =tNamingGame.getAVGofNonSuccessfullComunications(i);
			System.out.println(stageNumber+ " "+avgTotalWords);
			System.out.println(stageNumber+ " "+avgTotalUWords);
			System.out.println(stageNumber+ " "+successfull);
			System.out.println(stageNumber+ " "+uncessfull);
			
		}
	}
}
