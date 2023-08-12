package tests;

import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import pageObjects.LogoutPage;
import pageObjects.OrdersPage;
import testComponents.TestSetup;

public class ReviewOrdersTest extends TestSetup{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke" })
	public void loginFailureTest() throws Exception {		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_SUCCESS");
		
		// Login to site
		OrdersPage ordersPage = loginPage.loginApplicationOrders(email, password);
		
		ordersPage.openAccountMenu();
		
		ordersPage.openOrderPage();

		LogoutPage logoutPage = ordersPage.searchForOrders();
		
		helperFunctions.validatePageTitle("ordersTitle", webDriver.getTitle());
		
		logoutPage.openAccountMenu();	
		
		logoutPage.logout();
	}

}
