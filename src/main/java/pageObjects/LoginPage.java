package pageObjects;

import functions.Get;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestListener;

import data.PageTitleData;
import data.TextData;
import webElement.Element;

public class LoginPage implements ITestListener{

	WebDriver webDriver;

	By accountLinkBy = By.id("nav-global-location-popover-link");
	By continueButtonBy = By.id("continue");
	By accountMenuBy = By.id("nav-link-accountList-nav-line-1");
	By landingPageBy = By.cssSelector(".navFooterLine");
	By landingPageAltBy = By.cssSelector(".nav-footer-copyright");
	By loginButtonBy = By.id("signInSubmit");
	By loginFailPuzzle = By.id("cvf-page-content");
	By loginFailPuzzleMessageText = By.cssSelector(".a-spacing-mini");
	By loginFailImportantMessage = By.xpath("//*[contains(text(), '" + TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT +"')]");
	By loginFailImportantMessageText = By.xpath("//*[contains(text(), '" + TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT +"')]");
	By loginFailAlert = By.id("auth-error-message-box");
	By loginFailAlertMessageText = By.cssSelector(".a-alert-content");
	By signInLinkBy = By.xpath("//span[normalize-space()='Account & Lists']");
	By userEmailBy = By.id("ap_email");
	By userPasswordBy = By.id("ap_password");
	By yourAccountLinkBy = By.linkText(TextData.YOUR_ACCOUNT_TEXT);
	
	public LoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public LogoutPage initialiseLogoutPage() {
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
	
	public void navigateToURL() {
		if(Element.getElement(landingPageBy, webDriver) != null) {
			Element.getElement(landingPageBy, webDriver);
		}
		else if(Element.getElement(landingPageAltBy, webDriver) != null) {
			Element.getElement(landingPageAltBy, webDriver);
			if(Element.getElement(yourAccountLinkBy, webDriver) != null) {
				System.setProperty("YourAccount", "Your Account");
				Element.getElement(yourAccountLinkBy, webDriver).click();
			}
		}
	}
	
	public SearchPage generateSearchPage() {
		Element.getElement(accountLinkBy, webDriver);
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}
	
	public OrdersPage generateOrdersPage() {
		Element.getElement(accountLinkBy, webDriver);
		OrdersPage ordersPage = new OrdersPage(webDriver);
		return ordersPage;
	}	
	
	public void enterUserEmail(String email) {
		Element.getElement(userEmailBy, webDriver).sendKeys(email);
		Element.getElement(continueButtonBy, webDriver).click();
	}
	
	public void enterUserPassword(String password) {
		Element.getElement(userPasswordBy, webDriver).sendKeys(password);
		Element.getElement(loginButtonBy, webDriver).click();
	}
	
	public boolean checkIfSignedIn() {
		if(webDriver.getTitle().contains(PageTitleData.LANDING_PAGE_TITLE)) {
			return true;
		}
		else return false;
	}
	
	public void navigateToLanding() {
		Element.getElement(signInLinkBy, webDriver).click();
	}
	
	public void checkForPreviousLoginFailure() {
		String accountListText = Element.getElement(accountMenuBy, webDriver).getText();
		if(!accountListText.contains(TextData.LOGGED_OUT_TEXT)) {
			initialiseLogoutPage().openAccountMenu();
			initialiseLogoutPage().logout();
			webDriver.get(Get.url());
		}
	}
	
	public String findLoginFailureText() {
		String failureText = "";
		if(Element.isPresent(loginFailAlert, webDriver)) {
			failureText = TextData.LOGIN_FAILURE_ALERT_TEXT;
		}
		else if(Element.isPresent(loginFailPuzzle, webDriver)) {
			failureText =  TextData.LOGIN_PUZZLE_TEXT;
		}
		else if(Element.isPresent(loginFailImportantMessage, webDriver)) {
			failureText =  TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
		}
		return failureText;
	}
	
	public String loginFail() {
		String loginFailStatus = "";
		if(Element.isPresent(loginFailAlert, webDriver)) {
			loginFailStatus = TextData.LOGIN_FAILURE_ALERT_TEXT;
			validateLoginFailure(loginFailAlertMessageText, TextData.LOGIN_FAILURE_ALERT_TEXT);
		}
		else if(Element.isPresent(loginFailPuzzle, webDriver)) {
			loginFailStatus =  TextData.LOGIN_PUZZLE_TEXT;
			validateLoginFailure(loginFailPuzzleMessageText, TextData.LOGIN_PUZZLE_TEXT);
		}
		else if(Element.isPresent(loginFailImportantMessage, webDriver)) {
			loginFailStatus = TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
			validateLoginFailure(loginFailImportantMessageText, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
		}
		return loginFailStatus;
	}
	
	public void validateLoginFailure(By elementMessageText, String expectedText) {
		String messageText = Element.getElement(elementMessageText, webDriver).getText();
		Assert.assertEquals(messageText, expectedText);
	}
}