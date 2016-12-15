package comphuman.task1.naminggame;

public class RunnerNamingGame {

	public static void main(String[] args) {

		Integer numAgents = 1000;
		Integer numRound = 3000;
		Integer numStagesPerRound = 1000000;
		String[] arg = args;

		
		if (args.length <= 2)
			printHelp();
		if (args.length == 3) {
			numAgents = Integer.parseInt(arg[0]);
			numRound = Integer.parseInt(arg[1]);
			numStagesPerRound = Integer.parseInt(arg[2]);
		}

		NamingGame game = new NamingGame(numAgents, numRound, numStagesPerRound);
		game.startGaming();
	}

	private static void printHelp() {
		System.out.println("--- Usage ---");
		System.out.println();
		System.out.println("java -jar HCtask1_1.jar #agents #rouds #stagesPerRound");
		System.out.println();
		System.out.println("Example > java -jar HCtask1_1.jar 1000 3000 100000");
		System.exit(1);
	}
}
