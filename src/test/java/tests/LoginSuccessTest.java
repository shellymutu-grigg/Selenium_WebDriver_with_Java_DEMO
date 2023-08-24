package tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import resources.TestHelperFunctions;
import data.PageTitleData;
import data.TextData;
import pageObjects.LogoutPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginSuccessTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	String email = System.getenv("AMAZON_USERNAME");
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
	WebElement webElement;
	
	@Test(groups = { "Smoke", "Login" }, priority = 1, description = "Successful login scenario")
	
	public void loginSuccessTest(Method method) {
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Successful login scenario");
		
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has started."));

		loginPage.navigateToURL();
		testHelperFunctions.validatePageTitle("navigateToURL()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		webElement = testHelperFunctions.getElement(webDriver, TextData.LANDING_PAGE_SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "navigateToURL()"); 

		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle("navigateToLanding()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		webElement = testHelperFunctions.getElement(webDriver, TextData.SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "navigateToLanding()");
		
		loginPage.enterUserEmail(email); 		
		webElement = testHelperFunctions.getElement(webDriver, TextData.KEEP_SIGNED_IN_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserEmail(email)");
		
		loginPage.enterUserPassword(password);
		
		if(loginPage.checkIfSignedIn()) {
			testHelperFunctions.validatePageTitle("enterUserPassword(password)", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
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
		testHelperFunctions.validatePageTitle("initialiseLogoutPage()", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.openAccountMenu();
		webElement = testHelperFunctions.getElement(webDriver, TextData.ACCOUNT_MENU_TEXT);
		testHelperFunctions.validateElement(webElement, "openAccountMenu()");
		testHelperFunctions.validatePageTitle("openAccountMenu()", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
		webElement = testHelperFunctions.getElement(webDriver, TextData.SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "logout()");
		testHelperFunctions.validatePageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has finished."));
	}

}
