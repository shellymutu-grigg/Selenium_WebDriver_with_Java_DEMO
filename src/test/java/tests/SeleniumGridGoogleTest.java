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
public class SeleniumGridGoogleTest extends GridDriverConfig {

    static Logger logger = LoggerFactory.getLogger(SeleniumGridGoogleTest.class);

    @Test (groups = { "Grid" }, priority = 1, description = "Verify Selenium Grid can trigger chrome instance")
    public void GooglePageTest(Method method){
        ExtentTestManager.startTest(TestCaseName.convert(method.getName()), "Verify Selenium Grid can trigger chrome instance.");
        ExtentTestManager.getTest().assignAuthor("Shelly Mutu-Grigg");
        ExtentTestManager.getTest().assignDevice("Desktop");
        ExtentTestManager.getTest().assignCategory("Selenium Grid Chrome Test");
        ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
                TestCaseName.convert(method.getName()), "Chrome"));

        ExtentListeners extentListener = new ExtentListeners();
        extentListener.onTestStartScreenshot(method.getName());

        WebDriver webDriver = (WebDriver) LocalStore.getObject(String.valueOf(Thread.currentThread().getId()));

        webDriver.get(ConfigData.GOOGLE_WEBSITE_URL);
        logger.info("Thread {} ({}) with webDriver with hashCode {} has page title of {}", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), webDriver.getTitle());
        TestAssert.pageTitle("Verify expected page title matches actual page title", PageTitleData.GOOGLE_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
        logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully completed google browser test", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());
    }
}
