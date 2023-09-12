package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import data.ConfigData;
import data.LocalStore;
import functions.Get;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import functions.TestCaseName;
import pageObjects.LoginPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.WebDriverListener;
import resources.WebDriverManager;
import testComponents.LoginProcess;
import testComponents.TestSetup;
import webElement.Element;

@Listeners({ExtentListeners.class, WebDriverListener.class})
@Slf4j
public class LoginFailureTest extends TestSetup {
	LoginProcess loginSuccessProcess = new LoginProcess();
	String password = System.getenv(ConfigData.AMAZON_PASSWORD_FAIL);

	Logger logger = LoggerFactory.getLogger(LoginFailureTest.class);
	
	@Test(groups = { "LoginFailure" },
			priority = 1,
			description = "Entering an incorrect password will result in an error page being displayed to the user",
			retryAnalyzer = resources.RetryAnalyzer.class)
	/*
		GIVEN a user enters an incorrect password
		WHEN they are on the Enter Password screen
		THEN the application will return an error page
			AND the user will not be logged in
	 */
	public void loginFailureTest(Method method) {
		ExtentTestManager.startTest(TestCaseName.convert(method.getName()), "Entering an incorrect password will result in an error page being displayed to the user");
		ExtentTestManager.getTest().assignAuthor("Shelly Mutu-Grigg");
		ExtentTestManager.getTest().assignDevice("Desktop");
		ExtentTestManager.getTest().assignCategory("Login Failure");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				TestCaseName.convert(method.getName()), StringUtils.capitalize(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_BROWSER).toString())));

		WebDriverManager.getDriver().get(Get.url());

		LoginPage loginPage = new LoginPage();
		loginPage.navigateToURL();
		logger.info("Thread {} ({}) with webDriver with hashCode {} has navigated to landing page", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginSuccessProcess.completeLogin(password, loginPage);
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully completed login process", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		String pageTitle;
		if(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER).toString().contains(ConfigData.CHROME_DRIVER) ||
				LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER).toString().contains(ConfigData.EDGE_DRIVER)) {
			pageTitle = PageTitleData.SIGN_IN_PAGE_TITLE;
		} else {
			pageTitle = PageTitleData.LANDING_PAGE_TITLE;
		}
		String loginFailureText = loginPage.findLoginFailureText();
		if(loginFailureText.contains(TextData.LOGIN_FAILURE_ALERT_TEXT)) {
			logger.info("Thread {} ({}) with webDriver with hashCode {} has encountered error ", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_ALERT_TEXT +"')]")), "enterUserDetails(email, password) with incorrect password");
			TestAssert.pageTitle("enterUserDetails(email, password) with incorrect password", pageTitle, WebDriverManager.getDriver().getTitle());
		} else if(loginFailureText.contains(TextData.LOGIN_PUZZLE_TEXT)) {
			logger.info("Thread {} ({}) with webDriver with hashCode {} has encountered error ", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_PUZZLE_TEXT +"')]")), "enterUserDetails(email, password) with puzzle page presented");
			TestAssert.pageTitle("enterUserDetails(email, password) with puzzle page presented", PageTitleData.AUTHENTICATION_REQUIRED_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
		} else if(loginFailureText.contains(TextData.LOGIN_FAILURE_IMPORTANT_MESSAGE_TEXT)) {
			logger.info("Thread {} ({}) with webDriver with hashCode {} has encountered error ", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_IMPORTANT_MESSAGE_TEXT +"')]")), "enterUserDetails(email, password) with an important message page presented");
			TestAssert.pageTitle("enterUserDetails(email, password) with an important message page presented", PageTitleData.SIGN_IN_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
		}
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully failed to login with error: '{}'", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
	}
}
