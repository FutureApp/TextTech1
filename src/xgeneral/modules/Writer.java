package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import texttechno.task3.ortograph.newImp.IntegerSignature;
import texttechno.task3.ortograph.newImp.NetworkMatrix;
import texttechno.task3.ortograph.newImp.Nodes;

public class Writer {

	public static void write(File location, String content) {
		try {
			FileUtils.write(location, content + System.lineSeparator(), Encoding.getDefaultEncoding(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void delAndWrite(File location, String content) {
		if (location.exists())
			location.delete();
		try {
			FileUtils.write(location, content + System.lineSeparator(), Encoding.getDefaultEncoding(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void delAndWrite(File location, HashMap<String, IntegerSignature> calcRateSignatureForAllWords) {
		System.out.println("delAndWrite");
		if (location.exists())
			location.delete();
		for (Entry<String, IntegerSignature> element : calcRateSignatureForAllWords.entrySet()) {
			String between = element.getKey();
			String containing = element.getValue().toString();
			String content = between + " " + containing;
			System.out.println(content);
			write(location, content);
		}
	}

	public static void delAndWriteHash(File location, HashMap<String, Double> map) {
		System.out.println("delAndWrite");
		if (location.exists())
			location.delete();
		for (Entry<String, Double> element : map.entrySet()) {
			String between = element.getKey();
			String containing = element.getValue().toString();
			String content = between + " " + containing;
			System.out.println(content);
			write(location, content);
		}

	}

	public static void writeOnly(File file, String content) {
		try {
			FileUtils.write(file, content, Encoding.getDefaultEncoding(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void delAndWriteNodeList(File file, ArrayList<Nodes> nodes) {
		if (file.exists())
			file.delete();

		for (Nodes nodez : nodes) {
			String data = nodez.getNodeName() + " CW:" + nodez.getNodeCwValue() + " Nodes:[ ";
			writeOnly(file, data);
			for (Entry<String, Double> entry : nodez.getNodeMapEdgeWeight().entrySet()) {
				System.out.println(entry.getKey());
				String resultFormate = String.format("(%s|%.4f) ", entry.getKey(), entry.getValue());
				writeOnly(file, resultFormate);
			}
			write(file, "]");
		}
	}

	public static void saveResult(File file, NetworkMatrix matrix) {
		if (file.exists())
			file.delete();
		String clusterCWVALUE = "CW-Value cluster : "+matrix.getCWValue() ;
		String avgLike = "AVG-Value log-likelihood : "+matrix.getLikeAVG() ;
		
		
		
		write(file, clusterCWVALUE);
		write(file, avgLike);
		
	}
}
