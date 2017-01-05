package comphuman.task2.distanceReading.newOne;

import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import comphuman.task2.distanceReading.WikiNodePost;

public class VisRichArtDis {
	
	String RED = Color.red.toString();
	String BLACK = Color.BLACK.toString();
	String GREEN = javafx.scene.paint.Color.DARKGREEN.toString();
	String BLUE = Color.BLUE.toString();
	String BURLYWOOD = javafx.scene.paint.Color.BURLYWOOD.toString();
	String BEIGE = javafx.scene.paint.Color.BEIGE.toString();
	String CADETBLUE = javafx.scene.paint.Color.CADETBLUE.toString();
	String ALICEBLUE = javafx.scene.paint.Color.ALICEBLUE.toString();
	String LAVENDER = javafx.scene.paint.Color.BLANCHEDALMOND.toString();
	ArrayList<ArrayList<Node>> listOfListFromNodes;
	

	public VisRichArtDis(ArrayList<ArrayList<Node>> listOfListFromNodes) {
		super();
		this.listOfListFromNodes = listOfListFromNodes;
	}
	
	public void startVizRichDis(){
		Graph graph = new MultiGraph("Dicussions");
		graph.addAttribute("ui.stylehseet", "url('http://www.deep.in/the/site/mystylesheet')");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.setStrict(false);
		graph.setAutoCreate(true);
		graph.display(true);
		
		for (int i = 0; i < listOfListFromNodes.size(); i++) {
			ArrayList<Node> nodesToTopic = listOfListFromNodes.get(i);
			String style = "ui.style";
			for (int x = 0; x < nodesToTopic.size(); x++) {
				Node node = nodesToTopic.get(x);
				String nodeID = UUID.randomUUID().toString();
				
				// Root ( ArticleName)
				if(x == 0){
					graph.addNode(node.name).addAttribute("label", node.name);
					graph.getNode(node.name).addAttribute(style, "fill-color: '" + RED + "';");
				}
				// Section of article.
				else if(x == 1){
					graph.addNode(node.name).addAttribute("label", "["+node.name+"]");
					graph.getNode(node.name).addAttribute(style, "fill-color: '" + GREEN + "';");
					graph.addEdge(nodeID, node.name, node.father);
				}
				// Posts
				else{
					graph.addNode(node.name).addAttribute(style, "fill-color: '" + BLACK + "';");
					graph.addEdge(nodeID, node.name, node.father);
					graph.addNode("user"+nodeID).addAttribute("label", node.aut);
					graph.addNode("user"+nodeID).addAttribute(style, "fill-color: '" + CADETBLUE + "';");
					
					graph.addNode("date"+nodeID).addAttribute("label", node.date);
					graph.addNode("date"+nodeID).addAttribute(style, "fill-color: '" + LAVENDER + "';");
					graph.addEdge(UUID.randomUUID().toString(), node.name, "user"+nodeID);
					graph.addEdge(UUID.randomUUID().toString(), node.name, "date"+nodeID);
				}
			}
		}
		graph.display();
	}


	
}
