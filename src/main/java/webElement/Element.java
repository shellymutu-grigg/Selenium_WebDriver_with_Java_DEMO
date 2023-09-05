package webElement;

import data.TextData;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retry.Retry;
import wait.Wait;

@Slf4j
public class Element {

    static Logger logger = LoggerFactory.getLogger(Element.class);

    public static WebElement getElement(By locator){
        Objects.requireNonNull(locator, MessageFormat.format("Element with locator {0} must not be null", locator));
        WebElement webElement = Wait.setDriverWait().until(webDriver -> webDriver.findElement(locator));
        // Check that the element is visible & enabled before returning it
        return (webElement.isDisplayed() && webElement.isEnabled())
                ? webElement
                : null;
    }

    public static List<WebElement> getElements(By locator){
        Objects.requireNonNull(locator, MessageFormat.format("Elements with locator {0} must not be null", locator));
        List<WebElement> webElements= Wait.setDriverWait().until(webDriver -> webDriver.findElements(locator));
        // Check that the element list is not empty before returning it
       return (!webElements.isEmpty()) ? webElements : null;
    }

    public static boolean isPresent(By by) {
        List<WebElement> list = Element.getElements(by);
        return list != null && !list.isEmpty();
    }

    public static void click(By by, boolean list){
        try{
            if(list){
                Wait.setDriverWait().until(webDriver -> Objects.requireNonNull(Element.getElements(by)).get(0)).click();
            } else {
                Wait.setDriverWait().until(webDriver -> Objects.requireNonNull(Element.getElement(by))).click();
            }
        } catch (StaleElementReferenceException e){
            Retry.retry(by, list, TextData.CLICK, "", 2);
            logger.error("StaleElementReferenceException has message: {}", e.getMessage());
        }

    }

    public static void sendKeys(By by, String text){
        try{
            Wait.setDriverWait().until(driver -> driver.findElement(by)).sendKeys(text);
        } catch (StaleElementReferenceException e){
            Retry.retry(by, false, TextData.SEND_KEYS, "", 2);
            logger.error("StaleElementReferenceException has message: {}", e.getMessage());
        }
    }
}
