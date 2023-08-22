package tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import data.PageTitleData;
import pageObjects.LogoutPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginSuccessTest extends TestSetup{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke", "Login" }, priority = 1, description = "Successful login scenario")
	
	public void loginSuccessTest(Method method) throws Exception {
		ExtentTestManager.startTest(method.getName(), "Successful login scenario");
		
		String email = System.getenv("AMAZON_USERNAME");
		String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
		
		LogoutPage logoutPage = loginPage.loginApplication(email, password);
		
		logoutPage.openAccountMenu();
		
		helperFunctions.validatePageTitle(PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
	}

}
