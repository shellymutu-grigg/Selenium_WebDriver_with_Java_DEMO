package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import resources.GridDriver;

import java.text.MessageFormat;

public class SeleniumGridGoogleTest {
    static WebDriver webDriver;

    @Test (groups = { "Grid" }, priority = 1, description = "Verify Selenium Grid can trigger chrome instance")
    public void GooglePageTest(){
        webDriver = GridDriver.gridDriverSetup("chrome");
        webDriver.get("https://www.google.com");
        System.out.println(MessageFormat.format("Page title: {0}", webDriver.getTitle()));
        webDriver.close();
        webDriver.quit();
    }
}
