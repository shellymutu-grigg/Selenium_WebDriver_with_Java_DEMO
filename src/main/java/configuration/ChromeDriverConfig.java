package configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverConfig {

	public static WebDriver setUpChromeDriver(String browserNameString) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--ignore-ssl-errors=yes");
		chromeOptions.addArguments("--ignore-certificate-errors");
		if (browserNameString.contains("headless")) {
			chromeOptions.addArguments("--headless=new");
		}
		return new ChromeDriver(chromeOptions);
	}
}
