package resources;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class TestHelperFunctions {

	public void validatePageTitle(String testCaseName, String expectedPageTitle, String pageTitle) throws IOException {
		Assert.assertEquals(expectedPageTitle, pageTitle);
		if(ExtentTestManager.getTest().getStatus() == Status.PASS) {
			ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The {0} page title {1} matched expected text." + "<br />"+"Step {2}ed", testCaseName, pageTitle, ExtentTestManager.getTest().getStatus()));
		}else if(ExtentTestManager.getTest().getStatus() == Status.FAIL) {
			ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The {0} page title {1} did not match expected {2} text." + "<br />" + "Step {3}ed", testCaseName, pageTitle, expectedPageTitle, ExtentTestManager.getTest().getStatus()));
		}
		
	}
	
	public String convertTestCaseName(String testCaseName) {
		// Read the test case method name and split it based on capital letters
		Stream<String> convertTestMethodName = Arrays.stream(testCaseName.split("(?=\\p{Lu})"));
		
		// Capitalise the first letter of each word in test case name
		Stream<String> testNameCapitalize = convertTestMethodName.map(str -> str.substring(0, 1).
                toUpperCase() + str.substring(1));
		
		// Join words in test case name with a space between each
		return testNameCapitalize.unordered().collect(Collectors.joining(" "));
	}
}
