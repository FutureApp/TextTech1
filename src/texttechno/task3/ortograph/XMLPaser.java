package texttechno.task3.ortograph;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLPaser {
	public static void main(String[] args){

	      try {	
	         File inputFile = new File("C:/Users/admin/git/TextTech1/CompHuman/Task3/ressources/brief1_02.tei");
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" 
	            + doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("student");
	         System.out.println("----------------------------");
	         System.out.println(nList.getLength());
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	        	 
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	}
