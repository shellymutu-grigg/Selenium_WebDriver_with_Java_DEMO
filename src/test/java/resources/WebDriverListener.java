package resources;

import functions.TestCaseName;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class WebDriverListener implements IInvokedMethodListener {
    static Logger logger = LoggerFactory.getLogger(WebDriverListener.class);
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod()) {
            WebDriver webDriver = BrowserManager.browserSetup(result);
            WebDriverManager.setWebDriver(webDriver);
            logger.info("Thread {} ({}) is setting webDriver with hashCode {}", Thread.currentThread().getId(), TestCaseName.convert(result.getMethod().getMethodName()), webDriver.hashCode());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }
}
