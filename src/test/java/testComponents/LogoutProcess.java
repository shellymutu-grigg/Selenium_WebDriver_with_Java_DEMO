package testComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import data.PageTitleData;
import data.TextData;
import functions.AssertElementNotNull;
import functions.AssertPageTitle;
import pageObjects.LogoutPage;
import webElement.FindElement;

public class LogoutProcess {
	
	public void cartLogout(LogoutPage logoutPage, WebDriver webDriver) {
		logoutPage.openAccountMenu();

		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]"), webDriver), "openAccountMenu()");
		AssertPageTitle.assertPageTitle("openAccountMenu()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();

		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]"), webDriver), "logout()");
		AssertPageTitle.assertPageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
	
	public void logout(LogoutPage logoutPage, WebDriver webDriver, String testCaseName) {
		String pageTitle;
		if(testCaseName.contains("searchOrdersTest")) {
			pageTitle = PageTitleData.ORDERS_PAGE_TITLE;
		} else pageTitle = PageTitleData.LANDING_PAGE_TITLE;
		AssertPageTitle.assertPageTitle("initialiseLogoutPage()", pageTitle, webDriver.getTitle());
		
		logoutPage.openAccountMenu();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]"), webDriver), "openAccountMenu()");
		AssertPageTitle.assertPageTitle("openAccountMenu()", pageTitle, webDriver.getTitle());
		
		logoutPage.logout();

		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]"), webDriver), "logout()");
		AssertPageTitle.assertPageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
}
