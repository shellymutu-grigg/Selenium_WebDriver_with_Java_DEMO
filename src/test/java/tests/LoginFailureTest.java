package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Objects;

import data.ConfigData;
import data.LocalStore;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import functions.TestCaseName;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.LoginProcess;
import testComponents.TestSetup;
import webElement.Element;

@Listeners(ExtentListeners.class)
public class LoginFailureTest extends TestSetup {
	LoginProcess loginSuccessProcess = new LoginProcess();
	String password = System.getenv(ConfigData.AMAZON_PASSWORD_FAIL);
	
	@Test(groups = { "LoginFailure" }, priority = 1, description = "Entering an incorrect password will result in an error page being displayed to the user")
	/*
		GIVEN a user enters an incorrect password
		WHEN they are on the Enter Password screen
		THEN the application will return an error page
			AND the user will not be logged in
	 */
	public void loginFailureTest(Method method) {
		ExtentTestManager.startTest(TestCaseName.convert(method.getName()), "Entering an incorrect password will result in an error page being displayed to the user");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				TestCaseName.convert(method.getName()), StringUtils.capitalize(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_BROWSER).toString())));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginSuccessProcess.completeLogin(password, loginPage);
		
		String loginFailStatus = loginPage.loginFail();
		String pageTitle;
		if(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER).toString().contains(ConfigData.CHROME_DRIVER) ||
				LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER).toString().contains(ConfigData.EDGE_DRIVER)) {
			pageTitle = PageTitleData.SIGN_IN_PAGE_TITLE;
		} else {
			pageTitle = PageTitleData.LANDING_PAGE_TITLE;
		}
		if(Objects.equals(loginFailStatus, TextData.LOGIN_FAILURE_ALERT_TEXT)) {
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_ALERT_TEXT +"')]")), "enterUserDetails(email, password) with incorrect password");
			TestAssert.pageTitle("enterUserDetails(email, password) with incorrect password", pageTitle, webDriver.getTitle());
		} else if(Objects.equals(loginFailStatus, TextData.LOGIN_PUZZLE_TEXT)) {
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_PUZZLE_TEXT +"')]")), "enterUserDetails(email, password) with puzzle page presented");
			TestAssert.pageTitle("enterUserDetails(email, password) with puzzle page presented", PageTitleData.AUTHENTICATION_REQUIRED_PAGE_TITLE, webDriver.getTitle());
		} else if(Objects.equals(loginFailStatus, TextData.LOGIN_FAILURE_IMPORTANT_MESSAGE_TEXT)) {
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_IMPORTANT_MESSAGE_TEXT +"')]")), "enterUserDetails(email, password) with an important message page presented");
			TestAssert.pageTitle("enterUserDetails(email, password) with an important message page presented", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		}
	}
}
