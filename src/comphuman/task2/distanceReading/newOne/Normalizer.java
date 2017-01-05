package comphuman.task2.distanceReading.newOne;

public class Normalizer {
	
	public static String normalizeDateForFileName(String dateToNormalize) {
		return dateToNormalize.replace(".", "").replace(":","").replace(",", "").replace(" ", "");
	}
}
