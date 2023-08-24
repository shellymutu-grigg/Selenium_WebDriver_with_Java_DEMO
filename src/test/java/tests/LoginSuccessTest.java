package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import resources.TestHelperFunctions;
import data.PageTitleData;
import data.TextData;
import pageObjects.LogoutPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginSuccessTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	LoginProcess loginProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");	
	WebElement webElement;
	
	@Test(groups = { "Smoke", "Login" }, priority = 1, description = "Successful login scenario")
	
	public void loginSuccessTest(Method method) {
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Successful login scenario");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.", 
				testHelperFunctions.convertTestCaseName(method.getName()), StringUtils.capitalize(System.getProperty("Browser"))));

		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		loginProcess.completeLogin(password, loginPage, webDriver);
		
		if(loginPage.checkIfSignedIn()) {
			testHelperFunctions.validatePageTitle("enterUserPassword(password)", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		} else {
			if(loginPage.findLoginFailureText() == TextData.LOGIN_FAILURE_ALERT_TEXT) {
				webElement = testHelperFunctions.getElement(webDriver, TextData.LOGIN_FAILURE_ALERT_TEXT);
				testHelperFunctions.validateElement(webElement, "enterUserDetails(email, password) with incorrect password");
				testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with incorrect password", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());	
			} else if(loginPage.findLoginFailureText() == TextData.LOGIN_PUZZLE_TEXT) {
				webElement = testHelperFunctions.getElement(webDriver, TextData.LOGIN_PUZZLE_TEXT);
				testHelperFunctions.validateElement(webElement, "enterUserDetails(email, password) with puzzle page presented");
				testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with puzzle page presented", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());				
			} else if(loginPage.findLoginFailureText() == TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT) {
				webElement = testHelperFunctions.getElement(webDriver, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
				testHelperFunctions.validateElement(webElement, "enterUserDetails(email, password) with an important message page presented");
				testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with an important message page presented", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());				
			}
		}
		
		LogoutPage logoutPage = loginPage.initialiseLogoutPage();
		logoutProcess.completeLogout(logoutPage, webDriver, "");	
	}

}
