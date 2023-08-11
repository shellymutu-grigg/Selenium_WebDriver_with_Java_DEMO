package pageObjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;
import abstractComponents.HelperFunctions;

public class LoginPage extends AbstractComponents {

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

	public LoginPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public SearchPage loginApplication(String email, String password) throws InterruptedException, IOException {
		
		if(yourAccountLink != null) {
			yourAccountLink.click();
			waitForElementToAppear(yourAccountLinkBy);
		}
		accountLink.click();
		
		waitForElementToAppear(signInPageBy);
		helperFunctions.validatePageTitle("signInTitle", webDriver.getTitle());
		
		userEmail.sendKeys(email);
		continueButton.click();
		
		waitForElementToAppear(userEmailBy);
		
		userPassword.sendKeys(password);
		login.click();
		
		waitForElementToAppear(userPasswordBy);
		helperFunctions.validatePageTitle("loggedInLandingTitle", webDriver.getTitle());
		
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}


	public void navigate() throws IOException, InterruptedException {
		// Read in properties file
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//resources//globalData.properties");
		properties.load(fileInputStream);
		String url = System.getProperty("url") != null ? System.getProperty("url")
		: properties.getProperty("url");
		webDriver.get(url);
		
		waitForElementToAppear(landingPageBy);
		helperFunctions.validatePageTitle("landingTitle", webDriver.getTitle());
	}
}