package testComponents;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.LoginPage;

import abstractComponents.HelperFunctions;

public class TestSetup {

	public WebDriver webDriver;
	public LoginPage loginPage;
	HelperFunctions helperFunctions = new HelperFunctions();
	
	protected static ExtentReports extentReports;
	protected static ExtentTest extentTest;

	public WebDriver initializeDriver() throws IOException {
		String browserNameString = helperFunctions.getGlobalProperty("browser");

		if (browserNameString.contains("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--ignore-ssl-errors=yes");
			chromeOptions.addArguments("--ignore-certificate-errors");
			WebDriverManager.chromedriver().setup();
			if (browserNameString.contains("headless")) {
				chromeOptions.addArguments("headless");
			}
			webDriver = new ChromeDriver(chromeOptions);

			// Even when running in headless mode ensure screen is maximised to view all
			// elements
			webDriver.manage().window().setSize(new Dimension(1440, 900));

		} else if (browserNameString.equalsIgnoreCase("firefox")) {
			webDriver = new FirefoxDriver();
		} else if (browserNameString.equalsIgnoreCase("edge")) {
			webDriver = new EdgeDriver();
		}

		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		webDriver.manage().window().maximize();
		return webDriver;

	}
	
	@BeforeMethod(alwaysRun = true)
	protected void attachExtentReports() throws IOException, InterruptedException {
		// ExtentSparkReporter
		String pathnameString = System.getProperty("user.dir") + "//reports//index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(pathnameString);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		// ExtentReports
		extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Tester", "Shelly Mutu-Grigg");
	}

	@BeforeMethod(alwaysRun = true)
	protected LoginPage launchApplication() throws IOException, InterruptedException {
		webDriver = initializeDriver();
		loginPage = new LoginPage(webDriver);
		loginPage.navigateToURL();
		
		// ExtentSparkReporter
		String pathnameString = System.getProperty("user.dir") + "//reports//index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(pathnameString);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		// ExtentReports
		extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Tester", "Shelly Mutu-Grigg");
		return loginPage;
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		webDriver.close();
	}
}