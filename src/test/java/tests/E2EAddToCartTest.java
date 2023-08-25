package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import pageObjects.CartPage;
import pageObjects.LogoutPage;
import pageObjects.ResultsPage;
import pageObjects.SearchPage;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;
import data.ConfigData;
import data.PageTitleData;
import data.TextData;
import interfaces.IHelper;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.TestHelperFunctions;

@Listeners(ExtentListeners.class)
public class E2EAddToCartTest extends TestSetup implements IHelper{	
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	LoginProcess loginProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");	
	WebElement webElement;
	
	@Test(groups = { "E2E" }, priority = 1, description = "Add to Cart End to End scenario")
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to search for products
			AND add a product to their cart
			AND remove the product from their cart
			AND logout from their account
	 */
	public void addToCartTest(Method method) throws Exception {
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Verify a user is able to login, add an item to their cart, remove the item from their cart and log out successfully.");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.", 
				testHelperFunctions.convertTestCaseName(method.getName()), StringUtils.capitalize(System.getProperty("Browser"))));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginProcess.completeLogin(password, loginPage, webDriver);
		webElement = testHelperFunctions.getElement(webDriver, TextData.RETURNS_AND_ORDERS_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserPassword(password)");
		testHelperFunctions.validatePageTitle("enterUserPassword(password)", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		SearchPage searchPage = loginPage.pauseForSearchPage();	
		webElement = testHelperFunctions.getElement(webDriver, TextData.DELIVER_TO_TEXT);
		testHelperFunctions.validateElement(webElement, "pauseForSearchPage()");
		testHelperFunctions.validatePageTitle("pauseForSearchPage()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		ResultsPage resultsPage = searchPage.searchForProducts(TextData.SEARCH_TEXT);
		webElement = testHelperFunctions.getElement(webDriver, TextData.RESULTS_TEXT);
		testHelperFunctions.validateElement(webElement, "searchForProducts(TextData.SEARCH_TEXT)");
		testHelperFunctions.validatePageTitle("searchForProducts(TextData.SEARCH_TEXT)", PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		List<WebElement> productList = resultsPage.getProductList();
		testHelperFunctions.validatePageTitle("getProductList()", PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		int resultIndex = ConfigData.RESULTS_INDEX;

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];
		
		webElement = testHelperFunctions.getElement(webDriver, TextData.RESULTS_TEXT);
		testHelperFunctions.validateElement(webElement, "getProductList()");
		
		CartPage cartPage = resultsPage.addProductToCart(productName, index);
		webElement = testHelperFunctions.getElement(webDriver, TextData.ADDED_TO_CART_TEXT);
		testHelperFunctions.validateElement(webElement, "addProductToCart(productName, index)");
		testHelperFunctions.validatePageTitle("addProductToCart(productName, index)", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		cartPage.openCart();
		webElement = testHelperFunctions.getElement(webDriver, TextData.SHOPPING_CART_TEXT);
		testHelperFunctions.validateElement(webElement, "openCart()");
		testHelperFunctions.validatePageTitle("openCart()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
				
		LogoutPage logoutPage =  cartPage.deleteCart();	
		webElement = testHelperFunctions.getElement(webDriver, TextData.REMOVED_FROM_CART_TEXT);
		testHelperFunctions.validateElement(webElement, "deleteCart()");
		testHelperFunctions.validatePageTitle("deleteCart()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());

		logoutProcess.completeCartLogout(logoutPage, webDriver);
	}

	@Override
	public int determineIndex(String productName) {
		int index =0;
		if(productName.contains(TextData.PREVIOUSLY_VIEWED_TEXT)) {
			index = 1;
		}
		return index;
	}

}