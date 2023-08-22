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
import abstractComponents.HelperFunctions;
import data.ConfigData;
import data.PageTitleData;
import data.TextData;
import interfaces.IHelper;
import resources.ExtentListeners;
import resources.ExtentTestManager;

@Listeners(ExtentListeners.class)
public class E2EAddToCartTest extends TestSetup implements IHelper{	
	HelperFunctions helperFunctions = new HelperFunctions();
	String email = System.getenv("AMAZON_USERNAME");
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");

	
	@Test(groups = { "E2E" }, priority = 1, description = "End to End scenario")
	public void addToCartTest(Method method) throws Exception {	
		ExtentTestManager.startTest(method.getName(), "Verify a user is able to login, add an item to their cart, remove it and log out successfully.");

		SearchPage searchPage = loginPage.loginApplicationSuccess(email, password);		
				
		ResultsPage resultsPage = searchPage.searchForProducts(TextData.SEARCH_TEXT);
		
		List<WebElement> productList = resultsPage.getProductList();
		int resultIndex = ConfigData.RESULTS_INDEX;

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];
		
		CartPage cartPage = resultsPage.addProductToCart(productName, index);
				
		cartPage.openCart();
				
		LogoutPage logoutPage =  cartPage.deleteCart();
				
		logoutPage.openAccountMenu();
		
		helperFunctions.validatePageTitle(PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();

	}

	@Override
	public int determineIndex(String productName) {
		int index =0;
		if(productName.contains("Previously viewed")) {
			index = 1;
		}
		return index;
	}

}