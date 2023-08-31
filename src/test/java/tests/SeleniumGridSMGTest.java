package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import resources.GridDriver;

import java.text.MessageFormat;

public class SeleniumGridSMGTest {
    static WebDriver webDriver;

    @Test(groups = { "Grid" }, priority = 1, description = "Verify Selenium Grid can trigger edge instance")
    public void SMGPageTest(){
        webDriver = GridDriver.gridDriverSetup("edge");
        webDriver.get("https://www.shellymutugrigg.com");
        System.out.println(MessageFormat.format("Page title: {0}", webDriver.getTitle()));
        webDriver.quit();   }
}
