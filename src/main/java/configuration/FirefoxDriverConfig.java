package configuration;

import data.ConfigData;
import functions.Get;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverConfig {

	public static WebDriver setUpFirefoxDriver() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		boolean headless = Boolean.parseBoolean(Get.globalProperty(ConfigData.HEADLESS));
		if (headless) {
			firefoxOptions.addArguments("--headless");
		}
		return new FirefoxDriver(firefoxOptions);
	}
}
