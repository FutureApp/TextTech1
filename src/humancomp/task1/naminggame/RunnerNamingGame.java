package humancomp.task1.naminggame;

public class RunnerNamingGame {

	public static void main(String[] args) {

		Integer numAgents = 1000;
		Integer numRound = 100;
		Integer numStagesPerRound = 100000;
		String[] arg = args;
		if(args.length==3){
		 numAgents = Integer.parseInt(arg[0]);
		 numRound= Integer.parseInt(arg[1]);
		 numStagesPerRound =Integer.parseInt(arg[2]);
		}
		
		
		NamingGame game = new NamingGame(numAgents, numRound, numStagesPerRound);
		game.startGaming();
	}
}
