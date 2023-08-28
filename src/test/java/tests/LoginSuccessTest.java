package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import functions.AssertElementNotNull;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
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
import webElement.FindElement;

@Listeners(ExtentListeners.class)
public class LoginSuccessTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	LoginProcess loginProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");	
	WebElement webElement;
	
	@Test(groups = { "Smoke", "Login" }, priority = 1, description = "Successful login scenario")
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to review their account
	 */
	public void loginSuccessTest(Method method) {
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "A user is able to successfully login with a valid email and password combo");
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
				AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_ALERT_TEXT +"')]"), webDriver), "enterUserDetails(email, password) with incorrect password");
				testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with incorrect password", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());	
			} else if(loginPage.findLoginFailureText() == TextData.LOGIN_PUZZLE_TEXT) {
				AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_PUZZLE_TEXT +"')]"), webDriver), "enterUserDetails(email, password) with puzzle page presented");
				testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with puzzle page presented", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());				
			} else if(loginPage.findLoginFailureText() == TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT) {
				AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT +"')]"), webDriver), "enterUserDetails(email, password) with an important message page presented");
				testHelperFunctions.validatePageTitle("enterUserDetails(email, password) with an important message page presented", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());				
			}
		}
		
		LogoutPage logoutPage = loginPage.initialiseLogoutPage();
		logoutProcess.logout(logoutPage, webDriver, "");
	}

}
