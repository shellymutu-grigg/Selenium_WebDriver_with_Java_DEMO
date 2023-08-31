package tests;

import org.testng.annotations.Test;

import data.ConfigData;
import configuration.GridDriverConfig;

import java.text.MessageFormat;

public class SeleniumGridSMGTest  extends GridDriverConfig {

    @Test(groups = { "Grid" }, priority = 1, description = "Verify Selenium Grid can trigger edge instance")
    public void SMGPageTest(){
        webDriver = gridDriverSetup(ConfigData.EDGE_DRIVER);
        webDriver.get(ConfigData.SMG_WEBSITE_URL);
        System.out.println(MessageFormat.format("Page title: {0}", webDriver.getTitle()));
        webDriver.quit();   }
}
