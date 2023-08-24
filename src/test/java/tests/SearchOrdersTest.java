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
import testComponents.LogoutProcess;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class SearchOrdersTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	LoginProcess loginSuccessProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");	
	WebElement webElement;
	
	@Test(groups = { "Smoke" }, priority = 1, description = "Verify user is able search order history")
	public void searchOrdersTest(Method method) throws Exception {		
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Verify user is able search order history");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has started."));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginSuccessProcess.completeLogin(password, loginPage, webDriver);
		webElement = testHelperFunctions.getElement(webDriver, TextData.RETURNS_AND_ORDERS_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserPassword(password)");
		
		String pageTitle;
		if(System.getProperty("Browser").contains("chrome")) {
			pageTitle = PageTitleData.LANDING_PAGE_TITLE;
		} else {
			pageTitle = PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE;
		}
		testHelperFunctions.validatePageTitle("enterUserPassword(password)", pageTitle, webDriver.getTitle());
		
		OrdersPage ordersPage = loginPage.pauseForOrdersPage();
		testHelperFunctions.validatePageTitle("pauseForOrdersPage()", pageTitle, webDriver.getTitle());
		
		ordersPage.openAccountMenu();
		testHelperFunctions.validatePageTitle("openAccountMenu()", pageTitle, webDriver.getTitle());
		
		ordersPage.openOrderPage();
		testHelperFunctions.validatePageTitle("openOrderPage()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());

		LogoutPage logoutPage = ordersPage.searchForOrders();
		testHelperFunctions.validatePageTitle("searchForOrders()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		// 1 order matching text
		
		logoutProcess.completeLogout(logoutPage, webDriver, "searchOrdersTest");	
	}
}
