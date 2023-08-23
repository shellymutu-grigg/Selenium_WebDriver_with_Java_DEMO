package functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperFunctions {
	static WebDriver webDriver;

	public HelperFunctions(WebDriver webDriver) {
		HelperFunctions.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
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
	
	public boolean isElementPresent(By by, WebDriver webDriver) {
	  boolean exists = false;
	  List<WebElement> list = webDriver.findElements(by);
	  if(!list.isEmpty()) {
	      exists = true;
	  }
	  return exists;
	}
	
	public static void waitForElementToAppear(By findBy, WebDriver providedWebDriver) {
		WebDriverWait wait = new WebDriverWait(providedWebDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
}
