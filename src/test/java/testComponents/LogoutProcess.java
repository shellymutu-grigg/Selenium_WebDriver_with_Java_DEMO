package testComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import pageObjects.LogoutPage;
import webElement.Element;

public class LogoutProcess {
	
	public void cartLogout(LogoutPage logoutPage, WebDriver webDriver) {
		logoutPage.openAccountMenu();

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]"), webDriver), "openAccountMenu()");
		TestAssert.pageTitle("openAccountMenu()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]"), webDriver), "logout()");
		TestAssert.pageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
	
	public void logout(LogoutPage logoutPage, WebDriver webDriver, String testCaseName) {
		String pageTitle;
		if(testCaseName.contains("searchOrdersTest")) {
			pageTitle = PageTitleData.ORDERS_PAGE_TITLE;
		} else if(testCaseName.contains("TestException")){
			pageTitle = testCaseName.replace("TestException: ", "");
		} else pageTitle = PageTitleData.LANDING_PAGE_TITLE;
		TestAssert.pageTitle("initialiseLogoutPage()", pageTitle, webDriver.getTitle());
		
		logoutPage.openAccountMenu();

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]"), webDriver), "openAccountMenu()");
		TestAssert.pageTitle("openAccountMenu()", pageTitle, webDriver.getTitle());
		
		logoutPage.logout();

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.SIGNIN_TEXT +"')]"), webDriver), "logout()");
		TestAssert.pageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
}
