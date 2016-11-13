package humancomp.task1.naminggame;

public class RunnerNamingGame {

		static Integer numberOfAgents = 1000;
		static Integer numberOfRounds = 3000;
		static Integer numberOfStagesPerRound = 100000;

	public static void main(String[] args) {
		NamingGameSingleObject namingGame = new NamingGameSingleObject(numberOfAgents, numberOfRounds, numberOfStagesPerRound);
		namingGame.play();
	}
}
