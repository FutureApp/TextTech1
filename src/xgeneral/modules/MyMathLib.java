package xgeneral.modules;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyMathLib {
	

	
	/*http://stackoverflow.com/questions/15301885/calculate-value-of-n-choose-k*/
	public static Double choose(Double n, Double k) {
		Double result = 0d;
		if(k == 0) result=1d;
		else result=(n* choose(n-1, k-1))/k;
		MyMathLib.round(result, 4 );
		return MyMathLib.round(result, 4 );
	}
	
	/*http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places*/
	public static Double round(Double value, Integer places) {
		if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
