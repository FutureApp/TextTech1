package comphuman.task2.distanceReading;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xgeneral.modules.SystemMessage;

public class HotRunner {
	static File outputFile = new File("CompHuman/Task2/test/temp.txt");
	static WikiCharFormatterUNICODE wikiMapper = WikiCharFormatterUNICODE.getInstance();

	static String root;
	// Sections
	static Set<String> disHeaderType = new HashSet<>();
	static ArrayList<String> disHeaderText = new ArrayList<>();
	static ArrayList<String> disHeaderTypeAsArray = new ArrayList<>();
	static HashMap<String, ArrayList<Element>> sectionsMap = new HashMap<>();

	// Mappings
	static HashMap<String, ArrayList<WikiNodeDiscussion>> topicMapTopicDiscussion = new HashMap<>();

	public static void main(String[] args) {
		String URL = "https://de.wikipedia.org/wiki/Lindentunnel";
		root = URL.split("/")[URL.split("/").length - 1];
		System.out.println("ROOT-" + root);
		outputFile.delete();
		Document doc = URLReader.getContentOf(URL);
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
		sectionsMap.keySet().forEach(key -> {
			ArrayList<Element> contentOfHeaders = sectionsMap.get(key);
			ArrayList<WikiNodeDiscussion> wikinodes = new ArrayList<>();
			String content = new String();
			for (Element elem : contentOfHeaders) {
				content = content + elem.toString();
			}
			IterativNode iNode = new IterativNode(key, content);
			ArrayList<WikiNodeDiscussion> wikiCommentsToTopic = iNode.hot();

			if (!topicMapTopicDiscussion.containsKey(key)) {
				topicMapTopicDiscussion.put(key, wikiCommentsToTopic);
			} else {
				SystemMessage.eMessage("Found multi contentlists for one topic!");
			}

		});
		System.out.println();
		topicMapTopicDiscussion.forEach((key, content) -> {
			System.out.println(key);
			System.out.println(content.size());
		});

		visTheResults();

	}

	private static void visTheResults() {
		String RED = Color.red.toString();
		String AQUAMARINE = javafx.scene.paint.Color.AQUAMARINE.toString();
		String AZURE = javafx.scene.paint.Color.AZURE.toString();
		String BEIGE = javafx.scene.paint.Color.BEIGE.toString();
		String biggerSize = "size: 20px, 20px;";

		
		Graph graph = new MultiGraph("Dicussions");
		graph.addAttribute("ui.stylehseet", "url('http://www.deep.in/the/site/mystylesheet')");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.setStrict(false);
		graph.setAutoCreate(true);
		Viewer view = graph.display(true);
		view.enableAutoLayout();

		graph.addNode(root).setAttribute("label", root);
		graph.addNode(root).addAttribute("ui.style", "size: 15px, 20px;");
		// Do some work ...

		for (Entry<String, ArrayList<WikiNodeDiscussion>> entry : topicMapTopicDiscussion.entrySet()) {
			String key = entry.getKey();
			ArrayList value = entry.getValue();
			graph.addNode(key).setAttribute("label", "Topic:" + key);
			graph.addEdge(root + key, root, key);

		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		topicMapTopicDiscussion.forEach((key, content) -> {
			content.forEach((wikiNode) ->{
				graph.addEdge(UUID.randomUUID()+"", wikiNode.fatherNodeName, wikiNode.nodeName);
				graph.addNode(wikiNode.nodeName).addAttribute("ui.style", "fill-color: '"+BEIGE+"';");
				String randomeUUID = UUID.randomUUID()+"";
				graph.addNode(wikiNode.contentCreatedDate+randomeUUID).addAttribute("label", wikiNode.contentCreatedDate);
				graph.addNode(wikiNode.contentCreatedUsername+randomeUUID).addAttribute("label", wikiNode.contentCreatedUsername);
				
				graph.addNode(wikiNode.contentCreatedDate+randomeUUID).addAttribute("ui.style", "fill-color: '"+AQUAMARINE+"';");
				graph.addNode(wikiNode.contentCreatedDate+randomeUUID).addAttribute("ui.style", "shape: box;");
				graph.addNode(wikiNode.contentCreatedUsername+randomeUUID).addAttribute("ui.style", "fill-color: '"+AZURE+"';");
				graph.addNode(wikiNode.contentCreatedUsername+randomeUUID).addAttribute("ui.style", "shape: diamond;");
				graph.addNode(wikiNode.contentCreatedUsername+randomeUUID).addAttribute("ui.style", "stroke-color: blue;");
				
				
				
				graph.addEdge(UUID.randomUUID()+"", wikiNode.nodeName, wikiNode.contentCreatedDate+randomeUUID);
				graph.addEdge(UUID.randomUUID()+"", wikiNode.nodeName, wikiNode.contentCreatedUsername+randomeUUID);
			});
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
