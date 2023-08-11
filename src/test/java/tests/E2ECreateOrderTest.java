package tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.LogoutPage;
import pageObjects.ResultsPage;
import pageObjects.SearchPage;
import testComponents.TestSetup;
import abstractComponents.HelperFunctions;
import interfaces.IHelper;

public class E2ECreateOrderTest extends TestSetup implements IHelper{	
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "E2E" })
	public void createOrderTest() throws Exception {		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_SUCCESS");
		
		// Login to site
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
		logoutPage.openLogoutMenu();
		
		helperFunctions.validatePageTitle("cartTitle", webDriver.getTitle());
		
		logoutPage.logout();
		
		webDriver.quit();
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