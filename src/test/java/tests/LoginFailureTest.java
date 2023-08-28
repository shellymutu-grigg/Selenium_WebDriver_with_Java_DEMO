package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import functions.AssertElementNotNull;
import functions.AssertPageTitle;
import functions.ConvertTestCaseName;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.LoginProcess;
import testComponents.TestSetup;
import webElement.FindElement;

@Listeners(ExtentListeners.class)
public class LoginFailureTest extends TestSetup {
	LoginProcess loginSuccessProcess = new LoginProcess();
	String password = System.getenv("AMAZON_PASSWORD_FAIL");
	
	@Test(groups = { "Smoke", "ErrorHandling" }, priority = 1, description = "Entering an incorrect password will result in an error page being displayed to the user")
	/*
		GIVEN a user enters an incorrect password
		WHEN they are on the Enter Password screen
		THEN the application will return an error page
			AND the user will not be logged in
	 */
	public void loginFailureTest(Method method) throws Exception {
		ExtentTestManager.startTest(ConvertTestCaseName.convertTestCaseName(method.getName()), "Entering an incorrect password will result in an error page being displayed to the user");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				ConvertTestCaseName.convertTestCaseName(method.getName()), StringUtils.capitalize(System.getProperty("Browser"))));
		
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
			AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_ALERT_TEXT +"')]"), webDriver), "enterUserDetails(email, password) with incorrect password");
			AssertPageTitle.assertPageTitle("enterUserDetails(email, password) with incorrect password", pageTitle, webDriver.getTitle());
		} else if(loginFailStatus == TextData.LOGIN_PUZZLE_TEXT) {
			AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_PUZZLE_TEXT +"')]"), webDriver), "enterUserDetails(email, password) with puzzle page presented");
			AssertPageTitle.assertPageTitle("enterUserDetails(email, password) with puzzle page presented", PageTitleData.AUTHENTICATION_REQUIRED_PAGE_TITLE, webDriver.getTitle());
		} else if(loginFailStatus == TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT) {
			AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT +"')]"), webDriver), "enterUserDetails(email, password) with an important message page presented");
			AssertPageTitle.assertPageTitle("enterUserDetails(email, password) with an important message page presented", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		}
	}
}
