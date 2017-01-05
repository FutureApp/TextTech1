package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;
import java.util.UUID;

import org.jsoup.Jsoup;

public class ArtNodeExtractor {

	ArrayList<String> potNodes;
	private String artName;

	public ArtNodeExtractor(ArrayList<String> potNodes, String artname) {
		super();
		this.potNodes = potNodes;
		this.artName = artname;
	}

	public void extractNodes() {
		for (int i = 1; i < potNodes.size(); i++) {
			String section = potNodes.get(i);
			String[] split = section.split("\n");
			Fitter fit = new Fitter(split);
			fit.trimDL();
			fit.trimUL();

			RichExtractor myEx = new RichExtractor(fit.getContent(), artName);
			myEx.extract();
		}
	}

	public Integer countLeft(String con) {
		Integer c = 0;
		for (int i = 0; i < con.length(); i++) {
			if (con.charAt(i) == ' ')
				c++;
			else
				break;
		}
		return c;
	}
}
