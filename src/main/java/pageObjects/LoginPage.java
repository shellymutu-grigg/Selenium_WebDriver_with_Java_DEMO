package pageObjects;

import java.io.FileInputStream;
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
	
	public void navigateToLanding() throws InterruptedException, IOException {
		if(yourAccountLink != null) {
			yourAccountLink.click();
		}
		accountLink.click();
		
		waitForElementToAppear(signInPageBy);
		helperFunctions.validatePageTitle("signInTitle", webDriver.getTitle());
	}
	
	public void enterUserDetails(String email, String password) throws InterruptedException {
		userEmail.sendKeys(email);
		continueButton.click();
		
		waitForElementToAppear(userEmailBy);
		
		userPassword.sendKeys(password);
		login.click();
	}
	public void loginApplicationFail(String email, String password) throws InterruptedException, IOException {

		navigateToLanding();
		enterUserDetails(email, password);
		
		if(helperFunctions.isElementPresent(loginFailAlert, webDriver)) {
			validateLoginFailure(loginFailAlert, loginFailAlertMessageText, "loginFailAlertMessageText", "signInTitle");
		}
		else if(helperFunctions.isElementPresent(loginFailPuzzle, webDriver)) {
			validateLoginFailure(loginFailPuzzle, loginFailPuzzleMessageText, "loginPuzzleMessageText", "loginFailTitle");
		}
		else if(helperFunctions.isElementPresent(loginFailImportantMessage, webDriver)) {
			validateLoginFailure(loginFailImportantMessage, loginFailImportantMessageText, "loginFailImportantMessageText", "signInTitle");
		}
	}
	
	public void login(String email, String password) throws InterruptedException, IOException {
		navigateToLanding();
		enterUserDetails(email, password);
		
		waitForElementToAppear(userPasswordBy);
	}
	
	public void validateMessageText(By text, String property) throws IOException {
		WebElement message = webDriver.findElement(text);
		String messageText = message.getText();
		String expectedMessage = helperFunctions.getGlobalProperty(property);
		Assert.assertEquals(messageText, expectedMessage);
	}
	
	public void validateLoginFailure(By elementHandle, By elementMessageText, String globalProperty, String pageTitle) throws InterruptedException, IOException {
		waitForElementToAppear(elementHandle);
		validateMessageText(elementMessageText, globalProperty);
		
		helperFunctions.validatePageTitle(pageTitle, webDriver.getTitle());
	}
	
	public void navigateToURL() throws IOException, InterruptedException {
		// Read in properties file
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//resources//globalData.properties");
		properties.load(fileInputStream);
		String url = System.getProperty("url") != null ? System.getProperty("url")
		: properties.getProperty("url");
		webDriver.get(url);
		
		if(helperFunctions.isElementPresent(landingPageBy, webDriver)) {
			waitForElementToAppear(landingPageBy);
			helperFunctions.validatePageTitle("landingTitle", webDriver.getTitle());
		}
		else if(helperFunctions.isElementPresent(landingPageAltBy, webDriver)) {
			waitForElementToAppear(landingPageAltBy);
			helperFunctions.validatePageTitle("landingTitle", webDriver.getTitle());
		}
	}

	public SearchPage loginApplicationSuccess(String email, String password) throws InterruptedException, IOException {
		
		login(email, password);
		
		helperFunctions.validatePageTitle("loggedInLandingTitle", webDriver.getTitle());
		
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}
	
	public LogoutPage loginApplication(String email, String password) throws InterruptedException, IOException {
			
		login(email, password);
		
		helperFunctions.validatePageTitle("loggedInLandingTitle", webDriver.getTitle());
		
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
	
	public OrdersPage loginApplicationOrders(String email, String password) throws InterruptedException, IOException {
		
		login(email, password);
		
		helperFunctions.validatePageTitle("loggedInLandingTitle", webDriver.getTitle());
		
		OrdersPage ordersPage = new OrdersPage(webDriver);
		return ordersPage;
	}
}