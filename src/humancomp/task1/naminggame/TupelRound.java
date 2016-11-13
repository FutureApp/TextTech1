package humancomp.task1.naminggame;

public class TupelRound {
	Integer successFullComs;
	Integer nonSuccessFullComs; 
	Integer countWordsInTheSystem;
	Integer countUniqueWordInTheSystem;
	public TupelRound(Integer successFullComs, Integer nonSuccessFullComs, Integer wordsInTheSystem,
			Integer uniqueWordInTheSystem) {
		super();
		this.successFullComs = successFullComs;
		this.nonSuccessFullComs = nonSuccessFullComs;
		this.countWordsInTheSystem = wordsInTheSystem;
		this.countUniqueWordInTheSystem = uniqueWordInTheSystem;
	}
	public Integer getSuccessFullComs() {
		return successFullComs;
	}
	public Integer getNonSuccessFullComs() {
		return nonSuccessFullComs;
	}
	public Integer getWordsInTheSystem() {
		return countWordsInTheSystem;
	}
	public Integer getUniqueWordInTheSystem() {
		return countUniqueWordInTheSystem;
	}
}
