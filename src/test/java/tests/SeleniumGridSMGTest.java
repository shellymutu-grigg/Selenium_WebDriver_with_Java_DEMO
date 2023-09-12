package tests;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import data.ConfigData;
import configuration.GridDriverConfig;

@Slf4j
public class SeleniumGridSMGTest extends GridDriverConfig {

    static Logger logger = LoggerFactory.getLogger(SeleniumGridSMGTest.class);

    @Test(groups = { "Grid" }, priority = 1, description = "Verify Selenium Grid can trigger edge instance")
    public void SMGPageTest(){
        webDriver = gridDriverSetup(ConfigData.EDGE_DRIVER);
        webDriver.get(ConfigData.SMG_WEBSITE_URL);
        logger.info("Page title: {}", webDriver.getTitle());
        webDriver.close();
        webDriver.quit();
    }
}
