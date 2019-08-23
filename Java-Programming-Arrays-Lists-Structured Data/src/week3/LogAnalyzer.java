package week3;

/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.duke.*;

class LogPredicate implements Predicate<LogEntry> {
	LogEntry previous;
	Comparator<LogEntry> c = Comparator.comparing(LogEntry::getIpAddress);
	public boolean test(LogEntry p) {
		if(previous!=null && c.compare(previous, p)==0)
			return false;
		previous=p;
		return true;
	}
}

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
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIPs() {    
    	 Comparator<LogEntry> c = Comparator.comparing(LogEntry::getIpAddress);
    	 return (int)(records.stream().sorted(c).filter(new LogPredicate()).count());    	 
     }
     
     public void printAllHigherThanNum(int num) {
    	 records.stream().filter(logEntry -> logEntry.getStatusCode() > num).forEach(e-> System.out.println(e));
     }
     
     public ArrayList<LogEntry> uniqueIPVisitsOnDay(String someday) {    	 
    	 Comparator<LogEntry> c = Comparator.comparing(LogEntry::getIpAddress);
    	 return records.stream().sorted(c).filter(logEntry -> logEntry.getAccessTime().toString().contains(someday))
    	 .filter(new LogPredicate()).collect(Collectors.toCollection(ArrayList::new));
     }
     
     public int countUniqueIPsInRange(int low, int high) {
    	 Comparator<LogEntry> c = Comparator.comparing(LogEntry::getIpAddress);
    	 return (int)(records.stream().sorted(c).filter(logEntry -> (logEntry.getStatusCode() >= low && logEntry.getStatusCode() <= high))
    			 .filter(new LogPredicate()).count());
     }
     
     
}


