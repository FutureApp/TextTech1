package texttechno.task3.ortograph.old;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

/**
 * @author Michael Czaja
 *
 */
public class RawDataNewOLD {

	File teiFile;
	List<String> teiFileAsList;
	private ArrayList<StringTuple3OLD> fileAsTuple;
	TeiHAHALoaderOLD loader = new TeiHAHALoaderOLD();

	public RawDataNewOLD() {
	}

	public RawDataNewOLD(File file) {
		this.teiFile = file;
		this.teiFileAsList = loadFile();
	}

	public List<String> loadFile() { 
		List<String> readLines = new ArrayList<>();
		try {
			readLines = FileUtils.readLines(teiFile, Encoding.getDefaultEncoding());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (readLines.isEmpty())
			SystemMessage
					.wMessage("Input is empty. " + "Maybe system couldn't read the file. Given File <" + teiFile + ">");

		return readLines;
	}

	/**
	 * Transform the list into a tupleOf3 format. This format will be
	 * used:(LEMMA,TYPE,WORD).
	 * 
	 * Example: Given -
	 * <w xml:id="xd1_wo1289" lemma="Wirt" type="NN" function="dic">Wirtes</w>
	 * Result - Wirt, NN, Wirtes)
	 * 
	 * @return List containing tupleOf3;
	 */
	public ArrayList<StringTuple3OLD> transformToTupleOfThree() {
		
		fileAsTuple = loader.abstractTuplesOf3(teiFile);
		return fileAsTuple;
	}
}