package comphuman.task3.editNetwork;

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
	private ArrayList<WikiEditNetworkNodeByte> setOfNodesForExport;

	public ExportToVizToolByte(ArrayList<WikiEditNetworkNodeByte> editNodes) {
		this.setOfNodesForExport = editNodes;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();
	}

	/**
	 * Constructs the file in GRAPH-ML syntax.
	 * 
	 * @param locationToSave
	 *            Location, where to save the file.
	 */
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

			/*
			 * KEY-Section
			 */
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

			Element keyNodeHeigh = doc.createElement("key");
			keyNodeHeigh.setAttribute("id", "nodeKeyHeigh");
			keyNodeHeigh.setAttribute("for", "node");
			keyNodeHeigh.setAttribute("attr.name", "heigh");
			keyNodeHeigh.setAttribute("attr.type", "double");
			rootElement.appendChild(keyNodeHeigh);

			Element keyNodeWeight = doc.createElement("key");
			keyNodeWeight.setAttribute("id", "nodeKeyWeight");
			keyNodeWeight.setAttribute("for", "node");
			keyNodeWeight.setAttribute("attr.name", KEYWORD_WEIGHT);
			keyNodeWeight.setAttribute("attr.type", "double");
			rootElement.appendChild(keyNodeWeight);

			Element keyNodeBorder = doc.createElement("key");
			keyNodeBorder.setAttribute("id", "nodeKeyBorder");
			keyNodeBorder.setAttribute("for", "node");
			keyNodeBorder.setAttribute("attr.name", "border");
			keyNodeBorder.setAttribute("attr.type", "string");
			rootElement.appendChild(keyNodeBorder);

			Element keyNodeBorderWeight = doc.createElement("key");
			keyNodeBorderWeight.setAttribute("id", "nodeKeyBorderWeight");
			keyNodeBorderWeight.setAttribute("for", "node");
			keyNodeBorderWeight.setAttribute("attr.name", "borderweight");
			keyNodeBorderWeight.setAttribute("attr.type", "double");
			rootElement.appendChild(keyNodeBorderWeight);

			Element keyNodeTextColor = doc.createElement("key");
			keyNodeTextColor.setAttribute("id", "nodeKeyTextColor");
			keyNodeTextColor.setAttribute("for", "node");
			keyNodeTextColor.setAttribute("attr.name", "txtColor");
			keyNodeTextColor.setAttribute("attr.type", "string");
			rootElement.appendChild(keyNodeTextColor);

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

			/*
			 * NODE Section
			 */
			for (WikiEditNetworkNodeByte wikiEditNetworkNode : setOfNodesForExport) {
				String id = wikiEditNetworkNode.getName();
				String label = wikiEditNetworkNode.getName();

				// A node itself
				Element exportNode = doc.createElement("node");
				exportNode.setAttribute("id", id.replace(" ", "_").replaceAll("[-+!^,]", ""));
				exportNode.setAttribute("label", label);
				graphElement.appendChild(exportNode);

				/* Node fill-color. Depends on netAdded/Activity-Ratio */
				Element dataKeyColor = doc.createElement("data");
				dataKeyColor.setAttribute("key", "nodeKeyColor");
				dataKeyColor.setTextContent(
						CalcColors.clacInnerGrey(wikiEditNetworkNode.getNetAddedRatio()).toString().replace("0x", "#"));

				if (wikiEditNetworkNode.getNetAddedRatio() < -0.5d) {
					Element dataKeyTextColor = doc.createElement("data");
					dataKeyTextColor.setAttribute("key", "nodeKeyTextColor");
					dataKeyTextColor.setTextContent(Color.LIGHTGOLDENRODYELLOW.toString().replace("0x", "#"));
					exportNode.appendChild(dataKeyTextColor);
				}

				/*
				 * Border-color. LIGHTPINK -> Author, Black -> 'normal user',
				 * LIGHTSKYBLUE -> Last user( last input in revision-his. If the
				 * user has the role aut and last -> LIGHTSALMON
				 */
				Element dataKeyBorder = doc.createElement("data");
				dataKeyBorder.setAttribute("key", "nodeKeyBorder");
				if (wikiEditNetworkNode.getRole().equals("aut")) {
					dataKeyBorder.setTextContent(Color.LIGHTPINK.toString().replace("0x", "#"));
				} else if (wikiEditNetworkNode.getRole().equals("cur")) {
					dataKeyBorder.setTextContent(Color.BLACK.toString().replace("0x", "#"));

				} else if (wikiEditNetworkNode.getRole().equals("last")) {
					dataKeyBorder.setTextContent(Color.LIGHTSKYBLUE.toString().replace("0x", "#"));
				} else if (wikiEditNetworkNode.getRole().equals("both")) {
					dataKeyBorder.setTextContent(Color.DARKRED.toString().replace("0x", "#"));
				}
				Element dataKeyBorderWeight = doc.createElement("data");
				dataKeyBorderWeight.setAttribute("key", "keyNodeBorderWeight");
				dataKeyBorderWeight.setTextContent("10.0");

				// NODE label
				Element dataKeyLabel = doc.createElement("data");
				dataKeyLabel.setAttribute("key", "nodeKeyLabel");
				dataKeyLabel.setTextContent(label);

				// Node Height
				Element dataKeyHeigh = doc.createElement("data");
				dataKeyHeigh.setAttribute("key", "nodeKeyHeigh");
				System.out.println(label +" ---- "+wikiEditNetworkNode.getNodeHeight());
				dataKeyHeigh.setTextContent(wikiEditNetworkNode.getNodeHeight()/(double) 10+ "");

				// Node Weight
				Element dataKeyWeight = doc.createElement("data");
				dataKeyWeight.setAttribute("key", "nodeKeyWeight");
				dataKeyWeight.setTextContent((double) wikiEditNetworkNode.getNodeWeight()/(double) 10+ "");
				// Append-Section
				exportNode.appendChild(dataKeyColor);
				exportNode.appendChild(dataKeyLabel);
				exportNode.appendChild(dataKeyHeigh);
				exportNode.appendChild(dataKeyWeight);
				exportNode.appendChild(dataKeyBorder);
				exportNode.appendChild(dataKeyBorderWeight);
			}

			/* Edge Section */
			for (int i = 0; i < setOfNodesForExport.size(); i++) {
				WikiEditNetworkNodeByte wikiEditNetworkNode = setOfNodesForExport.get(i);
				String id = wikiEditNetworkNode.getName();
				ArrayList<String> revisorFor = wikiEditNetworkNode.getRevisorFor();
				
				for (int j = 0; j < revisorFor.size(); j++) {
					String userNameOfGetRevisor = revisorFor.get(j);
					Element path = doc.createElement("edge");
					path.setAttribute("id", UUID.randomUUID() + "");
					path.setAttribute("source", id.replace(" ", "_").replaceAll("[-+!^,]", ""));
					path.setAttribute("target", userNameOfGetRevisor.replace(" ", "_").replaceAll("[-+!^,]", ""));
					graphElement.appendChild(path);

					// Adds the edge-weight
					Element defaulEdgeStyle = doc.createElement("data");
					defaulEdgeStyle.setAttribute("key", "path");
					defaulEdgeStyle.setTextContent(wikiEditNetworkNode.getEdgeWeight(j)+"");
					path.appendChild(defaulEdgeStyle);
				}
			}

			/*
			 * FILE output-section
			 */
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(locationToSave);
			StreamResult result2 = new StreamResult(new File("C:/Users/admin/Desktop/test.xml"));
			transformer.transform(source, result2);

			transformer.transform(source, result);
			System.out.println("File saved!");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
