package tests;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import pageObjects.CartPage;
import pageObjects.LogoutPage;
import pageObjects.ResultsPage;
import pageObjects.SearchPage;
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
	String email = System.getenv("AMAZON_USERNAME");
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
	WebElement webElement;
	
	@Test(groups = { "E2E" }, priority = 1, description = "End to End scenario")
	public void addToCartTest(Method method) throws Exception {
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Verify a user is able to login, add an item to their cart, remove it and log out successfully.");

		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has started."));

		loginPage.navigateToURL();
		testHelperFunctions.validatePageTitle("navigateToURL()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		webElement = testHelperFunctions.getElement(webDriver, TextData.LANDING_PAGE_SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "navigateToURL()"); 

		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle("navigateToLanding()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		webElement = testHelperFunctions.getElement(webDriver, TextData.SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "navigateToLanding()");
		
		loginPage.enterUserEmail(email); 		
		webElement = testHelperFunctions.getElement(webDriver, TextData.KEEP_SIGNED_IN_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserEmail(email)");
		
		loginPage.enterUserPassword(password);
		webElement = testHelperFunctions.getElement(webDriver, TextData.RETURNS_AND_ORDERS_TEXT);
		testHelperFunctions.validateElement(webElement, "enterUserPassword(password)");
		testHelperFunctions.validatePageTitle("enterUserPassword(password)", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		SearchPage searchPage = loginPage.pauseForSearchPage();	
		webElement = testHelperFunctions.getElement(webDriver, TextData.DELIVER_TO_TEXT);
		testHelperFunctions.validateElement(webElement, "pauseForSearchPage()");
		testHelperFunctions.validatePageTitle("pauseForSearchPage()", PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());

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
		
		logoutPage.openAccountMenu();
		webElement = testHelperFunctions.getElement(webDriver, TextData.ACCOUNT_MENU_TEXT);
		testHelperFunctions.validateElement(webElement, "openAccountMenu()");
		testHelperFunctions.validatePageTitle("openAccountMenu()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
		webElement = testHelperFunctions.getElement(webDriver, TextData.SIGNIN_TEXT);
		testHelperFunctions.validateElement(webElement, "logout()");
		testHelperFunctions.validatePageTitle("logout()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has finished."));
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