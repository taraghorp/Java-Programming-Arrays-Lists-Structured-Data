package week2_assignments;

import edu.duke.*;
import java.util.*;

public class GladLibMap {
	/*private ArrayList<String> adjectiveList;
	private ArrayList<String> nounList;
	private ArrayList<String> colorList;
	private ArrayList<String> countryList;
	private ArrayList<String> nameList;
	private ArrayList<String> animalList;
	private ArrayList<String> timeList;
	private ArrayList<String> verbList;
	private ArrayList<String> fruitList;*/
	HashMap<String, ArrayList<String>> categoryWordsMap = new HashMap();
	
	private ArrayList<String> usedWords = new ArrayList<>();
	private ArrayList<String> usedLabels = new ArrayList();
	
	private int wordsReplaced = 0;
	
	private Random myRandom;
	
	private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
	private static String dataSourceDirectory = "datalong";
	
	public GladLibMap(){
		initializeFromSource(dataSourceDirectory);
		myRandom = new Random();
	}
	
	public GladLibMap(String source){
		initializeFromSource(source);
		myRandom = new Random();
	}
	
	public static void main(String[] args) {
		GladLibMap gl = new GladLibMap();
		gl.makeStory();
	}
	
	private void initializeFromSource(String source) {
		String[] categories = new String[] {"adjective","noun","color","country","name","animal","timeframe","verb","fruit"};
		for(String cat : categories) {
			categoryWordsMap.put(cat, readIt(source+"/" + cat + ".txt"));
		}		
	}
	
	private String randomFrom(ArrayList<String> source){
		int index = myRandom.nextInt(source.size());
		return source.get(index);
	}
	
	private String getSubstitute(String label) {
		if (label.equals("number")){
			return ""+myRandom.nextInt(50)+5;
		}
		if (categoryWordsMap.containsKey(label)) {
			usedLabels.add(label);
			return randomFrom(categoryWordsMap.get(label));
		} else return "**UNKNOWN**";
	}
	
	private String processWord(String w){
		int first = w.indexOf("<");
		int last = w.indexOf(">",first);
		if (first == -1 || last == -1){
			return w;
		}
		String prefix = w.substring(0,first);
		String suffix = w.substring(last+1);
		String sub = getSubstitute(w.substring(first+1,last));
		while (usedWords.contains(sub)) {
			System.out.println(sub + " was used already");
			sub = getSubstitute(w.substring(first+1,last));
		}
		usedWords.add(sub);
		System.out.println(w + " was replaced by " + sub);
		wordsReplaced ++;
		return prefix+sub+suffix;
	}
	
	private void printOut(String s, int lineWidth){
		int charsWritten = 0;
		for(String w : s.split("\\s+")){
			if (charsWritten + w.length() > lineWidth){
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w+" ");
			charsWritten += w.length() + 1;
		}
	}
	
	private String fromTemplate(String source){
		String story = "";
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		return story;
	}
	
	private ArrayList<String> readIt(String source){
		ArrayList<String> list = new ArrayList<String>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		return list;
	}
	
	public int totalWordsInMap() {
		int total = 0;
		for (Map.Entry<String, ArrayList<String>> entry : categoryWordsMap.entrySet()){
			total += entry.getValue().size();
		}
		return total;
	}
	
	public int totalWordsConsidered() {
		int total = 0;
		for(String usedLabel : usedLabels) {
			total += categoryWordsMap.get(usedLabel).size();
		}
		return total;
	}
	
	public void makeStory(){
	    System.out.println("\n");
	    usedWords.clear();
	    usedLabels.clear();
	    wordsReplaced = 0;
		String story = fromTemplate("datalong/madtemplate2.txt");
		printOut(story, 60);
		System.out.println("\n");
		System.out.println("Total # of words replaced: " + wordsReplaced);
		System.out.println("Total words in map: " + totalWordsInMap());
		System.out.println("Total words considerd: " + totalWordsConsidered());
	}
	
	


}