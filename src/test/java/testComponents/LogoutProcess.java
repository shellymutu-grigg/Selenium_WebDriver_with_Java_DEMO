package testComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import data.PageTitleData;
import data.TextData;
import pageObjects.LogoutPage;
import resources.TestHelperFunctions;

public class LogoutProcess {
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	WebElement webElement;
	
	public void completeCartLogout(LogoutPage logoutPage, WebDriver webDriver) {	
		logoutPage.openAccountMenu();
		webElement = testHelperFunctions.getElement(webDriver, TextData.YOUR_ACCOUNT_TEXT);
		testHelperFunctions.validateElement(webElement, "openAccountMenu()");
		testHelperFunctions.validatePageTitle("openAccountMenu()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
		webElement = testHelperFunctions.getElement(webDriver, TextData.SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "logout()");
		testHelperFunctions.validatePageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
	
	public void completeLogout(LogoutPage logoutPage, WebDriver webDriver, String testCaseName) {
		String pageTitle;
		if(testCaseName.contains("searchOrdersTest")) {
			pageTitle = PageTitleData.ORDERS_PAGE_TITLE;
		} else pageTitle = PageTitleData.LANDING_PAGE_TITLE;
		testHelperFunctions.validatePageTitle("initialiseLogoutPage()", pageTitle, webDriver.getTitle());
		
		logoutPage.openAccountMenu();
		webElement = testHelperFunctions.getElement(webDriver, TextData.YOUR_ACCOUNT_TEXT);
		testHelperFunctions.validateElement(webElement, "openAccountMenu()");
		testHelperFunctions.validatePageTitle("openAccountMenu()", pageTitle, webDriver.getTitle());
		
		logoutPage.logout();
		webElement = testHelperFunctions.getElement(webDriver, TextData.SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "logout()");
		testHelperFunctions.validatePageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
}
