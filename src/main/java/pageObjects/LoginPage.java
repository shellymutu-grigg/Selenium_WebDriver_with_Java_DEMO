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

public class LoginPage extends AbstractComponents {

	WebDriver webDriver;

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

	public LoginPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public SearchPage loginApplication(String email, String password) throws InterruptedException {
		
		if(yourAccountLink != null) {
			yourAccountLink.click();
			waitForElementToAppear(By.cssSelector(".ya-personalized"));
		}
		accountLink.click();
		
		waitForElementToAppear(By.cssSelector(".a-spacing-small"));
		
		userEmail.sendKeys(email);
		continueButton.click();
		
		waitForElementToAppear(By.cssSelector(".a-form-label"));
		
		userPassword.sendKeys(password);
		login.click();
		SearchPage searchPage = new SearchPage(webDriver);
		return searchPage;
	}


	public void navigate() throws IOException {
		// Read in properties file
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//resources//globalData.properties");
		properties.load(fileInputStream);
		String url = System.getProperty("url") != null ? System.getProperty("url")
		: properties.getProperty("url");
		webDriver.get(url);
	}
}