package testComponents;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.*;

import resources.ExtentManager;

public class TestSetup {

	@BeforeMethod(alwaysRun = true)
	protected void beforeMethod (Method method){

	}
	@AfterMethod(alwaysRun = true)
	protected void afterMethod(Method method){

	}

	@BeforeTest
	public void beforeTest(ITestContext context){

	}

	@AfterTest
	public void afterTest(ITestContext context) {
	}

	@BeforeClass(alwaysRun = true)
	public void setupWebBrowser(ITestContext context) {
	}

	@AfterClass(alwaysRun = true)
	public void removeThreadLocalDriver() {
	}

	@AfterSuite(alwaysRun = true)
	public void closeReports () {
		ExtentManager.extentReports.flush();
	}
}