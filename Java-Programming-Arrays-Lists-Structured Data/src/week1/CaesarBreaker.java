package week1;

import edu.duke.*;

public class CaesarBreaker {
	
	public static void main(String[] args) {
		CaesarBreaker breaker = new CaesarBreaker();
		breaker.quiz2();
	}
    
    public int[] countLetters(String message) {
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int i = 0; i < message.length(); i++) {
            char ch = Character.toLowerCase(message.charAt(i));
            int index = alph.indexOf(ch);
            if(index != -1) {
                counts[index] += 1;
            }
        }
        return counts;
    }
    
    public int maxIndex(int[] iarr) {
        int maxIndex = 0;
        for (int i = 0; i < iarr.length; i++) {
            if (iarr[i] > iarr[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    
    public String decrypt(String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        int[] freqs = countLetters(encrypted);
        int maxIndex = maxIndex(freqs);
        int decryptKey = maxIndex - 4;
        if (maxIndex < 4) {
            decryptKey = 26 - (4-maxIndex);
        }
        String message = cc.encrypt(encrypted, 26 - decryptKey);
        return cc.encrypt(encrypted, 26-decryptKey);
    }
    
    public void testDecrypt() {
        CaesarCipher cc = new CaesarCipher();
        String OrigMessage = "First Leeeeeeeeegion";
        int key = 4;
        System.out.println("Encrypting message: " + OrigMessage + " with an encryption key of " + key);
        
        String encryptedMsg = cc.encrypt(OrigMessage, key);
        System.out.println("Encrypted message is " + encryptedMsg);
        
        System.out.println("Now to test the decrypt method using " + encryptedMsg + " as the input");
        System.out.println("Decrypt should return 'First Legion'");
        String decrypted = decrypt(encryptedMsg);
        System.out.println("Decrypted message is " + decrypted);
    }   
    
    public String halfOfString(String message, int start) {
        StringBuilder msg = new StringBuilder(message);
        StringBuilder half = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char currChar = msg.charAt(i);
            int idx = message.indexOf(start);
            if ((start + i) < message.length()) {
                if (i % 2 == 0) {
                    char newChar = message.charAt(start + i);
                    half.append(newChar);
                }
            }
        }
        return half.toString();
    
    }
    
    public void testhalfOfString() {
        String test1 = "Qbkm Zgis";
        int start1 = 0;
        System.out.println("Testing halfOfString method...");
        System.out.println("Going to use the string " + test1 + " with the starting index " + start1);
        String expected = "Qk gs";
        System.out.println("Expecting result to be " + expected);
        String result = halfOfString(test1, start1);
        System.out.println("Result is " + result);
        
        int newStart = 1;
        System.out.println("Testing new starting point...");
        System.out.println("Going to use the string " + test1 + " with the starting index " + newStart);
        String newExpected = "bmZi";
        System.out.println("Expecting result to be " + newExpected);
        String newResult = halfOfString(test1, newStart);
        System.out.println("Result is " + newResult);
        
    }
    
    public int getKey(String s) {
        int[] freqs = countLetters(s);
        int maxIndex = maxIndex(freqs);
        int decryptKey = maxIndex - 4;
        if (maxIndex < 4) {
            decryptKey = 26 - (4-maxIndex);
        }
    
        return decryptKey;
    }
    
    public String decryptTwoKeys(String encrypted) {
        CaesarCipher cipher = new CaesarCipher();
        String half1 = halfOfString(encrypted, 0);
        String half2 = halfOfString(encrypted, 1);
        
        int keyHalf1 = getKey(half1);
        int keyHalf2 = getKey(half2);
        
        System.out.println("Key for " + half1 + " is " + keyHalf1);
        System.out.println("Key for " + half2 + " is " + keyHalf2);
        
        String message = cipher.encryptTwoKeys(encrypted, 26 - keyHalf1, 26 - keyHalf2);
        return message;
    }
    
    public void quiz2() {
        CaesarCipher cc = new CaesarCipher();
        
        /*System.out.println("Quiz 2, question 8:  The following phrase was encrypted with the two key " +
        "encryption method we discussed using the two keys 2 and 20. What is the decrypted message? " +
        "Hfs cpwewloj loks cd Hoto kyg Cyy.");
        String encrypted = "Hfs cpwewloj loks cd Hoto kyg Cyy.";
        int key1 = 14;
        int key2 = 24;
        String decrypted = cc.encryptTwoKeys(encrypted, 26-key1, 26-key2);
        System.out.println("Decrypted message is " + decrypted);
        
        System.out.println("Quiz 2, question 9:  Determine the following phrase that is encrypted with " +
        "the two key encryption method discussed in the programming exercise. " +
        "(You’ll need to figure out which keys were used to encrypt it.) " +
        "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!");

        encrypted = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        System.out.println("Running decryptTwoKeys method...");
        decrypted = decryptTwoKeys(encrypted);
        
        System.out.println("Decrypted message: " + decrypted);*/
        
        System.out.println("Quiz 2, question # 10: The file is encrypted with the two key encryption " + 
        "method we discussed. You will need to decrypt the complete file by figuring out which keys " +
        "were used to encrypt it.");
        
        //System.out.println("Choose the file: mysteryTwoKeysPractice.txt");
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        String decrypted = decryptTwoKeys(encrypted);
        System.out.println("Decrypted message is: ");
        System.out.println(decrypted);
        
    }
}
