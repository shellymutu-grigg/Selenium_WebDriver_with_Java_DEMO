package pageObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import functions.IsElementPresent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestListener;

import data.PageTitleData;
import data.TextData;
import functions.HelperFunctions;
import webElement.FindElement;

public class LoginPage extends HelperFunctions implements ITestListener{

	WebDriver webDriver;

	@FindBy(xpath="//span[normalize-space()='Account & Lists']")
	WebElement signInLink;
	
	@FindBy(id = "continue")
	WebElement continueButton;
	
	@FindBy(id = "signInSubmit")
	WebElement login;
	
	@FindBy(id = "ap_email")
	WebElement userEmail;

	@FindBy(id = "ap_password")
	WebElement userPassword;

	@FindBy(linkText="Your Account")
	WebElement yourAccountLink;

	By accountLinkBy = By.id("nav-global-location-popover-link");
	By accountMenuBy = By.id("nav-link-accountList-nav-line-1");
	By landingPageBy = By.cssSelector(".navFooterLine");
	By landingPageAltBy = By.cssSelector(".nav-footer-copyright");
	By loggedOutText = By.id("nav-link-accountList-nav-line-1");
	By loginFailPuzzle = By.id("cvf-page-content");
	By loginFailPuzzleMessageText = By.cssSelector(".a-spacing-mini");
	By loginFailImportantMessage = By.xpath("//*[contains(text(), '" + TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT +"')]");
	By loginFailImportantMessageText = By.xpath("//*[contains(text(), '" + TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT +"')]");
	By loginFailAlert = By.id("auth-error-message-box");
	By loginFailAlertMessageText = By.cssSelector(".a-alert-content");
	By userEmailBy = By.id("ap_email");
	By userPasswordBy = By.id("ap_password");
	By yourAccountLinkBy = By.cssSelector(".ya-personalized");
	
	public LoginPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public LogoutPage initialiseLogoutPage() {
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
	
	public void navigateToURL() {
		Properties properties = new Properties();
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(System.getProperty("user.dir")
					+ "//src//test//resources//globalData.properties");
			properties.load(fileInputStream);
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = System.getProperty("url") != null ? System.getProperty("url")
		: properties.getProperty("url");
		webDriver.get(url);
		
		if(FindElement.getWebElement(landingPageBy, webDriver) != null) {
			FindElement.getWebElement(landingPageBy, webDriver);
		}
		else if(FindElement.getWebElement(landingPageAltBy, webDriver) != null) {
			FindElement.getWebElement(landingPageAltBy, webDriver);
			if(yourAccountLink != null) {
				System.setProperty("YourAccount", "Your Account");
				yourAccountLink.click();
			}
		}
	}
	
	public SearchPage generateSearchPage() {
		FindElement.getWebElement(accountLinkBy, webDriver);
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}
	
	public OrdersPage generateOrdersPage() {
		System.out.println(MessageFormat.format("Account Link element: {0}", FindElement.getWebElement(accountLinkBy, webDriver).getText()));
		FindElement.getWebElement(accountLinkBy, webDriver);
		OrdersPage ordersPage = new OrdersPage(webDriver);
		return ordersPage;
	}	
	
	public void enterUserEmail(String email) {
		FindElement.getWebElement(userEmailBy, webDriver);
		userEmail.sendKeys(email);
		continueButton.click();
	}
	
	public void enterUserPassword(String password) {
		FindElement.getWebElement(userPasswordBy, webDriver);
		userPassword.sendKeys(password);
		login.click();
		
	}
	
	public boolean checkIfSignedIn() {
		FindElement.getWebElement(accountLinkBy, webDriver);
		if(webDriver.getTitle().contains(PageTitleData.LANDING_PAGE_TITLE)) {
			return true;
		}
		else return false;
	}
	
	public void navigateToLanding() {
		signInLink.click();
		FindElement.getWebElement(userEmailBy, webDriver);
	}
	
	public void checkForPreviousLoginFailure() {
		String accountListText = FindElement.getWebElement(accountMenuBy, webDriver).getText();
		if(!accountListText.contains(TextData.LOGGED_OUT_TEXT)) {
			initialiseLogoutPage().openAccountMenu();
			initialiseLogoutPage().logout();
			navigateToURL();
		}
	}
	
	public String findLoginFailureText() {
		String failureText = "";
		if(IsElementPresent.isElementPresent(loginFailAlert, webDriver)) {
			failureText = TextData.LOGIN_FAILURE_ALERT_TEXT;
		}
		else if(IsElementPresent.isElementPresent(loginFailPuzzle, webDriver)) {
			failureText =  TextData.LOGIN_PUZZLE_TEXT;
		}
		else if(IsElementPresent.isElementPresent(loginFailImportantMessage, webDriver)) {
			failureText =  TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
		}
		return failureText;
	}
	
	public String loginFail() {
		String loginFailStatus = "";
		if(IsElementPresent.isElementPresent(loginFailAlert, webDriver)) {
			loginFailStatus = TextData.LOGIN_FAILURE_ALERT_TEXT;
			validateLoginFailure(loginFailAlert, loginFailAlertMessageText, TextData.LOGIN_FAILURE_ALERT_TEXT);
		}
		else if(IsElementPresent.isElementPresent(loginFailPuzzle, webDriver)) {
			loginFailStatus =  TextData.LOGIN_PUZZLE_TEXT;
			validateLoginFailure(loginFailPuzzle, loginFailPuzzleMessageText, TextData.LOGIN_PUZZLE_TEXT);
		}
		else if(IsElementPresent.isElementPresent(loginFailImportantMessage, webDriver)) {
			loginFailStatus = TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
			validateLoginFailure(loginFailImportantMessage, loginFailImportantMessageText, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
		}
		return loginFailStatus;
	}
	
	public void validateLoginFailure(By elementHandle, By elementMessageText, String expectedText) {
		FindElement.getWebElement(elementHandle, webDriver);
		String messageText = FindElement.getWebElement(elementMessageText, webDriver).getText();
		Assert.assertEquals(messageText, expectedText);
	}
}