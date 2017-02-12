package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import scala.Array;
import scala.runtime.StringFormat;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.UtilsStrings_SingleTone;

public class NetworkMatrix {

	HashMap<String, Double> networkData;
	ArrayList<String> headerNames;
	HashMap<String, Integer> headerIndex;
	ArrayList<ArrayList<Double>> networkMatrixLog;
	KokkurenzList list;
	Double cwValueForCluster;

	public NetworkMatrix(HashMap<String, Double> networkData, KokkurenzList list) {
		super();
		this.networkData = networkData;
		this.list = list;
	}

	public NetworkMatrix(KokkurenzList list) {
		super();
		this.list = list;
	}

	public ArrayList<ArrayList<Double>> generateNetworkMatrix() {
		generateHeader();

		networkMatrixLog = generateBaseMatrix();

		for (Entry<String, Double> entry : networkData.entrySet()) {
			String[] keys = entry.getKey().split("@");
			Integer indexKey1 = headerIndex.get(keys[0]);
			Integer indexKey2 = headerIndex.get(keys[1]);
			networkMatrixLog.get(indexKey1).set(indexKey2, entry.getValue());
			networkMatrixLog.get(indexKey2).set(indexKey1, entry.getValue());
		}
		return networkMatrixLog;
	}

	private ArrayList<ArrayList<Double>> generateBaseMatrix() {
		ArrayList<ArrayList<Double>> popMatrix = new ArrayList<>();
		for (int i = 0; i < headerNames.size(); i++) {
			ArrayList<Double> baseList = new ArrayList<>();
			for (int j = 0; j < headerNames.size(); j++) {
				baseList.add(0d);
			}
			popMatrix.add(baseList);
		}

		return popMatrix;

	}

	private ArrayList<String> generateHeader() {
		headerIndex = new HashMap<>();
		Integer idCounter = 0;
		headerNames = new ArrayList<>();

		for (Entry<String, Double> entry : networkData.entrySet()) {
			String[] split = entry.getKey().split("@");
			if (!headerIndex.containsKey(split[0])) {
				headerIndex.put(split[0], idCounter);
				headerNames.add(split[0]);
				idCounter++;
			}
			if (!headerIndex.containsKey(split[1])) {
				headerIndex.put(split[1], idCounter);
				headerNames.add(split[1]);
				idCounter++;
			}
		}
		return headerNames;
	}

	public void showNetworkMatrix() {
		UtilsStrings_SingleTone stringUtils = UtilsStrings_SingleTone.getInstance();

		Integer space = 12;
		String header = stringUtils.getXWhiteSpaces(space);

		for (String string : headerNames) {
			header = header + stringUtils.fillLeftWithWhiteSpaces(string, space);
		}
		header = header + stringUtils.fillLeftWithWhiteSpaces("SUM", space);
		header = header + System.lineSeparator();
		String content = "";
		/*
		 */
		for (int i = 0; i < networkMatrixLog.size(); i++) {
			String leftSide = stringUtils.fillRightWithWhiteSpaces(headerNames.get(i), space);
			content = content + leftSide;
			for (int j = 0; j < networkMatrixLog.size(); j++) {
				Double double1 = networkMatrixLog.get(i).get(j);
				String number = stringUtils.fillLeftWithWhiteSpaces(String.format("%.3f", double1) + "", space);
				content = content + number;
			}
			Double rowSum = 0d;
			content = content + "|";
			for (int x = 0; x < networkMatrixLog.size(); x++) {
				rowSum = rowSum + networkMatrixLog.get(i).get(x);
			}
			content = content + stringUtils.fillLeftWithWhiteSpaces(rowSum + "", space);
			content = content + System.lineSeparator();
		}
		for (int i = 0; i < header.length(); i++) {
			content = content + "_";
		}

		content = content + System.lineSeparator();
		content = content + stringUtils.fillRightWithWhiteSpaces("SUM", space);
		for (int y = 0; y < networkMatrixLog.size(); y++) {
			Double col = 0d;
			for (int x = 0; x < networkMatrixLog.size(); x++) {
				Double fieldValue = networkMatrixLog.get(x).get(y);
				col += fieldValue;
			}
			content = content + stringUtils.fillLeftWithWhiteSpaces(String.format("%.3f", col) + "", space);
		}
		// CalcEntrys

		// Double sumOverallCols = sumOverAllColumns(matrix);
		// System.out.println("Rows " + sumOverallRows);
		// System.out.println("Cols " + sumOverallCols);

		// if(!(sumOverallCols - sumOverallRows == 0 )){
		// SystemMessage.eMessage("Sums doesn't matches");
		// content = content + stringUtils.fillLeftWithWhiteSpaces(" ?", space);
		// }
		// else{
		// content = content +
		// stringUtils.fillLeftWithWhiteSpaces(sumOverallCols+"", space);
		// content = content + System.lineSeparator();
		// }

		String matrixAsString = header + content;
		System.out.println(matrixAsString);
	}

