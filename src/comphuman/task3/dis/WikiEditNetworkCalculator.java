package comphuman.task3.dis;

public class WikiEditNetworkCalculator {

	public static Integer calcActivityIndex(WikiRevisionUser user) {
		Integer adds = user.getPostitivProcesses();
		Integer dels = user.getNegativeProcesses();
		Integer mods = user.getNeutralProcess();
		return adds+dels+mods;
	}
	
	public static Integer calcNetAdded(WikiRevisionUser user) {
		
		return 0;
	}
	
}
