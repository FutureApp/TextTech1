package comphuman.task3.dis;

import javafx.scene.paint.Color;

public class CalcColors {
	
	/**
	 * Calcs the interpolation  of the color grey.
	 * @param value Value between -1 and 1.
	 * @return Returns a interpolation of the color grey. 
	 */
	public static Color clacInnerGrey(Double value) {
		Double newValue = (value+1d)/(double)2;
		return Color.gray(newValue);
	}
}
