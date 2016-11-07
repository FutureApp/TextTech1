package texttechno.task1.token;

import java.io.File;
import java.util.ArrayList;

public class WordToker {
	
	ArrayList<File> textListAsFiles = new ArrayList<>();
	ArrayList<String> textListAsString = new ArrayList<>();
	
	
	public WordToker(ArrayList<File> textList) {
		super();
		this.textListAsFiles = textList;
	}
	
	public void loadNewTextList(String[] pathsOfTextes){
		ArrayList<File> resultList = new ArrayList<>();
		
		for (String text : pathsOfTextes) {
			resultList.add(new File(text));
		}
	}
	public void cleanTextInTextListAsFiles(){
		
	}
}
