/**
 * Author: Tara G.
 */

package week2;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import edu.duke.*; 

public class Characters { 

       public static void main(String[] args) {
              Characters characters = new Characters();
              characters.mostSpeakingPart();
              characters.speakingLinesBetween(10, 15);
       }     

       public void mostSpeakingPart() {
    	   
              FileResource fr = new FileResource();
              //Regex pattern to search for character names which always appear in uppercase
              Pattern p = Pattern.compile("^[A-Z]");
              
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
              Pattern p = Pattern.compile("^[A-Z]");
              
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