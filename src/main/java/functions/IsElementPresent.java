package functions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IsElementPresent {
    public static boolean isElementPresent(By by, WebDriver webDriver) {
        boolean exists = false;
        List<WebElement> list = webDriver.findElements(by);
        if(!list.isEmpty()) {
            exists = true;
        }
        return exists;
    }
}
