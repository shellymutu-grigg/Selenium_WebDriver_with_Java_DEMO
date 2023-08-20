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
import interfaces.IHelper;
import resources.ExtentListeners;
import resources.ExtentTestManager;

@Listeners(ExtentListeners.class)
public class E2ECreateOrderTest extends TestSetup implements IHelper{	
	HelperFunctions helperFunctions = new HelperFunctions();
	String email = helperFunctions.GetParameter("EMAIL");
	String password = helperFunctions.GetParameter("PASSWORD_SUCCESS");

	
	@Test(groups = { "E2E" }, priority = 1, description = "End to End scenario")
	public void createOrderTest(Method method) throws Exception {	
		ExtentTestManager.startTest(method.getName(), "Verify a user is able to login, add and item to their cart, remove it and log out successfully.");
		
		SearchPage searchPage = loginPage.loginApplicationSuccess(email, password);		
				
		ResultsPage resultsPage = searchPage.searchForProducts(helperFunctions.getGlobalProperty("searchText"));
		
		List<WebElement> productList = resultsPage.getProductList();
		int resultIndex = Integer.parseInt(helperFunctions.getGlobalProperty("resultIndex"));

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];
		
		CartPage cartPage = resultsPage.addProductToCart(productName, index);
				
		cartPage.openCart();
				
		LogoutPage logoutPage =  cartPage.deleteCart();
				
		logoutPage.openAccountMenu();
		
		helperFunctions.validatePageTitle("cartTitle", webDriver.getTitle());
		
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