package tests;

import org.testng.annotations.Test;

import data.ConfigData;
import configuration.GridDriverConfig;

import java.text.MessageFormat;

public class SeleniumGridGoogleTest extends GridDriverConfig {

    @Test (groups = { "Grid" }, priority = 1, description = "Verify Selenium Grid can trigger chrome instance")
    public void GooglePageTest(){
        webDriver = gridDriverSetup(ConfigData.CHROME_DRIVER);
        webDriver.get(ConfigData.GOOGLE_WEBSITE_URL);
        System.out.println(MessageFormat.format("Page title: {0}", webDriver.getTitle()));
        webDriver.close();
        webDriver.quit();
    }
}
