package comphuman.task2.distanceReading;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HotRunner {
	static File outputFile = new File("CompHuman/Task2/test/temp.txt");
	static WikiCharFormatterUNICODE wikiMapper = WikiCharFormatterUNICODE.getInstance();
	// Sections
	static Set<String> disHeaderType = new HashSet<>();
	static ArrayList<String> disHeaderText = new ArrayList<>();
	static ArrayList<String> disHeaderTypeAsArray = new ArrayList<>();
	static HashMap<String, ArrayList<Element>> sectionsMap = new HashMap<>();

	public static void main(String[] args) {
		outputFile.delete();
		Document doc = URLReader
				.getContentOf("https://de.wikipedia.org/wiki/Diskussion:Lindentunnel#Unterschiede_zu_luise-berlin.de");
		String realBasis = getRealBasis(doc.baseUri());

		String getURLOFDiscussion = doc.select("#ca-talk>span>a").attr("href");
		Document discussionContent = URLReader.getContentOf(realBasis + getURLOFDiscussion);

		Elements headers = discussionContent.select("span[class=mw-headline]");
		headers.forEach((a) -> {

			disHeaderType.add(a.parentNode().nodeName());
			disHeaderText.add(a.text());
		});

		Object[] arrayOfObjects = disHeaderType.toArray();
		for (Object object : arrayOfObjects) {
			disHeaderTypeAsArray.add(object.toString());
		}

		// Prepare for exec
		Elements nodes = discussionContent.select("*");
		if (disHeaderTypeAsArray.size() != 0) {

			for (String selector : disHeaderTypeAsArray) {
				nodes = discussionContent.select(selector + ":has(span[class=mw-headline])").before("<start>").get(0)
						.siblingElements();
			}
		}

		System.err.println(disHeaderTypeAsArray);
		abstractContentToInvestigate(nodes);
		investigateNodes();

	}

	private static void investigateNodes() {
		ArrayList<WikiDisContribution> contributions = new ArrayList<>();
		ArrayList<Element> dasd = sectionsMap.get("Unterschiede zu luise-berlin.de");
		ArrayList<String> idHolder = new ArrayList<>();
		Integer layer = 0;
		WikiDisContribution currContri = new WikiDisContribution(System.nanoTime() + "");
		for (int i = 0; i < dasd.size(); i++) {
			Element toInvest = dasd.get(i);
			/*
			 * <a href="/wiki/Benutzerin:44Pinguine"
			 * title="Benutzerin:44Pinguine"> 44pinguine <span
			 * class="hintergrundfarbe9">☕</span> </a> 18:25, 17. Dez. 2016
			 * (CET)
			 */
			
		}
	}

	private static void abstractContentToInvestigate(Elements nodes) {
		String currentSectionKey = "null";
		Integer counterOfNodeLabels = 0;
		ArrayList<Element> sectionContent = new ArrayList<>();
		Boolean firstTimeStart = true;
		Boolean appendNodes = false;
		Boolean ignoreFirstNode = false;

		for (Element node : nodes) {
			if (node.toString().equals("<start></start>")) {
				if (firstTimeStart) {
					currentSectionKey = disHeaderText.get(counterOfNodeLabels);
					counterOfNodeLabels++;
					firstTimeStart = false;
					appendNodes = true;
				} else {
					sectionsMap.put(currentSectionKey, sectionContent);
					sectionContent = new ArrayList<>();
					currentSectionKey = disHeaderText.get(counterOfNodeLabels);
					counterOfNodeLabels++;
					ignoreFirstNode = true;
				}
			} else {
				if (appendNodes) {
					if (ignoreFirstNode)
						ignoreFirstNode = false;
					else
						sectionContent.add(node);
				}
			}
		}
		sectionsMap.put(currentSectionKey, sectionContent);
		// DEBUG
		sectionsMap.forEach((a, b) -> {
			String toWrite = "";
			toWrite = toWrite + a + " %%%" + b + "$";
			try {
				FileUtils.write(outputFile, toWrite, "UTF-8", true);
				FileUtils.write(outputFile, System.lineSeparator(), "UTF-8", true);
				FileUtils.write(outputFile, System.lineSeparator(), "UTF-8", true);
				FileUtils.write(outputFile, System.lineSeparator(), "UTF-8", true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static String getRealBasis(String baseUri) {
		return baseUri.substring(0, baseUri.replace("//", "$").indexOf('/') + 1);
	}

}
