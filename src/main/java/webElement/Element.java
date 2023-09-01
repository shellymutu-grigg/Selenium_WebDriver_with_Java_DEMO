package webElement;

import org.openqa.selenium.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import wait.Wait;

public class Element {

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

    public static void click(By by){
        try{
            Wait.setDriverWait().until(webDriver -> webDriver.findElement(by)).click();
        } catch (StaleElementReferenceException e){
             System.out.println(MessageFormat.format("StaleElementReferenceException has message:  {0}", e.getMessage()));
             //TODO implement retry functionality
        }

    }

    public static void click(WebElement webElement){
        try{
            Wait.setDriverWait().until(webDriver -> webElement).click();
        } catch (StaleElementReferenceException e){
            System.out.println(MessageFormat.format("StaleElementReferenceException has message:  {0}", e.getMessage()));
            //TODO implement retry functionality
        }
    }
    public static void sendKeys(By by, String text){
        try{
            Wait.setDriverWait().until(webDriver -> webDriver.findElement(by)).sendKeys(text);
        } catch (StaleElementReferenceException e){
            System.out.println(MessageFormat.format("StaleElementReferenceException has message:  {0}", e.getMessage()));
            //TODO implement retry functionality
        }

    }
}
