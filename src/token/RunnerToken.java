	package token;
	
	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Map;
	
	import org.apache.commons.io.FileUtils;
	
	public class RunnerToken {
		
		static String encoding = "iso-8859-1";
		static File geothe = new File("Task1/Goethe-Wahlverwandtschaften-clear.txt");
		static File schiller = new File("Task1/Schiller-Die-Raeuber-clear.txt");
		static File[] fileListOfTexts = { geothe, schiller };
		static ArrayList<String> listOfTexts = new ArrayList<>();
		static ArrayList<String> listOfCleanTexts = new ArrayList<>();
	
		private static ArrayList<HashMap<String, Integer>> listOfTokenMapCount = new ArrayList<>();
	
		Map<String, Integer> wordCounts = new HashMap<String, Integer>();
	
		public static void main(String[] args) throws IOException {
	
			for (File file : fileListOfTexts) {
				listOfTexts.add(FileUtils.readFileToString(file, encoding));
			}
	
			cleanText(listOfTexts.get(0));
			splittText(listOfCleanTexts);
			printResult();
	
		}
	
		private static void splittText(ArrayList<String> listOfCleanTexts) throws IOException {
			for (String text : listOfCleanTexts) {
				String[] tokens = text.split(" ");
	
				System.out.println(tokens.length);
				HashMap<String, Integer> tokenMapCount = new HashMap<>();
				for (String token : tokens) {
					if (!token.equals(" ")) {
	
						FileUtils.write(new File("Task1/Result/geotheToken.txt"), token + System.lineSeparator(), encoding,
								true);
						if (tokenMapCount.containsKey(token)) {
							tokenMapCount.put(token, tokenMapCount.get(token) + 1);
						} else {
							tokenMapCount.put(token, 1);
						}
					}
					else{
						
					}
				}
				listOfTokenMapCount.add(tokenMapCount);
			}
			System.out.println(listOfTokenMapCount.get(0).get("die"));
		}
	
		private static void cleanText(String inputText) {
			// inputText="Ich bin. ein Läuch! >>";
			System.out.println("input " + inputText);
			listOfCleanTexts.add(inputText.replaceAll("[^a-zA-Zäöüß\\d]", " ").replace("\\n", " "));
		}
	
		private static void printResult() throws IOException {
			FileUtils.write(new File("Task1/Result/geothe.txt"), listOfCleanTexts.get(0), encoding, false);
		}
	
	}
