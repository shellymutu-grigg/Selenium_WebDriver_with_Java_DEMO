package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;
import abstractComponents.HelperFunctions;
import data.PageTitleData;

public class LogoutPage extends AbstractComponents {
	
	WebDriver webDriver;

	HelperFunctions helperFunctions = new HelperFunctions();
	
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
	
	public void openAccountMenu() throws InterruptedException, IOException {
		WebElement logoutMenu = webDriver.findElement(accountLinkBy);
		actions.moveToElement(logoutMenu).perform();
		
		waitForElementToAppear(accountMenuBy);
	}
	
	public void logout() throws InterruptedException, IOException {
		WebElement signOutLink = webDriver.findElement(logoutLinkBy);
		actions.moveToElement(signOutLink).perform();
		signOutLink.click();
		
		waitForElementToAppear(loggedOutBy);
		helperFunctions.validatePageTitle(PageTitleData.SIGN_IN_PAGE_TITLE, webDriver.getTitle());
	}
}
