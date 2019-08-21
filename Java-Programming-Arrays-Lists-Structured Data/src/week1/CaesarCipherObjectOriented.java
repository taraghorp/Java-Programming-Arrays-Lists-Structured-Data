/**
 * Author: Tara G
 */

package week1;

public class CaesarCipherObjectOriented {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;
    
    public CaesarCipherObjectOriented(int key) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
        mainKey = key;
    }
  
    public String encrypt(String input) {
        
        StringBuilder encrypted = new StringBuilder(input);
        String alphabetLower = alphabet.toLowerCase();
        String shiftedLower = shiftedAlphabet.toLowerCase();
        for(int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(currChar);
            int idxLower = alphabetLower.indexOf(currChar);
            if(idx != -1){
                char newChar = shiftedAlphabet.charAt(idx);
                encrypted.setCharAt(i, newChar);
            }
            else if (idxLower != -1) {
                char newCharLower = shiftedLower.charAt(idxLower);
                encrypted.setCharAt(i, newCharLower);
            }
            
        }
        return encrypted.toString();
    }
    
    public String decrypt(String input) {
        //CaesarCipherOO ccOO = new CaesarCipherOO(26 - mainKey);
    	mainKey = 26 - mainKey;
        return encrypt(input);
        
    }
    
    
    public String encryptTwoKeys(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder(input);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetLower = alphabet.toLowerCase();
        String shiftedUpperKey1 = alphabet.substring(key1) + alphabet.substring(0,key1);
        String shiftedLowerKey1 = shiftedUpperKey1.toLowerCase();
        
        String shiftedUpperKey2 = alphabet.substring(key2) + alphabet.substring(0,key2);
        String shiftedLowerKey2 = shiftedUpperKey2.toLowerCase();
        for(int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(currChar);
            int idxLower = alphabetLower.indexOf(currChar);
            if(idx != -1){
                if (i % 2 == 0) {
                    char newChar = shiftedUpperKey1.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
                else {
                    char newChar2 = shiftedUpperKey2.charAt(idx);
                    encrypted.setCharAt(i, newChar2);
                }
            }
            else if (idxLower != -1) {
                if (i % 2 == 0) {
                    char newCharLower = shiftedLowerKey1.charAt(idxLower);
                    encrypted.setCharAt(i, newCharLower);
                }
                else {
                    char newCharLower2 = shiftedLowerKey2.charAt(idxLower);
                    encrypted.setCharAt(i, newCharLower2);
                }
            }
            
        }
        return encrypted.toString();
    
    }
    
    public void testEncryptTwoKeys() {
        System.out.println("String is 'First Legion,' key 1 is 23 and key 2 is 17");
        System.out.println("Expected result is Czojq Ivdzle");
        System.out.println();
        System.out.println("Testing encryptTwoKeys...");
        System.out.println("Result is " + encryptTwoKeys("First Legion", 23, 17));
        System.out.println();
        
        System.out.println("Quiz question #6, encrypt with two keys, key1 = 8 and key2 = 21");
        System.out.println("String being encrypted is 'At noon be in the conference room with your hat on for a surprise party. YELL LOUD!'");
       System.out.println("Result is " + encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 8, 21));
       
    }
    
    
}
