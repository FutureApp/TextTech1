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
			
			for (int x = 0; x < nodesToTopic.size(); x++) {
				Node node = nodesToTopic.get(x);
				
				// Root ( ArticleName)
				if(x == 0){
					graph.addNode(node.name).setAttribute("label", node.name);
					graph.addEdge(UUID.randomUUID().toString(), node.name, node.father);
					
				}
				// Section of article.
				else if(x == 1){
					
				}
				// Posts
				else{
					
				}
				
				
			}
		}
		graph.display();
	}


	
}
