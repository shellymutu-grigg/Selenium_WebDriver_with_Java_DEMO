package wait;

import data.LocalStore;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {

    public static WebDriverWait setDriverWait(){
        WebDriver webDriver = (WebDriver) LocalStore.getObject(String.valueOf(Thread.currentThread().getId()));
        return new WebDriverWait(
                webDriver,
                Duration.ofSeconds(1),
                Duration.ofMillis(500));
    }
}
