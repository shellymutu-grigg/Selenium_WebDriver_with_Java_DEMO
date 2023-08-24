package pageObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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

public class LoginPage extends HelperFunctions implements ITestListener{

	WebDriver webDriver;
	
	HelperFunctions helperFunctions = new HelperFunctions(webDriver);

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
	By loginFailImportantMessage = By.cssSelector(".a-list-item");
	By loginFailImportantMessageText = By.cssSelector(".a-list-item");
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
		
		if(helperFunctions.isElementPresent(landingPageBy, webDriver)) {
			waitForElementToAppear(landingPageBy, webDriver);
		}
		else if(helperFunctions.isElementPresent(landingPageAltBy, webDriver)) {
			waitForElementToAppear(landingPageAltBy, webDriver);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public SearchPage pauseForSearchPage() {
		waitForElementToAppear(accountLinkBy, webDriver);
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}
	
	public OrdersPage pauseForOrdersPage() {
		waitForElementToAppear(accountLinkBy, webDriver);
		OrdersPage ordersPage = new OrdersPage(webDriver);
		return ordersPage;
	}	
	
	public void enterUserEmail(String email) {
		waitForElementToAppear(userEmailBy, webDriver);
		userEmail.sendKeys(email);
		continueButton.click();
	}
	
	public void enterUserPassword(String password) {
		waitForElementToAppear(userPasswordBy, webDriver);
		userPassword.sendKeys(password);
		login.click();
		
	}
	
	public boolean checkIfSignedIn() {
		waitForElementToAppear(accountLinkBy, webDriver);
		if(webDriver.getTitle().contains(PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE)) {
			return true;
		}
		else return false;
	}
	
	public void navigateToLanding() {
		if(yourAccountLink != null) {
			yourAccountLink.click();
		}
		checkForPreviousLoginFailure();
		signInLink.click();
		waitForElementToAppear(userEmailBy, webDriver);
	}
	
	public void checkForPreviousLoginFailure() {
		String accountListText = webDriver.findElement(accountMenuBy).getText();
		if(!accountListText.contains(TextData.LOGGED_OUT_TEXT)) {
			initialiseLogoutPage().openAccountMenu();
			initialiseLogoutPage().logout();
			navigateToURL();
		}
	}
	
	public String findLoginFailureText() {
		String failureText = "";
		if(helperFunctions.isElementPresent(loginFailAlert, webDriver)) {
			failureText = TextData.LOGIN_FAILURE_ALERT_TEXT;
		}
		else if(helperFunctions.isElementPresent(loginFailPuzzle, webDriver)) {
			failureText =  TextData.LOGIN_PUZZLE_TEXT;
		}
		else if(helperFunctions.isElementPresent(loginFailImportantMessage, webDriver)) {
			failureText =  TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
		}
		return failureText;
	}
	
	public void loginFail() {
		if(helperFunctions.isElementPresent(loginFailAlert, webDriver)) {
			System.setProperty("LoginFailStatus", TextData.LOGIN_FAILURE_ALERT_TEXT);
			validateLoginFailure(loginFailAlert, loginFailAlertMessageText, TextData.LOGIN_FAILURE_ALERT_TEXT);
		}
		else if(helperFunctions.isElementPresent(loginFailPuzzle, webDriver)) {
			System.setProperty("LoginFailStatus", TextData.LOGIN_PUZZLE_TEXT);
			validateLoginFailure(loginFailPuzzle, loginFailPuzzleMessageText, TextData.LOGIN_PUZZLE_TEXT);
		}
		else if(helperFunctions.isElementPresent(loginFailImportantMessage, webDriver)) {
			System.setProperty("LoginFailStatus", TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
			validateLoginFailure(loginFailImportantMessage, loginFailImportantMessageText, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
		}
	}
	
	public void validateLoginFailure(By elementHandle, By elementMessageText, String expectedText) {
		waitForElementToAppear(elementHandle, webDriver);
		WebElement message = webDriver.findElement(elementMessageText);
		String messageText = message.getText();
		Assert.assertEquals(messageText, expectedText);
	}
}