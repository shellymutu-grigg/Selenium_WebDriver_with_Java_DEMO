package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.TestHelperFunctions;
import testComponents.LoginProcess;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginFailureTest extends TestSetup {
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	LoginProcess loginSuccessProcess = new LoginProcess();
	String password = System.getenv("AMAZON_PASSWORD_FAIL");
	WebElement webElement;
	
	@Test(groups = { "Smoke", "ErrorHandling" }, priority = 1, description = "Unsuccessful login scenario")
	public void loginFailureTest(Method method) throws Exception {		
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Unsuccessful login scenario");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.", 
				testHelperFunctions.convertTestCaseName(method.getName()), StringUtils.capitalize(System.getProperty("Browser"))));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginSuccessProcess.completeLogin(password, loginPage, webDriver);
		
		String loginFailStatus = loginPage.loginFail();
		String pageTitle;
		if(System.getProperty("Browser").contains("edge")) {
			pageTitle = PageTitleData.SIGN_IN_PAGE_TITLE;
		} else {
			pageTitle = PageTitleData.LANDING_PAGE_TITLE;
		}
		if(loginFailStatus == TextData.LOGIN_FAILURE_ALERT_TEXT) {
			webElement = testHelperFunctions.getElement(webDriver, TextData.LOGIN_FAILURE_ALERT_TEXT);
			testHelperFunctions.validateElement(webElement, "enterUserDetails(email, password) with incorrect password");
			testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with incorrect password", pageTitle, webDriver.getTitle());	
		} else if(loginFailStatus == TextData.LOGIN_PUZZLE_TEXT) {
			webElement = testHelperFunctions.getElement(webDriver, TextData.LOGIN_PUZZLE_TEXT);
			testHelperFunctions.validateElement(webElement, "enterUserDetails(email, password) with puzzle page presented");
			testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with puzzle page presented", PageTitleData.AUTHENTICATION_REQUIRED_PAGE_TITLE, webDriver.getTitle());				
		} else if(loginFailStatus == TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT) {
			webElement = testHelperFunctions.getElement(webDriver, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
			testHelperFunctions.validateElement(webElement, "enterUserDetails(email, password) with an important message page presented");
			testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with an important message page presented", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());				
		}
	}
}
