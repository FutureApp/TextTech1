package humancomp.task1.naminggame;

public class TupleStage {
	private int stageID;
	private int totalNumberOfWords;
	private int totalNumberOfUWords;
	private int successfullCons;
	private int nonSuccessfullCons;
	private int maxNumOfCommunications;

	public TupleStage(int stageID, int totalNumberOfWords, int totalNumberOfUWords, int goodCommunicatioons, int badCommunications) {
		super();
		this.stageID=stageID;
		this.totalNumberOfWords = totalNumberOfWords;
		this.totalNumberOfUWords = totalNumberOfUWords;
		this.successfullCons = goodCommunicatioons;
		this.nonSuccessfullCons = badCommunications;
		this.maxNumOfCommunications = -1;
	}


	public int getTotalNumberOfWords() {
		return totalNumberOfWords;
	}

	public int getTotalNumberOfUWords() {
		return totalNumberOfUWords;
	}

	public int getSuccessfullComs() {
		return successfullCons;
	}

	public int getNonSuccessfullComs() {
		return nonSuccessfullCons;
	}

	public int getMaxNumOfCommunications() {
		return maxNumOfCommunications;
	}


	public void setSuccessfullCons(int successfullCons) {
		this.successfullCons = successfullCons;
	}

	public void setTotalNumberOfWords(int totalNumberOfWords) {
		this.totalNumberOfWords = totalNumberOfWords;
	}

	public void setTotalNumberOfUWords(int totalNumberOfUWords) {
		this.totalNumberOfUWords = totalNumberOfUWords;
	}

	public void setNonSuccessfullCons(int nonSuccessfullCons) {
		this.nonSuccessfullCons = nonSuccessfullCons;
	}

	public void setMaxNumOfCommunications(int maxNumOfCommunications) {
		this.maxNumOfCommunications = maxNumOfCommunications;
	}

	public int getStageID() {
		return stageID;
	}

}
