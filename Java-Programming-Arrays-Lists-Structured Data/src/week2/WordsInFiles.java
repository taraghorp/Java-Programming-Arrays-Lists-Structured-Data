/**
 * Author: Tara G.
 */

package week2;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import edu.duke.*;

public class WordsInFiles {

	public static void main(String[] args) {		
		WordsInFiles wordsInFiles = new WordsInFiles();
		wordsInFiles.uniqueWords();
	}
	
	public void uniqueWords() {
		FileResource fr = new FileResource(); //likeit.txt
		//get the stream of words
		//then map the stream to lowercase words
		//then count number of occurrences of each word and put that into a map
		Map<String,Long> wordFreq =  StreamSupport.stream(fr.words().spliterator(), false)
		.map(word -> word.toLowerCase())
		.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		int numberOfUniqueWords = wordFreq.size(); //4932
		System.out.println("There are " + numberOfUniqueWords);
		
		String wordMostOften = wordFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey(); //the
		System.out.println("word most often " + wordMostOften);
		Long mostOftenTimes = wordFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue(); //692
		System.out.println("word most often times " + mostOftenTimes);
		
	}

}
