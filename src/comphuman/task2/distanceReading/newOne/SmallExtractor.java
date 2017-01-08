package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;
import java.util.UUID;

public class SmallExtractor extends RichExtractor{

	public SmallExtractor(ArrayList<String> content, String artName) {
		super(content, artName);
	}
	
	public void extract() {
		extractArtRoot();
		extractDisRoot();
		extractPosts();
	}
	
	@Override
	protected void extractDisRoot() {
		nodeList.add(new Node(artName, artName, "x", "x"));
	}
	
	@Override
	protected void extractPosts() {
		super.extractPosts();
	}
}
