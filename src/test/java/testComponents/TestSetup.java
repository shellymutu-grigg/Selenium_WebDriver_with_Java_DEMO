package testComponents;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.LoginPage;
import resources.ExtentManager;
import abstractComponents.HelperFunctions;

public class TestSetup {

	public WebDriver webDriver;
	public LoginPage loginPage;
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@BeforeClass(alwaysRun = true)
	public WebDriver initializeDriver() throws IOException{
		String browserNameString = helperFunctions.getGlobalProperty("browser");

		if (browserNameString.contains("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--ignore-ssl-errors=yes");
			chromeOptions.addArguments("--ignore-certificate-errors");
			chromeOptions.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir"));
			WebDriverManager.chromedriver().setup();
			if (browserNameString.contains("headless")) {
				chromeOptions.addArguments("headless");
			}
			webDriver = new ChromeDriver(chromeOptions);

		} else if (browserNameString.equalsIgnoreCase("firefox")) {
			webDriver = new FirefoxDriver();
		} else if (browserNameString.equalsIgnoreCase("edge")) {
			webDriver = new EdgeDriver();
		}

		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		ITestContext context = Reporter.getCurrentTestResult().getTestContext();
		context.setAttribute("WebDriver", webDriver);
		return webDriver;
	}

	@BeforeMethod(alwaysRun = true)
	protected void launchApplication() throws IOException, InterruptedException {
		loginPage = new LoginPage(webDriver);
		loginPage.navigateToURL();		
	}

	@AfterClass(alwaysRun = true)
	public void teardown() {
		webDriver.quit();
	}
	
	@AfterSuite(alwaysRun = true)
	public void closeReports() {
		ExtentManager.extentReports.flush();
	}

}