package resources;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import data.ConfigData;
import data.LocalStore;
import configuration.ChromeDriverConfig;
import configuration.EdgeDriverConfig;
import configuration.FirefoxDriverConfig;
import functions.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import static resources.ExtentListeners.getTestMethodName;

@Slf4j
public class BrowserManager {
    static Logger logger = LoggerFactory.getLogger(BrowserManager.class);
    public static WebDriver browserSetup(ITestResult result) {
        WebDriver webDriver = null;
        String browserName = Get.globalProperty(ConfigData.BROWSER);
        LocalStore.setObject(ConfigData.SYSTEM_PROPERTY_BROWSER, browserName);

        if (browserName.equalsIgnoreCase(ConfigData.CHROME_DRIVER)) {
            webDriver = ChromeDriverConfig.setUpChromeDriver();
        } else if (browserName.equalsIgnoreCase(ConfigData.FIREFOX_DRIVER)) {
            webDriver = FirefoxDriverConfig.setUpFirefoxDriver();
        } else if (browserName.equalsIgnoreCase(ConfigData.EDGE_DRIVER)) {
            webDriver = EdgeDriverConfig.setUpEdgeDriver();
        }

        webDriver.manage().window().setSize(new Dimension(1050, 650));

        //Add implicit timeout
        assert webDriver != null;
        webDriver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(2));

        logger.info("Thread {} ({}) has generated webDriver with hashCode {}", Thread.currentThread().getId(), getTestMethodName(result), webDriver.hashCode());
        LocalStore.setObject(String.valueOf(Thread.currentThread().getId()), webDriver);
        return webDriver;
    }
}
