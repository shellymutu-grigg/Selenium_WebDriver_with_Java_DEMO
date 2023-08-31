package testComponents;

import data.ConfigData;
import data.LocalStore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import pageObjects.LogoutPage;
import webElement.Element;

public class LogoutProcess {

	public void cartLogout(LogoutPage logoutPage) {
		WebDriver webDriver = (WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER);
		logoutPage.openAccountMenu();

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]")), "openAccountMenu()");
		TestAssert.pageTitle("openAccountMenu()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]")), "logout()");
		TestAssert.pageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
	
	public void logout(LogoutPage logoutPage, String testCaseName) {
		WebDriver webDriver = (WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER);
		String pageTitle;
		if(testCaseName.contains("searchOrdersTest")) {
			pageTitle = PageTitleData.ORDERS_PAGE_TITLE;
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.SEARCH_RESULTS_TEXT +"')]")), "logout()");
		} else if(testCaseName.contains("TestException")){
			pageTitle = testCaseName.replace("TestException: ", "");
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.MOVED_RECENTLY_TEXT +"')]")), "logout()");
		} else {
			pageTitle = PageTitleData.LANDING_PAGE_TITLE;
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.DELIVER_TO_TEXT +"')]")), "logout()");
		}
		TestAssert.pageTitleContains("initialiseLogoutPage()", pageTitle, webDriver.getTitle());
		
		logoutPage.openAccountMenu();
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]")), "openAccountMenu()");
		TestAssert.pageTitle("openAccountMenu()", pageTitle, webDriver.getTitle());
		
		logoutPage.logout();
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]")), "logout()");
		TestAssert.pageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
}
