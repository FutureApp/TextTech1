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
		// First entry contains garbage.
//		for (int i = 1; i < 5; i++) {
		for (int i = 1; i < potNodes.size(); i++) {
			String section = potNodes.get(i);
			String disHeader = extractDisHeader(section);
			extractNode(section);
		}
	}

	private void extractNode(String section) {
		String[] split = section.split("\n");
		Fitter fit = new Fitter(split);
		fit.trimDL();
		fit.trimUL();
		
		RichExtractor myEx = new RichExtractor(fit.getContent(),artName); 
		myEx.extract();
	}

	private Integer detLevel(String line) {
		return countLeft(line);
	}

	private String extractDisHeader(String section) {
		String disHeader = Jsoup.parse(section).getElementsByClass("mw-headline").text();
		return disHeader;
	}

	public void hot(String section) {
		
	}

	public Integer countLeft(String con) {
	Integer c =0;
	for (int i = 0; i < con.length(); i++) {
		if(con.charAt(i) == ' ') c++;
		else break;
	}
	return c;
	}
	
	/*

	 String line = section.split("\n")[i];
			Integer level = detLevel(line);
			System.out.println(line);
			System.out.println(level);
			nodeContent += line;
			if(level == activeLevel){
				System.out.println("GUT");
				if(ex.containsSomethigLikeCreationDate(line)) System.out.println(ex.findCreationDate(line));
				if(ex.containsSomethingLikeUserName(line)) System.out.println(ex.findUserName(line));
				
			}
			if(level > activeLevel){
				System.out.println("Next");
				activeLevel++;
			}
			if(level < activeLevel) {
				System.out.println("prev");
				activeLevel--;
			}
	 */
}
