package testComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import data.PageTitleData;
import data.TextData;
import pageObjects.LoginPage;
import resources.TestHelperFunctions;

public class LoginProcess {
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	String email = System.getenv("AMAZON_USERNAME");
	WebElement webElement;
	
	public void completeLogin(String password, LoginPage loginPage, WebDriver webDriver) {
		loginPage.checkForPreviousLoginFailure();
		
		String pageTitle;
		String pageText;
		if(System.getProperty("YourAccount") == TextData.YOUR_ACCOUNT_TEXT) {
			pageTitle = PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE;
			pageText = TextData.DELIVER_TO_TEXT;
		} else {
			pageTitle = PageTitleData.LANDING_PAGE_TITLE;
			pageText = TextData.LANDING_PAGE_SIGNIN_TEXT;
		}
		testHelperFunctions.validatePageTitle("navigateToURL()", pageTitle, webDriver.getTitle());
		webElement = testHelperFunctions.getElement(webDriver, pageText);
		testHelperFunctions.validateElement(webElement, "navigateToURL()"); 
		
		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle("navigateToLanding()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		webElement = testHelperFunctions.getElement(webDriver, TextData.SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "navigateToLanding()");
		
		loginPage.enterUserEmail(email); 		
		webElement = testHelperFunctions.getElement(webDriver, TextData.KEEP_SIGNED_IN_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserEmail(email)");
		
		loginPage.enterUserPassword(password);
	}
}
