package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.PageFactory;

import functions.HelperFunctions;
import webElement.FindElement;

public class LogoutPage extends HelperFunctions {
	
	WebDriver webDriver;

	Actions actions;
	
	By accountLinkBy = By.xpath("//*[contains(text(), 'Account & Lists')]");
	By logoutLinkBy = By.id("nav-item-signout");
	By userEmailBy = By.id("ap_email");

	public LogoutPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		actions = new Actions(webDriver);
	}
	
	public void openAccountMenu() {
		try{
	        actions.moveToElement(FindElement.getWebElement(accountLinkBy, webDriver)).perform();
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
	}
	
	public void logout() {
		try{
			actions.moveToElement(FindElement.getWebElement(logoutLinkBy, webDriver)).perform();
			FindElement.getWebElement(logoutLinkBy, webDriver).click();
			FindElement.getWebElement(userEmailBy, webDriver);
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
	}
}
