package testComponents;

import functions.AssertElementNotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import data.PageTitleData;
import data.TextData;
import pageObjects.LoginPage;
import resources.TestHelperFunctions;
import webElement.FindElement;

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
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ pageText +"')]"), webDriver), "navigateToURL()");
		
		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle("navigateToLanding()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]"), webDriver), "navigateToLanding()");
		
		loginPage.enterUserEmail(email);
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.KEEP_SIGNED_IN_TEXT +"')]"), webDriver), "enterUserEmail(email)");
		
		loginPage.enterUserPassword(password);
	}
}
