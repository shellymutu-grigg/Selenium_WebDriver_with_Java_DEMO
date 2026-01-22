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
import java.time.Duration;

@Slf4j
public class GridDriverConfig {

    static Logger logger = LoggerFactory.getLogger(GridDriverConfig.class);

    public static WebDriver gridDriverSetup(ITestResult result, String browserType) {
        logger.info(
                "Thread {} ({}) has initiated gridDriverSetup({})",
                Thread.currentThread().getId(),
                result.getMethod().getMethodName(),
                browserType
        );

        if (browserType == null || browserType.trim().isEmpty()) {
            browserType = Get.globalProperty(ConfigData.BROWSER);
        }

        if (browserType == null || browserType.trim().isEmpty()) {
            throw new IllegalArgumentException("Browser type not specified");
        }

        AbstractDriverOptions<?> browserOptions;

        boolean headless = Boolean.parseBoolean(Get.globalProperty(ConfigData.HEADLESS));

        if (browserType.equalsIgnoreCase(ConfigData.CHROME_DRIVER)) {
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            browserOptions = options;

        } else if (browserType.equalsIgnoreCase(ConfigData.FIREFOX_DRIVER)) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            browserOptions = options;

        } else if (browserType.equalsIgnoreCase(ConfigData.EDGE_DRIVER)) {
            EdgeOptions options = new EdgeOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            browserOptions = options;

        } else {
            throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }

        WebDriver webDriver;
        try {
            String gridUrl = Get.globalProperty(ConfigData.REMOTE_DRIVER_URL);
            if (gridUrl == null || gridUrl.trim().isEmpty()) {
                gridUrl = "http://localhost:4444"; // runtime fallback
            }
            webDriver = new RemoteWebDriver(new URL(gridUrl), browserOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL", e);
        }

        webDriver.manage().window().setSize(new Dimension(1050, 650));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        logger.info(
                "Thread {} ({}) has instantiated webDriver with hashCode {}",
                Thread.currentThread().getId(),
                result.getMethod().getMethodName(),
                webDriver.hashCode()
        );

        LocalStore.setObject(String.valueOf(Thread.currentThread().getId()), webDriver);
        return webDriver;
    }
}
