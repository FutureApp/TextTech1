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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xgeneral.modules.SystemMessage;

/**
 * Represents the 'main' class to search,extract and vis the information(
 * Main-Page-title, all topic's in the discussion-forum(based on
 * main-page-title), all post to corresponding discussion-topic and
 * Corresponding information(username & creation-date)).
 * 
 * @author mcz
 *
 */
public class GermanWikiArticleDiscussionAnalyzer {
	 private File outputFile = new File("CompHuman/Task2/test/temp.txt");
	// Sections
	 private Set<String> disHeaderType = new HashSet<>();
	 private ArrayList<String> disHeaderText = new ArrayList<>();
	 private ArrayList<String> disHeaderTypeAsArray = new ArrayList<>();
	 private HashMap<String, ArrayList<Element>> sectionsMap = new HashMap<>();
	// Mappings
	 private HashMap<String, ArrayList<WikiNodePost>> topicMapTopicDiscussion = new HashMap<>();
	

	
	// Need Informations
	 private String URL_ToDiscussion;
	 private  String articleName;
	
	 
	public GermanWikiArticleDiscussionAnalyzer(String URL_ToDiscussion, String articleName) {
		super();
		this.URL_ToDiscussion = URL_ToDiscussion;
		this.articleName=articleName;
	}


	/**
	 * Visits the given and abstracts the information. After all, all information get saved and visualized.
	 * @param args Only one parameter is supported -> arg[0] = wikipedia-weblink.
	 * 
	 */
	public void runAnalyses(){
		
//		URL = "https://de.wikipedia.org/wiki/Liste_von_Hallo-Welt-Programmen/H%C3%B6here_Programmiersprachen";
		//Tested with
//		URL = "https://de.wikipedia.org/wiki/Lindentunnel";
		
		//-----

		Document discussionContent = URL_Handler.getContentOf(URL_ToDiscussion);

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
		exploreNodesSmart();
	}

	
	/**
	 * Explores each section and extracts the important informations (user-name,creation-date).
	 */
	private  void exploreNodesSmart() {
		sectionsMap.keySet().forEach(key -> {
			ArrayList<Element> contentOfHeaders = sectionsMap.get(key);
			String content = new String();
			for (Element elem : contentOfHeaders) {
				content = content + elem.toString();
			}
			SectionIterator iNode = new SectionIterator(key, content);
			ArrayList<WikiNodePost> wikiCommentsToTopic = iNode.hot();

			if (!topicMapTopicDiscussion.containsKey(key)) {
				topicMapTopicDiscussion.put(key, wikiCommentsToTopic);
			} else {
				SystemMessage.eMessage("Found multi contentlists for one topic!");
			}

		});
		// Beautifying of section if section exits but contains no entry's. Happens if not signed or due an fail-recognition.
		topicMapTopicDiscussion.forEach((key, content) -> {
			System.out.println(key);
			System.out.println(content.size());
			if (content.size() == 0) {
				ArrayList<WikiNodePost> emptyPost = new ArrayList<>();
				emptyPost.add(new WikiNodePost(key, "", "noUser", "noDate"));
				topicMapTopicDiscussion.put(key, emptyPost);
			}
		});
	}

	
	/**
	 * Abstracts the sections and offers the content for exploration.
	 * 
	 * @param nodes
	 *            Nodes,which containing the content.
	 */
	private  void abstractContentToInvestigate(Elements nodes) {
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
	 * Visualize the informations which are generated by
	 * {@link #exploreNodesSmart()}
	 */
	public  void visTheResults() {
		String RED = Color.red.toString();
		String BLACK = Color.BLACK.toString();
		String GREEN = javafx.scene.paint.Color.DARKGREEN.toString();
		String BLUE = Color.BLUE.toString();
		String BURLYWOOD = javafx.scene.paint.Color.BURLYWOOD.toString();
		String BEIGE = javafx.scene.paint.Color.BEIGE.toString();

		Graph graph = new MultiGraph("Dicussions");
		graph.addAttribute("ui.stylehseet", "url('http://www.deep.in/the/site/mystylesheet')");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.setStrict(false);
		graph.setAutoCreate(true);
		graph.display(true);

		graph.addNode(articleName).setAttribute("label", articleName);
		graph.addNode(articleName).addAttribute("ui.style", "size: 40px, 40px;");
		graph.addNode(articleName).addAttribute("ui.style", "fill-color: '" + RED + "';");
		graph.addNode(articleName).addAttribute("ui.style", "stroke-color: '" + BLACK + "';stroke-width: 10px;");
		// Do some work ...

		for (Entry<String, ArrayList<WikiNodePost>> entry : topicMapTopicDiscussion.entrySet()) {
			String key = entry.getKey();
			graph.addNode(key).setAttribute("label", "Section[ " + key+" ] ");
			graph.addNode(key).addAttribute("ui.style", "fill-color: '" + GREEN + "';");
			graph.addNode(key).addAttribute("ui.style", "size: 30px, 30px;");
			graph.addEdge(articleName + key, articleName, key);

		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		topicMapTopicDiscussion.forEach((key, content) -> {
			content.forEach((wikiNode) -> {
				String randomeUUID = UUID.randomUUID() + "";

				// Node - Post
				graph.addNode(wikiNode.nodeName).addAttribute("ui.style", "fill-color: '" + BLUE + "';");
				graph.addNode(wikiNode.nodeName).addAttribute("ui.style", "size: 20px,20px;");
				graph.addEdge(UUID.randomUUID() + "", wikiNode.fatherNodeName, wikiNode.nodeName);

				if (wikiNode.contentCreatedDate.contains("noDate")) {
					// if no informations then do nothing. Only the post will be
					// visible.
				} else {
					// Node-CreationDate
					graph.addNode(wikiNode.contentCreatedDate + randomeUUID).addAttribute("label",
							wikiNode.contentCreatedDate);
					graph.addNode(wikiNode.contentCreatedDate + randomeUUID).addAttribute("ui.style",
							"fill-color: '" + BEIGE + "';");
					graph.addNode(wikiNode.contentCreatedDate + randomeUUID).addAttribute("ui.style",
							"size: 15px, 15px;");

					// Node-User
					graph.addNode(wikiNode.contentCreatedUsername + randomeUUID).addAttribute("label",
							wikiNode.contentCreatedUsername);
					graph.addNode(wikiNode.contentCreatedUsername + randomeUUID).addAttribute("ui.style",
							"fill-color: '" + BURLYWOOD + "';");
					graph.addNode(wikiNode.contentCreatedUsername + randomeUUID).addAttribute("ui.style",
							"size: 15px, 15px;");

					// Edge between post <-> userName
					graph.addEdge(UUID.randomUUID() + "", wikiNode.nodeName, wikiNode.contentCreatedDate + randomeUUID);
					// Edge between post <-> creationDate
					graph.addEdge(UUID.randomUUID() + "", wikiNode.nodeName,
							wikiNode.contentCreatedUsername + randomeUUID);
				}
			});
		});
	}


	/**
	 * Save the content.
	 * @param string
	 */
	public void saveResults(String string) {
		// TODO Auto-generated method stub
		
	}


}
