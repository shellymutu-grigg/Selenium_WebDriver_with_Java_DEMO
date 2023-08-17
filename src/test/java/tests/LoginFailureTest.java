package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import resources.ExtentListeners;
import testComponents.TestSetup;

@Listeners(ExtentListeners.class)
public class LoginFailureTest extends TestSetup {
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke", "ErrorHandling" })
	public void loginFailureTest() throws Exception {		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_FAIL");
		
		loginPage.loginApplicationFail(email, password);
	}

}
