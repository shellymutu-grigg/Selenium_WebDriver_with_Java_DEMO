package resources;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

// https://www.swtestacademy.com/extent-reports-in-selenium-with-testng/
public class ExtentManager {
	
    public static final ExtentReports extentReports = new ExtentReports();
    
    public synchronized static ExtentReports createExtentReports() {
    	Date calendarDate = Calendar.getInstance().getTime();  
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
	    String date = dateFormat.format(calendarDate);
	    String filePathName = System.getProperty("user.dir") + "//reports//Test Report_"+ date.replace(":", "_").replace(" ", "_") +".html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(filePathName);
    	reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setReportName("Amazon Automation Test Results");
		reporter.config().setTimeStampFormat("EEEE dd MMMM yyyy HH:mm:ss '('zzz')'");
		reporter.config().setDocumentTitle("Test Results");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}
