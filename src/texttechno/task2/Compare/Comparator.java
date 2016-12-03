package texttechno.task2.Compare;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

import org.apache.commons.io.FileUtils;

public class Comparator {

	public Comparator() {
		//TODO Init the methods over this method
	}

	public Float calcProForMatches(File file01, File file02) {
		Float pro = 0f;
		ArrayList<ArrayList<String>> listOfFiles = new ArrayList<>();
		listOfFiles.add(filter(read(file01)));
		listOfFiles.add(filter(read(file02)));

		return pro;

	}

	private ArrayList<String> filter(List<String> read) {
		ArrayList<String> filteredConten = null;

		for (String line : filteredConten) {
			Boolean returnLine = false; 
			//TODO wenn # dann weg, wenn leer dann weg, wenn number dann tupel zerlegen (id,word,pos)
//			if(line.contains(s))
		}
		return filteredConten;
	}

	private List<String> read(File file) {
		List<String> content = null;
		try {
			content = FileUtils.readLines(file, Encoding.getDefaultEncoding());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (content == null)
			SystemMessage.eMessage("Couldn't read content of file <" + file.getAbsolutePath() + ">");
		return content;
	}
}
