package comphuman.task2.distanceReading;

import xgeneral.modules.SystemMessage;

public class ErrorHandling {

	public static void SectionProblem(String string) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SystemMessage.wMessage("Section<"+string+">Doesn't flows");
		
	}

}
