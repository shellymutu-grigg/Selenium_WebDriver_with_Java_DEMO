package resources;

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

public class GridDriver {
    static WebDriver webDriver;
    public static WebDriver gridDriverSetup(String browserName) {
        if (browserName != null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (browserName.equalsIgnoreCase("chrome")) {
                // Setup for Selenium Grid implementation
                setCapabilities(capabilities, "chrome");
            } else if (browserName.equalsIgnoreCase("firefox")) {
                // Setup for Selenium Grid implementation
                setCapabilities(capabilities, "firefox");
            } else if (browserName.equalsIgnoreCase("edge")) {
                // Setup for Selenium Grid implementation
                setCapabilities(capabilities, "MicrosoftEdge");
            }

            webDriver.manage().window().setSize(new Dimension(1050, 650));

            try {
                //Add implicit timeout
                webDriver.manage()
                        .timeouts()
                        .implicitlyWait(Duration.ofSeconds(10));
            } catch (NullPointerException e) {
                System.out.println(MessageFormat.format("NullPointerException: {0}", e.getMessage()));
            }
        }
        return webDriver;
    }

    public static void setCapabilities(DesiredCapabilities capabilities, String browserName){
        try {
            capabilities.setBrowserName(browserName);
            capabilities.setPlatform(Platform.WIN11);
            capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            webDriver = new RemoteWebDriver(new URL("http://192.168.50.207:4444"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
