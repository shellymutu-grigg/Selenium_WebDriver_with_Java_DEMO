package resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverConfig {

	public static WebDriver setUpFirefoxDriver(String browserNameString, WebDriver webDriver) {
		FirefoxOptions options = new FirefoxOptions();
		return webDriver = new FirefoxDriver(options);
	}
}
