package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;

public class LogoutPage extends AbstractComponents {
	
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
		
		try {
			waitForElementToAppear(accountMenuBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logout() {
		WebElement signOutLink = webDriver.findElement(logoutLinkBy);
		actions.moveToElement(signOutLink).perform();
		signOutLink.click();
		
		try {
			waitForElementToAppear(loggedOutBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
