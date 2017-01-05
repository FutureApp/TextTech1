package comphuman.task2.distanceReading.newOne;

import java.util.ArrayList;

public class Fitter {

	private String[] split;
	ArrayList<String> con = new ArrayList<>();
	Boolean changed = false;

	public Fitter(String[] split) {
		this.split = split;
		for (int i = 0; i < split.length; i++) {
			con.add(split[i]);
		}
	}

	/**
	 * Normalizes the level of tags. Anchor to normalize is the <dl>-tag.
	 */
	public void trimDL() {
		int depLevel = 1;
		ArrayList<String> newCon = new ArrayList<>();
		for (String line : con) {
			String myLine = new String(line);
			String trueLine= new String(myLine);
			if(depLevel == countLeft(myLine)){
			}else{
				myLine = new String(removeLeft(depLevel-1, myLine));
			}
			if(myLine.contains("<"+"dl"+">")) depLevel++;
			if(myLine.contains("</"+"dl"+">")){
				depLevel--;
				myLine= removeLeft(depLevel-1, trueLine);
			}
			newCon.add(myLine);
		}
		con.clear();
		con.addAll(newCon);
	}
	
	
	/**
	 * Normalizes the level of tags. Anchor to normalize is the <dl>-tag.
	 */
	public void trimUL() {
		ArrayList<String> newCon = new ArrayList<>();
		ArrayList<Integer> lefter = new ArrayList<>();
		lefter.add(0);
		Integer activeLefter = 0;
		for (String line : con) {
			String newLine = removeLeft(lefter.get(activeLefter), line);
			if(line.contains("<ul>")){
				lefter.add(countLeft(line));
				activeLefter++;
			}
			if(line.contains("</ul>")){
				lefter.remove(lefter.size()-1);
				newLine = line;
				activeLefter--;
			};
			newCon.add(newLine);
		}
		con.clear();
		con.addAll(newCon);
	}
	

	public ArrayList<String> getContent() {
		return con;
	}

	/**
	 * Counts the white-spaces on the left side till first non-white-space char.
	 * @param con Line
	 * @return Count of white spaces.
	 */
	public Integer countLeft(String con) {
		Integer c = 0;
		for (int i = 0; i < con.length(); i++) {
			if (con.charAt(i) == ' ')
				c++;
			else
				break;
		}
		return c;
	}

	/**
	 * Removes given amount of white spaces from the left side.
	 * @param howMany How many should be ignored.
	 * @param line Line.
	 * @return Line with without ignored white-spaces.
	 */
	public String removeLeft(Integer howMany, String line) {
		return line.substring(howMany);
	}

	public void trimSMALL() {
		ArrayList<String> newCon = new ArrayList<>();
		ArrayList<Integer> lefter = new ArrayList<>();
		lefter.add(0);
		Integer activeLefter = 0;
		for (String line : con) {
			String newLine = removeLeft(lefter.get(activeLefter), line);
			if(line.contains("<small>")){
				lefter.add(countLeft(line));
				activeLefter++;
			}
			if(line.contains("</small>")){
				lefter.remove(lefter.size()-1);
				newLine = line;
				activeLefter--;
			};
			newCon.add(newLine);
		}
		con.clear();
		con.addAll(newCon);
	}
	
	public void trimI() {
		ArrayList<String> newCon = new ArrayList<>();
		ArrayList<Integer> lefter = new ArrayList<>();
		lefter.add(0);
		Integer activeLefter = 0;
		for (String line : con) {
			String newLine = removeLeft(lefter.get(activeLefter), line);
			if(line.contains("<i>")){
				lefter.add(countLeft(line));
				activeLefter++;
			}
			if(line.contains("</i>")){
				lefter.remove(lefter.size()-1);
				newLine = line;
				activeLefter--;
			};
			newCon.add(newLine);
		}
		con.clear();
		con.addAll(newCon);
	}

}
