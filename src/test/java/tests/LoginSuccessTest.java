package tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import pageObjects.LogoutPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginSuccessTest extends TestSetup{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke", "Login" }, priority = 2, description = "Successful login scenario")
	
	public void loginSuccessTest(Method method) throws Exception {
		ExtentTestManager.startTest(method.getName(), "Successful login scenario");
		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_SUCCESS");
		
		LogoutPage logoutPage = loginPage.loginApplication(email, password);
		
		logoutPage.openAccountMenu();
		
		helperFunctions.validatePageTitle("loggedInLandingTitle", webDriver.getTitle());
		
		logoutPage.logout();
	}

}
