package pageObjects;

import data.ConfigData;
import data.LocalStore;
import functions.Get;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestListener;

import data.PageTitleData;
import data.TextData;
import webElement.Element;

public class LoginPage implements ITestListener{
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
	
	public LogoutPage initialiseLogoutPage() {
		LogoutPage logoutPage = new LogoutPage();
		return logoutPage;
	}
	
	public void navigateToURL() {
		if(Element.getElement(landingPageBy) != null) {
			Element.getElement(landingPageBy);
		}
		else if(Element.getElement(landingPageAltBy) != null) {
			Element.getElement(landingPageAltBy);
			if(Element.getElement(yourAccountLinkBy) != null) {
				LocalStore.setObject(ConfigData.SYSTEM_PROPERTY_YOUR_ACCOUNT, TextData.YOUR_ACCOUNT_TEXT);
				Element.getElement(yourAccountLinkBy).click();
			}
		}
	}
	
	public SearchPage generateSearchPage() {
		Element.getElement(accountLinkBy);
		SearchPage searchPage = new SearchPage();
		return searchPage;
	}
	
	public OrdersPage generateOrdersPage() {
		Element.getElement(accountLinkBy);
		OrdersPage ordersPage = new OrdersPage();
		return ordersPage;
	}	
	
	public void enterUserEmail(String email) {
		Element.getElement(userEmailBy).sendKeys(email);
		Element.getElement(continueButtonBy).click();
	}
	
	public void enterUserPassword(String password) {
		Element.getElement(userPasswordBy).sendKeys(password);
		Element.getElement(loginButtonBy).click();
	}
	
	public boolean checkIfSignedIn() {
		WebDriver webDriver = (WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER);
		if(webDriver.getTitle().contains(PageTitleData.LANDING_PAGE_TITLE)) {
			return true;
		}
		else return false;
	}
	
	public void navigateToLanding() {
		Element.getElement(signInLinkBy).click();
	}
	
	public void checkForPreviousLoginFailure() {
		WebDriver webDriver = (WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER);
		String accountListText = Element.getElement(accountMenuBy).getText();
		if(!accountListText.contains(TextData.LOGGED_OUT_TEXT)) {
			initialiseLogoutPage().openAccountMenu();
			initialiseLogoutPage().logout();
			webDriver.get(Get.url());
		}
	}
	
	public String findLoginFailureText() {
		String failureText = "";
		if(Element.isPresent(loginFailAlert)) {
			failureText = TextData.LOGIN_FAILURE_ALERT_TEXT;
		}
		else if(Element.isPresent(loginFailPuzzle)) {
			failureText =  TextData.LOGIN_PUZZLE_TEXT;
		}
		else if(Element.isPresent(loginFailImportantMessage)) {
			failureText =  TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
		}
		return failureText;
	}
	
	public String loginFail() {
		String loginFailStatus = "";
		if(Element.isPresent(loginFailAlert)) {
			loginFailStatus = TextData.LOGIN_FAILURE_ALERT_TEXT;
			validateLoginFailure(loginFailAlertMessageText, TextData.LOGIN_FAILURE_ALERT_TEXT);
		}
		else if(Element.isPresent(loginFailPuzzle)) {
			loginFailStatus =  TextData.LOGIN_PUZZLE_TEXT;
			validateLoginFailure(loginFailPuzzleMessageText, TextData.LOGIN_PUZZLE_TEXT);
		}
		else if(Element.isPresent(loginFailImportantMessage)) {
			loginFailStatus = TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT;
			validateLoginFailure(loginFailImportantMessageText, TextData.LOGIN_FAILURE_IMPORANT_MESSAGE_TEXT);
		}
		return loginFailStatus;
	}
	
	public void validateLoginFailure(By elementMessageText, String expectedText) {
		String messageText = Element.getElement(elementMessageText).getText();
		Assert.assertEquals(messageText, expectedText);
	}
}