package configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverConfig {

	public static WebDriver setUpChromeDriver(String browserNameString, WebDriver webDriver) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--ignore-ssl-errors=yes");
		chromeOptions.addArguments("--ignore-certificate-errors");
		chromeOptions.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir"));
		if (browserNameString.contains("headless")) {
			chromeOptions.addArguments("--headless=new");
		}
		return webDriver = new ChromeDriver(chromeOptions);
	}
}
