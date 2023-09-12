package configuration;

import data.ConfigData;
import functions.Get;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverConfig {

	public static WebDriver setUpChromeDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--ignore-ssl-errors=yes");
		chromeOptions.addArguments("--ignore-certificate-errors");
		if (Get.globalProperty(ConfigData.HEADLESS)=="true") {
			chromeOptions.addArguments("--headless=new");
		}
		return new ChromeDriver(chromeOptions);
	}
}
