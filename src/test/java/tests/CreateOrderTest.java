package tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.LogoutPage;
import pageObjects.ResultsPage;
import pageObjects.SearchPage;
import testComponents.TestSetup;
import abstractComponents.HelperFunctions;

public class CreateOrderTest extends TestSetup {	
	@Test(groups = { "Login" })
	public void createOrderTest() throws IOException, InterruptedException {		
		String email = GetParameter("EMAIL");
		String password = GetParameter("PASSWORD_SUCCESS");
		
		// Login to site
		SearchPage searchPage = loginPage.loginApplication(email, password);		
		
		ResultsPage resultsPage = searchPage.searchForProducts(getGlobalProperty("searchText"));
		
		List<WebElement> productList = resultsPage.getProductList();
		int resultIndex = Integer.parseInt(getGlobalProperty("resultIndex"));

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		HelperFunctions helperFunction = new HelperFunctions();
		int index = helperFunction.determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];

		CartPage cartPage = resultsPage.addProductToCart(productName, index);		
		cartPage.openCart();
		
		LogoutPage logoutPage =  cartPage.deleteCart();
		logoutPage.openLogoutMenu();
		logoutPage.logout();
		
	}

}