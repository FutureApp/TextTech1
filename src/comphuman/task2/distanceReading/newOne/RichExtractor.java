package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.print.Doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RichExtractor extends ExtractorGermanWiki {

	private String artName;
	private ArrayList<String> content;
	private ArrayList<Node> nodeList;

	/**
	 * Extractor for nodes to german wiki-dis-page. You need to deliver the dis
	 * in sections.
	 * 
	 * @param content
	 *            List where every list-content contains a section.
	 * @param artName
	 *            Name of the article.
	 */
	public RichExtractor(ArrayList<String> content, String artName) {
		super();
		this.content = content;
		this.artName = artName;
		this.nodeList = new ArrayList<>();
	}

	/**
	 * Start the extraction of need informations.
	 * 
	 */
	public void extract() {
		extractArtRoot();
		extractDisRoot();
		extractPosts();
	}

	/**
	 * Extracts the post from the section.
	 */
	private void extractPosts() {

		String defaultValue = "";
		String autName = new String(defaultValue);
		String date = new String(defaultValue);
		Integer level = 1;
		Node father = nodeList.get(1);
		// System.out.println(father.name);
		ArrayList<Node> fatherNodes = new ArrayList<>();
		// Creates a dummy array for looking up a father-node.
		// You could only have so many father like lines.MAX-Value
		for (int i = 0; i < content.size(); i++) {
			fatherNodes.add(new Node("", "", "", ""));
		}
		// Adds the dis-topic node as the first father-node.
		
		fatherNodes.add(level, father);

		for (int i = 1; i < content.size(); i++) {
			String line = content.get(i);
			/*
			 * Check if line contains user-name Check if line contains
			 * creation-date if both informations are given then create a node
			 * with the given info point to another father (current level > new
			 * active level. create new father based on last node and point to
			 * him.(current level < new active level
			 * 
			 */
			if (containsSomethingLikeUserName(line)) {
				autName = extractName(line);
				// System.out.println("name -- "+autName);
			}
			if (containsSomethigLikeCreationDate(line)) {
				date = findCreationDate(line);
				// System.out.println("date -- "+date);
			}
			if (autName.equals(defaultValue) && date.equals(defaultValue)) {
				// System.out.println("No Push -- ");
			}
			if (!autName.equals(defaultValue) && !date.equals(defaultValue)) {
				// System.out.println("PUSH!");
				Node node = new Node(UUID.randomUUID().toString(), father.getName(), autName, date);
				nodeList.add(node);
				autName = new String(defaultValue);
				date = new String(defaultValue);
			}
			if (level > countLeft(line)) {
				level = countLeft(line);
				father = fatherNodes.get(level);
			}
			if (level < countLeft(line)) {
				level = countLeft(line);
				father = nodeList.get(nodeList.size() - 1);
				fatherNodes.add(level, father);
			}
		}
		if (nodeList.size() < 3) {
			for (String string : content) {
				System.out.println(string);
			}
			System.out.println(content);
			for (Node node : fatherNodes) {
				System.out.println(node.getName());
			}
			for (Node node : nodeList) {
				System.out.println(node.getName());
			}
			System.out.println("achtung");

			// Add one empty Post because a dis-dopic without a post couldn't be.
			// That means: 1- Post not identified  2- Post isn't signed.
			nodeList.add(new Node(UUID.randomUUID().toString(), father.getName(), "", ""));
		}
		// For debugging purpose.
		// showRelation();
	}
	

	/**
	 * Shows the relation between notes.
	 */
	@SuppressWarnings("unused")
	private void showRelation() {
		for (Node node : nodeList) {
			// System.out.println(node.father + " <- " + node.name + " -" +
			// node.aut);
		}

	}

	/**
	 * Extracts a user-name in a given line.
	 * 
	 * @param line
	 *            Line which contains something like a user-name. See
	 *            {@link #containsSomethingLikeUserName(String)}
	 * @return Returns the user-name-
	 */
	private String extractName(String line) {
		String userName = "";
		Document doc = Jsoup.parse(line);
		Elements select = doc.select("a");
		List<Element> elements = new ArrayList<>();
		select.forEach((a) -> elements.add(a));
		// start from behind to det last aut and date.
		Collections.reverse(elements);
		big: for (int i = 0; i < elements.size(); i++) {
			Element elem = elements.get(i);
			String title = elem.attr("title");
			if (title.startsWith("Spezial")) {
				userName = title.split(":")[1].split("/")[1];
			}
			if (title.startsWith("Benut")) {
				userName = title.split(":")[1];
			}
			if (!title.equals(""))
				break big;
		}
		// specific togerman wiki .
		if (userName.contains(" (Seite nicht vorhanden)"))
			userName = userName.substring(0, userName.indexOf(" (Seite nicht vorhanden)"));
		return userName;
	}

	/**
	 * Extracts the article name and pushes,based on the name, the root-node.
	 */
	private void extractArtRoot() {
		System.out.println("artName - "+artName);
		nodeList.add(new Node(artName, artName, "", ""));
	}

	/**
	 * Extracts the root-node.
	 */
	private void extractDisRoot() {
		String disRootName = extractDisRootName(content.get(0));
		Node disRoot = new Node(disRootName, nodeList.get(0).getName(), "", "");
		nodeList.add(disRoot);
	}

	/**
	 * Extracts the discussion section Name.
	 * 
	 * @param line
	 *            Line which contains the discussion section Name.
	 * @return The discussion section Name .
	 */
	private String extractDisRootName(String line) {
		Document doc = Jsoup.parse(line);
		String disRootName = "";
		String lineContainsRootName = doc.text();
		
		if (lineContainsRootName.contains("[Quelltext bearbeiten]")){
			
			disRootName = lineContainsRootName.substring(0, lineContainsRootName.indexOf("[Quelltext bearbeiten]"));
		}
		else{
			disRootName = new String(lineContainsRootName);
		}
		
		if(disRootName.length() <3){
			System.out.println(lineContainsRootName);
			System.out.println("STOP");
			System.exit(1);
		}
		return disRootName;
	}

	/**
	 * Counts white-spaces from left.
	 * 
	 * @param oneLine
	 *            Line where to count.
	 * @return Number of white-spaces until first non-white-space char.
	 */
	public Integer countLeft(String oneLine) {
		Integer c = 0;
		for (int i = 0; i < oneLine.length(); i++) {
			if (oneLine.charAt(i) == ' ')
				c++;
			else
				break;
		}
		return c;
	}

	/**
	 * Returns all extracted nodes from the given section. Before use: Execute
	 * {@link #extract()}
	 * 
	 * @return Returns all extracted nodes.
	 */
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}
}
