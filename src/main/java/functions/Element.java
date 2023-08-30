package functions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import webElement.FindElement;

public class Element {
    public static boolean isElementPresent(By by, WebDriver webDriver) {
        boolean exists = false;
        List<WebElement> list = FindElement.getWebElements(by, webDriver);
        if(list !=null && !list.isEmpty()) {
            exists = true;
        }
        return exists;
    }
}
