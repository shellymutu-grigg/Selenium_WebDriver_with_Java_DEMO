package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import data.ConfigData;
import data.LocalStore;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import functions.TestCaseName;
import pageObjects.LogoutPage;
import pageObjects.OrdersPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;
import webElement.Element;

@Listeners(ExtentListeners.class)
public class SearchOrdersTest extends TestSetup{
	LoginProcess loginSuccessProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv(ConfigData.AMAZON_PASSWORD_SUCCESS);
	
	@Test(groups = { "Smoke", "SearchOrders" }, priority = 1, description = "Verify user is able to login, search their order history and log out")
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to access their previous orders list
			AND the user will be able to search previous orders
			AND the user will be able to log out.
	 */
	public void searchOrdersTest(Method method) {
		ExtentTestManager.startTest(TestCaseName.convert(method.getName()), "Verify user is able to login, search their order history and log out");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				TestCaseName.convert(method.getName()), StringUtils.capitalize(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_BROWSER).toString())));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginSuccessProcess.completeLogin(password, loginPage);
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.RETURNS_AND_ORDERS_TEXT +"')]")), "enterUserPassword(password)");

		TestAssert.pageTitle("completeLogin()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		OrdersPage ordersPage = loginPage.generateOrdersPage();
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.ACCOUNT_MENU_LINK_TEXT +"')]")), "pauseForOrdersPage()");
		TestAssert.pageTitle("generateOrdersPage()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openAccountMenu();
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.YOUR_ACCOUNT_TEXT +"')]")), "openAccountMenu()");
		TestAssert.pageTitle("openAccountMenu()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		ordersPage.openOrdersPage();

		TestAssert.elementNotNull(Element.getElement(By.xpath("//h1[normalize-space()='"+ TextData.YOUR_ORDERS_TEXT +"']") ), "openOrderPage()");
		TestAssert.pageTitle("openOrderPage()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());

		LogoutPage logoutPage = ordersPage.searchForOrders();
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.SEARCH_RESULTS_TEXT +"')]")), "searchForOrders()");
		TestAssert.pageTitle("searchForOrders()", PageTitleData.ORDERS_PAGE_TITLE, webDriver.getTitle());
		
		logoutProcess.logout(logoutPage, "searchOrdersTest");
	}
}
