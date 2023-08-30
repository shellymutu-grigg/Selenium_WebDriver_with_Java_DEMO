package resources;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import functions.ConvertTestCaseName;
import testComponents.TestSetup;
 
public class ExtentListeners extends TestSetup implements ITestListener {
	
    private static String getTestMethodName(ITestResult result) {
        return ConvertTestCaseName.convertTestCaseName(result.getMethod().getConstructorOrMethod().getName());
    }
    
	@Override
    public void onStart(ITestContext context) {
        context.setAttribute("WebDriver", this.webDriver);
    }	

	@Override
    public void onTestStart(ITestResult result) {
		System.out.println(MessageFormat.format("{0} has started", getTestMethodName(result)));
    }
	
	@Override
    public void onFinish(ITestContext context) {
        
    }
 
	@Override
    public void onTestSuccess(ITestResult result) {
        if(ExtentTestManager.getTest() !=null) {
            System.out.println(MessageFormat.format("{0} has succeeded", getTestMethodName(result)));

            try {
                String fileName = captureScreenshot(result.getMethod().getMethodName());
                ExtentTestManager.getTest().pass("<font color=" + "green>" + "Test case: " + getTestMethodName(result) + " successfully ended at:" + "</font>",
                        MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            } catch (Exception e) {
                System.out.println(MessageFormat.format("Screenshot capture failed with error: {0}", e.getMessage()));
            }
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), ConvertTestCaseName.convertTestCaseName(result.getMethod().getMethodName() + " has finished."));
            ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createLabel(ConvertTestCaseName.convertTestCaseName(result.getMethod().getMethodName()) + " - PASS", ExtentColor.GREEN));
        }
    }
 
	@Override
    public void onTestFailure(ITestResult result) {
        if(ExtentTestManager.getTest() !=null){
            System.out.println(MessageFormat.format("{0} has failed", getTestMethodName(result)));
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has failed", getTestMethodName(result)));

            String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
            ExtentTestManager.getTest().fail("Exception Occured in test case: " + getTestMethodName(result) + " <details>" + "<summary>" +  "<font color=" + "red>" + "Click here to see Stack Trace"
                    + "</font>" + "</summary>" + exceptionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
            try {
               String fileName = captureScreenshot(result.getMethod().getMethodName());
               ExtentTestManager.getTest().fail("<font color=" + "red>" + "Screenshot of location of failure" + "</font>",
                       MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            } catch (Exception e) {
                System.out.println(MessageFormat.format("Screenshot capture failed with error: {0}", e.getMessage()));
            }
            ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(ConvertTestCaseName.convertTestCaseName(result.getMethod().getMethodName()) + " - FAIL", ExtentColor.RED));
        }
    }
 
    @Override
    public void onTestSkipped(ITestResult result) {
        if(ExtentTestManager.getTest() !=null) {
            System.out.println(MessageFormat.format("{0} has been skipped", getTestMethodName(result)));
            ExtentTestManager.getTest().info(getTestMethodName(result) + " test has been skipped.");
            ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
        }
    }
 
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
    
    public void onTestStartScreenshot(String testCaseName) {
        if(ExtentTestManager.getTest() !=null) {
            try {
                String fileName = captureScreenshot(testCaseName);
                ExtentTestManager.getTest().pass("<font color=" + "green>" + "Test case: " + ConvertTestCaseName.convertTestCaseName(testCaseName) + " successfully started at:" + "</font>",
                        MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            } catch (Exception e) {
                System.out.println(MessageFormat.format("Screenshot capture failed with error: {0}", e.getMessage()));
            }
        }
    }
    
    public String captureScreenshot(String testCaseName) throws IOException {
		Date calendarDate = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
        String date = dateFormat.format(calendarDate);  
        TakesScreenshot takeScreenshot = (TakesScreenshot)getDriver();
        File screenshot = takeScreenshot.getScreenshotAs(OutputType.FILE);
        File screenshotOutputFile = new File(System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.toString().replace(":", "_").replace(" ", "_") + ".png");
		FileUtils.copyFile(screenshot, screenshotOutputFile);
		return System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.toString().replace(":", "_").replace(" ", "_") + ".png";
    }
}
