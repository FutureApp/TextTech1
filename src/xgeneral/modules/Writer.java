package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import texttechno.task3.ortograph.newImp.IntegerSignature;

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
	
}
