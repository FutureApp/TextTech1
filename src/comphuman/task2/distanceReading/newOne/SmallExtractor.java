package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;
import java.util.UUID;

public class SmallExtractor extends RichExtractor {

	/**
	 * Representes the extractor-class for poor content. Use this if your
	 * sections doesn't contain the 'mw-headline' attribute.
	 * 
	 * @param content Section where to finde some Information
	 * @param artName Name of the article
	 */
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
		nodeList.add(new Node(artName, artName, "", ""));
	}

	@Override
	protected void extractPosts() {
		super.extractPosts();
	}
}
