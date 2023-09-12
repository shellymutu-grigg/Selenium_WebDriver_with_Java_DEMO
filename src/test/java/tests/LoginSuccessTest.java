package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import functions.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.ConfigData;
import data.LocalStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import functions.TestCaseName;
import pageObjects.LoginPage;
import pageObjects.LogoutPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.WebDriverListener;
import resources.WebDriverManager;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;
import webElement.Element;

@Listeners({ExtentListeners.class, WebDriverListener.class})
@Slf4j
public class LoginSuccessTest extends TestSetup{
	LoginProcess loginProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv(ConfigData.AMAZON_PASSWORD_SUCCESS);

	Logger logger = LoggerFactory.getLogger(LoginSuccessTest.class);
	
	@Test(groups = { "Smoke", "LoginSuccess" },
			priority = 1,
			description = "Successful login scenario",
			retryAnalyzer = resources.RetryAnalyzer.class)
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to review their account
	 */
	public void loginSuccessTest(Method method) {
		ExtentTestManager.startTest(TestCaseName.convert(method.getName()), "A user is able to successfully login with a valid email and password combo");
		ExtentTestManager.getTest().assignAuthor("Shelly Mutu-Grigg");
		ExtentTestManager.getTest().assignDevice("Desktop");
		ExtentTestManager.getTest().assignCategory("Login Success");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				TestCaseName.convert(method.getName()), StringUtils.capitalize(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_BROWSER).toString())));

		WebDriverManager.getDriver().get(Get.url());

		LoginPage loginPage = new LoginPage();
		loginPage.navigateToURL();
		logger.info("Thread {} ({}) with webDriver with hashCode {} has navigated to landing page", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());

		loginProcess.completeLogin(password, loginPage);
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully entered user email and password", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		String loginFailureText = loginPage.findLoginFailureText();
		logger.info("Thread {} ({}) with webDriver with hashCode {} has loginFailureText of '{}'", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
		if(loginFailureText !=""){
			if(loginFailureText.contains(TextData.LOGIN_FAILURE_ALERT_TEXT)) {
				logger.info("Thread {} ({}) with webDriver with hashCode {} has encountered error ", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
				TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_ALERT_TEXT +"')]")), "enterUserDetails(email, password) with incorrect password");
				TestAssert.pageTitle("enterUserDetails(email, password) with incorrect password", PageTitleData.SIGN_IN_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
			} else if(loginFailureText.contains(TextData.LOGIN_PUZZLE_TEXT)) {
				logger.info("Thread {} ({}) with webDriver with hashCode {} has encountered error ", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
				TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_PUZZLE_TEXT +"')]")), "enterUserDetails(email, password) with puzzle page presented");
				TestAssert.pageTitle("enterUserDetails(email, password) with puzzle page presented", PageTitleData.SIGN_IN_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
			} else if(loginFailureText.contains(TextData.LOGIN_FAILURE_IMPORTANT_MESSAGE_TEXT)) {
				logger.info("Thread {} ({}) with webDriver with hashCode {} has encountered error ", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), loginFailureText);
				TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.LOGIN_FAILURE_IMPORTANT_MESSAGE_TEXT +"')]")), "enterUserDetails(email, password) with an important message page presented");
				TestAssert.pageTitle("enterUserDetails(email, password) with an important message page presented", PageTitleData.SIGN_IN_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
			}
		}
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully completed login process", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		LogoutPage logoutPage = loginPage.initialiseLogoutPage();
		logoutProcess.logout(logoutPage, "");
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully completed logout process", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());
	}

}
