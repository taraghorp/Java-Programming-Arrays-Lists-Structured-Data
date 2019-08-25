package week4_assignments;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import edu.duke.*;

public class VigenereBreakerThree {
	
	public static void main(String[] args) {
    	VigenereBreakerThree breaker = new VigenereBreakerThree();
    	breaker.breakVigenere();
    }
	
	public char mostCommonCharIn(HashSet<String> dictionary) {
		Map<Character,Integer> charCountMap = dictionary.stream()
		.map(word -> word.chars()).map(String::valueOf).collect(Collectors.joining())
		.chars().boxed()
		.collect(Collectors.toMap(k -> Character.valueOf((char) k.intValue()),v -> 1,Integer::sum)); 
			
		return Collections.max(charCountMap.entrySet(), Comparator.comparingInt(e -> e.getValue())).getKey();
	}
	
	public String breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {
				
		Map<String,Integer> languageWordsMap = languages.entrySet().stream()
		.collect(Collectors.toMap(e -> e.getKey(), e -> breakForLanguage(encrypted,e.getValue())));
		
		//select the max number of understood words from all languages
		String likelyLanguage = Collections.max(languageWordsMap.entrySet(), Comparator.comparingInt(e -> e.getValue())).getKey();
		int understoodWords = Collections.max(languageWordsMap.entrySet(), Comparator.comparingInt(e -> e.getValue())).getValue();
		Character mostCommonChar = mostCommonCharIn(languages.get(likelyLanguage));
		
		Map<Integer,Integer> keysAndWordsMap = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toMap(Function.identity(), i -> {
    		int[] key = tryKeyLength(encrypted, i, mostCommonChar);             
            VigenereCipher vc = new VigenereCipher(key); 
            String decryptedMsg = vc.decrypt(encrypted); 
            return countWords(decryptedMsg, languages.get(likelyLanguage));
    	}));
    	
    	//find the highest number of understood words from the above map 
    	Map.Entry<Integer, Integer> maxEntry = keysAndWordsMap.entrySet().stream().max(Map.Entry.comparingByValue()).get();
    	//get the length of key
    	int keyLength = maxEntry.getKey();
    	//Use this key length to break
    	int[] key = tryKeyLength(encrypted, keyLength, mostCommonChar);
    	System.out.println(key);
    	VigenereCipher cipher = new VigenereCipher(key);
    	String decrypted = cipher.decrypt(encrypted);
    	System.out.println("Decrypted Message: " + decrypted);
    	return decrypted;
		
	}
	
	public String sliceString(String message, int whichSlice, int totalSlices) {
        int len = message.length();
        
        int limit = (len - whichSlice) / totalSlices + Math.min((len - whichSlice) % totalSlices, 1);
        
        String s = Stream.iterate(whichSlice, i -> i + totalSlices)
        .limit(limit)
        .map(message::charAt)
        .map(String::valueOf)
        .collect(Collectors.joining());
        return s;
    }
	
	public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
    	//notice the word parallel, we are trying different key lengths in parallel
    	
    	CaesarCracker ccr = new CaesarCracker(mostCommon);
    	List<Integer> keyList = IntStream.range(0, klength).boxed().parallel()
    	.map(i -> {
    		return ccr.getKey(sliceString(encrypted,(int)i,klength));
    	})
    	.collect(Collectors.toList());
    	return keyList.stream().mapToInt(x -> x).toArray();
        
    }
	
	public int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        for (String word : message.split("\\W")) {
            if (dictionary.contains(word.toLowerCase())) {
                count ++;
            }
        }
        return count;
    }
	
	public int breakForLanguage(String encrypted, HashSet<String> dictionary) {
		
		Character mostCommonChar = mostCommonCharIn(dictionary);
    	return IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toMap(Function.identity(), i -> {
    		int[] key = tryKeyLength(encrypted, i, mostCommonChar);             
            VigenereCipher vc = new VigenereCipher(key); 
            String decryptedMsg = vc.decrypt(encrypted); 
            return countWords(decryptedMsg, dictionary);
    	})).entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue().intValue();
    	/*
    	
    	//find the highest number of understood words from the above map 
    	Map.Entry<Integer, Integer> maxEntry = keysAndWordsMap.entrySet().stream().max(Map.Entry.comparingByValue()).get();
    	//get the length of key
    	int keyLength = maxEntry.getKey();
    	//Use this key length to break
    	int[] key = tryKeyLength(encrypted, keyLength, 'e');
    	System.out.println(key);
    	VigenereCipher cipher = new VigenereCipher(key);
    	String decrypted = cipher.decrypt(encrypted);
    	System.out.println("Decrypted Message: " + decrypted);
    	return decrypted;
    	*/
    	
    }
	
	public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dict = new HashSet<String>(); 
        for (String line : fr.lines()) {
            dict.add(line.toLowerCase());
        }
        return dict;
    }
	
	public void breakVigenere () {
		HashMap<String, HashSet<String>> langDicts = new HashMap<String, HashSet<String>>();
        System.out.println("Choose all dictionaries from the appropriate folder");
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f.getPath()); // creates a file resource based on the path of the file
            String fileName = f.getName(); // gets the name of the file, which should be the language name
            HashSet<String> currDict = readDictionary(fr); // gets the dictionary and puts it into a hashset from the method readDictionary
            langDicts.put(fileName, currDict);
            System.out.println("Mapped the language \"" + fileName + "\" to its dictionary");
        }
        
        System.out.println("Choose an encrypted file or file to encrypt");
        FileResource fr = new FileResource();
        String fileText = fr.asString();
        int testKeyLength = 38;
        
        breakForAllLanguages(fileText, langDicts);
    }

}
