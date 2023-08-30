package pageObjects;

import functions.Element;
import functions.Url;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestListener;

import data.PageTitleData;
import data.TextData;
import webElement.FindElement;

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
	By yourAccountLinkBy = By.linkText("Your Account");
	
	public LoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public LogoutPage initialiseLogoutPage() {
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
	
	public void navigateToURL() {
		if(FindElement.getWebElement(landingPageBy, webDriver) != null) {
			FindElement.getWebElement(landingPageBy, webDriver);
		}
		else if(FindElement.getWebElement(landingPageAltBy, webDriver) != null) {
			FindElement.getWebElement(landingPageAltBy, webDriver);
			if(FindElement.getWebElement(yourAccountLinkBy, webDriver) != null) {
				System.setProperty("YourAccount", "Your Account");
				FindElement.getWebElement(yourAccountLinkBy, webDriver).click();
			}
		}
	}
	
	public SearchPage generateSearchPage() {
		FindElement.getWebElement(accountLinkBy, webDriver);
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}
	
	public OrdersPage generateOrdersPage() {
		FindElement.getWebElement(accountLinkBy, webDriver);
		OrdersPage ordersPage = new OrdersPage(webDriver);
		return ordersPage;
	}	
	
	public void enterUserEmail(String email) {
		FindElement.getWebElement(userEmailBy, webDriver).sendKeys(email);
		FindElement.getWebElement(continueButtonBy, webDriver).click();
	}
	
	public void enterUserPassword(String password) {
		FindElement.getWebElement(userPasswordBy, webDriver).sendKeys(password);
		FindElement.getWebElement(loginButtonBy, webDriver).click();
	}
	
	public boolean checkIfSignedIn() {
		if(webDriver.getTitle().contains(PageTitleData.LANDING_PAGE_TITLE)) {
			return true;
		}
		else return false;
	}
	
	public void navigateToLanding() {
		FindElement.getWebElement(signInLinkBy, webDriver).click();
	}
	
	public void checkForPreviousLoginFailure() {
		String accountListText = FindElement.getWebElement(accountMenuBy, webDriver).getText();
		if(!accountListText.contains(TextData.LOGGED_OUT_TEXT)) {
			initialiseLogoutPage().openAccountMenu();
			initialiseLogoutPage().logout();
			webDriver.get(Url.getUrl());
		}
	}
	
	public String findLoginFailureText() {
		String failureText = "";
		if(Element.isElementPresent(loginFailAlert, webDriver)) {
			failureText = TextData.LOGIN_FAILURE_ALERT_TEXT;
		}
		else if(Element.isElementPresent(loginFailPuzzle, webDriver)) {
			failureText =  TextData.LOGIN_PUZZLE_TEXT;
		}
		else if(Element.isElementPresent(loginFailImportantMessage, webDriver)) {
			failureText =  TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
		}
		return failureText;
	}
	
	public String loginFail() {
		String loginFailStatus = "";
		if(Element.isElementPresent(loginFailAlert, webDriver)) {
			loginFailStatus = TextData.LOGIN_FAILURE_ALERT_TEXT;
			validateLoginFailure(loginFailAlertMessageText, TextData.LOGIN_FAILURE_ALERT_TEXT);
		}
		else if(Element.isElementPresent(loginFailPuzzle, webDriver)) {
			loginFailStatus =  TextData.LOGIN_PUZZLE_TEXT;
			validateLoginFailure(loginFailPuzzleMessageText, TextData.LOGIN_PUZZLE_TEXT);
		}
		else if(Element.isElementPresent(loginFailImportantMessage, webDriver)) {
			loginFailStatus = TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
			validateLoginFailure(loginFailImportantMessageText, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
		}
		return loginFailStatus;
	}
	
	public void validateLoginFailure(By elementMessageText, String expectedText) {
		String messageText = FindElement.getWebElement(elementMessageText, webDriver).getText();
		Assert.assertEquals(messageText, expectedText);
	}
}