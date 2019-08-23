/**
 * Author: Tara G.
 */

package week2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import edu.duke.*; 

public class Characters { 
	
	Map<String,Integer> characterLines = new HashMap<>();

       public static void main(String[] args) {
              Characters characters = new Characters();
              characters.speakingLines();
              /*characters.mostSpeakingPart();
              characters.speakingLinesBetween(0, 100);
              System.out.println("========================");*/
              characters.speakingParts();
       } 
       
       private void addLineToCharacter(String character) {
    	   if (character == null || character.startsWith("ACT"))
    		   return;
    	   if (characterLines.containsKey(character)) {
    		   int lines = characterLines.get(character);
    		   characterLines.put(character, lines + 1);
    	   } else {
    		   characterLines.put(character, 1);
    	   }
       }
       
       public void speakingLines() {
    	   FileResource fr = new FileResource();
    	   String currentChar = null;
    	   int lines = 0;
    	   for (String line : fr.lines()) {
    		   if (!line.isEmpty()) {
    			   if (line.indexOf(". ") > 0) {    		   
	    			   String w = line.substring(0, line.indexOf(". ")).trim();
	    			   if (w.toUpperCase().equals(w)) { // we have a character name at the beginning of line
	    				   currentChar = w;
	    				   addLineToCharacter(currentChar);
	    			   } else {
	    				   addLineToCharacter(currentChar);
	    			   }
	    		   } else {
	    			   addLineToCharacter(currentChar);
	    		   }	    		   
    		   }
    	   }
    	   
    	   Map<String,Integer> characterLinesSorted = characterLines.entrySet().stream()
           .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
           .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                   (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    	   
    	   
    	   System.out.println("Lines spoken by each character");
    	   System.out.println(characterLinesSorted);
       }
       
       
       public void speakingParts() {
    	   FileResource fr = new FileResource();
           
           Map<String,Long> speakingMap = StreamSupport.stream(fr.lines().spliterator(),false)
           .filter(line -> line.indexOf(". ") > 0)
           .map(line -> line.substring(0, line.indexOf(". ")).trim())
           .filter(line -> line.toUpperCase().equals(line) && !line.startsWith("ACT"))
           .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
           
           Map<String,Long> speakingMapSorted = speakingMap.entrySet().stream()
                   .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                           (oldValue, newValue) -> oldValue, LinkedHashMap::new));
           
           System.out.println("Speaking parts by each character");
    	   System.out.println(speakingMapSorted);
           
       }

       public void mostSpeakingPart() {
    	   
              FileResource fr = new FileResource();
                            
              Optional<Entry<String,Long>> maxEntry = StreamSupport.stream(fr.lines().spliterator(),false)
              .filter(line -> line.indexOf(". ") > 0)
              .map(line -> line.substring(0, line.indexOf(". ")).trim())
              .filter(line -> line.toUpperCase().equals(line))
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
              .entrySet()
              .stream()
              .max((Entry<String,Long> e1, Entry<String,Long> e2) -> e1.getValue().compareTo(e2.getValue()));       

              System.out.println("Person with most speaking part: " + maxEntry.get().getKey());
              System.out.println(maxEntry.get().getKey() + " has " + maxEntry.get().getValue() + " speaking parts.");
       }

       public void speakingLinesBetween(int min, int max) {
    	   
              FileResource fr = new FileResource();
                            
              Map<String,Long> speakersBetween = StreamSupport.stream(fr.lines().spliterator(),false)
              .filter(line -> line.indexOf(". ") > 0)
              .map(line -> line.substring(0, line.indexOf(". ")).trim())
              .filter(line -> line.toUpperCase().equals(line))
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
              .entrySet()
              .stream()
              .filter(e -> (e.getValue() >= min && e.getValue() <= max))
              .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

              System.out.println(speakersBetween);
      }
       
      
}