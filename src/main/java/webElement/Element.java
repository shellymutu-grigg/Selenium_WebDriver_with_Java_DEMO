package webElement;

import org.openqa.selenium.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import wait.Wait;

public class Element {

    public static WebElement getElement(By locator){
        Objects.requireNonNull(locator, MessageFormat.format("{0} must not be null", locator));
        WebElement webElement = Wait.setDriverWait().until(d -> d.findElement(locator));
        // Check that the element is visible & enabled before returning it
        if(webElement.isDisplayed() && webElement.isEnabled()){
            return webElement;
        // TODO: Need to find a more elegant solution here
        } else return null;
    }

    public static List<WebElement> getElements(By locator){
        List<WebElement> webElements= Wait.setDriverWait().until(d -> d.findElements(locator));
        // Check that the element list is not empty before returning it
        if (!webElements.isEmpty()) {
            return webElements;
        // TODO: Need to find a more elegant solution here
        } else return null;
    }

    public static boolean isPresent(By by) {
        boolean exists = false;
        List<WebElement> list = Element.getElements(by);
        if(list !=null && !list.isEmpty()) {
            exists = true;
        }
        return exists;
    }
}
