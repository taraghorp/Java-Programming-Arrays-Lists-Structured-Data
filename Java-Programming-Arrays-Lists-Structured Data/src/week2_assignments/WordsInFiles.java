package week2_assignments;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.duke.*;

public class WordsInFiles {
	
	Map<String, ArrayList<String>> wordFilesMap = new HashMap<>();

	public static void main(String[] args) {
		
		WordsInFiles wordsInFiles = new WordsInFiles();
		wordsInFiles.tester();
	}
	
	private void addWordsFromFile(File f) {
		FileResource fr = new FileResource(f);
		for(String word : fr.words()) {
			if (wordFilesMap.containsKey(word)) { //already there
				ArrayList<String> files = wordFilesMap.get(word);
				if (!files.contains(f.getName()))
					files.add(f.getName());
			} else { //new word
				ArrayList<String> files = new ArrayList<>();
				files.add(f.getName());
				wordFilesMap.put(word, files);
			}
		}		
	}
	
	public void buildWordFileMap() {
		DirectoryResource dr = new DirectoryResource();
		wordFilesMap.clear();
		for(File f : dr.selectedFiles()) {
			addWordsFromFile(f);
		}
	}
	
	public int maxNumber() {
		Map.Entry<String, ArrayList<String>> maxEntry = null;
		for (Map.Entry<String, ArrayList<String>> entry : wordFilesMap.entrySet()){
		    if (maxEntry == null || entry.getValue().size() > maxEntry.getValue().size()){
		        maxEntry = entry;
		    }
		}
		return maxEntry.getValue().size();
	}
	
	public ArrayList<String> wordsInNumFiles(int number){
		ArrayList<String> words = new ArrayList<>();
		for (Map.Entry<String, ArrayList<String>> entry : wordFilesMap.entrySet()){
			if (entry.getValue().size() == number)
				words.add(entry.getKey());
		}
		return words;		
	}
	
	public void printFilesIn(String word) {
		for (Map.Entry<String, ArrayList<String>> entry : wordFilesMap.entrySet()){
			if (entry.getKey().equals(word)) {
				for(String fileName : entry.getValue()) {
					System.out.println(word + " appears in " + fileName);
				}
			}
		}
	}
	
	public void tester() {
		buildWordFileMap();
		System.out.println("Maximum number of files any word is in : " + maxNumber());
		System.out.println("All the words that are in max number of files "+ wordsInNumFiles(maxNumber()));
		System.out.println("Number of words that occur in 7 files "+ wordsInNumFiles(7).size());
		System.out.println("Number of words that occur in 4 files "+ wordsInNumFiles(4).size());
		printFilesIn("laid");
		printFilesIn("tree");
		/*for(String word:wordsInNumFiles(5)){
			if (word.equals("sad"))
				printFilesIn(word);
		}*/
	}

}
