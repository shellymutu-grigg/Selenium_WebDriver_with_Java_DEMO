package testComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Dimension;
//import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.LoginPage;

public class TestSetup {

	public WebDriver webDriver;
	public LoginPage loginPage;

	public WebDriver initializeDriver() throws IOException {
		String browserNameString = getGlobalProperty("browser");

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
	
	public String getGlobalProperty(String parameter) throws IOException {;
		// Read in properties file
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//resources//globalData.properties");
		properties.load(fileInputStream);
		
		// If the value is sent via Maven commands use that otherwise use the globalData.properties file
		String browser = System.getProperty(parameter) != null ? System.getProperty(parameter)
				: properties.getProperty(parameter);
		return browser;
	}

	@BeforeMethod(alwaysRun = true)
	protected LoginPage launchApplication() throws IOException {
		webDriver = initializeDriver();
		loginPage = new LoginPage(webDriver);
		loginPage.navigate();
		return loginPage;

	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		//webDriver.close();
	}

}