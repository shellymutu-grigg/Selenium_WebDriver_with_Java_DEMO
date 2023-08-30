package wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {

    public static WebDriverWait setDriverWait(WebDriver webDriver){
        WebDriverWait webDriverWait = new WebDriverWait(
                webDriver,
                Duration.ofSeconds(10),
                Duration.ofMillis(500));
        return webDriverWait;
    }
}
