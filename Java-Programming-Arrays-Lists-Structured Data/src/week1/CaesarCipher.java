/**
 * Author: Tara G.
 */

package week1;

import edu.duke.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CaesarCipher {

	public static void main(String[] args) {
		
		CaesarCipher caesercipher = new CaesarCipher();
		//System.out.println(caesercipher.encrypt("Can you imagine life WITHOUT the internet AND computers in your pocket?", 15));
		//System.out.println(caesercipher.encrypt("First Legion", 23));
		//System.out.println(caesercipher.encrypt("First Legion", 17));
		//caesercipher.testCaeser();
		System.out.println(caesercipher.encryptTwoKeys("Can you imagine life WITHOUT the internet AND computers in your pocket?", 21,8));
	}
	
	public int getShifted(int c, int key) {
		
		if (Character.isLetter(c) && Character.getType(c) == Character.UPPERCASE_LETTER) {
			return (c + key - (int)'A') % 26 + (int)'A';
		} else if (Character.isLetter(c) && Character.getType(c) == Character.LOWERCASE_LETTER) {
			return (c + key - (int)'a') % 26 + (int)'a';
		} else {
			return c;
		}
	}
	
	public String encrypt(String input, int key) {
		
		return input.chars()
		.mapToObj(c -> (char)getShifted(c,key))
		.map(s -> s.toString()) 
		.collect(Collectors.joining()); 
	}
	
	public String encryptTwoKeys(String input, int key1, int key2) {
		
		StringBuilder sb = new StringBuilder();
		IntStream.range(0,input.length()).forEach(index -> {
			if ((index + 1) % 2 != 0) {
				sb.append((char)(getShifted(input.charAt(index),key1)));
			} else if ((index + 1) % 2 == 0) {
				sb.append((char)(getShifted(input.charAt(index),key2)));
			}
		});
		return sb.toString();
	}
	
	public void testCaeser() {
		int key = 23;
		FileResource fr = new FileResource();
		String message = fr.asString();
		String encrypted = encrypt(message, key);
		System.out.println("key is " + key + "\n" + encrypted);
	}
}
