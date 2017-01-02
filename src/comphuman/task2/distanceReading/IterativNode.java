package comphuman.task2.distanceReading;

import java.util.ArrayList;
import java.util.HashMap;

public class IterativNode {
	private String content;
	private String father;
	private Extractor ex;

	public IterativNode(String currentFather, String content) {
		super();
		this.ex = new Extractor();
		this.father = currentFather;
		this.content = content;
	}

	public void SysoElement() {
		System.err.println(content.toString());
	}

	public ArrayList<WikiNodeDiscussion> hot() {
		ArrayList<String> fatherNodes = new ArrayList<>();
		ArrayList<WikiNodeDiscussion> wikiNodes = new ArrayList<>();
		fatherNodes.add(father);

		ArrayList<String> contentSplitted = mysplit(content);
		Integer activeIndex = 0;
		String activeContent = new String("");
		String activeUsername = new String("");
		String activeCreationDate = new String("");

		Boolean readyToPush = false;
		for (String contentFrag : contentSplitted) {
			activeContent += contentFrag;
			if (ex.containsSomethingLikeUserName(contentFrag)) {
				activeUsername = ex.findUserName(contentFrag);
			}
			if (ex.containsSomethigLikeCreationDate(contentFrag)) {
				activeCreationDate = ex.findCreationDate(contentFrag);
				readyToPush = true;
			}

			if (contentFrag.contains("<dl>")) {
				fatherNodes.add(wikiNodes.get(wikiNodes.size() - 1).getNodeName());
				activeIndex++;
			}
			if (contentFrag.contains("</dl>")) {
				readyToPush=true;
				fatherNodes.remove(fatherNodes.size()-1);
				activeIndex--;
			}
			if (readyToPush) {
				
				System.out.println("PUSHING");
				System.out.println("USER-Name: " + activeUsername);
				System.out.println("DATE: " + activeCreationDate);
				WikiNodeDiscussion wikiNode = new WikiNodeDiscussion(fatherNodes.get(activeIndex), activeContent, activeUsername,
						activeCreationDate);
				wikiNodes.add(wikiNode);
				readyToPush = false;
				activeContent = new String("");
			}
		}
		System.out.println("-------");
		System.out.println(fatherNodes.size());
		for (WikiNodeDiscussion node : wikiNodes) {
			System.out.println();
			System.out.println(node.fatherNodeName);
			System.out.println(node.nodeName);
			System.out.println(node.contentCreatedUsername);
			System.out.println(node.contentCreatedDate);
			
		}
		return wikiNodes;
	}

	private ArrayList<String> mysplit(String content2) {
		ArrayList<String> list = new ArrayList<>();
		String temp = new String();
		for (int i = 0; i < content2.length(); i++) {
			if (content2.charAt(i) == '<') {
				list.add(temp);
				temp = new String("<");
			} else if (content2.charAt(i) == '>') {
				temp += '>';
				list.add(temp);
				temp = new String();
			} else {
				temp += content2.charAt(i);
			}

		}
		return list;
	}

}
