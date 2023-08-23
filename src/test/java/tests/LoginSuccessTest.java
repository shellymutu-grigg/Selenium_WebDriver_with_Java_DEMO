package tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import resources.TestHelperFunctions;
import data.PageTitleData;
import pageObjects.LogoutPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginSuccessTest extends TestSetup{
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	
	@Test(groups = { "Smoke", "Login" }, priority = 1, description = "Successful login scenario")
	
	public void loginSuccessTest(Method method) throws Exception {
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Successful login scenario");
		
		String email = System.getenv("AMAZON_USERNAME");
		String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
		
		loginPage.navigateToURL();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());

		loginPage.enterUserDetails(email, password);
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		loginPage.pause();
		
		LogoutPage logoutPage = loginPage.logout();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.openAccountMenu();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		logoutPage.logout();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}

}
