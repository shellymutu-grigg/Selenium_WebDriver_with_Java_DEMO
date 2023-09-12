package tests;

import data.LocalStore;
import data.PageTitleData;
import functions.TestAssert;
import functions.TestCaseName;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.ConfigData;
import configuration.GridDriverConfig;
import resources.*;

import java.lang.reflect.Method;
import java.text.MessageFormat;

@Listeners({GridListener.class, ExtentListeners.class})
@Slf4j
public class SeleniumGridSMGTest extends GridDriverConfig {

    static Logger logger = LoggerFactory.getLogger(SeleniumGridSMGTest.class);

    @Test(groups = { "Grid" }, priority = 1, description = "Verify Selenium Grid can trigger edge instance")
    public void SMGPageTest(Method method){
        ExtentTestManager.startTest(TestCaseName.convert(method.getName()), "Verify Selenium Grid can trigger edge instance.");
        ExtentTestManager.getTest().assignAuthor("Shelly Mutu-Grigg");
        ExtentTestManager.getTest().assignDevice("Desktop");
        ExtentTestManager.getTest().assignCategory("Selenium Grid Edge Test");
        ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
                TestCaseName.convert(method.getName()),  "Edge"));

        ExtentListeners extentListener = new ExtentListeners();
        extentListener.onTestStartScreenshot(method.getName());

        WebDriver webDriver = (WebDriver) LocalStore.getObject(String.valueOf(Thread.currentThread().getId()));

        webDriver.get(ConfigData.SMG_WEBSITE_URL);
        logger.info("Thread {} ({}) with webDriver with hashCode {} has page title of {}", Thread.currentThread().getId(), method.getName(), WebDriverManager.getDriver().hashCode(), webDriver.getTitle());
        TestAssert.pageTitle("Verify expected page title matches actual page title", PageTitleData.SMG_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
        logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully completed edge browser test", Thread.currentThread().getId(), method.getName(), WebDriverManager.getDriver().hashCode());
    }
}
