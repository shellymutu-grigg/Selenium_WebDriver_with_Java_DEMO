package resources;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import functions.TestCaseName;
import testComponents.TestSetup;

@Slf4j
public class ExtentListeners extends TestSetup implements ITestListener {
    Logger logger = LoggerFactory.getLogger(ExtentListeners.class);
	
    static String getTestMethodName(ITestResult result) {
        return TestCaseName.convert(result.getMethod().getConstructorOrMethod().getName());
    }
    
	@Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

	@Override
    public void onTestStart(ITestResult result) {
    }
 
	@Override
    public void onTestSuccess(ITestResult result) {
        if(ExtentTestManager.getTest() !=null) {
            try {
                String fileName = captureScreenshot(result.getMethod().getMethodName());
                ExtentTestManager.getTest().pass("<font color=green>" + "Test case: " + getTestMethodName(result) + " successfully ended at:" + "</font>",
                        MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            } catch (Exception e) {
                logger.error("Exception: Screenshot capture failed with error: {}", e.getMessage());
            }
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), TestCaseName.convert(result.getMethod().getMethodName() + " has finished."));
            ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createLabel(TestCaseName.convert(result.getMethod().getMethodName()) + " - PASS", ExtentColor.GREEN));
            WebDriver webDriver = WebDriverManager.getDriver();
            logger.info("Thread {} ({}) with webDriver hashCode {} was successful", Thread.currentThread().getId(), getTestMethodName(result), webDriver.hashCode());
            logger.info("Thread {} ({}) is quitting webDriver with hashCode {}", Thread.currentThread().getId(), getTestMethodName(result), webDriver.hashCode());
            webDriver.quit();
        }
    }
 
	@Override
    public void onTestFailure(ITestResult result) {
        if(ExtentTestManager.getTest() !=null){
            logger.error("Thread {} ({}) has FAILED", Thread.currentThread().getId(), getTestMethodName(result));
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), "<font color=red><strong>" + MessageFormat.format("{0} with threadID {1} has failed", getTestMethodName(result), Thread.currentThread().getId()) + "</strong>");

            ExtentTestManager.getTest().fail("An exception occurred in test case: " + getTestMethodName(result) + " <details>" + "<summary>" +  "Click here to see exception message"
                    + "</font>" + "</summary>" + "<font color=red><strong>" + result.getThrowable().getMessage() + "</strong></details>"+" \n");
            try {
               String fileName = captureScreenshot(result.getMethod().getMethodName());
               ExtentTestManager.getTest().fail("<font color=red>" + "Where failure occurred screenshot" + "</font>",
                       MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            } catch (Exception e) {
                logger.error("Exception: Screenshot capture failed with error: {}", e.getMessage());
            }
            ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(TestCaseName.convert(result.getMethod().getMethodName()) + " - FAIL", ExtentColor.RED));
        }
    }
 
    @Override
    public void onTestSkipped(ITestResult result) {
        if(ExtentTestManager.getTest() !=null) {
            logger.warn("Thread {} ({}) has been skipped", Thread.currentThread().getId(), getTestMethodName(result));
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
                ExtentTestManager.getTest().pass("<font color=" + "green>" + "Test case: " + TestCaseName.convert(testCaseName) + " with thread " + Thread.currentThread().getId() + " has successfully started. Test started from:" + "</font>",
                        MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            } catch (Exception e) {
                logger.error("Exception: Screenshot capture failed with error: {}", e.getMessage());
            }
        }
    }
    
    public String captureScreenshot(String testCaseName) throws IOException {
		Date calendarDate = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(calendarDate);  
        TakesScreenshot takeScreenshot = (TakesScreenshot)WebDriverManager.getDriver();
        File screenshot = takeScreenshot.getScreenshotAs(OutputType.FILE);
        File screenshotOutputFile = new File(System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.replace(":", "_").replace(" ", "_") + ".png");
		FileUtils.copyFile(screenshot, screenshotOutputFile);
		return System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.replace(":", "_").replace(" ", "_") + ".png";
    }
}
