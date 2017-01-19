package comphuman.task3.dis;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.scene.paint.Color;

public class ExportToVizToolByte {

	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder;
	Document doc;
	String KEYWORD_WEIGHT = "weight";
	private ArrayList<WikiEditNetworkNode> setOfNodesForExport;

	/**
	 * Creates the export-class. Exports in a GraphMl format. See
	 * {@link http://graphml.graphdrawing.org/}
	 * 
	 * @param editNodes
	 *            List of Nodes containing WikiEditNetworkNode objects.
	 *            {@link WikiEditNetworkNode}
	 */
	public ExportToVizToolByte(ArrayList<WikiEditNetworkNode> editNodes) {
		// TODO Auto-generated constructor stub
		this.setOfNodesForExport = editNodes;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();
	}

	public void exportToGraphMlFormate(File locationToSave) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("graphml");
			rootElement.setAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
			rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElement.setAttribute("xsi:schemaLocation",
					"http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
			doc.appendChild(rootElement);

			Element keyNodeColor = doc.createElement("key");
			keyNodeColor.setAttribute("id", "nodeKeyColor");
			keyNodeColor.setAttribute("for", "node");
			keyNodeColor.setAttribute("attr.name", "color");
			keyNodeColor.setAttribute("attr.type", "string");
			rootElement.appendChild(keyNodeColor);
			
			Element keyNodeLabel = doc.createElement("key");
			keyNodeLabel.setAttribute("id", "nodeKeyLabel");
			keyNodeLabel.setAttribute("for", "node");
			keyNodeLabel.setAttribute("attr.name", "label");
			keyNodeLabel.setAttribute("attr.type", "string");
			rootElement.appendChild(keyNodeLabel);
//			
			Element keyNodeHeigh = doc.createElement("key");
			keyNodeHeigh.setAttribute("id", "nodeKeyHeigh");
			keyNodeHeigh.setAttribute("for", "node");
			keyNodeHeigh.setAttribute("attr.name", "heigh");
			keyNodeHeigh.setAttribute("attr.type", "double");
			rootElement.appendChild(keyNodeHeigh);
//			
			Element keyNodeWeight = doc.createElement("key");
			keyNodeWeight.setAttribute("id", "nodeKeyWeight");
			keyNodeWeight.setAttribute("for", "node");
			keyNodeWeight.setAttribute("attr.name", KEYWORD_WEIGHT);
			keyNodeWeight.setAttribute("attr.type", "double");
			rootElement.appendChild(keyNodeWeight);
			
			Element keyPath = doc.createElement("key");
			keyPath.setAttribute("id", "path");
			keyPath.setAttribute("for", "edge");
			keyPath.setAttribute("attr.name", KEYWORD_WEIGHT);
			keyPath.setAttribute("attr.type", "double");
			rootElement.appendChild(keyPath);

			
			
			Element graphElement = doc.createElement("graph");
			graphElement.setAttribute("id", "G-Graph-SimpleEditNetwork");
			graphElement.setAttribute("edgedefault", "undirected");
			rootElement.appendChild(graphElement);

			
			Element defElem = doc.createElement("default");
			defElem.setTextContent((Color.BLANCHEDALMOND+"").replace("0x", "#"));
			keyNodeColor.appendChild(defElem);

			
			// THE NODES
			for (WikiEditNetworkNode wikiEditNetworkNode : setOfNodesForExport) {
				String id = wikiEditNetworkNode.getUserName();
				String label = wikiEditNetworkNode.getUserName();
				Element exportNode = doc.createElement("node");
				exportNode.setAttribute("id", id.replace(" ", "_").replaceAll("[-+!^,]",""));
				exportNode.setAttribute("label", label);
				graphElement.appendChild(exportNode);
				
				Element dataKeyColor = doc.createElement("data");
				dataKeyColor.setAttribute("key", "nodeKeyColor");
				if(wikiEditNetworkNode.userRole.equals("aut")){
					dataKeyColor.setTextContent(Color.DARKRED.toString().replace("0x", "#"));
				}
				else if(wikiEditNetworkNode.userRole.equals("cur")){
					dataKeyColor.setTextContent(Color.BLANCHEDALMOND.toString().replace("0x", "#"));
				}
				else if(wikiEditNetworkNode.userRole.equals("last")){
					dataKeyColor.setTextContent(Color.DARKSLATEGREY.toString().replace("0x", "#"));
				}

				Element dataKeyLabel = doc.createElement("data");
				dataKeyLabel.setAttribute("key", "nodeKeyLabel");
				dataKeyLabel.setTextContent(label);
				

				Element dataKeyHeigh = doc.createElement("data");
				dataKeyHeigh.setAttribute("key", "nodeKeyHeigh");
				dataKeyHeigh.setTextContent((double)wikiEditNetworkNode.nodeHigh+"");
//				
				Element dataKeyWeight = doc.createElement("data");
				dataKeyWeight.setAttribute("key", "nodeKeyWeight");
				dataKeyWeight.setTextContent((double)wikiEditNetworkNode.nodeWeight+"");
//				
				exportNode.appendChild(dataKeyColor);
				exportNode.appendChild(dataKeyLabel);
				exportNode.appendChild(dataKeyHeigh);
				exportNode.appendChild(dataKeyWeight);
			}
			
			//THE EDGES
			for (WikiEditNetworkNode wikiEditNetworkNode : setOfNodesForExport) {
				String id = wikiEditNetworkNode.getUserName();
			
				ArrayList<String> revisorFor = wikiEditNetworkNode.getRevisorFor();
				
				Element defaulEdgeStyle = doc.createElement("data");
				defaulEdgeStyle.setAttribute("key", "path");
				defaulEdgeStyle.setTextContent("1.0");
				
				for (String userNameOfGetRevisor : revisorFor) {
					Element path = doc.createElement("edge");
					path.setAttribute("id", UUID.randomUUID()+"");
					path.setAttribute("source", id.replace(" ", "_").replaceAll("[-+!^,]",""));
					path.setAttribute("target", userNameOfGetRevisor.replace(" ", "_").replaceAll("[-+!^,]",""));
				    graphElement.appendChild(path);
				    path.appendChild(defaulEdgeStyle);
				}
			}

			
			
			
			
			
			
			
			
			
			
			
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(locationToSave);
			StreamResult result2 = new StreamResult(new File("C:/Users/admin/Desktop/test.xml"));
			transformer.transform(source, result2);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

			//
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
