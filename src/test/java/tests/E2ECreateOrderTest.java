package tests;

import java.util.List;

import org.openqa.selenium.WebElement;
//import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.CartPage;
import pageObjects.LogoutPage;
import pageObjects.ResultsPage;
import pageObjects.SearchPage;
import testComponents.TestSetup;
import abstractComponents.HelperFunctions;
import interfaces.IHelper;
import resources.ListenersNG;

@Listeners(ListenersNG.class)
public class E2ECreateOrderTest extends TestSetup implements IHelper{	
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "E2E" })
	public void createOrderTest() throws Exception {	
		extentTest = extentReports.createTest("End to End Test");
		extentTest.assignAuthor("Shelly Mutu-Grigg");
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_SUCCESS");
		
		//listener.logResult(extentTest.getStatus(), "Test Started");
		
		// Login to site
		SearchPage searchPage = loginPage.loginApplicationSuccess(email, password);		
		
		//listener.logResult(extentTest.getStatus(), "User logged into application");
		
		ResultsPage resultsPage = searchPage.searchForProducts(helperFunctions.getGlobalProperty("searchText"));
		
		List<WebElement> productList = resultsPage.getProductList();
		int resultIndex = Integer.parseInt(helperFunctions.getGlobalProperty("resultIndex"));

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];

		//listener.logResult(extentTest.getStatus(), "User attempts to search for a prouct");
		
		CartPage cartPage = resultsPage.addProductToCart(productName, index);
		
		//listener.logResult(extentTest.getStatus(), "User attempts to add prouct to cart");
		
		cartPage.openCart();
		
		//listener.logResult(extentTest.getStatus(), "User attempts to navigate to cart");		
		
		LogoutPage logoutPage =  cartPage.deleteCart();
		
		//listener.logResult(extentTest.getStatus(), "User attempts to delete product from cart");
		
		logoutPage.openAccountMenu();
		
		//listener.logResult(extentTest.getStatus(), "User attempts to open account menu");
		
		helperFunctions.validatePageTitle("cartTitle", webDriver.getTitle());
		
		logoutPage.logout();
		
		//listener.logResult(extentTest.getStatus(), "User attempts to log out");
		
		
		//listener.onFinish(null);
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