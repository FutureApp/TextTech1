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

	/**
	 * The network-matrix.
	 * 
	 * @param networkData
	 *            Data containing the informations to generate the
	 *            network-matrix.
	 * @param list
	 *            List containing a {@link KokkurenzList} for look-up's.
	 */
	public NetworkMatrix(HashMap<String, Double> networkData, KokkurenzList list) {
		super();
		this.networkData = networkData;
		this.list = list;
	}

	/**
	 * The network-matrix.
	 * 
	 * @param list
	 *            List containing a {@link KokkurenzList} for look-up's.
	 */
	public NetworkMatrix(KokkurenzList list) {
		super();
		this.list = list;
	}

	/**
	 * Generates the network-matrix based on the given data. (Constructor)
	 * 
	 * @return The network-matrix.
	 */
	public ArrayList<ArrayList<Double>> generateNetworkMatrix() {
		generateHeader();

		networkMatrixLog = generateBaseMatrix();
		// Foreach data-set generate entry-value.
		for (Entry<String, Double> entry : networkData.entrySet()) {
			String[] keys = entry.getKey().split("@");
			Integer indexKey1 = headerIndex.get(keys[0]);
			Integer indexKey2 = headerIndex.get(keys[1]);
			networkMatrixLog.get(indexKey1).set(indexKey2, entry.getValue());
			networkMatrixLog.get(indexKey2).set(indexKey1, entry.getValue());
		}
		return networkMatrixLog;
	}

	/**
	 * Creates and empty matrix. Each value in the matrix is given by 0.
	 * 
	 * @return An empty matrix containing 0 only.
	 */
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

	/**
	 * Creates the columns-names for each column.
	 * 
	 * @return A list containing all column-names.
	 */
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

	/**
	 * Prints the network-matrix.
	 */
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
		String matrixAsString = header + content;
		System.out.println(matrixAsString);
	}

	/**
	 * Calcs the cluster-weight value and generates for each(word) a node.
	 * 
	 * @return A list of nodes.{@link Nodes}.
	 */
	public ArrayList<Nodes> calcNodesClusterWeight() {
		Double avgWeight = list.calcAvgWeight(networkData);
		ArrayList<Nodes> nodesList = new ArrayList<>();
		cwValueForCluster = 0d;
		// for each header -> unique word
		for (String nodeName : headerNames) {
			Word primeWord = list.uWords.get(nodeName);
			String namePrime = primeWord.name;
			HashMap<String, Integer> toCheck = new HashMap<>();

			ArrayList<String> primeNodesAreContaining = primeWord.getFollowers();
			Integer nodeDegree = primeNodesAreContaining.size();

			// The counters
			Double CwValueOfNode = 0d;
			Double weightProduct = 0d;

			// Calcs the right term of the math formula. wij,wik,wjk
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

			// The calc of the cluster-weight.
			Double graphAVGWeightPot3 = Math.pow(avgWeight, 3);
			Double valueBot = (graphAVGWeightPot3 * nodeDegree * (nodeDegree - 1)) / 2;
			Double leftArgument = 1d / valueBot;
			CwValueOfNode = leftArgument * weightProduct;

			// Shows result for the node in the console
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
			// calc the cw-value for the whole cluster.
			cwValueForCluster += CwValueOfNode;
			System.out.println("CW: " + cwValueForCluster);

			// Generates an new node-entry.
			HashMap<String, Double> edgeWeights = lookUpAllWeights(namePrime, primeNodesAreContaining);
			Nodes node = new Nodes(namePrime, CwValueOfNode, edgeWeights);
			nodesList.add(node);
		}
		cwValueForCluster = (1d / (double) headerNames.size()) * cwValueForCluster;
		System.out.println("True CW Value =" + cwValueForCluster);
		return nodesList;
	}

	/**
	 * Looks up a for a connection between to words.
	 * 
	 * @param comp1
	 *            Word 1
	 * @param comp2
	 *            Word 2
	 * @return Weight of the connection.
	 */
	public Double lookUpWeight(String comp1, String comp2) {
		Double weight = 0d;
		String var1 = comp1 + "@" + comp2;
		String var2 = comp2 + "@" + comp1;

		if (networkData.containsKey(var1))
			weight = networkData.get(var1);
		else if (networkData.containsKey(var2))
			weight = networkData.get(var2);
		else {
			// Do Nothing
		}
		return weight;

	}

	/**
	 * Calcs the cluster-value for a given network.
	 * 
	 * @param nodesOfACluster
	 *            List which contains nodes. All nodes together ->
	 *            network-cluster -> network-matrix.
	 * @return The cluster-value of a network-matrix/network-cluster.
	 */
	public Double calcClusterWeight(ArrayList<Nodes> nodesOfACluster) {
		Double clusterWeight = 0d;
		for (Nodes nodes : nodesOfACluster) {
			clusterWeight += nodes.nodeCwValue;
		}
		clusterWeight = (1d / (double) nodesOfACluster.size()) * clusterWeight;
		return clusterWeight;

	}

	/**
	 * Looks up all weights.
	 * 
	 * @param nodeName
	 *            Name of node for examination.
	 * @param nodeNamesFollowed
	 *            All nodes which are followers of nodName.
	 * @return A look up of all weights for nodeName -> follower1,follower2,...
	 */
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

	/**
	 * Returns the cluster-weight value.
	 * 
	 * @return CW-Value
	 */
	public Double getCWValue() {
		return cwValueForCluster;
	}

	/**
	 * Returns the avg-Value of the likeli-hood values.
	 * 
	 * @return AVG of all likeli-hood values.
	 */
	public Double getLikeAVG() {
		return list.calcAvgWeight(networkData);
	}
}
