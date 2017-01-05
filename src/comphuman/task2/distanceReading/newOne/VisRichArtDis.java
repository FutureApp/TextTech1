package comphuman.task2.distanceReading.newOne;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Quality;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

public class VisRichArtDis {
	String RED = Color.red.toString();
	String BLACK = Color.BLACK.toString();
	String GREEN = javafx.scene.paint.Color.DARKGREEN.toString();
	String CADETBLUE = javafx.scene.paint.Color.CADETBLUE.toString();
	String BLANCHEDALMOND = javafx.scene.paint.Color.BLANCHEDALMOND.toString();
	ArrayList<ArrayList<Node>> listOfListFromNodes;

	/**
	 * 
	 * @param listOfListFromNodes
	 */
	public VisRichArtDis(ArrayList<ArrayList<Node>> listOfListFromNodes) {
		super();
		this.listOfListFromNodes = listOfListFromNodes;
	}

	public void startVizRichDis(File fileWhereToSaveVis, Boolean showViz) {
		Graph graph = new MultiGraph("Dicussions");
		graph.addAttribute("ui.stylehseet", "url('http://www.deep.in/the/site/mystylesheet')");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.setStrict(false);
		graph.setAutoCreate(true);

		for (int i = 0; i < listOfListFromNodes.size(); i++) {
			ArrayList<Node> nodesToTopic = listOfListFromNodes.get(i);
			String style = "ui.style";
			for (int x = 0; x < nodesToTopic.size(); x++) {
				Node node = nodesToTopic.get(x);
				String nodeID = UUID.randomUUID().toString();

				// Root ( ArticleName)
				if (x == 0) {
					graph.addNode(node.name).addAttribute("label", node.name);
					graph.getNode(node.name).addAttribute(style, "fill-color: '" + RED + "';");
				}
				// Section of article.
				else if (x == 1) {
					graph.addNode(node.name).addAttribute("label", "[" + node.name + "]");
					graph.getNode(node.name).addAttribute(style, "fill-color: '" + GREEN + "';");
					graph.addEdge(nodeID, node.name, node.father);
				}
				// Posts
				else {
					//Empty aut. and date means post not signed or wasn't identified.
					if(node.aut=="" && node.date==""){
						graph.addNode(node.name).addAttribute(style, "fill-color: '" + BLACK + "';");
						graph.addEdge(nodeID, node.name, node.father);
					}else{
						
					graph.addNode(node.name).addAttribute(style, "fill-color: '" + BLACK + "';");
					graph.addEdge(nodeID, node.name, node.father);
					graph.addNode("user" + nodeID).addAttribute("label", node.aut);
					graph.addNode("user" + nodeID).addAttribute(style, "fill-color: '" + CADETBLUE + "';");

					graph.addNode("date" + nodeID).addAttribute("label", node.date);
					graph.addNode("date" + nodeID).addAttribute(style, "fill-color: '" + BLANCHEDALMOND + "';");
					graph.addEdge(UUID.randomUUID().toString(), node.name, "user" + nodeID);
					graph.addEdge(UUID.randomUUID().toString(), node.name, "date" + nodeID);
					}
				}
			}
		}
		// show graph or not.
		if(showViz) graph.display(true);
		
		FileSinkImages pic = new FileSinkImages(OutputType.JPG, Resolutions.HD720);
		pic.setAutofit(true);
		pic.setQuality(Quality.HIGH);
		pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		try {
			pic.writeAll(graph, fileWhereToSaveVis.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
