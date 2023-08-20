package tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginFailureTest extends TestSetup {
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke", "ErrorHandling" }, priority = 3, description = "Unsuccessful login scenario")
	public void loginFailureTest(Method method) throws Exception {		
		ExtentTestManager.startTest(method.getName(), "Unsuccessful login scenario");
		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_FAIL");
		
		loginPage.loginApplicationFail(email, password);
	}

}
