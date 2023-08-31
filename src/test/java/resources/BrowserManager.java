package resources;


import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import data.ConfigData;
import data.LocalStore;
import configuration.ChromeDriverConfig;
import configuration.EdgeDriverConfig;
import configuration.FirefoxDriverConfig;
import functions.Get;
import testComponents.TestSetup;

public class BrowserManager extends TestSetup {
    public static WebDriver browserSetup() {
        String browserName;
        WebDriver webDriver = null;
        browserName = Get.globalProperty(ConfigData.BROWSER);
        LocalStore.setObject(ConfigData.SYSTEM_PROPERTY_BROWSER, browserName);

        if (browserName.equalsIgnoreCase(ConfigData.CHROME_DRIVER)) {
            webDriver = ChromeDriverConfig.setUpChromeDriver(browserName);
        } else if (browserName.equalsIgnoreCase(ConfigData.FIREFOX_DRIVER)) {
            webDriver = FirefoxDriverConfig.setUpFirefoxDriver(browserName);
        } else if (browserName.equalsIgnoreCase(ConfigData.EDGE_DRIVER)) {
            webDriver = EdgeDriverConfig.setUpEdgeDriver(browserName);
        }
        //Set the webDriver in the threadLocalDriver
        threadLocalDriver.set(webDriver);

        getDriver().manage().window().setSize(new Dimension(1050, 650));

        //Add implicit timeout
        assert webDriver != null;
        webDriver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(15));

        LocalStore.setObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER, webDriver);
        return webDriver;
    }
}
