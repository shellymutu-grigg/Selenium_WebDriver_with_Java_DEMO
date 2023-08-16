package resources;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ListenersNG implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println(MessageFormat.format("onTestStart() result: {0}", result));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println(MessageFormat.format("onTestSuccess() result: {0}", result));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println(MessageFormat.format("onTestFailure() result: {0}", result));
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println(MessageFormat.format("onFinish() context: {0}", context));
	}
	
	public void logResult(Status status, String result) {
		System.out.println(MessageFormat.format("logResult() status: {0}, result: {1}", status, result));
	}
	
	public String getScreenshot(String testCaseName, WebDriver webDriver) throws IOException {

		TakesScreenshot screenshotAction = (TakesScreenshot) webDriver;
		File screenshotTakenFile = screenshotAction.getScreenshotAs(OutputType.FILE);
		File screenshOutputFile = new File(System.getProperty("user.dir") + "//Desktop//" + testCaseName + ".png");
		FileUtils.copyFile(screenshotTakenFile, screenshOutputFile);
		return System.getProperty("user.dir") + "//Desktop//" + testCaseName + ".png";

	}
}

