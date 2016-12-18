package comphuman.task2.distanceReading;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class StackOverflow {
    public static void main(String[] args) {
    	String html = "<div> Blah <p> However </p> </div>";
    	Document doc = Jsoup.parseBodyFragment(html);
    	Element p = doc.select("p").first();

    	String myText = p.parent().text();  //selects the enclosing div, and gets all the text in the div.

    	System.out.println(myText);
    }

    
}