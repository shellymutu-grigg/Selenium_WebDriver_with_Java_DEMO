package testComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import data.PageTitleData;
import data.TextData;
import functions.AssertElementNotNull;
import functions.AssertPageTitle;
import pageObjects.LoginPage;
import webElement.FindElement;

public class LoginProcess {
	String email = System.getenv("AMAZON_USERNAME");
	
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
		AssertPageTitle.assertPageTitle("navigateToURL()", pageTitle, webDriver.getTitle());
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ pageText +"')]"), webDriver), "navigateToURL()");
		
		loginPage.navigateToLanding();
		AssertPageTitle.assertPageTitle("navigateToLanding()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]"), webDriver), "navigateToLanding()");
		
		loginPage.enterUserEmail(email);
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.KEEP_SIGNED_IN_TEXT +"')]"), webDriver), "enterUserEmail(email)");
		
		loginPage.enterUserPassword(password);
	}
}
