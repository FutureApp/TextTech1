package comphuman.task2.distanceReading;

import java.util.ArrayList;
import java.util.UUID;

import xgeneral.modules.SystemMessage;


public class SectionIterator {
	private String content;
	private String father;
	private ExtractorGermanWiki ex;

	String terminator = UUID.randomUUID()+"";
	public SectionIterator(String currentFather, String content) {
		super();
		this.ex = new ExtractorGermanWiki();
		this.father = currentFather;
		this.content = content;
	}

	public ArrayList<WikiNodePost> hot() {
		ArrayList<String> fatherNodes = new ArrayList<>();
		ArrayList<WikiNodePost> wikiNodes = new ArrayList<>();
		fatherNodes.add(father);

		ArrayList<String> contentSplitted = mysplit(content);
		Integer activeIndex = 0;
		String activeContent = new String("");
		String activeUsername = new String(terminator);
		String activeCreationDate = new String(terminator);

		Boolean readyToPush = false;
		big: for (String contentFrag : contentSplitted) {
			activeContent += contentFrag;
			if (ex.containsSomethingLikeUserName(contentFrag)) {
				activeUsername = ex.findUserName(contentFrag);
			}
			if (ex.containsSomethigLikeCreationDate(contentFrag)) {
				activeCreationDate = ex.findCreationDate(contentFrag);
				readyToPush = true;
			}

			if (contentFrag.contains("<dl>")) {
				if(wikiNodes.size() - 1 < 0){
					SystemMessage.wMessage("Section<"+fatherNodes.get(0)+"> will be ignored. The content of the section uses a format which isn't known.");
					System.exit(1);
					break big;
				}else{
					fatherNodes.add(wikiNodes.get(wikiNodes.size() - 1).getNodeName());
					activeIndex++;
				}
			}
			if (contentFrag.contains("</dl>")) {
				readyToPush=true;
				fatherNodes.remove(fatherNodes.size()-1);
				activeIndex--;
			}
			if (readyToPush) {
				// Push node only if not similar to default values.
				if (activeCreationDate.compareTo(terminator) != 0 || activeUsername.compareTo(terminator) != 0) {
					System.out.println("PUSHING");
					System.out.println("USER-Name: " + activeUsername);
					System.out.println("DATE: " + activeCreationDate);
					WikiNodePost wikiNode = new WikiNodePost(fatherNodes.get(activeIndex), activeContent,
							activeUsername, activeCreationDate);
					wikiNodes.add(wikiNode);
				}
				readyToPush = false;
				activeContent = new String("");
				activeCreationDate = terminator;
				activeUsername = terminator;
			}
		}
		System.out.println("-------");
		System.out.println(fatherNodes.size());
		for (WikiNodePost node : wikiNodes) {
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
