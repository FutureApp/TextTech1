package comphuman.task2.distanceReading;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import xgeneral.modules.SystemMessage;

public class URLReader {
	
	public static Document getContentOf(String URL) {
		Document doc = null;
		try {
			 doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(doc == null) SystemMessage.eMessage("Couldn't establish connection to <"+URL+">");
		return doc;
	}
}
