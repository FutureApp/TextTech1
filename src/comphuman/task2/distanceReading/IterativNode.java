package comphuman.task2.distanceReading;

import java.util.ArrayList;
import java.util.UUID;

import xgeneral.modules.SystemMessage;

public class IterativNode {
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RESET = "\u001B[0m";
	private String content;
	private String father;
	private Extractor2 ex;

	public IterativNode(String currentFather, String content) {
		super();
		this.ex = new Extractor2();
		this.father = currentFather;
		this.content = content;
	}

	public void SysoElement() {
		System.err.println(content.toString());
	}

	public void hot() {
		ArrayList<String> fatherNodes = new ArrayList<>();
		ArrayList<WikiNodeDiscussion> wikiNodes = new ArrayList<>();
		fatherNodes.add(father);

		String[] contentSplitted = content.split("\n");
		Integer activeIndex = 0;
		String activeContent = new String("");
		String activeUsername = new String("");
		String activeCreationDate = new String("");
		Integer opendedTags =0;
		for (int i = 0; i < contentSplitted.length; i++) {
			String currentContent = contentSplitted[i];
			System.out.println(i + " " + currentContent);
			activeContent += currentContent;
			if (ex.containsSomethigLikeCreationDate(currentContent)
					&& ex.containsSomethingLikeUserName(currentContent)) {
				activeCreationDate = ex.findCreationDate(currentContent);
				activeUsername = ex.findUserName(currentContent);
			}
			if (currentContent.contains("<dl>")) {
				opendedTags++;
				WikiNodeDiscussion wikiNode = new WikiNodeDiscussion(fatherNodes.get(activeIndex), activeContent, activeUsername, activeCreationDate);
				if(activeUsername.equals("") || activeCreationDate.equals("")) SystemMessage.wMessage("Node informations not complete");
				wikiNodes.add(wikiNode);
				fatherNodes.add(wikiNode.getNodeName());
				activeContent= new String("");
				activeIndex++;
			}
			else if (currentContent.contains("</dl>")) {
				activeIndex--;
			}
			else if (currentContent.contains("</dd>")){
				System.out.println(opendedTags+"------");
				if(activeContent.replaceAll(" ", "").contains("</dl></dd>")){
					System.out.println("DO NOTHING");
				}else{
					opendedTags--;
					WikiNodeDiscussion wikiNode = new WikiNodeDiscussion(fatherNodes.get(activeIndex), activeContent, activeUsername, activeCreationDate);
					if(activeUsername.equals("") || activeCreationDate.equals("")) SystemMessage.wMessage("Node informations not complete");
					wikiNodes.add(wikiNode);
					activeContent= new String("");
				}
				activeContent = new String("");
			}
			else{
				activeContent += currentContent;
			}
			
			if(wikiNodes.isEmpty()){
				WikiNodeDiscussion wikiNode = new WikiNodeDiscussion(fatherNodes.get(activeIndex), activeContent, activeUsername, activeCreationDate);
				if(activeUsername.equals("") || activeCreationDate.equals("")) SystemMessage.wMessage("Node informations not complete");
				wikiNodes.add(wikiNode);
				activeContent= new String("");
			}
		}
		
		System.out.println(fatherNodes);
		for (WikiNodeDiscussion wikiNode : wikiNodes) {System.out.println();
			System.out.println("Father - "+wikiNode.getFatherNodeName());
			System.out.println("NodeName - "+wikiNode.getNodeName());
			System.out.println("User - "+wikiNode.getContentCreatedUsername());
			System.out.println("Date - "+wikiNode.getContentCreatedDate());
			System.out.println("Content - "+wikiNode.getContent());
		}
	}

	public void hotOld() {
		ArrayList<String> fatherNodes = new ArrayList<>();
		ArrayList<WikiNodeDiscussion> wikiNodes = new ArrayList<>();
		fatherNodes.add(father);

		String[] contentSplitted = conntentSplittAndRemoveFirstDL(content);
		String nodeContent = new String();
		Integer activeIndex = 0;
		Boolean lookUpForUserAndDate = false;

		String activeNodeContent = new String();
		String activeNodeUsername = new String();
		String activeNodeCreationDate = new String();
		for (int i = 0; i < contentSplitted.length; i++) {
			String currContent = contentSplitted[i];
			if (currContent.contains("<dl>")) {
				String nodeId = UUID.randomUUID() + "";
				fatherNodes.add(nodeId);
				activeIndex++;
			} else if (currContent.contains("</dl>")) {
				activeIndex--;
			} else if (currContent.contains("<dd>")) {
				lookUpForUserAndDate = true;
			} else if (currContent.contains("</dd>")) {
				activeNodeContent += currContent;
				WikiNodeDiscussion wikiNode = new WikiNodeDiscussion(fatherNodes.get(activeIndex), activeNodeContent, activeNodeUsername,
						activeNodeCreationDate);
				wikiNodes.add(wikiNode);
				lookUpForUserAndDate = false;
			}
			if (lookUpForUserAndDate) {
				System.out.println("Searching");
				if (ex.containsSomethigLikeCreationDate(currContent))
					activeNodeCreationDate = ex.findCreationDate(currContent);
				if (ex.containsSomethingLikeUserName(currContent))
					activeNodeUsername = ex.findUserName(currContent);
			} else {
				if (!currContent.replaceAll(" ", "").equals(""))
					nodeContent = nodeContent + currContent;
			}
		}

		System.out.println(fatherNodes);
		System.out.println(wikiNodes.size());
	}

	private String[] conntentSplittAndRemoveFirstDL(String content) {
		String[] splittedContent = content.trim().split("\n");
		if (splittedContent[0].contains("<dl>"))
			splittedContent[0] = "";
		if (splittedContent[splittedContent.length - 1].contains("</dl>"))
			splittedContent[splittedContent.length - 1] = "";
		return splittedContent;
	}
}
