package resources;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.time.Duration;

import configuration.ChromeDriverConfig;
import configuration.EdgeDriverConfig;
import configuration.FirefoxDriverConfig;
import functions.GlobalProperty;
import testComponents.TestSetup;

public class BrowserManager extends TestSetup {
    public static WebDriver browserSetup() {
        String browserName = "";
        WebDriver webDriver = null;
        try {
            browserName = GlobalProperty.getGlobalProperty("browser");
            System.setProperty("Browser", browserName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (browserName != null) {
            if (browserName.equalsIgnoreCase("chrome")) {
                webDriver = ChromeDriverConfig.setUpChromeDriver(browserName);
            } else if (browserName.equalsIgnoreCase("firefox")) {
                webDriver = FirefoxDriverConfig.setUpFirefoxDriver(browserName);
            } else if (browserName.equalsIgnoreCase("edge")) {
                webDriver = EdgeDriverConfig.setUpEdgeDriver(browserName);
            }
            //Set the webDriver in the threadLocalDriver
            threadLocalDriver.set(webDriver);

            getDriver().manage().window().setSize(new Dimension(1050, 650));

            //Add implicit timeout
            webDriver.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(10));
        }
        return webDriver;
    }
}
