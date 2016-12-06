package humancomp.task1.naminggame;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

@SuppressWarnings("deprecation")
public class NamingGame {
	Integer numAgents, numRounds, numStages;
	TupleNamingGameMediumSize tNamingGameMedium;
	Boolean isPrinting = false;

	public NamingGame(Integer numAgents, Integer numRounds, Integer numStages) {
		super();
		this.numAgents = numAgents;
		this.numRounds = numRounds;
		this.numStages = numStages;
		this.tNamingGameMedium = new TupleNamingGameMediumSize(numRounds, numStages);
	}

	public void startGaming() {

		// Will play all round which given stage range.
		for (int i = 1; i <= numRounds; i++) {
			System.out.printf("(%d,%d) curRound/maxRounds", i, numRounds);
			System.out.println();
			Round round = new Round(numAgents, numStages, i);
			round.startStages();
			TupleRound tRound = round.getPlayedStages();
			tNamingGameMedium.updateNamingGameStages(tRound);
		}

		// Save the results of the naming game.
		isPrinting = true;
		File resultFile = new File("Results/NamingGame/results.csv");
		startPrintingAnimation(resultFile);
		saveResults(resultFile, tNamingGameMedium);
		isPrinting = false;
	}

	/**
	 * Saves the particular round.
	 * 
	 * @param file
	 *            Location where the results should be saved.
	 * @param tNamingGameMedium2
	 *            The game which contain the results.
	 */
	private void saveResults(File file, TupleNamingGameMediumSize tNamingGameMedium2) {
		FileUtils.deleteQuietly(file);
		try {
			String header = "StageID AVGwords AVGuwords successful notSuccessful";
			FileUtils.write(file, header + System.lineSeparator(), "UTF-8", true);
			for (TupleStageAdvance stage : tNamingGameMedium2.getAllTuples()) {
				String result = stage.returnInformationAsString().replace("(", "").replace(")", "")
						.replace("|", " ");
				FileUtils.write(file, result + System.lineSeparator(), "UTF-8", true);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void oldSavve(File file, TupleNamingGameSmallSize tNamingGame2) {
		FileUtils.deleteQuietly(file);
		for (int i = 0; i < numStages; i++) {
			String data = tNamingGame2.getAVGofGlobalWordsInTheSystem(i) + " ";
			try {
				FileUtils.write(file, data + System.lineSeparator(), "UTF-8", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Starts the animation of the writing process.
	 * 
	 * @param resultFile
	 *            The absolute path of this file will be printed at least.
	 *            Normally this should be the dir or file where the results are
	 *            placed.
	 */
	private void startPrintingAnimation(File resultFile) {
		/*
		 * Animation source code under :
		 * http://stackoverflow.com/questions/852665/command-line-progress-bar-
		 * in-java
		 */
		new Thread(new Runnable() {
			public void run() {
				String anim = "|/-\\";
				while (isPrinting) {
					try {
						for (int x = 0; x < 100; x++) {
							String data = "\r" + "Writing results to hard-disk " + anim.charAt(x % anim.length()) + " ";
							System.out.write(data.getBytes());
							Thread.sleep(100);
						}
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println();
				System.out.println("Task complete!");
				System.out.println("Results under > " + resultFile.getAbsolutePath());
			}
		}).start();
	}
}
