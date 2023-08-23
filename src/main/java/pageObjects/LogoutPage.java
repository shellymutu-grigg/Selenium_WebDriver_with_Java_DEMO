package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import functions.HelperFunctions;

public class LogoutPage extends HelperFunctions {
	
	WebDriver webDriver;

	Actions actions;
	
	By accountLinkBy = By.xpath("//span[normalize-space()='Account & Lists']");
	By accountMenuBy = By.id("nav-al-your-account");
	By logoutLinkBy = By.id("nav-item-signout");
	By loggedOutBy = By.xpath("//h1[normalize-space()='Sign in']");

	public LogoutPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		actions = new Actions(webDriver);
	}
	
	public void openAccountMenu() {
		WebElement logoutMenu = webDriver.findElement(accountLinkBy);
		actions.moveToElement(logoutMenu).perform();
		
		waitForElementToAppear(accountMenuBy, webDriver);
	}
	
	public void logout() {
		WebElement signOutLink = webDriver.findElement(logoutLinkBy);
		actions.moveToElement(signOutLink).perform();
		signOutLink.click();
		
		waitForElementToAppear(loggedOutBy, webDriver);
	}
}
