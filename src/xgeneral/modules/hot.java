package xgeneral.modules;

public class hot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(MyMathLib.choose(10d, 2d));
		System.out.println(MyMathLib.round(10.236d, 2));

		Integer funf = 5; 
		Double funfD = (double) funf;
		System.out.println(funfD);
		
		Double log = Math.log(8d)/Math.log(2);
		System.out.println("log "+log);
	}

}
