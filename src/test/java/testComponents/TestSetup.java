package testComponents;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import data.ConfigData;
import functions.Get;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import pageObjects.LoginPage;
import resources.BrowserManager;
import resources.ExtentManager;

@Slf4j
public class TestSetup {

	public LoginPage loginPage;
	public static WebDriver webDriver;

	protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

	static Logger logger = LoggerFactory.getLogger(TestSetup.class);

	//Get thread-safe webDriver
	public static WebDriver getDriver () {
		return threadLocalDriver.get();
	}

	@BeforeMethod(alwaysRun = true)
	protected void launchApplication (Method method){
		loginPage = new LoginPage();
	}

	@BeforeTest
	public void threadId(){
		logger.info("@BeforeTest ThreadID: {}", Thread.currentThread().getId());
	}

	@AfterTest
	public void tearDown() {
		logger.info("@AfterTest ThreadID: {}", Thread.currentThread().getId());
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
				logger.error("@AfterSuite NoSuchSessionException has message: {}", e.getMessage());
				throw new NoSuchSessionException(MessageFormat.format("NoSuchSessionException: {0}", e.getMessage()));
			}
			// Edge generates a SocketException: Connection reset
		}
	}
}