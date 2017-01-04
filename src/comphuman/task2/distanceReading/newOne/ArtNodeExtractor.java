package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.NodeVisitor;

public class ArtNodeExtractor {

	ArrayList<String> potNodes;

	public ArtNodeExtractor(ArrayList<String> potNodes) {
		super();
		this.potNodes = potNodes;
	}

	public void extractNodes() {

		// First entry contains garbage.
		for (int i = 1; i < potNodes.size(); i++) {
			String section = potNodes.get(i);
			String disHeader = extractDisHeader(section);
			hot(section);
			break;
		}
	}

	private String extractDisHeader(String section) {
		String disHeader = Jsoup.parse(section).getElementsByClass("mw-headline").text();
		return disHeader;
	}

	public void hot(String section) {
		for (int i = 1; i < section.split("\n").length; i++) {
			String line = section.split("\n")[i];
			System.out.println(line);
			countLeft(line);
		}
	}

	public void countLeft(String con) {
	Integer c =0;
	for (int i = 0; i < con.length(); i++) {
		if(con.charAt(i) == ' ') c++;
		else break;
	}
	System.out.println(c);
	}
}
