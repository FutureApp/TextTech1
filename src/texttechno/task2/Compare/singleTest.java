package texttechno.task2.Compare;

public class singleTest {
	
	public static void print() {
		Properties_SingleTone prop = Properties_SingleTone.getInstance();
		
		System.out.println("Hello " +prop.getCompare());
	}

}
