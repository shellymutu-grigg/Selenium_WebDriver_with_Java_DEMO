package functions;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.MessageFormat;

import resources.ExtentTestManager;

public class TestAssert {

    public static void elementNotNull(WebElement webElement, String testCaseStepName) {
        Assert.assertNotNull(webElement);
        if(ExtentTestManager.getTest().getStatus() == Status.PASS) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step element with text \"{1}\", was successfully located in page.", testCaseStepName, webElement.getText()));
        }else if(ExtentTestManager.getTest().getStatus() == Status.FAIL) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step element with text \"{1}\", was NOT located in page. <br /> <font color=red> Step {2}ed", testCaseStepName, webElement.getText(), ExtentTestManager.getTest().getStatus()));
        }
    }

    public static void pageTitle(String testCaseStepName, String expectedPageTitle, String pageTitle) {
        Assert.assertEquals(pageTitle, expectedPageTitle);
        if(ExtentTestManager.getTest().getStatus() == Status.PASS) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step page title: \"{1}\", matched expected text \"{2}\".", testCaseStepName, pageTitle, expectedPageTitle));
        }else if(ExtentTestManager.getTest().getStatus() == Status.FAIL) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step page title: \"{1}\", did not match expected \"{2}\" text. <br /> <font color=red> Step {2}ed", testCaseStepName, pageTitle, expectedPageTitle, ExtentTestManager.getTest().getStatus()));
        }
    }

    public static void pageTitleContains(String testCaseStepName, String expectedPageTitle, String pageTitle) {
        Assert.assertTrue(pageTitle.contains(expectedPageTitle));
        if(ExtentTestManager.getTest().getStatus() == Status.PASS) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step page title: \"{1}\", matched expected text \"{2}\".", testCaseStepName, pageTitle, expectedPageTitle));
        }else if(ExtentTestManager.getTest().getStatus() == Status.FAIL) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step page title: \"{1}\", did not match expected \"{2}\" text. <br /> <font color=red> Step {2}ed", testCaseStepName, pageTitle, expectedPageTitle, ExtentTestManager.getTest().getStatus()));
        }
    }
}
