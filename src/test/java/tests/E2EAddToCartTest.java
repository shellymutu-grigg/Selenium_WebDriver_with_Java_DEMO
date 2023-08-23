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

	
	@Test(groups = { "E2E" }, priority = 1, description = "End to End scenario")
	public void addToCartTest(Method method) throws Exception {
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Verify a user is able to login, add an item to their cart, remove it and log out successfully.");

		loginPage.navigateToURL();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());

		loginPage.enterUserDetails(email, password);
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		loginPage.pause();
		
		SearchPage searchPage = loginPage.searchPage(email, password);		
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());

		searchPage.searchForProducts1(TextData.SEARCH_TEXT);
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		ResultsPage resultsPage = searchPage.searchForProducts(TextData.SEARCH_TEXT);
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		List<WebElement> productList = resultsPage.getProductList();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		int resultIndex = ConfigData.RESULTS_INDEX;

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];
		
		CartPage cartPage = resultsPage.addProductToCart(productName, index);
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		cartPage.openCart();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
				
		LogoutPage logoutPage =  cartPage.deleteCart();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.openAccountMenu();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}

	@Override
	public int determineIndex(String productName) {
		int index =0;
		if(productName.contains(TextData.PREVIOUSLY_VIEWED)) {
			index = 1;
		}
		return index;
	}

}