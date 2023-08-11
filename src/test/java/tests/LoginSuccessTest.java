package tests;

import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import pageObjects.LogoutPage;
import testComponents.TestSetup;

public class LoginSuccessTest extends TestSetup{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke" })
	public void loginFailureTest() throws Exception {		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_SUCCESS");
		
		// Login to site
		LogoutPage logoutPage = loginPage.loginApplication(email, password);
		
		logoutPage.openLogoutMenu();
		
		helperFunctions.validatePageTitle("loggedInLandingTitle", webDriver.getTitle());
		
		logoutPage.logout();
		
		webDriver.quit();
	}

}
