package wait;

import data.ConfigData;
import data.LocalStore;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {

    public static WebDriverWait setDriverWait(){
        WebDriver webDriver = (WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER);
        return new WebDriverWait(
                webDriver,
                Duration.ofSeconds(10),
                Duration.ofMillis(500));
    }
}
