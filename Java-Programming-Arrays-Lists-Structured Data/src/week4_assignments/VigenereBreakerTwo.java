package week4_assignments;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import edu.duke.*;


public class VigenereBreakerTwo {

	//English, Unknown Key Length
	
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
    
    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dict = new HashSet<String>(); 
        for (String line : fr.lines()) {
            dict.add(line.toLowerCase());
        }
        return dict;
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
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
    	Map<Integer,Integer> keysAndWordsMap = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toMap(Function.identity(), i -> {
    		int[] key = tryKeyLength(encrypted, i, 'e');             
            VigenereCipher vc = new VigenereCipher(key); 
            String decryptedMsg = vc.decrypt(encrypted); 
            return countWords(decryptedMsg, dictionary);
    	}));
    	
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
    	
    }

    public void breakVigenere () {
    	FileResource fr = new FileResource("data/secretmessage2.txt");
    	HashSet<String> dict = readDictionary(new FileResource("data/dictionaries/English"));
    	String encrypted = fr.asString();
    	String s = breakForLanguage(encrypted, dict);
    }
    
    public static void main(String[] args) {
    	VigenereBreakerTwo breaker = new VigenereBreakerTwo();
    	breaker.breakVigenere();
    }
	
}
