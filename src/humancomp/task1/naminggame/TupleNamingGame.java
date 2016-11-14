package humancomp.task1.naminggame;

import java.util.ArrayList;

public class TupleNamingGame {
	ArrayList<TupleRound> allRounds;

	public TupleNamingGame(ArrayList<TupleRound> allRounds) {
		super();
		this.allRounds = allRounds;
	}
	
	public TupleNamingGame() {
		this.allRounds = new ArrayList<>();
	}
	
	public void add(TupleRound tRound) {allRounds.add(tRound);}

	public float getAVGofGlobalWordsInTheSystem(Integer stage) {
		int wordsOfStage = 0;
		for (TupleRound rounds : allRounds) {
			wordsOfStage += rounds.getStageTuples().get(stage).getTotalNumberOfWords();
		}
		return (float)wordsOfStage/(float)allRounds.size();
	}
	
	public float getAVGofGlobalUWordsInTheSystem(Integer stage) {
		int wordsOfStage = 0;
		for (TupleRound rounds : allRounds) {
			wordsOfStage += rounds.getStageTuples().get(stage).getTotalNumberOfUWords();
		}
		return (float)wordsOfStage/(float)allRounds.size();
	}

	public float getAVGofSuccessfullComunications(Integer stage) {
		int wordsOfStage = 0;
		for (TupleRound rounds : allRounds) {
			wordsOfStage += rounds.getStageTuples().get(stage).getSuccessfullComs();
		}
		return (float)wordsOfStage/(float)allRounds.size();
	}
	
}
