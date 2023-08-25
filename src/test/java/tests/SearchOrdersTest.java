package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import pageObjects.LogoutPage;
import pageObjects.OrdersPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.TestHelperFunctions;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class SearchOrdersTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	LoginProcess loginSuccessProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");	
	WebElement webElement;
	
	@Test(groups = { "Smoke" }, priority = 1, description = "Verify user is able to login, search their order history and log out")
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to access their previous orders list
			AND the user will be able to search previous orders
			AND the user will be able to log out.
	 */
	public void searchOrdersTest(Method method) throws Exception {		
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Verify user is able to login, search their order history and log out");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.", 
				testHelperFunctions.convertTestCaseName(method.getName()), StringUtils.capitalize(System.getProperty("Browser"))));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginSuccessProcess.completeLogin(password, loginPage, webDriver);
		webElement = testHelperFunctions.getElement(webDriver, TextData.RETURNS_AND_ORDERS_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserPassword(password)");
		
		testHelperFunctions.validatePageTitle("enterUserPassword(password)", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		OrdersPage ordersPage = loginPage.pauseForOrdersPage();
		webElement = testHelperFunctions.getElement(webDriver, TextData.ACCOUNT_MENU_LINK_TEXT);
		testHelperFunctions.validateElement(webElement, "pauseForOrdersPage()");
		testHelperFunctions.validatePageTitle("pauseForOrdersPage()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openAccountMenu();
		webElement = testHelperFunctions.getElement(webDriver, TextData.YOUR_ACCOUNT_TEXT);
		testHelperFunctions.validateElement(webElement, "openAccountMenu()");
		testHelperFunctions.validatePageTitle("openAccountMenu()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openOrderPage();
		webElement = testHelperFunctions.getElement(webDriver, TextData.YOUR_ORDERS_TEXT);
		testHelperFunctions.validateElement(webElement, "openOrderPage()");
		testHelperFunctions.validatePageTitle("openOrderPage()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());

		LogoutPage logoutPage = ordersPage.searchForOrders();
		webElement = testHelperFunctions.getElement(webDriver, TextData.SEARCH_RESULTS_TEXT);
		testHelperFunctions.validateElement(webElement, "searchForOrders()");
		testHelperFunctions.validatePageTitle("searchForOrders()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		logoutProcess.completeLogout(logoutPage, webDriver, "searchOrdersTest");	
	}
}
