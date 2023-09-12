package configuration;

import data.ConfigData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.Duration;

@Slf4j
public class GridDriverConfig {
    public static WebDriver webDriver;

    static Logger logger = LoggerFactory.getLogger(GridDriverConfig.class);

    public static WebDriver gridDriverSetup(String browserName) {
        logger.info("gridDriverSetup({})", browserName);
        if (browserName != null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (browserName.equalsIgnoreCase(ConfigData.CHROME_DRIVER)) {
                // Setup for Selenium Grid implementation
                setCapabilities(capabilities, ConfigData.CHROME_DRIVER);
            } else if (browserName.equalsIgnoreCase(ConfigData.FIREFOX_DRIVER)) {
                // Setup for Selenium Grid implementation
                setCapabilities(capabilities, ConfigData.FIREFOX_DRIVER);
            } else if (browserName.equalsIgnoreCase(ConfigData.EDGE_DRIVER)) {
                // Setup for Selenium Grid implementation
                setCapabilities(capabilities, ConfigData.EDGE_MICROSOFT_DRIVER);
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
        logger.info("{} has instantiated webDriver with hashCode of {}", browserName, webDriver.hashCode());
        return webDriver;
    }

    public static void setCapabilities(DesiredCapabilities capabilities, String browserName){
        try {
            capabilities.setBrowserName(browserName);
            capabilities.setPlatform(Platform.WIN11);
            capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            webDriver = new RemoteWebDriver(new URL(ConfigData.REMOTE_DRIVER_URL), capabilities);
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: {}", e.getMessage());
            throw new RuntimeException(MessageFormat.format("MalformedURLException: {0}", e.getMessage()));
        }
    }
}
