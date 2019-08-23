package week3_assignments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.duke.FileResource;
import week3.LogEntry;
import week3.WebLogParser;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource fr = new FileResource(filename);
         for(String line : fr.lines()) {
        	 records.add(WebLogParser.parseEntry(line));
         }
     }
     
     public Map<String,Long> countVisitsPerIP() {
    	 return records.stream().collect(Collectors.groupingBy(e -> e.getIpAddress(), (Collectors.counting())));   			 
     }
     
     public int mostNumberVisitsByIP(Map<String,Long> ipVisistMap) {
    	 return Collections.max(ipVisistMap.values()).intValue();   	 
     }
     
     public List<String> iPsMostVisits(Map<String,Long> ipVisistMap){
    	 Long max = Collections.max(ipVisistMap.values());
    	 return ipVisistMap.entrySet().stream()
    			    .filter(entry -> entry.getValue() == max)
    			    .map(entry -> entry.getKey())
    			    .collect(Collectors.toList());
     }
     
     public Map<String,List<String>> iPsForDays() {
    	 SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd");
    	 return records.stream().collect(Collectors.groupingBy(e -> dateformat.format(e.getAccessTime()), Collectors.mapping(e -> e.getIpAddress(), Collectors.toList())));    	 
     }
     
     public String dayWithMostIPVisits(Map<String,List<String>> iPsForDays) {
    	 return Collections.max(iPsForDays.entrySet(), Comparator.comparingInt(e -> e.getValue().size())).getKey();
     }
     
     public List<String> iPsWithMostVisitsOnDay(Map<String,List<String>> iPsForDays, String someday) {
    	 List<String> ips = iPsForDays().entrySet().stream()
    			 .filter(e -> e.getKey().equals(someday))
    			 .map(e -> e.getValue())
    			 .flatMap(List::stream)
    			 .collect(Collectors.toList());
    	 Map<String,Long> temp = ips.stream().collect(Collectors.groupingBy(Function.identity(), (Collectors.counting()))); 
    	 return iPsMostVisits(temp);
    	 
     }
     
}
