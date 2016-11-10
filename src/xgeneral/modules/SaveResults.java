package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import texttechno.task1.calculators.MATTRCalculator;
import texttechno.task1.calculators.TTRCalculator;
import texttechno.task1.prototype.types.TupelIS;
/**
 * Writes given results in different type into specific files.
 * @author Michael Czaja
 *
 */
public class SaveResults {
	public String encoding = "UTF-8";

	public SaveResults(String encoding) {
		super();
		this.encoding = encoding;
	}

	public Boolean saveTupelIS(ArrayList<TupelIS> list, String location, String filename) {
		Boolean succesfull = false;
		filename = setProperFileNameIfNeeded(filename);
		File saveFile = new File(location + filename);
		deleteFileIfExsits(saveFile);

		for (TupelIS tupelIS : list) {
			try {
				String key = tupelIS.getKey() + "";
				while (key.length() <= 5)
					key = " " + key;
				String content = key + " " + tupelIS.getValue();
				FileUtils.write(saveFile, content + System.lineSeparator(), encoding, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succesfull;
	}

	public Boolean saveTTR(TTRCalculator TTR, String location, String filename) {
		Boolean succesfull = false;
		filename = setProperFileNameIfNeeded(filename);
		File saveFile = new File(location + filename);
		deleteFileIfExsits(saveFile);
		try {
			String line1 = TTR.getTTRLastCalc() + System.lineSeparator();
			String line2 = "Calculation based on: " + System.lineSeparator();
			;
			String line3 = TTR.getUniqueTokensLastCalc() + " " + "unique tokens" + System.lineSeparator();
			String line4 = TTR.getTokensLastCalc() + " " + "tokens at all" + System.lineSeparator();
			String line5 = TTR.getUniqueTokensLastCalc() + "/" + TTR.getTokensLastCalc() + "=" + TTR.getTTRLastCalc();
			String content = line1 + line2 + line3 + line4 + line5;
			FileUtils.write(saveFile, content + System.lineSeparator(), encoding, true);
			succesfull = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return succesfull;
	}

	private Boolean deleteFileIfExsits(File saveFile) {
		return FileUtils.deleteQuietly(saveFile);
	}

	private String setProperFileNameIfNeeded(String filename) {
		if (!filename.startsWith("/") || !filename.startsWith("\\"))
			filename = "/" + filename;
		if (!filename.endsWith(".textTech") || !filename.endsWith(".humanComp"))
			filename = filename + ".textTech";
		return filename;
	}

	public Boolean saveMATTR(MATTRCalculator mAttr, String location, String filename) {
		Boolean succesfull = false;
		filename = setProperFileNameIfNeeded(filename);
		File saveFile = new File(location + filename);
		deleteFileIfExsits(saveFile);
		String content = mAttr.getMATTRLastRun() + "";
		try {
			FileUtils.write(saveFile, content + System.lineSeparator(), encoding, true);
			FileUtils.write(saveFile, "Calculation based on"+mAttr.getListOfWindows().size()+" sliding window:" + System.lineSeparator(), encoding, true);
			/*
			  For deeper analysis:

              FileUtils.write(saveFile, "(Window_ID,TTR_on_window)" + System.lineSeparator(), encoding, true);
			  for (int i = 0; i < mAttr.getListOfWindows().size(); i++) {
			  String contentInner = String.format("(%s,%s)", (i + 1 + ""),
			  mAttr.getListOfWindows().get(i)); FileUtils.write(saveFile,
			  contentInner + System.lineSeparator(), encoding, true);
			  
			  }
			 */
			succesfull = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return succesfull;
	}

}
