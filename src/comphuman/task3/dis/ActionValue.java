package comphuman.task3.dis;


/**
 * Enums for actionValues. 
 * negative = -1;
 * positive =  1; 
 * neutral  =  0;
 * @author Michael Czaja
 *
 */
public enum ActionValue {
	negative(-1),positiv(1),neutral(0);
	private int value;
	private ActionValue(int value) {
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}
