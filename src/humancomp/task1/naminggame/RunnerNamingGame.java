package humancomp.task1.naminggame;

import java.util.ArrayList;

import javax.sound.midi.Synthesizer;

public class RunnerNamingGame {

		static Integer numberOfAgents = 1000;
		static Integer numberOfRounds = 100000;
		static Integer numberOfRepeats = 3000;

	public static void main(String[] args) {
		NamingGame namingGame = new NamingGame(numberOfAgents, numberOfRounds, numberOfRepeats);
		namingGame.play();
	}
}
