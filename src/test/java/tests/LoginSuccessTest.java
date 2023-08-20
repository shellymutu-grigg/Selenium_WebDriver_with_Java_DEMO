package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import pageObjects.LogoutPage;
import resources.ExtentListeners;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginSuccessTest extends TestSetup{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke", "Login" })
	
	public void loginSuccessTest() throws Exception {		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_SUCCESS");
		
		LogoutPage logoutPage = loginPage.loginApplication(email, password);
		
		logoutPage.openAccountMenu();
		
		helperFunctions.validatePageTitle("loggedInLandingTitle", webDriver.getTitle());
		
		logoutPage.logout();
	}

}
