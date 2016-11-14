package humancomp.task1.naminggame;

public class RunnerNamingGame {

	public static void main(String[] args) {

		String[] arg = args;
//		Integer numAgents = Integer.parseInt(arg[0]);
//		Integer numRound= Integer.parseInt(arg[1]);
//		Integer numStagesPerRound =Integer.parseInt(arg[2]);
		
		Integer numAgents = 1000;
		Integer numRound = 1;
		Integer numStagesPerRound = 100000;
		
		NamingGame game = new NamingGame(numAgents, numRound, numStagesPerRound);
		game.startGaming();
	}
}
