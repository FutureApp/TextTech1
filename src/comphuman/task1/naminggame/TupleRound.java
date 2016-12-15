package comphuman.task1.naminggame;

import java.util.ArrayList;

public class TupleRound {
	ArrayList<TupleStage> stageTuples;
	
	public TupleRound(ArrayList<TupleStage> stageTuples) {
		super();
		this.stageTuples = stageTuples;
	}
	public TupleRound() {
		super();
		stageTuples = new ArrayList<>();
	}
	public void add(TupleStage tStage){stageTuples.add(tStage);}
	public ArrayList<TupleStage> getStageTuples() {return stageTuples;} 

}
