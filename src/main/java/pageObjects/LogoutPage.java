package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import webElement.FindElement;

public class LogoutPage {
	
	WebDriver webDriver;

	Actions actions;
	
	By accountLinkBy = By.xpath("//*[contains(text(), 'Account & Lists')]");
	By logoutLinkBy = By.id("nav-item-signout");

	public LogoutPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		actions = new Actions(webDriver);
	}
	
	public void openAccountMenu() {
		try{
	        actions.moveToElement(FindElement.getWebElement(accountLinkBy, webDriver)).perform();
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void logout() {
		try{
			actions.moveToElement(FindElement.getWebElement(logoutLinkBy, webDriver)).perform();
			FindElement.getWebElement(logoutLinkBy, webDriver).click();
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
	}
}
