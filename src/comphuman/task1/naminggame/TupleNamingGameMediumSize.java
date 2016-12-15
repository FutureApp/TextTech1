package comphuman.task1.naminggame;

import java.util.ArrayList;

public class TupleNamingGameMediumSize {

	Integer rounds, stagesPerRound;
	ArrayList<TupleStageAdvance> namingGameStages;

	/**
	 * Should be used to play a naming game. You will update and in this manner
	 * calc. the AVG for every played round. Only on this way, you can play the
	 * game with a huge amount of agents,round and stages.
	 * 
	 * @param rounds
	 *            Represents the number of round which will be played. This
	 *            value is important to calc. the AVG. All given vales of stages
	 *            will be divided by this number.
	 * @param stagesPerRound
	 *            Represents the number of stages which are played each round.
	 */
	public TupleNamingGameMediumSize(Integer rounds, Integer stagesPerRound) {
		super();
		this.rounds = rounds;
		this.stagesPerRound = stagesPerRound;
		this.namingGameStages = new ArrayList<>();
		for (int i = 1; i <= stagesPerRound; i++) {
			namingGameStages.add(new TupleStageAdvance(i, rounds, 0, 0, 0, 0));
		}
	}

	/**
	 * Updates the status of all containing stages. This based on passed new
	 * round.
	 * 
	 * @param tRound
	 *            Round and the stages which should influence the current status
	 *            of played stagess.
	 */
	public void updateNamingGameStages(TupleRound tRound) {

		for (int i = 0; i < tRound.getStageTuples().size(); i++) {
			TupleStageAdvance tsg = namingGameStages.get(i);
			TupleStage ts = tRound.getStageTuples().get(i);
			tsg.updateBy(ts, rounds);
		}
	}

	/**
	 * Prints all the stages step by step. More or less used for debugging.
	 */
	public void showNamingGamingStages() {
		for (TupleStageAdvance tsa : namingGameStages) {
			tsa.showInformations();
		}
	}

	/**
	 * Returns all the stages which this data type contains.
	 * 
	 * @return All played stages(AVG calc).
	 */
	public ArrayList<TupleStageAdvance> getAllTuples() {
		return namingGameStages;
	}

}
