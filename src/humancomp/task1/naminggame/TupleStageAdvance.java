package humancomp.task1.naminggame;

import java.util.Formatter;

public class TupleStageAdvance {
	private int stageID;
	private double totalNumberOfWords;
	private double totalNumberOfUWords;
	private double successfullComs;
	private double nonSuccessfullComs;

	public TupleStageAdvance(int stageID, double totalNumberOfWords, double totalNumberOfUWords,
			double goodCommunicatioons, double badCommunications) {
		super();
		this.stageID = stageID;
		this.totalNumberOfWords = totalNumberOfWords;
		this.totalNumberOfUWords = totalNumberOfUWords;
		this.successfullComs = goodCommunicatioons;
		this.nonSuccessfullComs = badCommunications;
	}

	public void updateBy(TupleStage ts, Integer rounds) {
		if(stageID!= ts.getStageID()) {
			System.err.println("Mixed up stages");
			System.exit(1);
		}
		stageID = ts.getStageID();
		totalNumberOfWords += ts.getTotalNumberOfWords() / ((double) rounds);
		totalNumberOfUWords += ts.getTotalNumberOfUWords() / ((double) rounds);
		successfullComs += ts.getSuccessfullComs() / ((double) rounds);
		nonSuccessfullComs += ts.getNonSuccessfullComs() / ((double) rounds);

	}

	public void showInformations() {
		System.out.printf("(%d|%f|%f|%f|%f)", stageID, totalNumberOfWords, totalNumberOfUWords, successfullComs,
				nonSuccessfullComs);
		System.out.println();
	}

	public String returnInformationAsString() {
		String result = String.format("(%d|%f|%f|%f|%f)", stageID, totalNumberOfWords, totalNumberOfUWords, successfullComs,
				nonSuccessfullComs);
		return result;
	}
	public double getTotalNumberOfWords() {
		return totalNumberOfWords;
	}

	public double getTotalNumberOfUWords() {
		return totalNumberOfUWords;
	}

	public double getSuccessfullComs() {
		return successfullComs;
	}

	public double getNonSuccessfullCons() {
		return nonSuccessfullComs;
	}

	public double getSuccessfullCons() {
		return successfullComs;
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
