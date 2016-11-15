package humancomp.task1.naminggame;

import java.util.ArrayList;

public class TupleNamingGameMediumSize {

	Integer rounds,stagesPerRound;
	ArrayList<TupleStageAdvance> namingGameStages;
	public TupleNamingGameMediumSize(Integer rounds, Integer stagesPerRound) {
		super();
		this.rounds = rounds;
		this.stagesPerRound = stagesPerRound;
		this.namingGameStages = new ArrayList<>();
		for (int i = 0; i < stagesPerRound; i++) {
			namingGameStages.add(new TupleStageAdvance(i+1,0, 0, 0, 0));
		}
	}

	public void updateNamingGameStages(TupleRound tRound) {
		
		for (int i = 0; i < tRound.getStageTuples().size(); i++) {
			TupleStageAdvance tsg = namingGameStages.get(i);
			TupleStage ts = tRound.getStageTuples().get(i);
			tsg.updateBy(ts,rounds);
		}
	}
	
	public void showNamingGamingStages() {
		for (TupleStageAdvance tsa : namingGameStages) {
			tsa.showInformations();
		}
	}
	public ArrayList<TupleStageAdvance> getAllTuples() {
		return namingGameStages;
	}
	
}
