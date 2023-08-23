package tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.TestHelperFunctions;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginFailureTest extends TestSetup {
	TestHelperFunctions testHelperFunctions = new TestHelperFunctions();
	
	@Test(groups = { "Smoke", "ErrorHandling" }, priority = 1, description = "Unsuccessful login scenario")
	public void loginFailureTest(Method method) throws Exception {		
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Unsuccessful login scenario");
		
		String email = System.getenv("AMAZON_USERNAME");
		String password = System.getenv("AMAZON_PASSWORD_FAIL");
		
		loginPage.navigateToURL();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle(testHelperFunctions.convertTestCaseName(method.getName()), PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		
		loginPage.enterUserDetails(email, password);
		
		loginPage.loginFail();
		//TODO validate page title
	}
}
