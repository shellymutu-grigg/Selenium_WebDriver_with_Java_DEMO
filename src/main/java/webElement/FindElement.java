package webElement;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;
import java.time.Duration;

public class FindElement {

    public static WebElement getWebElement(By locator, WebDriver webDriver){
    // Check that element is visible & enabled before returning it
        WebElement webElement = null;
        try{
            webElement = fetchWebElement(locator, webDriver);
        } catch(NoSuchElementException e){
            // Implement Retry
            System.out.println(MessageFormat.format("NoSuchElementException:  {0}", e.getMessage()));
        }
        return webElement;
    }
    private static WebElement fetchWebElement(By locator, WebDriver webDriver) {
        WebDriverWait webDriverWait = new WebDriverWait(
                webDriver,
                Duration.ofSeconds(10),
                Duration.ofMillis(500));
        WebElement webElement= webDriverWait.until(d -> d.findElement(locator));

        if(webElement.isDisplayed() && webElement.isEnabled()){
            return webElement;
        } else throw new NoSuchElementException(MessageFormat.format("WebElement with locator: {0} could not be found", locator));
    }
}
