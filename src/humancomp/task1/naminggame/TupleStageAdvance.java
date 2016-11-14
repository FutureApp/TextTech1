package humancomp.task1.naminggame;

public class TupleStageAdvance {
	private float totalNumberOfWords;
	private float totalNumberOfUWords;
	private float successfullCons;
	private float nonSuccessfullCons;
	private float maxNumOfCommunications;

	public TupleStageAdvance(float totalNumberOfWords, float totalNumberOfUWords, float goodCommunicatioons, float badCommunications) {
		super();
		this.totalNumberOfWords = totalNumberOfWords;
		this.totalNumberOfUWords = totalNumberOfUWords;
		this.successfullCons = goodCommunicatioons;
		this.nonSuccessfullCons = badCommunications;
		this.maxNumOfCommunications = -1;
	}

	public TupleStageAdvance(float globalCountsWord, float globalUniqueCountsWord, float goodCommunicatioons, float badCommunications,
			float maxCommunications) {
		this.totalNumberOfWords = globalCountsWord;
		this.totalNumberOfUWords = globalUniqueCountsWord;
		this.successfullCons = goodCommunicatioons;
		this.nonSuccessfullCons = badCommunications;
		this.maxNumOfCommunications = maxCommunications;

	}

	public float getTotalNumberOfWords() {
		return totalNumberOfWords;
	}

	public float getTotalNumberOfUWords() {
		return totalNumberOfUWords;
	}

	public float getSuccessfullComs() {
		return successfullCons;
	}

	public float getNonSuccessfullCons() {
		return nonSuccessfullCons;
	}

	public float getMaxNumOfCommunications() {
		return maxNumOfCommunications;
	}

	public float getSuccessfullCons() {
		return successfullCons;
	}

	public void setSuccessfullCons(float successfullCons) {
		this.successfullCons = successfullCons;
	}

	public void setTotalNumberOfWords(float totalNumberOfWords) {
		this.totalNumberOfWords = totalNumberOfWords;
	}

	public void setTotalNumberOfUWords(float totalNumberOfUWords) {
		this.totalNumberOfUWords = totalNumberOfUWords;
	}

	public void setNonSuccessfullCons(float nonSuccessfullCons) {
		this.nonSuccessfullCons = nonSuccessfullCons;
	}

	public void setMaxNumOfCommunications(float maxNumOfCommunications) {
		this.maxNumOfCommunications = maxNumOfCommunications;
	}

}
