package resources;

import data.LocalStore;
import org.openqa.selenium.WebDriver;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
            return webDriver.get();
    }

    static void setWebDriver(WebDriver driver) {
        LocalStore.setObject(String.valueOf(Thread.currentThread().getId()), driver);
        webDriver.set(driver);
    }
}
