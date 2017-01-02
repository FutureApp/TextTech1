package comphuman.task2.distanceReading;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class VisualizeTopicMapTopicDiscussion {
	public void draw() {
		Graph graph = new SingleGraph("Tutorial 1");
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		graph.display();
	}
}