	public ArrayList<Nodes> calcNodesClusterWeight() {
		Double avgWeight = list.calcAvgWeight(networkData);
		ArrayList<Nodes> nodesList = new ArrayList<>();
		cwValueForCluster = 0d;
		for (String nodeName : headerNames) {
			Word primeWord = list.uWords.get(nodeName);
			String namePrime = primeWord.name;
			HashMap<String, Integer> toCheck = new HashMap<>();

			ArrayList<String> primeNodesAreContaining = primeWord.getFollowers();
			Integer nodeDegree = primeNodesAreContaining.size();

			Double CwValueOfNode = 0d;
			Double weightProduct = 0d;

			for (int i = 0; i < primeNodesAreContaining.size(); i++) {
				String secondNodeName = primeNodesAreContaining.get(i);
				Double weightwij = lookUpWeight(namePrime, secondNodeName);

				for (int j = i + 1; j < primeNodesAreContaining.size(); j++) {
					String thirdNodeName = primeNodesAreContaining.get(j);
					Double weightwik;
					weightwik = lookUpWeight(namePrime, thirdNodeName);

					Double weightwil = lookUpWeight(secondNodeName, thirdNodeName);
					weightProduct += weightwij * weightwik * weightwil;
				}
			}

			Double graphAVGWeightPot3 = Math.pow(avgWeight, 3);
			Double valueBot = (graphAVGWeightPot3 * nodeDegree * (nodeDegree - 1)) / 2;
			Double leftArgument = 1d / valueBot;
			CwValueOfNode = leftArgument * weightProduct;
			System.out.println(String.format("CwValueOfNode=%4f|weightProduct=%4f", CwValueOfNode, weightProduct));
			System.out.println("Prime " + namePrime + " weight:" + CwValueOfNode);

			for (int i = 0; i < primeNodesAreContaining.size(); i++) {
				Word secondNode = list.uWords.get(primeNodesAreContaining.get(i));
				ArrayList<String> secondNodesContains = secondNode.getFollowers();
				for (int j = 0; j < secondNodesContains.size(); j++) {
					String ThirdNode = secondNodesContains.get(j);
					if (primeNodesAreContaining.contains(ThirdNode)) {
						toCheck.put(secondNode.name + "@" + ThirdNode, 1);
					}
				}
			}
			cwValueForCluster += CwValueOfNode;
			System.out.println("CW: " + cwValueForCluster);
			HashMap<String, Double> edgeWeights = lookUpAllWeights(namePrime, primeNodesAreContaining);
			Nodes node = new Nodes(namePrime, CwValueOfNode, edgeWeights);
			nodesList.add(node);
		}
		cwValueForCluster = (1d / (double) headerNames.size()) * cwValueForCluster;
		System.out.println("True CW Value =" + cwValueForCluster);
		return nodesList;
	}

	public Double lookUpWeight(String comp1, String comp2) {
		Double weight = 0d;
		String var1 = comp1 + "@" + comp2;
		String var2 = comp2 + "@" + comp1;

		if (networkData.containsKey(var1))
			weight = networkData.get(var1);
		else if (networkData.containsKey(var2))
			weight = networkData.get(var2);
		else {
			// SystemMessage.wMessage("Warning:Given weights <" + var1 + " " +
			// var2 + ">. Return value will be 0.");
		}
		return weight;

	}

	private Double extractNeededWeight(String var1, String var2) {
		Double weightwij = 0d;
		if (networkData.containsKey(var1))
			weightwij = networkData.get(var1);
		else if (networkData.containsKey(var2))
			weightwij = networkData.get(var2);
		else {
			// SystemMessage.wMessage("Warning:Given weights <" + var1 + " " +
			// var2 + ">. Return value will be 0.");
		}
		return weightwij;
	}

	public Double calcClusterWeight(ArrayList<Nodes> calcNodesClusterWeight) {
		Double clusterWeight = 0d;
		for (Nodes nodes : calcNodesClusterWeight) {
			clusterWeight += nodes.nodeCwValue;
		}
		clusterWeight = (1d / (double) calcNodesClusterWeight.size()) * clusterWeight;
		return clusterWeight;

	}

	public HashMap<String, Double> lookUpAllWeights(String nodeName, ArrayList<String> nodeNamesFollowed) {
		HashMap<String, Double> resultMap = new HashMap<>();
		for (int i = 0; i < nodeNamesFollowed.size(); i++) {
			System.out.println("LookUp " + nodeName + " " + nodeNamesFollowed + " ");
			String folNode = nodeNamesFollowed.get(i);
			Double extractNeedWeight = lookUpWeight(nodeName, folNode);
			resultMap.put(folNode, extractNeedWeight);
		}
		return resultMap;
	}

	public Double getCWValue() {
		return cwValueForCluster;
	}

	public Double getLikeAVG() {
		return list.calcAvgWeight(networkData);
	}
}
