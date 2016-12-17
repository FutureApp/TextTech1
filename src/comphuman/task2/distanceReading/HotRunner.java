package comphuman.task2.distanceReading;

import org.jsoup.nodes.Document;

public class HotRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc = URLReader.getContentOf("https://de.wikipedia.org/wiki/Lindentunnel");
		String realBasis = getRealBasis(doc.baseUri());
		String getURLOFDiscussion = doc.select("#ca-talk>span>a").attr("href");
		Document dis = URLReader.getContentOf(getURLOFDiscussion);
		System.out.println(dis.toString());
		
	}

	private static String getRealBasis(String baseUri) {
		System.out.println(baseUri.substring(0, baseUri.replace("//", "$").indexOf('/')+1));
		return null;
	}

}
