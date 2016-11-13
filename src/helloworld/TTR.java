package helloworld;

import java.util.ArrayList;
import java.util.List;

import texttechno.task1.GeneralAndTTR_MTTR.MATTRCalculator;

public class TTR {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		
		MATTRCalculator calc = new MATTRCalculator(list, 5);
		calc.calcMATTR();
	}

}
