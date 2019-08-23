package week3;


/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public static void main(String[] args) {
    	Tester tester = new Tester();
    	//tester.testLogAnalyzer();
    	//tester.testUniqueIP();
    	//tester.testprintAllHigherThanNum();
    	//tester.testuniqueIPVisitsOnDay();
    	tester.testCountUniqueIPsInRange();
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("data/short-test_log");
    }
    
    public void testUniqueIP() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("data/weblog2_log");
        int uniqueIPs = logAnalyzer.countUniqueIPs();
    }
    
    public void testprintAllHigherThanNum() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
    	logAnalyzer.readFile("data/weblog1_log");
    	logAnalyzer.printAllHigherThanNum(400);
    }
    
    public void testuniqueIPVisitsOnDay() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
    	logAnalyzer.readFile("data/weblog2_log");
    	ArrayList<LogEntry> records = logAnalyzer.uniqueIPVisitsOnDay("Sep 24");
    	records.stream().forEach(e -> System.out.println(e));
    }
    
    public void testCountUniqueIPsInRange() {
    	LogAnalyzer logAnalyzer = new LogAnalyzer();
    	logAnalyzer.readFile("data/weblog2_log");
    	int count = logAnalyzer.countUniqueIPsInRange(400, 499);
    	System.out.println("here");
    	count = logAnalyzer.countUniqueIPsInRange(300, 399);
    	System.out.println("here");
    }
}
