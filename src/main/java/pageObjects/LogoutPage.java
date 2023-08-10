package pageObjects;

import java.util.List;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;

public class LogoutPage extends AbstractComponents {
	
	WebDriver webDriver;

	Actions actions;
	
	// PageFactory Pattern
	@FindBy(xpath="//span[normalize-space()='Account & Lists']")
	WebElement accountLink;
		
	By accountLinkBy = By.xpath("//span[normalize-space()='Account & Lists']");

	public LogoutPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		 actions = new Actions(webDriver);
	}
	
	public void openLogoutMenu() {
		WebElement logoutMenu = webDriver.findElement(accountLinkBy);
		actions.moveToElement(logoutMenu).perform();
	}
	
	public void logout() {
		WebElement signOutLink = webDriver.findElement(By.id("nav-item-signout"));
		actions.moveToElement(signOutLink).perform();
		signOutLink.click();
		webDriver.quit();
	}
}
