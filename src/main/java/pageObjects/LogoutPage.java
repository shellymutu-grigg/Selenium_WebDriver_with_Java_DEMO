package pageObjects;

import data.TextData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import webElement.Element;

public class LogoutPage {
	
	WebDriver webDriver;

	Actions actions;
	
	By accountLinkBy = By.xpath("//*[contains(text(), '" + TextData.ACCOUNT_MENU_LINK_TEXT + "')]");
	By logoutLinkBy = By.id("nav-item-signout");

	public LogoutPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		actions = new Actions(webDriver);
	}
	
	public void openAccountMenu() {
		try{
	        actions.moveToElement(Element.getElement(accountLinkBy, webDriver)).perform();
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
			actions.moveToElement(Element.getElement(logoutLinkBy, webDriver)).perform();
			Element.getElement(logoutLinkBy, webDriver).click();
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
	}
}
