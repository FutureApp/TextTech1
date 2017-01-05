package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;

public class ArtNodeExtractor {

	ArrayList<String> potNodes;
	private String artName;
	ArrayList<ArrayList<Node>> allNodesAllSections;
	public ArtNodeExtractor(ArrayList<String> potNodes, String artname) {
		super();
		this.potNodes = potNodes;
		this.artName = artname;
		allNodesAllSections = new ArrayList<>();
	}

	public void extractNodes() {
		for (int i = 1; i < potNodes.size(); i++) {
			String section = potNodes.get(i);
			String[] split = section.split("\n");
			
			// Normalized the content.
			Fitter fit = new Fitter(split);
			fit.trimDL();
			fit.trimUL();
			fit.trimSMALL();
			fit.trimI();
			// start of node extraction-
//			System.out.println(fit.getContent());
			RichExtractor myEx = new RichExtractor(fit.getContent(), artName);
			myEx.extract();
			ArrayList<Node> nodeList = myEx.getNodeList();
			allNodesAllSections.add(nodeList);
		}
	}

	/**
	 * Returns all nodes over all sections.
	 * @return List of List, which contains all nodes from a art-dis.
	 */
	public ArrayList<ArrayList<Node>> getAllNodesAllSections() {
		return allNodesAllSections;
	}
}
