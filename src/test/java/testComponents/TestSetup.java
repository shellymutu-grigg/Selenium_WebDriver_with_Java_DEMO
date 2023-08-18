package testComponents;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.LoginPage;

import abstractComponents.HelperFunctions;

public class TestSetup {

	public WebDriver webDriver;
	public LoginPage loginPage;
	HelperFunctions helperFunctions = new HelperFunctions();
	
	@BeforeMethod(alwaysRun = true)
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
			webDriver.manage().window().setSize(new Dimension(1440, 900));

		} else if (browserNameString.equalsIgnoreCase("firefox")) {
			webDriver = new FirefoxDriver();
		} else if (browserNameString.equalsIgnoreCase("edge")) {
			webDriver = new EdgeDriver();
		}

		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		webDriver.manage().window().maximize();
		ITestContext context = Reporter.getCurrentTestResult().getTestContext();
		context.setAttribute("WebDriver", webDriver);
		return webDriver;
	}

	@BeforeMethod(alwaysRun = true)
	protected LoginPage launchApplication() throws IOException, InterruptedException {
		loginPage = new LoginPage(webDriver);
		loginPage.navigateToURL();
		
		return loginPage;
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		webDriver.close();
	}
	
	public String captureScreenshot(String testCaseName) throws IOException {
		Date calendarDate = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
        String date = dateFormat.format(calendarDate);  
        TakesScreenshot takeScreenshot = (TakesScreenshot)webDriver;
        File screenshot = takeScreenshot.getScreenshotAs(OutputType.FILE);
        File screenshotOutputFile = new File(System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.toString().replace(":", "_").replace(" ", "_") + ".png");
		FileUtils.copyFile(screenshot, screenshotOutputFile);
		return System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.toString().replace(":", "_").replace(" ", "_") + ".png";
    }
}