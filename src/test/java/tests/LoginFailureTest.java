package tests;

import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import testComponents.TestSetup;

public class LoginFailureTest extends TestSetup{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke" })
	public void loginFailureTest() throws Exception {		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_FAIL");
		
		loginPage.loginApplicationFail(email, password);
		
		webDriver.quit();
	}

}
