package webElement;

import org.openqa.selenium.*;
import wait.Wait;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class FindElement {

    public static WebElement getWebElement(By locator, WebDriver webDriver){
        Objects.requireNonNull(locator, MessageFormat.format("{0} must not be null", locator));
        WebElement webElement = Wait.setDriverWait(webDriver).until(d -> d.findElement(locator));
        // Check that the element is visible & enabled before returning it
        if(webElement.isDisplayed() && webElement.isEnabled()){
            return webElement;
        // TODO: Need to find a more elegant solution here
        } else return null;
    }

    public static List<WebElement> getWebElements(By locator, WebDriver webDriver){
        List<WebElement> webElements= Wait.setDriverWait(webDriver).until(d -> d.findElements(locator));
        // Check that the element list is not empty before returning it
        if (!webElements.isEmpty()) {
            return webElements;
        // TODO: Need to find a more elegant solution here
        } else return null;
    }
}
