package week3_assignments;


import java.util.List;
import java.util.Map;


public class Tester
{
      
    public static void main(String[] args) {
    	Tester tester = new Tester();
    	//tester.testMostNumberVisitsByIP();
    	//tester.testIPsMostVisits();
    	//tester.testIPsForDays();
    	//tester.testDayWithMostIPVisits();
    	tester.testIPsWithMostVisitsOnDay();
    }
    
    public void testMostNumberVisitsByIP() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("data/weblog2_log");
        Map<String,Long> ipVisitsMap = logAnalyzer.countVisitsPerIP();
        int mostVisits = logAnalyzer.mostNumberVisitsByIP(ipVisitsMap);
        System.out.println("Most Visits: " + mostVisits);
    }
    
    public void testIPsMostVisits() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("data/weblog2_log");
        Map<String,Long> ipVisitsMap = logAnalyzer.countVisitsPerIP();
        List<String> ipsWithMostVisits = logAnalyzer.iPsMostVisits(ipVisitsMap);
        ipsWithMostVisits.forEach(e -> System.out.println(e));
    }
    
    public void testIPsForDays() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("data/weblog3-short_log");
        Map<String,List<String>> IPsForDays = logAnalyzer.iPsForDays();
        System.out.println("here");
    }
    
    public void testDayWithMostIPVisits() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("data/weblog2_log");
        Map<String,List<String>> IPsForDays = logAnalyzer.iPsForDays();
        String maxDate = logAnalyzer.dayWithMostIPVisits(IPsForDays);
        System.out.println("Max day: " + maxDate);
    }
    
    public void testIPsWithMostVisitsOnDay() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("data/weblog2_log");
        Map<String,List<String>> IPsForDays = logAnalyzer.iPsForDays();
        List<String> ips = logAnalyzer.iPsWithMostVisitsOnDay(IPsForDays,"Sep 30");
        ips.forEach(e -> System.out.println(e));
    	
    }
    
    
}