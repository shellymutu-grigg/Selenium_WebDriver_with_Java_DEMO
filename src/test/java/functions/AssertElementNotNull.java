package functions;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.ExtentTestManager;

import java.text.MessageFormat;

public class AssertElementNotNull {

    public static void assertElementNotNull(WebElement webElement, String testCaseStepName) {
        Assert.assertNotNull(webElement);
        if(ExtentTestManager.getTest().getStatus() == Status.PASS) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step element with text \"{1}\", was successfully located in page." + "<br />"+"Step {2}ed", testCaseStepName, webElement.getText(), ExtentTestManager.getTest().getStatus()));
        }else if(ExtentTestManager.getTest().getStatus() == Status.FAIL) {
            ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("The \"{0}\" step element with text \"{1}\", was NOT located in page." + "<br />" +"<font color=" + "red>" + "Step {2}ed", testCaseStepName, webElement.getText(), ExtentTestManager.getTest().getStatus()));
        }
    }
}
