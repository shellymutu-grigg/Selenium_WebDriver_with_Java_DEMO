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
	String email = System.getenv("AMAZON_USERNAME");
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
	
	@Test(groups = { "Smoke", "ErrorHandling" }, priority = 1, description = "Unsuccessful login scenario")
	public void loginFailureTest(Method method) throws Exception {		
		ExtentTestManager.startTest(testHelperFunctions.convertTestCaseName(method.getName()), "Unsuccessful login scenario");

		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has started."));
		
		loginPage.navigateToURL();
		testHelperFunctions.validatePageTitle("navigateToURL()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		loginPage.navigateToLanding();
		testHelperFunctions.validatePageTitle("navigateToLanding()", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		
		loginPage.enterUserEmail(email);
		testHelperFunctions.validatePageTitle("enterUserEmail(email)", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		
		loginPage.enterUserPassword(password);
		testHelperFunctions.validatePageTitle("enterUserPassword(password)", PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
		
		loginPage.loginFail();
		//TODO validate page title
		
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), testHelperFunctions.convertTestCaseName(method.getName() + " has finished."));
	}
}
