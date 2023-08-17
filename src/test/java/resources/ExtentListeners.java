package resources;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testComponents.TestSetup;
 
public class ExtentListeners extends TestSetup implements ITestListener {
	Date calendarDate = Calendar.getInstance().getTime();  
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-HH_mm_ss");  
    String date = dateFormat.format(calendarDate);
    String filePathName = System.getProperty("user.dir") + "//reports//TestReport_"+ date.toString().replace(":", "_").replace(" ", "_") +".html";
    
    ExtentReports extentReports;
    ExtentTest extentTest;
    
    ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

    @Override
    public void onStart(ITestContext context) {
    	ExtentSparkReporter reporter = new ExtentSparkReporter(filePathName);
    	reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setReportName("Amazon Automation Test Results");
		reporter.config().setDocumentTitle("Test Results");
		
		extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Author", "Shelly Mutu-Grigg");
		extentReports.setSystemInfo("Activity Name", "Selenium with Java");
		extentReports.setSystemInfo("Test Case Name:", "Selenium with Java");
		
		extentTest = extentReports.createTest("Test Suite: " + context.getSuite().getName());
        extentTest.assignAuthor("Shelly Mutu-Grigg");
        testReport.set(extentTest);
    }

    @Override
    public void onTestStart(ITestResult result) {
    	
    }
 
    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName=result.getMethod().getMethodName();
        String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " - PASSED"+"</b>";      
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        testReport.get().pass(markup);
    }
 
    @Override
    public void onTestFailure(ITestResult result) { 
    	ITestContext context = result.getTestContext();
        webDriver = (WebDriver) context.getAttribute("WebDriver");
     	String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
     	testReport.get().fail("<b>Exception Occured:</b> <details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Click to see Stack Trace"
                + "</font>" + "</b >" + "</summary>" +exceptionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
     	try {
           String fileName = captureScreenshot(result.getMethod().getMethodName());
           extentTest.fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
            MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        } catch (Exception e) {
        	System.out.println(MessageFormat.format("Screenshot capture failed with error: {0}", e.getMessage())); 
        }
        String failureLog="TEST CASE FAILED";
        Markup markup = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
        testReport.get().log(Status.FAIL, markup);
    }
 
    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName=result.getMethod().getMethodName();
        String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " - SKIPPED"+"</b>";     
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
        extentTest.skip(markup);
        testReport.get().skip(markup);
    }
 
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
 
    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
