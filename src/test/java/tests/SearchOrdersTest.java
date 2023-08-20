package tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import pageObjects.LogoutPage;
import pageObjects.OrdersPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class SearchOrdersTest extends TestSetup{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke" }, priority = 1, description = "Verify user is able search order history")
	public void reviewOrdersTest(Method method) throws Exception {		
		ExtentTestManager.startTest(method.getName(), "Verify user is able search order history");
		
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
