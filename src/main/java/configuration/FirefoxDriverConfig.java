package configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverConfig {

	public static WebDriver setUpFirefoxDriver(String browserNameString) {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		if (browserNameString.contains("headless")) {
			firefoxOptions.addArguments("--headless=new");
		}
		return new FirefoxDriver(firefoxOptions);
	}
}
