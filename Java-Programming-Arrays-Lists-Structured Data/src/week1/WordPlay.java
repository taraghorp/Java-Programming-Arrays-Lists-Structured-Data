/**
 * Author: Tara G
 */

package week1;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WordPlay {

	public static void main(String[] args) {
		WordPlay wordPlay = new WordPlay();
		//wordPlay.testIsVowel();
		//wordPlay.testReplaceVowels();
		wordPlay.testEmphasize();

	}
	
	public boolean isVowel (char ch) {
		if (ch == 'a' ||
			ch == 'e'||
			ch == 'i'||
			ch == 'o'||
			ch == 'u'||
			ch == 'A' ||
			ch == 'E'||
			ch == 'I'||
			ch == 'O'||
			ch == 'U')
			return true;
		return false;
	}
	
	public void testIsVowel() {
		System.out.println("a:" + isVowel('a'));
		System.out.println("I:" + isVowel('I'));
		System.out.println("b:" + isVowel('b'));
		System.out.println("Z:" + isVowel('Z'));
	}
	
	public String replaceVowels(String phrase, char ch) {
		
		return phrase.chars() // get a stream of chars from string
		.mapToObj(s -> (char)s) //convert to char
		.map(c -> isVowel(c)?ch:c) //check if vowel
		.map(s -> s.toString()) //convert back to string
		.collect(Collectors.joining()); //collect
	}
	
	public void testReplaceVowels() {
		System.out.println("Hello World and * : " + replaceVowels("Hello World", '*'));
		System.out.println("Live Long and Prosper and a : " + replaceVowels("Live Long and Prosper", 'a'));
	}
	
	public String emphasize(String phrase, char ch) {
		
		StringBuilder sb = new StringBuilder();
		
		//using range based Integer stream to loop over
		IntStream.range(0,phrase.length()).forEach(index -> {
			if (((index + 1) % 2 == 0) && (phrase.charAt(index) == ch || phrase.charAt(index) == Character.toUpperCase(ch))) { //even position ?
				sb.append("+");
			} else if (((index + 1) % 2 != 0) && (phrase.charAt(index) == ch || phrase.charAt(index) == Character.toUpperCase(ch))) { //odd position ?
				sb.append("*");
			} else {
				sb.append(phrase.charAt(index));
			}
		});
		
		return sb.toString();
	}
	
	public void testEmphasize() {
		System.out.println(emphasize("dna ctgaaactga", 'a'));
		System.out.println(emphasize("Mary Bella Abracadabra", 'a'));
	}

}
