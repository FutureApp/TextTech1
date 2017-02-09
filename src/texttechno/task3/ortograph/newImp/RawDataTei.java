package texttechno.task3.ortograph.newImp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xgeneral.modules.SystemMessage;

public class RawDataTei {

	File locationOfFile;
	private ArrayList<texttechno.task3.ortograph.newImp.StringTuple3> lemmaInformationOfInterest;

	public RawDataTei(File locationOfFile) {
		super();
		this.locationOfFile = locationOfFile;
		this.lemmaInformationOfInterest = new ArrayList<>();
	}

	/**
	 * Returns a list of lemma-informationf ( represented as
	 * {@link StringTuple3} which are interesting for further investigation.
	 * 
	 * @returns Liste which contains lemma-information
	 */
	public ArrayList<StringTuple3> getDataOfInterest() {
		if (lemmaInformationOfInterest.size() == 0)
			extractLemmata();
		return lemmaInformationOfInterest;
	}

	/**
	 * Abstracts all lemma/ lemma-information of interest. Will generate a list
	 * of these lemma-information in {@link StringTuple3} representation.
	 * 
	 * @return List of lemma which need to be investigated. The lemma in the
	 *         list are represented as {@link StringTuple3}.
	 */
	public ArrayList<StringTuple3> extractLemmata() {
		System.out.println("Abstracting Data");
		lemmaInformationOfInterest = new ArrayList<>();
		try {
			// Source
			// https://www.tutorialspoint.com/java_xml/java_xpath_parse_document.htm
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(locationOfFile);
			doc.getDocumentElement().normalize();
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = ".//*/w";
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

			// The extraction part. Check if lemma is interesting.
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node nNode = nodeList.item(i);
				NamedNodeMap attributes = nNode.getAttributes();
				String attrLemma = attributes.getNamedItem("lemma").getTextContent();
				String attrType = attributes.getNamedItem("type").getTextContent();
				String attrText = nNode.getTextContent();

				// If lemma is interesting then add to the return-list.
				if (isLemmaOfInterst(attrType))
					lemmaInformationOfInterest.add(new StringTuple3(attrType, attrLemma, attrText));
				else
					;
			}
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			SystemMessage.wMessage("Error while parsing file <" + locationOfFile.getAbsolutePath() + ">.");
			SystemMessage.wMessage("Verify if the documents follows the format for a tei-data.");
			e.printStackTrace();
		}
		return lemmaInformationOfInterest;
	}

	/**
	 * Dets. if a given attrLemma is a lemma of interest. Lemma is interesting
	 * if the lemma start with N,V oder ADJ.
	 * 
	 * @param attrLemma
	 *            Lemma where to det. if interesting.
	 * @return True - if interesting.
	 */
	private boolean isLemmaOfInterst(String attrLemma) {
		String[] lemmaOfInterest = { "N", "V", "ADJ" };
		Boolean isInteresting = false;
		for (int i = 0; i < lemmaOfInterest.length; i++) {
			if (attrLemma.toLowerCase().startsWith(lemmaOfInterest[i].toLowerCase())) {
				isInteresting = true;
				break;
			}
		}
		System.out.println(attrLemma + " " + isInteresting);
		return isInteresting;
	}
}
