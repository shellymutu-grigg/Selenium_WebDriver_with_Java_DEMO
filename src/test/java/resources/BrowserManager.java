package resources;

import data.ConfigData;
import data.TextData;
import functions.Get;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;

import configuration.ChromeDriverConfig;
import configuration.EdgeDriverConfig;
import configuration.FirefoxDriverConfig;
import testComponents.TestSetup;

public class BrowserManager extends TestSetup {
    public static WebDriver browserSetup() {
        String browserName = "";
        WebDriver webDriver = null;
        try {
            browserName = Get.globalProperty(ConfigData.BROWSER);
            System.setProperty(ConfigData.SYSTEM_PROPERTY_BROWSER, browserName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (browserName != null) {
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

            try{
                //Add implicit timeout
                webDriver.manage()
                        .timeouts()
                        .implicitlyWait(Duration.ofSeconds(15));
            } catch(NullPointerException e){
                System.out.println(MessageFormat.format("NullPointerException: {0}", e.getMessage()));
            }
        }
        return webDriver;
    }
}
