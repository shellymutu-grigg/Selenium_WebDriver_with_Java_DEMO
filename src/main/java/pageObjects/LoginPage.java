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

import abstractComponents.AbstractComponents;
import abstractComponents.HelperFunctions;
import data.TextData;

public class LoginPage extends AbstractComponents implements ITestListener{

	WebDriver webDriver;
	
	HelperFunctions helperFunctions = new HelperFunctions();

	// PageFactory Pattern
	@FindBy(id = "ap_email")
	WebElement userEmail;

	@FindBy(id = "ap_password")
	WebElement userPassword;

	@FindBy(id = "continue")
	WebElement continueButton;

	@FindBy(xpath="//span[normalize-space()='Account & Lists']")
	WebElement accountLink;
	
	@FindBy(linkText="Your Account")
	WebElement yourAccountLink;
	
	@FindBy(id = "signInSubmit")
	WebElement login;
	
	By yourAccountLinkBy = By.cssSelector(".ya-personalized");
	By signInPageBy = By.id("ap_email");
	By userEmailBy = By.cssSelector(".a-form-label");
	By userPasswordBy = By.id("nav-global-location-popover-link");
	By landingPageBy = By.cssSelector(".navFooterLine");
	By landingPageAltBy = By.cssSelector(".nav-footer-copyright");
	By loginFailPuzzle = By.id("cvf-page-content");
	By loginFailPuzzleMessageText = By.cssSelector(".a-spacing-mini");
	By loginFailImportantMessage = By.cssSelector(".a-list-item");
	By loginFailImportantMessageText = By.cssSelector(".a-list-item");
	By loginFailAlert = By.id("auth-error-message-box");
	By loginFailAlertMessageText = By.cssSelector(".a-alert-content");

	public LoginPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public LogoutPage logout() {
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
			try {
				waitForElementToAppear(landingPageBy);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(helperFunctions.isElementPresent(landingPageAltBy, webDriver)) {
			try {
				waitForElementToAppear(landingPageAltBy);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void pause() {
		try {
			waitForElementToAppear(userPasswordBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enterUserDetails(String email, String password) {
		userEmail.sendKeys(email);
		continueButton.click();
		
		try {
			waitForElementToAppear(userEmailBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userPassword.sendKeys(password);
		login.click();
	}
	
	public void navigateToLanding() {
		if(yourAccountLink != null) {
			yourAccountLink.click();
		}
		accountLink.click();
		
		try {
			waitForElementToAppear(signInPageBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SearchPage searchPage(String email, String password) {		
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}
	
	public void loginFail() {

		if(helperFunctions.isElementPresent(loginFailAlert, webDriver)) {
			// TODO validate that PageTitleData.SIGN_IN_PAGE_TITLE is rendered
			validateLoginFailure(loginFailAlert, loginFailAlertMessageText, TextData.LOGIN_FAILURE_ALERT_TEXT);
		}
		else if(helperFunctions.isElementPresent(loginFailPuzzle, webDriver)) {
			// TODO validate that PageTitleData.LOGIN_FAILURE_PAGE_TITLE is rendered
			validateLoginFailure(loginFailPuzzle, loginFailPuzzleMessageText, TextData.LOGIN_PUZZLE_TEXT);
		}
		else if(helperFunctions.isElementPresent(loginFailImportantMessage, webDriver)) {
			// TODO validate that PageTitleData.SIGN_IN_PAGE_TITLE is rendered
			validateLoginFailure(loginFailImportantMessage, loginFailImportantMessageText, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
		}
	}
	
	public void validateLoginFailure(By elementHandle, By elementMessageText, String expectedText) {
		try {
			waitForElementToAppear(elementHandle);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement message = webDriver.findElement(elementMessageText);
		String messageText = message.getText();
		Assert.assertEquals(messageText, expectedText);
	}
	
	public OrdersPage ordersPage() {			
		OrdersPage ordersPage = new OrdersPage(webDriver);
		return ordersPage;
	}
}