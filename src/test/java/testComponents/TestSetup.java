package testComponents;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import data.ConfigData;
import functions.Get;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import pageObjects.LoginPage;
import resources.BrowserManager;
import resources.ExtentManager;

public class TestSetup {

	public LoginPage loginPage;
	public WebDriver webDriver;

	protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

	//Get thread-safe webDriver
	public static WebDriver getDriver () {
		return threadLocalDriver.get();
	}

	@BeforeMethod(alwaysRun = true)
	protected void launchApplication (Method method){
		loginPage = new LoginPage(getDriver());
		String methodName = method.getName();
		System.setProperty(ConfigData.SYSTEM_PROPERTY_METHOD_NAME, methodName);
	}

	@BeforeTest
	public void threadId(){
		System.out.println(MessageFormat.format("@BeforeTest ThreadID: {0}", Thread.currentThread().getId()));
	}

	@AfterTest
	public void tearDown() {
		System.out.println(MessageFormat.format("@AfterTest ThreadID: {0}", Thread.currentThread().getId()));
		threadLocalDriver.remove();
	}
	@BeforeClass(alwaysRun = true)
	public void setup(ITestContext context) {
		webDriver = BrowserManager.browserSetup();
		context.setAttribute(ConfigData.SYSTEM_PROPERTY_WEBDRIVER, getDriver());
		getDriver().get(Get.url());
	}

	@AfterClass(alwaysRun = true)
	public void closeWebDriver () {
		if (getDriver()!=null) {
			getDriver().close();
		}
	}

	@AfterSuite(alwaysRun = true)
	public void closeReports () {
		ExtentManager.extentReports.flush();
		if (getDriver()!=null) {
			try {
				getDriver().quit();
				// Firefox generates a "NoSuchSessionException: Tried to run command without establishing a connection exception"
				// if .quit() called directly
			} catch (NoSuchSessionException e) {

			}
			// Edge generates a SocketException: Connection reset
		}
	}
}