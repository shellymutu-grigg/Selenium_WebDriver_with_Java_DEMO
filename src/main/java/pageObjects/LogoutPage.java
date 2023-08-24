package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.PageFactory;

import functions.HelperFunctions;

public class LogoutPage extends HelperFunctions {
	
	WebDriver webDriver;

	Actions actions;
	
	By accountLinkBy = By.xpath("//*[contains(text(), 'Account & Lists')]");
	By accountMenuBy = By.cssSelector(".nav-action-signin-button");
	By logoutLinkBy = By.id("nav-item-signout");
	By userEmailBy = By.id("ap_email");

	public LogoutPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		actions = new Actions(webDriver);
	}
	
	public void openAccountMenu() {
		WebElement logoutMenu = webDriver.findElement(accountLinkBy);
		
		try{
	        actions.moveToElement(logoutMenu).perform();
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
	}
	
	public void logout() {
		waitForElementToAppear(logoutLinkBy, webDriver);
		WebElement signOutLink = webDriver.findElement(logoutLinkBy);
		try{
			actions.moveToElement(signOutLink).perform();
			signOutLink.click();
			waitForElementToAppear(userEmailBy, webDriver);
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
	}
	
	public void waitForLogoutPage() {
		waitForElementToAppear(userEmailBy, webDriver);
	}
}
