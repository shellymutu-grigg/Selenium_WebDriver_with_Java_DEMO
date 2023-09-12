package configuration;

import data.ConfigData;
import functions.Get;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverConfig {

	public static WebDriver setUpFirefoxDriver() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		if (Get.globalProperty(ConfigData.HEADLESS)=="true") {
			firefoxOptions.addArguments("--headless=new");
		}
		return new FirefoxDriver(firefoxOptions);
	}
}
