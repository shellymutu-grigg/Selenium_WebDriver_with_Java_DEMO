package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;
import abstractComponents.HelperFunctions;

public class LogoutPage extends AbstractComponents {
	
	WebDriver webDriver;

	HelperFunctions helperFunctions = new HelperFunctions();
	
	Actions actions;
	
	// PageFactory Pattern
	@FindBy(xpath="//span[normalize-space()='Account & Lists']")
	WebElement accountLink;
		
	By accountLinkBy = By.xpath("//span[normalize-space()='Account & Lists']");
	By accountMenuBy = By.id("nav-al-your-account");
	By loggedOutBy = By.xpath("//h1[normalize-space()='Sign in']");

	public LogoutPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		 actions = new Actions(webDriver);
	}
	
	public void openLogoutMenu() throws InterruptedException, IOException {
		WebElement logoutMenu = webDriver.findElement(accountLinkBy);
		actions.moveToElement(logoutMenu).perform();
		
		waitForElementToAppear(accountMenuBy);
		helperFunctions.validatePageTitle("cartTitle", webDriver.getTitle());
	}
	
	public void logout() throws InterruptedException, IOException {
		WebElement signOutLink = webDriver.findElement(By.id("nav-item-signout"));
		actions.moveToElement(signOutLink).perform();
		signOutLink.click();
		
		waitForElementToAppear(loggedOutBy);
		helperFunctions.validatePageTitle("loginTitle", webDriver.getTitle());
	}
}
