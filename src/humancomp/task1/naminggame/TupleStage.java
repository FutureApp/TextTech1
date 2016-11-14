package humancomp.task1.naminggame;

public class TupleStage {
	private int totalNumberOfWords;
	private int totalNumberOfUWords;
	private int successfullCons;
	private int nonSuccessfullCons;
	private int maxNumOfCommunications;


	public TupleStage(int totalNumberOfWords, int totalNumberOfUWords, int successfullCons, int nonSuccessfullCons) {
		super();
		this.totalNumberOfWords = totalNumberOfWords;
		this.totalNumberOfUWords = totalNumberOfUWords;
		this.successfullCons = successfullCons;
		this.nonSuccessfullCons = nonSuccessfullCons;
		this.maxNumOfCommunications=-1;
	}

	public TupleStage(int globalCountsWord, int globalUniqueCountsWord, int goodCommunicatioons, int badCommunications,
			int maxCommunications) {
		this.totalNumberOfWords = globalCountsWord;
		this.totalNumberOfUWords = globalUniqueCountsWord;
		this.successfullCons = goodCommunicatioons;
		this.nonSuccessfullCons = badCommunications;
		this.maxNumOfCommunications= maxCommunications;

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

	public int getNonSuccessfullCons() {
		return nonSuccessfullCons;
	}

	public int getMaxNumOfCommunications() {
		return maxNumOfCommunications;
	}
}
