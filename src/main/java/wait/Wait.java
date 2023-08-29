package wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {

    public static WebDriverWait setWait(WebDriverWait webDriverWait, WebDriver webDriver){
        webDriverWait = new WebDriverWait(
                webDriver,
                Duration.ofSeconds(10),
                Duration.ofMillis(500));
        return webDriverWait;
    }
}
