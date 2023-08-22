package abstractComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HelperFunctions {
	
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
	
	public void validatePageTitle(String expectedPageTitle, String pageTitle) throws IOException {
		Assert.assertEquals(expectedPageTitle, pageTitle);
	}
	
	public boolean isElementPresent(By by, WebDriver webDriver) {
	  boolean exists = false;
	  List<WebElement> list = webDriver.findElements(by);
	  if(!list.isEmpty()) {
	      exists = true;
	  }
	  return exists;
	}
	
	public String convertTestCaseName(String testCaseName) {
		String testName = Arrays.stream(testCaseName.split("(?=\\p{Lu})")).map(str -> str.substring(0, 1).
                toUpperCase() + str.substring(1)).collect(Collectors.joining(" "));
		return testName;
	}
	
}
