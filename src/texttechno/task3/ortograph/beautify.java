package texttechno.task3.ortograph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;

public class beautify {

	public static void main(String[] args) throws IOException {
		TeiHAHALoader loader = new TeiHAHALoader();
		loader.abstractTuplesOf3("TextTechno/03Task/ressources/test.tei");
		
	}
}
