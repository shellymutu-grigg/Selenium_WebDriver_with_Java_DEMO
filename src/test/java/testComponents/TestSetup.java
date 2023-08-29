package testComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import functions.GetGlobalProperty;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import org.testng.Reporter;

import pageObjects.LoginPage;
import configuration.ChromeDriverConfig;
import configuration.EdgeDriverConfig;
import resources.ExtentManager;
import configuration.FirefoxDriverConfig;

public class TestSetup {

	public WebDriver webDriver;
	public LoginPage loginPage;
	public String loginFailureStatus;
	
	@BeforeClass(alwaysRun = true)
	public WebDriver initializeDriver() {
		String browserNameString = "";
		try {
			browserNameString = GetGlobalProperty.getGlobalProperty("browser");
			System.setProperty("Browser", browserNameString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(browserNameString != null) {
			if (browserNameString.contains("chrome")) {
				final WebDriver chromeDriver = ChromeDriverConfig.setUpChromeDriver(browserNameString, webDriver);
				webDriver = chromeDriver;
			} else if (browserNameString.equalsIgnoreCase("firefox")) {
				final WebDriver firefoxDriver = FirefoxDriverConfig.setUpFirefoxDriver(browserNameString, webDriver);
				webDriver = firefoxDriver;
			} else if (browserNameString.equalsIgnoreCase("edge")) {
				final WebDriver edgeDriver = EdgeDriverConfig.setUpEdgeDriver(browserNameString, webDriver);
				webDriver = edgeDriver;
			}
			webDriver.manage().window().setSize(new Dimension(1050, 650));
			webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			ITestContext context = Reporter.getCurrentTestResult().getTestContext();
			context.setAttribute("WebDriver", webDriver);
			return webDriver;
		} else {
			return null;
		}
	}

	@BeforeMethod(alwaysRun = true)
	protected void launchApplication(Method method){
		loginPage = new LoginPage(webDriver);
		String methodName = method.getName();
		System.setProperty("MethodName", methodName);
	}

	@AfterClass(alwaysRun = true)
	public void teardown() {
		if(!webDriver.toString().contains("null")) {
			webDriver.close();
		}
	}
	
	@AfterSuite(alwaysRun = true)
	public void closeReports() {
		ExtentManager.extentReports.flush();
		if (!webDriver.toString().contains("null")) {
			try {
				webDriver.quit();
			// Firefox generates a "NoSuchSessionException: Tried to run command without establishing a connection exception" 
			// if .quit() called directly
			} catch (NoSuchSessionException e) {
		        return;
			} 
			// Edge generates a SocketException: Connection reset
		}
	}
}