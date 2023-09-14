package configuration;

import data.ConfigData;
import data.LocalStore;
import functions.Get;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.Duration;

@Slf4j
public class GridDriverConfig {
    public static AbstractDriverOptions browserOptions;
    static Logger logger = LoggerFactory.getLogger(GridDriverConfig.class);

    public static WebDriver gridDriverSetup(ITestResult result, String browserType) {
        WebDriver webDriver = null;
        logger.info("Thread {} ({}) has initiated gridDriverSetup({})", Thread.currentThread().getId(), result.getMethod().getMethodName(), browserType);
        if (browserType != null) {
            if (browserType.equalsIgnoreCase(ConfigData.CHROME_DRIVER)) {
                ChromeOptions chromeOptions = new ChromeOptions();
                boolean headless = Boolean.parseBoolean(Get.globalProperty(ConfigData.HEADLESS));
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                browserOptions = chromeOptions;
            } else if (browserType.equalsIgnoreCase(ConfigData.FIREFOX_DRIVER)) {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                boolean headless = Boolean.parseBoolean(Get.globalProperty(ConfigData.HEADLESS));
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                browserOptions = firefoxOptions;
            } else if (browserType.equalsIgnoreCase(ConfigData.EDGE_DRIVER)) {
                browserOptions = new EdgeOptions();
                EdgeOptions edgeOptions = new EdgeOptions();
                boolean headless = Boolean.parseBoolean(Get.globalProperty(ConfigData.HEADLESS));
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                browserOptions = edgeOptions;
            }

            browserOptions.setPlatformName("Windows 11");

            try {
                webDriver = new RemoteWebDriver(new URL(ConfigData.REMOTE_DRIVER_URL), browserOptions);
            } catch (MalformedURLException e) {
                logger.error("MalformedURLException: {}", e.getMessage());
                throw new RuntimeException(MessageFormat.format("MalformedURLException: {0}", e.getMessage()));
            }

            webDriver.manage().window().setSize(new Dimension(1050, 650));

            try {
                //Add implicit timeout
                webDriver.manage()
                        .timeouts()
                        .implicitlyWait(Duration.ofSeconds(5));
            } catch (NullPointerException e) {
                logger.error("NullPointerException: {}", e.getMessage());
                throw new NullPointerException(MessageFormat.format("NullPointerException: {0}", e.getMessage()));
            }
        }
        logger.info("Thread {} ({}) has instantiated webDriver with hashCode of {}", Thread.currentThread().getId(), result.getMethod().getMethodName(), webDriver.hashCode());
        LocalStore.setObject(String.valueOf(Thread.currentThread().getId()), webDriver);
        return webDriver;
    }
}
