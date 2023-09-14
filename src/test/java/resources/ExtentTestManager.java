package resources;

import java.util.Hashtable;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	
    static Map<Integer, ExtentTest> extentTestMap = new Hashtable<>();
    static ExtentReports extentReports = ExtentManager.createExtentReports();
    
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }
    
    public static synchronized void startTest(String testName, String desc) {
        ExtentTest test = extentReports.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
    }
    
    
}
