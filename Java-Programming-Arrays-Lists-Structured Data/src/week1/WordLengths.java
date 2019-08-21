/**
 * Author: Tara G
 */

package week1;

import edu.duke.*;

public class WordLengths {

	public static void main(String[] args) {
		WordLengths wordLengths = new WordLengths();
		wordLengths.testCountWordLengths();

	}
	
	public void countWordLengths(FileResource resource, int[] counts) {
		for(String word : resource.words()) {
			int wordlength = word.length();
            
            if (!Character.isLetter(word.charAt(word.length()-1))) {
                wordlength--;
            }
            if (wordlength >= counts.length) {
                wordlength = counts.length - 1;
            }
            
            if (wordlength > 0) {
                counts[wordlength] += 1;
            }
		}
	}
	
	public int indexOfMax(int[] values) {
        int maxIndex = 0;
        
        for (int i = 0; i < values.length; i++) {
            if (values[i] > 0 && values[i] >= values[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
	
	public void testCountWordLengths() {
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        
        countWordLengths(fr, counts);
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] != 0) {
                System.out.println(counts[i] + " words of length " + i + ": ");
            }
        }
        
        int maxIndex = indexOfMax(counts);
        
        System.out.println("The most common word length in the file is " + maxIndex);
	}

}
