package week2_assignments;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import edu.duke.*;

public class CodonCount {
	
	private Map<String,Long> codonMap = new HashMap<>();
	
	public static void main(String[] args){
		CodonCount codonCount = new CodonCount();
		//codonCount.buildCodonMap(2, "CGTTCAAGTTCAA");
		//codonCount.printCodonCounts(0, 100);
		codonCount.tester();
	}

	public void buildCodonMap(int start, String dna) {
		codonMap.clear();
		AtomicInteger splitCounter = new AtomicInteger(0);
		codonMap = dna.substring(start)
		                                    .chars()
		                                    .mapToObj(_char -> String.valueOf((char)_char))
		                                    .collect(Collectors.groupingBy(stringChar -> splitCounter.getAndIncrement() / 3 ,Collectors.joining()))
		                                    .values().stream()
		                                    .filter(s -> s.length() == 3)
		                                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
	}
	
	public String getMostCommonCodonKey() {
		return codonMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
	}
	public long getMostCommonCodonValue() {
		return codonMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
	}
	
	public void printCodonCounts(int start, int end) {
		codonMap.entrySet().stream().filter(e -> (e.getValue() >= start && e.getValue() <= end)).forEach(e-> System.out.println(e));
	}
	
	public void tester() {
		FileResource fr = new FileResource(); 
		StringBuilder sb =  new StringBuilder();
		for (String line : fr.lines()) {
			sb.append(line.trim().toUpperCase());			
		}
		String dna = sb.toString();
		//String dna = "CGTTCAAGTTCAA";
		
		int start = 0; int end = 2;
		while (start <= end) {
			buildCodonMap(start, dna);
			System.out.println("Reading frame starting with " + start + " results in " + codonMap.size() + " unique codons");
			System.out.println("and most commong codon is " + getMostCommonCodonKey() + " with count " + getMostCommonCodonValue());
			System.out.println("Counts of codon between 1 and 5 inclusive are ");
			printCodonCounts(0, (int)getMostCommonCodonValue()); 			
			System.out.println("----------------------------");
			start++;
		}
	}
}
