package resources;

import configuration.GridDriverConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class GridListener extends GridDriverConfig implements IInvokedMethodListener {
    Logger logger = LoggerFactory.getLogger(GridListener.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod()) {
            WebDriver webDriver;
            String testName = String.valueOf(method.getTestMethod());
            String browserType = "";
            if(testName.contains("SMG")){
                browserType="edge";
            } else if(testName.contains("Google")){
                browserType="chrome";
            }
            webDriver = GridDriverConfig.gridDriverSetup(result, browserType);
            WebDriverManager.setWebDriver(webDriver);
            logger.info("Thread {} ({}) is setting webDriver of type {} with hashCode {}", Thread.currentThread().getId(), result.getMethod().getMethodName(), browserType, webDriver.hashCode());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }
}
