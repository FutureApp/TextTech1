package humancomp.task1.naminggame;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TupleStageAdvance {
	private int stageID;
	private double totalNumberOfWords;
	private double totalNumberOfUWords;
	private double successfullComs;
	private double nonSuccessfullComs;
	private int rounds;
	

	public TupleStageAdvance(int stageID, int rounds, double totalNumberOfWords, double totalNumberOfUWords,
			double goodCommunicatioons, double badCommunications) {
		super();
		this.stageID = stageID;
		this.totalNumberOfWords = totalNumberOfWords;
		this.totalNumberOfUWords = totalNumberOfUWords;
		this.successfullComs = goodCommunicatioons;
		this.nonSuccessfullComs = badCommunications;
		this.rounds = rounds;
	}

	public void updateBy(TupleStage ts, Integer rounds) {
		if (stageID != ts.getStageID()) {
			System.err.println("Mixed up stages");
			System.exit(1);
		}
		stageID = ts.getStageID();

		if (totalNumberOfWords > totalNumberOfWords + ts.getTotalNumberOfWords()) {
			System.err.println("Bufferoverflow Total Number of Words");
			System.exit(1);
		}
		if (totalNumberOfUWords > totalNumberOfUWords + ts.getTotalNumberOfUWords()) {
			System.err.println("Bufferoverflow total number of Uwords");
			System.exit(1);
		}
		if (successfullComs > successfullComs + ts.getSuccessfullComs()) {
			System.err.println("Bufferoverflow total number of suc. communications.");
			System.exit(1);
		}
		if (nonSuccessfullComs > nonSuccessfullComs + ts.getNonSuccessfullComs()) {
			System.err.println("Bufferoverflow total number of nonSuc. communications.");
			System.exit(1);
		}

		totalNumberOfWords += ts.getTotalNumberOfWords();
		totalNumberOfUWords += ts.getTotalNumberOfUWords();
		successfullComs += ts.getSuccessfullComs();
		nonSuccessfullComs += ts.getNonSuccessfullComs();

	}

	public void showInformations() {
		System.out.printf("(%d|%f|%f|%f|%f)", stageID, totalNumberOfWords, totalNumberOfUWords, successfullComs,
				nonSuccessfullComs);
		System.out.println();
	}

	public String returnInformationAsString() {
		String result = String.format("(%d|%s|%s|%s|%s)", stageID, getTotalNumberOfWords().toPlainString(),
				getTotalNumberOfUWords().toPlainString(), getSuccessfullComs().toPlainString(),
				getNonSuccessfullCons().toPlainString());
		return result;
	}

	public BigDecimal getTotalNumberOfWords() {
		BigDecimal bigTotalNumberOfWords = BigDecimal.valueOf(totalNumberOfWords);
		BigDecimal bigRounds = BigDecimal.valueOf(rounds);
		return bigTotalNumberOfWords.divide(bigRounds,2,RoundingMode.HALF_UP);
	}

	public BigDecimal getTotalNumberOfUWords() {
		BigDecimal bigTotalNumberOfUWords = BigDecimal.valueOf(totalNumberOfUWords);
		BigDecimal bigRounds = BigDecimal.valueOf(rounds);
		return bigTotalNumberOfUWords.divide(bigRounds,2,RoundingMode.HALF_UP);
	}

	public BigDecimal getSuccessfullComs() {
		BigDecimal bigTotalNumberOfSuccs = BigDecimal.valueOf(successfullComs);
		BigDecimal bigRounds = BigDecimal.valueOf(rounds);
		return bigTotalNumberOfSuccs.divide(bigRounds,2,RoundingMode.HALF_UP);
	}

	public BigDecimal getNonSuccessfullCons() {
		BigDecimal bignonSuccessfullComs = BigDecimal.valueOf(nonSuccessfullComs);
		BigDecimal bigRounds = BigDecimal.valueOf(rounds);
		return bignonSuccessfullComs.divide(bigRounds,2,RoundingMode.HALF_UP);
	}

	public void setSuccessfullCons(double successfullCons) {
		this.successfullComs = successfullCons;
	}

	public void setTotalNumberOfWords(double totalNumberOfWords) {
		this.totalNumberOfWords = totalNumberOfWords;
	}

	public void setTotalNumberOfUWords(double totalNumberOfUWords) {
		this.totalNumberOfUWords = totalNumberOfUWords;
	}

	public void setNonSuccessfullCons(double nonSuccessfullCons) {
		this.nonSuccessfullComs = nonSuccessfullCons;
	}

}
