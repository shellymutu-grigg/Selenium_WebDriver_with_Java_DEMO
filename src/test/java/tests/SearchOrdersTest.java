package tests;

import java.lang.reflect.Method;

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
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class SearchOrdersTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	LoginProcess loginSuccessProcess = new LoginProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");	
	WebElement webElement;
	
	@Test(groups = { "Smoke" }, priority = 1, description = "Verify user is able search order history")
	public void searchOrdersTest(Method method) throws Exception {		
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Verify user is able search order history");

		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has started."));
		
		loginSuccessProcess.completeLogin(password, loginPage, webDriver);
		webElement = testHelperFunctions.getElement(webDriver, TextData.RETURNS_AND_ORDERS_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserPassword(password)");
		testHelperFunctions.validatePageTitle("enterUserPassword(password)", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		OrdersPage ordersPage = loginPage.pauseForOrdersPage();
		testHelperFunctions.validatePageTitle("pauseForOrdersPage()", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openAccountMenu();
		testHelperFunctions.validatePageTitle("openAccountMenu()", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openOrderPage();
		testHelperFunctions.validatePageTitle("openOrderPage()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());

		LogoutPage logoutPage = ordersPage.searchForOrders();
		testHelperFunctions.validatePageTitle("searchForOrders()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		// 1 order matching text
		
		logoutPage.openAccountMenu();	
		testHelperFunctions.validatePageTitle("openAccountMenu()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
		testHelperFunctions.validatePageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());

		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has finished."));
	}

}
