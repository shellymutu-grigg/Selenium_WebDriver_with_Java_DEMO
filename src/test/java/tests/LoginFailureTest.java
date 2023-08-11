package tests;

import org.testng.ITestListener;
import org.testng.annotations.Test;

import abstractComponents.HelperFunctions;
import testComponents.TestSetup;

public class LoginFailureTest extends TestSetup implements ITestListener{
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@Test(groups = { "Smoke" })
	public void loginFailureTest() throws Exception {		
		String email = helperFunctions.GetParameter("EMAIL");
		String password = helperFunctions.GetParameter("PASSWORD_FAIL");
		
		loginPage.loginApplicationFail(email, password);
		
		Thread.sleep(5);
		webDriver.quit();
	}

}
