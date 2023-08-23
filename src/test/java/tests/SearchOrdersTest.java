package tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import pageObjects.LogoutPage;
import pageObjects.OrdersPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.TestHelperFunctions;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class SearchOrdersTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	
	@Test(groups = { "Smoke" }, priority = 1, description = "Verify user is able search order history")
	public void searchOrdersTest(Method method) throws Exception {		
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Verify user is able search order history");
		
		String email = System.getenv("AMAZON_USERNAME");
		String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
		
		loginPage.navigateToURL();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());

		loginPage.enterUserDetails(email, password);
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		loginPage.pause();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		OrdersPage ordersPage = loginPage.ordersPage();
		
		ordersPage.openAccountMenu();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openOrderPage();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());

		LogoutPage logoutPage = ordersPage.searchForOrders();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.openAccountMenu();	
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}

}
