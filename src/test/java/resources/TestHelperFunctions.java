package resources;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class TestHelperFunctions {

	public void validatePageTitle(String testCaseStepName, String expectedPageTitle, String pageTitle) {
		Assert.assertEquals(pageTitle, expectedPageTitle);
		if(ExtentTestManager.getTest().getStatus() == Status.PASS) {
			ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step page title: \"{1}\", matched expected text." + "<br />"+"Step {2}ed", testCaseStepName, pageTitle, ExtentTestManager.getTest().getStatus()));
		}else if(ExtentTestManager.getTest().getStatus() == Status.FAIL) {
			ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step page title: \"{1}\", did not match expected {2} text." + "<br />" + "<font color=" + "green>" +"Step {2}ed", testCaseStepName, pageTitle, expectedPageTitle, ExtentTestManager.getTest().getStatus()));
		}	
	}
	
	public void validateElement(WebElement webElement, String testCaseStepName) {
		Assert.assertNotNull(webElement);
		if(ExtentTestManager.getTest().getStatus() == Status.PASS) {
			ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step element with text \"{1}\", was successfully located in page." + "<br />"+"Step {2}ed", testCaseStepName, webElement.getText(), ExtentTestManager.getTest().getStatus()));
		}else if(ExtentTestManager.getTest().getStatus() == Status.FAIL) {
			ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step element with text \"{1}\", was NOT located in page." + "<br />" +"<font color=" + "red>" + "Step {2}ed", testCaseStepName, webElement.getText(), ExtentTestManager.getTest().getStatus()));
		}
	}
	
	public String convertTestCaseName(String testCaseName) {
		// Read the test case method name and split it based on capital letters
		Stream<String> convertTestMethodName = Arrays.stream(testCaseName.split("(?=\\p{Lu})"));
		
		// Capitalise the first letter of each word in test case name
		Stream<String> testNameCapitalize = convertTestMethodName.map(word -> StringUtils.capitalize(word));
		
		// Join words in test case name with a space between each
		return testNameCapitalize.collect(Collectors.joining(" "));
	}
	
	public WebElement getElement(WebDriver webDriver, String string) {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		waitForElementToAppear(By.xpath("//*[contains(text(), '"+ string +"')]"), webDriver);
		return webDriver.findElement(By.xpath("//*[contains(text(), '"+ string +"')]"));
	}
}
