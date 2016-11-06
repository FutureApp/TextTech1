package humancomp.task1.namingGame;

import java.util.ArrayList;

import javax.sound.midi.Synthesizer;

public class RunnerNamingGame {

		Integer numberOfAgents = 1000;
		Integer numberOfRounds = 100000;
		Integer numberOfRepeats = 3000;

	public static void main(String[] args) {

		ArrayList<String> hello = new ArrayList<>();
		hello.add("hey");
		AgentsOneObject agent = new AgentsOneObject(hello);
		System.out.println(agent.saySomething().getMessage());
		System.out.println(agent.doYouKnow("hey"));
		System.out.println(agent.getAllKnowingWords());
	
	}
}
