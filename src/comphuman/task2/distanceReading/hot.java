package comphuman.task2.distanceReading;

public class hot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ab = "title=\"Benutzer Diskussion:DonPedro71\">Diskussion</a>) 17:05, 9. Aug. 2016 (CEST)";
		String regexWikiGermanDateFormate = "[0-2][0-9]:[0-9][0-9], [0-3]*[0-9]. [a-zA-Z]{3}. [0-9][0-9][0-9][0-9].*";
		for (int i = 0; i < ab.length(); i++) {
			String a= ab.substring(i);
			System.out.println(a.matches(regexWikiGermanDateFormate));
			if(a.matches(regexWikiGermanDateFormate)){
				System.out.println(a);
				break;
			}
		}
	}

}
