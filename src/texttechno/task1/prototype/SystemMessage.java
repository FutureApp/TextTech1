package texttechno.task1.prototype;

public class SystemMessage {
	
	public static void eMessage(String textOfMessage){
		String prefix="ERROR - ";
		String message = prefix+textOfMessage;
		System.err.println(message);
	}
	public static void wMessage(String textOfMessage){
		String prefix="WARNING - ";
		String message = prefix+textOfMessage;
		System.err.println(message);
	}
	public static void allArguments(String arg[]){
		for (int i = 0; i < arg.length; i++) {
			eMessage("Argument "+ (i+1)+" <"+arg[i]+">");
			
		}
	}
	
	public static void messageForArgument(String arg[], Integer pos ,String message){
			wMessage("Argument "+ (pos+1)+" <"+arg[pos]+">");
			wMessage(message);
			
	}
	
}
