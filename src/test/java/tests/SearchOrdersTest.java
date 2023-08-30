package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import functions.AssertElementNotNull;
import functions.AssertPageTitle;
import functions.ConvertTestCaseName;
import pageObjects.LogoutPage;
import pageObjects.OrdersPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;
import webElement.FindElement;

@Listeners(ExtentListeners.class)
public class SearchOrdersTest extends TestSetup{
	LoginProcess loginSuccessProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
	
	@Test(groups = { "Smoke" }, priority = 1, description = "Verify user is able to login, search their order history and log out")
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to access their previous orders list
			AND the user will be able to search previous orders
			AND the user will be able to log out.
	 */
	public void searchOrdersTest(Method method) {
		ExtentTestManager.startTest(ConvertTestCaseName.convertTestCaseName(method.getName()), "Verify user is able to login, search their order history and log out");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				ConvertTestCaseName.convertTestCaseName(method.getName()), StringUtils.capitalize(System.getProperty("Browser"))));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginSuccessProcess.completeLogin(password, loginPage, webDriver);
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.RETURNS_AND_ORDERS_TEXT +"')]"), webDriver), "enterUserPassword(password)");

		AssertPageTitle.assertPageTitle("completeLogin()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		OrdersPage ordersPage = loginPage.generateOrdersPage();
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.ACCOUNT_MENU_LINK_TEXT +"')]"), webDriver), "pauseForOrdersPage()");
		AssertPageTitle.assertPageTitle("generateOrdersPage()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openAccountMenu();
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]"), webDriver), "openAccountMenu()");
		AssertPageTitle.assertPageTitle("openAccountMenu()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openOrdersPage();

		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//h1[normalize-space()='"+ TextData.YOUR_ORDERS_TEXT +"']") , webDriver), "openOrderPage()");
		AssertPageTitle.assertPageTitle("openOrderPage()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());

		LogoutPage logoutPage = ordersPage.searchForOrders();
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.SEARCH_RESULTS_TEXT +"')]"), webDriver), "searchForOrders()");
		AssertPageTitle.assertPageTitle("searchForOrders()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		logoutProcess.logout(logoutPage, webDriver, "searchOrdersTest");
	}
}
