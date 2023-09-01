package testComponents;

import data.ConfigData;
import data.LocalStore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import pageObjects.LoginPage;
import webElement.Element;

public class LoginProcess {
	String email = System.getenv(ConfigData.AMAZON_USERNAME);
	
	public void completeLogin(String password, LoginPage loginPage) {
		WebDriver webDriver = (WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER);
		loginPage.checkForPreviousLoginFailure();
		
		String pageTitle;
		String pageText;
		if(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_YOUR_ACCOUNT) == TextData.YOUR_ACCOUNT_TEXT) {
			pageTitle = PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE;
			pageText = TextData.DELIVER_TO_TEXT;
		} else {
			pageTitle = PageTitleData.LANDING_PAGE_TITLE;
			pageText = TextData.LANDING_PAGE_SIGN_IN_TEXT;
		}
		TestAssert.pageTitle("navigateToURL()", pageTitle, webDriver.getTitle());
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ pageText +"')]")), "navigateToURL()");
		
		loginPage.navigateToLanding();
		TestAssert.pageTitle("navigateToLanding()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.SIGN_IN_TEXT +"')]")), "navigateToLanding()");
		
		loginPage.enterUserEmail(email);
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.KEEP_SIGNED_IN_TEXT +"')]")), "enterUserEmail(email)");
		
		loginPage.enterUserPassword(password);
	}
}
