package comphuman.task2.distanceReading;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xgeneral.modules.SystemMessage;

public class HotRunner {
	static File outputFile = new File("CompHuman/Task2/test/temp.txt");
	static WikiCharFormatterUNICODE wikiMapper = WikiCharFormatterUNICODE.getInstance();
	// Sections
	static Set<String> disHeaderType = new HashSet<>();
	static ArrayList<String> disHeaderText = new ArrayList<>();
	static ArrayList<String> disHeaderTypeAsArray = new ArrayList<>();
	static HashMap<String, ArrayList<Element>> sectionsMap = new HashMap<>();
	
	 //Mappings
	static HashMap<String, ArrayList<WikiNodeDiscussion>> topicMapTopicDiscussion = new HashMap<>();
	

	public static void main(String[] args) {
		outputFile.delete();
		Document doc = URLReader.getContentOf("https://de.wikipedia.org/wiki/Diskussion:Lindentunnel");
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
		investigateNodesSmart();
	}

	private static void investigateNodesSmart() {
		sectionsMap.keySet().forEach(key ->{
			ArrayList<Element> contentOfHeaders = sectionsMap.get(key);
			ArrayList<WikiNodeDiscussion> wikinodes = new ArrayList<>();
			String content = new String();
			for (Element elem : contentOfHeaders) {
				content = content + elem.toString();
			}
			IterativNode iNode = new IterativNode(key, content);
			ArrayList<WikiNodeDiscussion> wikiCommentsToTopic = iNode.hot();
			
			if(!topicMapTopicDiscussion.containsKey(key)){
				topicMapTopicDiscussion.put(key, wikiCommentsToTopic);
			}
			else{
				SystemMessage.eMessage("Found multi contentlists for one topic!");
			}
			
		});
		System.out.println();
		topicMapTopicDiscussion.forEach((key,content) ->{
			System.out.println(key);
			System.out.println(content.size());
		});
	}

	/**
	 * Abstracts and batches the content.
	 * 
	 * @param nodes
	 *            Nodes,which containing the content.
	 */
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

	/**
	 * Returns the truly base-URL.
	 * 
	 * @param URI
	 *            URL which contains a base-URL.
	 * @return The real BASE-URL
	 */
	private static String getRealBasis(String URI) {
		return URI.substring(0, URI.replace("//", "$").indexOf('/') + 1);
	}

}
