package resources;

import java.text.MessageFormat;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import testComponents.TestSetup;
 
public class ExtentListeners extends TestSetup implements ITestListener, IInvokedMethodListener {
		
	ExtentReports extentReports;    
    ExtentTest extentTest;
    
    ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

	@Override
    public void onStart(ITestContext context) {
		extentReports = (ExtentReports) context.getAttribute("ExtentReports");
		extentTest = extentReports.createTest("Test Case Name: " + context.getName());
        extentTest.assignAuthor("Shelly Mutu-Grigg");
        context.setAttribute("ExtentTest", extentTest);
        testReport.set(extentTest);
        context.setAttribute("TestReport", testReport);
    }	

	@Override
    public void onTestStart(ITestResult result) {

    }
 
	@SuppressWarnings("unchecked")
	@Override
    public void onTestSuccess(ITestResult result) {
    	ITestContext context = result.getTestContext();
        webDriver = (WebDriver) context.getAttribute("WebDriver");
        extentTest = (ExtentTest)context.getAttribute("ExtentTest");
        testReport = (ThreadLocal<ExtentTest>)context.getAttribute("TestReport");       
    	try {
            String fileName = captureScreenshot(result.getMethod().getMethodName());
            extentTest.pass("<font color=" + "green>" + "Screenshot of success" + "</font>",
             MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
         } catch (Exception e) {
         	System.out.println(MessageFormat.format("Screenshot capture failed with error: {0}", e.getMessage())); 
         } 
    	testReport.get().log(Status.PASS, MarkupHelper.createLabel(result.getMethod().getMethodName() + " PASS", ExtentColor.GREEN));
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public void onTestFailure(ITestResult result) { 
    	ITestContext context = result.getTestContext();
        webDriver = (WebDriver) context.getAttribute("WebDriver");
        extentTest = (ExtentTest)context.getAttribute("ExtentTest");
        testReport = (ThreadLocal<ExtentTest>)context.getAttribute("TestReport");
     	String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
     	testReport.get().fail("Exception Occured: <details>" + "<summary>" +  "<font color=" + "red>" + "Click here to see Stack Trace"
                + "</font>" + "</summary>" + exceptionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
     	try {
           String fileName = captureScreenshot(result.getMethod().getMethodName());
           extentTest.fail("<font color=" + "red>" + "Screenshot of failure" + "</font>",
            MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        } catch (Exception e) {
        	System.out.println(MessageFormat.format("Screenshot capture failed with error: {0}", e.getMessage())); 
        }
         testReport.get().log(Status.FAIL, MarkupHelper.createLabel(result.getMethod().getMethodName() + " FAIL", ExtentColor.RED));
    }
 
    @Override
    public void onTestSkipped(ITestResult result) {
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
    
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
      System.out.println(method.getTestMethod().getMethodName());
    }
}
